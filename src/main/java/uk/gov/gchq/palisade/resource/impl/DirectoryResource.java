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

package uk.gov.gchq.palisade.resource.impl;

import uk.gov.gchq.palisade.Generated;
import uk.gov.gchq.palisade.ToStringBuilder;
import uk.gov.gchq.palisade.resource.AbstractResource;
import uk.gov.gchq.palisade.resource.ChildResource;
import uk.gov.gchq.palisade.resource.ParentResource;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class DirectoryResource extends AbstractResource implements ChildResource, ParentResource {

    private ParentResource parent;

    public DirectoryResource() {
        //no-args constructor needed for serialization only
    }

    @Override
    public DirectoryResource id(final String id) {
        if (id.endsWith("/")) {
            return (DirectoryResource) super.id(id);
        } else {
            return (DirectoryResource) super.id(id + "/");
        }
    }

    public DirectoryResource parent(final ParentResource parent) {
        this.setParent(parent);
        return this;
    }

    @Override
    @Generated
    public ParentResource getParent() {
        return parent;
    }

    @Override
    @Generated
    public void setParent(final ParentResource parent) {
        requireNonNull(parent);
        this.parent = parent;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DirectoryResource)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        final DirectoryResource that = (DirectoryResource) o;
        return Objects.equals(parent, that.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), parent);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("parent", parent)
                .toString();
    }
}
