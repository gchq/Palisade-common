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

import uk.gov.gchq.palisade.resource.LeafResource;

/**
 * This class defines the top level of persistence prepopulation.
 * <p>
 * The only requirement is that there is a build method to construct a LeafResource
 */
@JsonPropertyOrder(value = {"class"}, alphabetic = true)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = As.EXISTING_PROPERTY,
        property = "class"
)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public interface ResourcePrepopulationFactory {

    /**
     * Creates a {@link LeafResource} using the data within an implementation of the {@link ResourcePrepopulationFactory}.
     *
     * @return the {@link LeafResource} that has been created.
     */
    LeafResource build();

    @JsonGetter("class")
    default String _getClass() {
        return getClass().getName();
    }

    @JsonSetter("class")
    default void _setClass(final String className) {
        // do nothing.
    }
}