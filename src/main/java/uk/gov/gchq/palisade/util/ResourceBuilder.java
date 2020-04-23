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

package uk.gov.gchq.palisade.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.gov.gchq.palisade.resource.ParentResource;
import uk.gov.gchq.palisade.resource.Resource;
import uk.gov.gchq.palisade.resource.impl.DirectoryResource;
import uk.gov.gchq.palisade.resource.impl.FileResource;
import uk.gov.gchq.palisade.resource.impl.SystemResource;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Provides a common set of utilities for constructing resources with all parents
 * automatically constructed recursively. This primarily targets filesystem-like
 * resources (Files, Directories etc.)
 * Internally, the resourceId is converted to a URI.
 *
 * Can produce any of the following output types:
 * - {@link FileResource}
 * - {@link DirectoryResource}
 * - {@link SystemResource}
 * Any parents automatically constructed will also be from this collection.
 *
 * If another method of creating a resource is required (i.e. directly using strings)
 * there is no guarantee that this can correctly resolve parents. Instead use the
 * methods provided by the appropriate resource impl.
 */
public class ResourceBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceBuilder.class);

    private ResourceBuilder() {
        // empty private constructor
    }

    private enum Scheme {
        file,
        hdfs
    }

    public static boolean canCreate(final URI uri) {
        try {
            Scheme.valueOf(uri.getScheme()); // Or throw
            return uri.isAbsolute();
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    public static Resource create(final URI uri) {
        // The hostname is all in the connectionDetail still, so we never have file://hostname/some/uri
        // A lot of this is trying to normalize file:///some/uri (file://<no-hostname>/some/uri) to file:/some/uri
        URI normalize = UriBuilder.create(uri)
                .withoutScheme()
                .withoutAuthority()
                .withoutPath()
                .withoutQuery()
                .withoutFragment();
        if (!normalize.isAbsolute()) {
            throw new IllegalArgumentException("No support for non-absolute uri " + uri);
        }
        switch (Scheme.valueOf(normalize.getScheme())) {
            case file:
            case hdfs:
                return filesystemSchema(normalize);
            default:
                throw new IllegalArgumentException("No such implementation for uri scheme " + normalize.getScheme());
        }
    }

    public static Resource create(final String uriString) {
        try {
            return create(new URI(uriString));
        } catch (URISyntaxException ex) {
            throw new IllegalArgumentException("URISyntaxException converting string '" + uriString + "' to uri");
        }
    }

    private static FileResource fileResource(final URI uri) {
        return new FileResource()
                .id(uri.normalize().toString())
                .parent((ParentResource) filesystemSchema(uri.resolve(".")));
    }

    private static DirectoryResource directoryResource(final URI uri) {
        return new DirectoryResource()
                .id(uri.normalize().toString())
                .parent((ParentResource) filesystemSchema(uri.resolve("..")));
    }

    private static SystemResource systemResource(final URI uri) {
        return new SystemResource()
                .id(uri.normalize().toString());
    }

    private static Resource filesystemSchema(final URI uri) {
        if (!uri.resolve(".").equals(uri)) {
            return fileResource(uri);
        } else if (Objects.nonNull(Path.of(uri).getParent())) {
            return directoryResource(uri);
        } else {
            return systemResource(uri);
        }
    }
}
