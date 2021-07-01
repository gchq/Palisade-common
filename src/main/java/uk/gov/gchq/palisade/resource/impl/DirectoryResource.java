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

package uk.gov.gchq.palisade.resource.impl;

import uk.gov.gchq.palisade.resource.AbstractResource;
import uk.gov.gchq.palisade.resource.ChildResource;
import uk.gov.gchq.palisade.resource.ParentResource;
import uk.gov.gchq.palisade.util.FileResourceBuilder;

import java.net.URI;

/**
 * A DirectoryResource is a Palisade representation of a directory and can have Children and Parent resources
 * {@code eg. "file:/dev/Palisade/pom.xml" = System "/" -> Directory "/dev/" -> Directory "/dev/Palisade/" -> File "/dev/Palisade/pom.xml" }
 */
public class DirectoryResource extends AbstractResource implements ChildResource, ParentResource {
    private static final long serialVersionUID = 1L;

    public DirectoryResource() {
        //no-args constructor needed for serialization only
    }

    @Override
    public ParentResource getParent() {
        return (ParentResource) new FileResourceBuilder().build(URI.create(id).resolve(".."));
    }

    @Override
    public DirectoryResource id(final String id) {
        if (id.endsWith("/")) {
            return (DirectoryResource) super.id(id);
        } else {
            return (DirectoryResource) super.id(id + "/");
        }
    }

}
