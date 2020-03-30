/*
 * Copyright 2019 Crown Copyright
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

package uk.gov.gchq.palisade;


import java.util.Objects;
import java.util.StringJoiner;

import static java.util.Objects.requireNonNull;

/**
 * This class contains the information that makes a request unique.
 */
public class RequestId {

    private String id;

    public RequestId() {
        //no-args constructor needed for serialization only
    }

    public RequestId id(final String id) {
        requireNonNull(id, "The id cannot be set to null.");
        this.id = id;
        return this;
    }

    @Generated
    public String getId() {
        return id;
    }

    @Generated
    public void setId(final String id) {
        requireNonNull(id);
        this.id = id;
    }

    @Override
    @Generated
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestId requestId = (RequestId) o;
        return id.equals(requestId.id);
    }

    @Override
    @Generated
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    @Generated
    public String toString() {
        return new StringJoiner(", ", RequestId.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .toString();
    }
}
