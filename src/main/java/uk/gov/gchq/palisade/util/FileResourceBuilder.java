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

package uk.gov.gchq.palisade.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.gov.gchq.palisade.resource.ParentResource;
import uk.gov.gchq.palisade.resource.Resource;
import uk.gov.gchq.palisade.resource.impl.DirectoryResource;
import uk.gov.gchq.palisade.resource.impl.FileResource;
import uk.gov.gchq.palisade.resource.impl.SystemResource;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
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
 * there is no guarantee that this can correctly resolve parents. Instead, use the
 * methods provided by the appropriate resource impl.
 */
public class FileResourceBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileResourceBuilder.class);
    private static final URI ROOT;

    private FileResourceBuilder() {
        // Empty Constructor
    }

    static {
        File userDir = new File(System.getProperty("user.dir"));
        URI root;
        try {
            root = userDir.getCanonicalFile().toURI();
        } catch (IOException ex) {
            LOGGER.error("Failed to get canonical file for {}", userDir, ex);
            root = userDir.getAbsoluteFile().toURI();
        }
        ROOT = root;

        ResourceBuilder.registerBuilder("file", FileResourceBuilder::filesystemSchema);
        ResourceBuilder.registerBuilder("hdfs", FileResourceBuilder::filesystemSchema);
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
        // If passed relative paths, we can resolve them against the user.dir system property
        URI absolute;
        if (uri.isAbsolute()) {
            absolute = uri;
        } else {
            absolute = ROOT.resolve(uri);
        }
        // Decide whether a File, Directory or System resource
        if (!absolute.resolve(".").equals(absolute)) {
            return fileResource(uri);
        } else if (Objects.nonNull(Path.of(absolute).getParent())) {
            return directoryResource(absolute);
        } else {
            return systemResource(absolute);
        }
    }
}
