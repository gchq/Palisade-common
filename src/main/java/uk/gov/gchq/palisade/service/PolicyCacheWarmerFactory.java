/*
 * Copyright 2020 Crown Copyright
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

package uk.gov.gchq.palisade.service;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import uk.gov.gchq.palisade.resource.Resource;
import uk.gov.gchq.palisade.service.request.Policy;

import java.util.List;
import java.util.Map.Entry;

/**
 * This class defines the top level of the cache warming.
 * <p>
 * The only requirement is that there is a warm method, used to add data into the relevant cache
 */
@JsonPropertyOrder(value = {"class"}, alphabetic = true)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = As.EXISTING_PROPERTY,
        property = "class"
)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public interface PolicyCacheWarmerFactory {

    /**
     * Creates a {@link Policy} using the data within an implementation of the {@link PolicyCacheWarmerFactory}.
     *
     * @param users     a {@link List} of {@link UserCacheWarmerFactory} implementations
     * @return          an {@link Entry} value that consists of a {@link Resource} and the created {@link Policy}.
     */
    Entry<Resource, Policy> policyWarm(List<? extends UserCacheWarmerFactory> users);

    /**
     * Creates a {@link Resource} that will have a policy applied to it.
     *
     * @return the {@link Resource} that has been created.
     */
    Resource createResource();

    @JsonGetter("class")
    default String _getClass() {
        return getClass().getName();
    }

    @JsonSetter("class")
    default void _setClass(final String className) {
        // do nothing.
    }
}
