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

import uk.gov.gchq.palisade.resource.LeafResource;
import uk.gov.gchq.palisade.resource.ParentResource;
import uk.gov.gchq.palisade.resource.Resource;
import uk.gov.gchq.palisade.resource.impl.DirectoryResource;
import uk.gov.gchq.palisade.resource.impl.FileResource;
import uk.gov.gchq.palisade.resource.impl.SystemResource;
import uk.gov.gchq.palisade.service.ConnectionDetail;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * Provides a common set of utilities for constructing resources with all parents
 * automatically constructed recursively. This primarily targets filesystem-like
 * resources (Files, Directories etc.)
 * Internally, the resourceId is converted to a URI.
 * Can produce any of the following output types:
 * - {@link FileResource}
 * - {@link DirectoryResource}
 * - {@link SystemResource}
 * Any parents automatically constructed will also be from this collection.
 * If another method of creating a resource is required (i.e. directly using strings)
 * there is no guarantee that this can correctly resolve parents. Instead use the
 * methods provided by the appropriate resource impl.
 */
public class ResourceBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceBuilder.class);
    private static final URI ROOT;

    static {
        File userDir = new File(System.getProperty("user.dir"));
        URI root;
        try {
            root = userDir.getCanonicalFile().toURI();
        } catch (IOException ex) {
            LOGGER.error("ResourceBuilder threw an error when getting the CanonicalFile {}", ex);
            root = userDir.getAbsoluteFile().toURI();
        }
        ROOT = root;
    }

    private ResourceBuilder() {
        // empty private constructor
    }

    private enum Scheme {
        FILE,
        HDFS
    }

    public static boolean canCreate(final URI uri) {
        try {
            Scheme.valueOf(uri.getScheme()); // Or throw
            return uri.isAbsolute();
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    // Create a leafResource from a uri, connectionDetail, type, serialisedFormat and attribute map
    // Throw IllegalArgumentException if unsupported scheme
    // Throw ClassCastException if uri did not point to a leaf resource
    public static LeafResource create(final URI uri, final ConnectionDetail connectionDetail, final String type, final String serialisedFormat, final Map<String, String> attributes) {
        return ((LeafResource) create(uri, attributes))
                .connectionDetail(connectionDetail)
                .type(type)
                .serialisedFormat(serialisedFormat);
    }

    // Create a resource from a uri and attribute map
    // Throw IllegalArgumentException if unsupported scheme
    public static Resource create(final URI uri, final Map<String, String> attributes) {
        // If passed relative paths, we can resolve them against the user.dir system property
        URI absolute = uri.isAbsolute() ? uri : ROOT.resolve(uri);
        // The hostname is all in the connectionDetail, so we never have a case of file://hostname/some/uri
        // A lot of this is trying to normalize file:///some/uri (file://<no-hostname>/some/uri) to file:/some/uri
        URI normal = UriBuilder.create(absolute)
                .withoutScheme()
                .withoutAuthority()
                .withoutPath()
                .withoutQuery()
                .withoutFragment();

        // This should be assigning the attributes map to the returned object, once resources support attribute maps

        switch (Scheme.valueOf(normal.getScheme().toUpperCase())) {
            // Both file:/ and hdfs:/ schema produce filesystem-like structures
            case FILE:
            case HDFS:
                return filesystemSchema(normal);
            default:
                throw new IllegalArgumentException("No such implementation for uri scheme " + normal.getScheme());
        }
    }

    // Create a resource from a uri
    // Default to an empty attribute map
    // Throw IllegalArgumentException if unsupported scheme
    public static Resource create(final URI uri) {
        return create(uri, Collections.emptyMap());
    }

    // Create a resource from a uri string and attribute map
    // Throw IllegalArgumentException if invalid uri string or unsupported scheme
    public static Resource create(final String uriString, final Map<String, String> attributes) {
        try {
            return create(new URI(uriString), attributes);
        } catch (URISyntaxException ex) {
            LOGGER.error("Resource create threw an error when creating a URI {}", ex);
            throw new IllegalArgumentException("URISyntaxException converting string '" + uriString + "' to uri");
        }
    }

    // Create a resource from a uri string
    // Default to an empty attribute map
    // Throw IllegalArgumentException if invalid uri string or unsupported scheme
    public static Resource create(final String uriString) {
        return create(uriString, Collections.emptyMap());
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
