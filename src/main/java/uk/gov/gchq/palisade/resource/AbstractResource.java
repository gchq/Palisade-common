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

package uk.gov.gchq.palisade.resource;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import uk.gov.gchq.palisade.Generated;
import uk.gov.gchq.palisade.ToStringBuilder;

import java.util.Comparator;

import static java.util.Objects.requireNonNull;

public abstract class AbstractResource implements Resource {

    private static Comparator<Resource> comp = Comparator.comparing(Resource::getId);
    protected String id;

    public AbstractResource() {
    }

    public AbstractResource id(final String id) {
        requireNonNull(id, "The ID of a resource cannot be set to null.");
        this.id = id;
        return this;
    }

    @Override
    @Generated
    public String getId() {
        return id;
    }

    @Override
    @Generated
    public void setId(final String id) {
        requireNonNull(id);
        this.id = id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final AbstractResource that = (AbstractResource) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(29, 31)
                .append(id)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .toString();
    }


    @Override
    public int compareTo(final Resource o) {
        return comp.compare(this, o);
    }
}
