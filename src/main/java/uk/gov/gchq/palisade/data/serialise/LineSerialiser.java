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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

/**
 * Standard serialiser used to serialise and deserialise file contents.
 *
 * @param <T> the domain object type
 */
public abstract class LineSerialiser<T> implements Serialiser<T> {
    public static final Charset CHARSET = StandardCharsets.UTF_8;

    /**
     * Serialises an object to a string value.
     *
     * @param obj the object to be serialised
     * @return a string value of the object
     */
    public abstract String serialiseLine(final T obj);

    /**
     * Deserialises a String value to an object.
     *
     * @param line the string value to be deserialised
     * @return the object value of the string
     */
    public abstract T deserialiseLine(final String line);

    @Override
    public void serialise(final Stream<T> objects, final OutputStream output) {
        serialise(objects.iterator(), output);
    }

    /**
     * Serialises a {@link Iterator} of objects to an {@link OutputStream}. If {@code objects} is {@code null}, then
     * nothing will be written.
     *
     * @param itr the iterator of objects
     * @param output the output stream to write the serialised bytes to
     * @return the serialiser object
     */
    public Serialiser<T> serialise(final Iterator<T> itr, final OutputStream output) {
        requireNonNull(output, "output");
        if (nonNull(itr)) {
            try(PrintWriter printOut = new PrintWriter(new OutputStreamWriter(output, CHARSET));) {
                itr.forEachRemaining(item -> printOut.println(serialiseLine(item)));
            }
        }
        return this;
    }

    /**
     * Deserialises an {@link InputStream} into a {@link Stream} of objects.
     *
     * @param stream the input stream to deserialise
     * @return the stream of objects
     */
    @Override
    public Stream<T> deserialise(final InputStream stream) {
        if (isNull(stream)) {
            return Stream.empty();
        }
        return new BufferedReader(new InputStreamReader(stream, CHARSET))
                .lines()
                .map(this::deserialiseLine);
    }
}
