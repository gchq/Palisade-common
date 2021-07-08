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

package uk.gov.gchq.palisade.resource;

import java.util.Map;

/**
 * A leaf resource is the interface for any resource that can be read for data
 * and is not just part of the hierarchical resource structure.
 * A LeafResource is expected to have a type and a serialised format. The type is a way of grouping
 * data of the same structure. The serialised format is the format of the file, e.g CSV, Parquet.
 */

public interface LeafResource extends ChildResource {

    /**
     * The type of resource, used for grouping data of the same structure
     *
     * @param type the type of resource
     * @return a LeafResource with type attached
     */
    LeafResource type(final String type);

    /**
     * The format of the resource, e.g CSV, txt
     *
     * @param serialisedFormat the format of resource
     * @return a LeafResource with format attached
     */
    LeafResource serialisedFormat(final String serialisedFormat);

    /**
     * The service where the LeafResource is stored. Used by the Data Service to connect to the correct service
     *
     * @param connectionDetail the location of the LeafResource
     * @return a LeafResource with connectionDetail attached
     */
    LeafResource connectionDetail(final ConnectionDetail connectionDetail);

    /**
     * Any additional Attributes about the LeafResource
     *
     * @param attributes additional attributes or metadata about the LeafResource
     * @return a LeafResource with attributes attached
     */
    LeafResource attributes(final Map<String, String> attributes);

    String getType();

    String getSerialisedFormat();

    void setType(final String type);

    void setSerialisedFormat(final String serialisedFormat);

    ConnectionDetail getConnectionDetail();

    void setConnectionDetail(final ConnectionDetail connectionDetail);

    Map<String, String> getAttributes();

    void setAttributes(Map<String, String> attributes);
}
