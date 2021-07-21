/*
 * Copyright 2018-2021 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.gov.gchq.palisade.data.serialise;

import akka.NotUsed;
import akka.japi.Pair;
import akka.stream.Attributes;
import akka.stream.Materializer;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Keep;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import akka.stream.javadsl.StreamConverters;
import akka.util.ByteString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

/**
 * Serialisers are the part of Palisade that convert from a particular data serialised format to a stream of objects and back again.
 * The interface offers some static methods allowing dyamically creating a {@link Serialiser} instance from a resource type and format.
 * <p>
 * Also present are default implementations of the akka methods, which are backed by their Java stdlib alternatives.
 * These may suffice for some implementations, but others may wish to override these defaults with more performant custom implementations.
 *
 * @param <T> the domain object type
 */
public interface Serialiser<T> {
    Logger LOGGER = LoggerFactory.getLogger(Serialiser.class);

    /**
     * Try to create a serialiser instance from a serialiser class and domainClass name (from {@link Class#getName()})
     *
     * @param serialiserClass the serialiser class to construct an instance of
     * @param domainClass     the domainClass {@link T} for the {@code Serialiser<T>}
     * @param <T>             the domainClass of the serialiser (type of objects the serialiser accepts)
     * @return a new {@link Serialiser} of the given class and domainClass
     * @throws NoSuchMethodException     if no single-element constructor for the {@code Class<T>} type was found
     * @throws IllegalAccessException    if the constructor had an inaccessible access modifier (e.g. {@code private})
     * @throws InvocationTargetException if the constructor threw an exception, where the cause can be found with {@link InvocationTargetException#getTargetException()}
     * @throws InstantiationException    if the {@link Constructor#newInstance(Object...)} method fails for any other reason,
     *                                   e.g. the class represents an abstract class or interface that is not instantiable
     */
    // Cannot reasonably type this due to Java's generics and type erasure, suppress cast to Serialiser<T> on class reflection
    @SuppressWarnings({"unchecked"})
    static <T> Serialiser<T> create(Class<Serialiser<?>> serialiserClass, Class<T> domainClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<Serialiser<?>> constructor = serialiserClass.getDeclaredConstructor(Class.class);
        return (Serialiser<T>) constructor.newInstance(domainClass);
    }

    /**
     * Try to create a serialiser instance from a serialiser class and domainClass name (from {@link Class#getName()})
     *
     * @param serialiserClass the serialiser class to construct an instance of
     * @param domainClassName the class name of the domainClass {@link T} for the {@code Serialiser<T>}
     * @param <T>             the domainClass of the serialiser (type of objects the serialiser accepts)
     * @return {@link Optional#of(Object)} if the serialiser was constructed successfully, {@link Optional#empty()} otherwise and log the error that occurred.
     */
    // Suppress unchecked cast for domainClassName to Class<T> for some T
    // Suppress warnings for dynamic class loading from String domainClassName to Class domainClass
    @SuppressWarnings({"unchecked", "java:S2658"})
    static <T> Optional<Serialiser<T>> tryCreate(Class<Serialiser<?>> serialiserClass, String domainClassName) {
        try {
            return Optional.of(Serialiser.create(serialiserClass, (Class<T>) Class.forName(domainClassName)));
        } catch (Exception ex) {
            LOGGER.warn("Failed to construct serialiser for serialiserClass '{}' and domainClassName '{}'", serialiserClass, domainClassName, ex);
            return Optional.empty();
        }
    }

    /**
     * Convert a stream of objects into serialised bytes
     *
     * @param objects the incoming stream of objects to serialise
     * @return an outgoing stream of serialised bytes
     */
    InputStream serialise(final Stream<T> objects);

    /**
     * Convert a stream of objects into serialised bytes
     *
     * @return an akka {@link Flow} converting objects into their serialised {@link ByteString} counterparts
     */
    default Flow<T, ByteString, CompletionStage<NotUsed>> serialiseFlow() {
        return Flow.fromMaterializer((Materializer mat, Attributes attrs) -> {
            Flow<Stream<T>, InputStream, NotUsed> fn = Flow.fromFunction(this::serialise);
            Pair<Stream<T>, Sink<T, NotUsed>> pair = StreamConverters.<T>asJavaStream()
                    .preMaterialize(mat);
            Source<ByteString, NotUsed> src = Source.single(pair.first()).viaMat(fn, Keep.right())
                    .flatMapConcat(is -> StreamConverters.fromInputStream(() -> is));
            return Flow.fromSinkAndSource(pair.second(), src);
        });
    }

    /**
     * Convert a stream of bytes into deserialised objects
     *
     * @param stream the incoming stream of bytes to deserialise
     * @return an outgoing stream of objects
     */
    Stream<T> deserialise(final InputStream stream);

    /**
     * Convert a stream of bytes into deserialised objects
     *
     * @return an akka {@link Flow} converting {@link ByteString}s into their deserialised object counterparts
     */
    default Flow<ByteString, T, CompletionStage<NotUsed>> deserialiseFlow() {
        return Flow.fromMaterializer((Materializer mat, Attributes attrs) -> {
            Flow<InputStream, Stream<T>, NotUsed> fn = Flow.fromFunction(this::deserialise);
            Pair<InputStream, Sink<ByteString, NotUsed>> pair = StreamConverters.asInputStream()
                    .preMaterialize(mat);
            Source<T, NotUsed> src = Source.single(pair.first()).viaMat(fn, Keep.right())
                    .flatMapConcat(is -> StreamConverters.fromJavaStream(() -> is));
            return Flow.fromSinkAndSource(pair.second(), src);
        });
    }
}
