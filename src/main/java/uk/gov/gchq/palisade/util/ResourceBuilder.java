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

import uk.gov.gchq.palisade.resource.ChildResource;
import uk.gov.gchq.palisade.resource.ParentResource;
import uk.gov.gchq.palisade.resource.impl.DirectoryResource;
import uk.gov.gchq.palisade.resource.impl.FileResource;
import uk.gov.gchq.palisade.resource.impl.SystemResource;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

public class ResourceBuilder {

    private ResourceBuilder() {
        // empty private constructor
    }

    private static String normalize(final String resourceId) {
        try {
            URI uri = new URI(resourceId);
            if (Objects.isNull(uri.getScheme())) {
                uri = new URI("file://" + resourceId);
            }
            return Path.of(uri).toFile().getCanonicalPath();
        } catch (URISyntaxException | IOException ex) {
            return resourceId;
        }
    }

    public static FileResource fileResource(final String fileUri) {
        String normalized = normalize(fileUri);
        FileResource fileResource = new FileResource().id(normalized);
        return fileResource.parent(parentResource(fileResource));
    }

    public static DirectoryResource directoryResource(final String dirUri) {
        String normalized = normalize(dirUri);
        DirectoryResource directoryResource = new DirectoryResource().id(normalized);
        return directoryResource.parent(parentResource(directoryResource));
    }

    public static SystemResource systemResource(final String sysName) {
        String normalized = normalize(sysName);
        return new SystemResource().id(normalized);
    }

    private static ParentResource parentResource(final ChildResource childResource) {
        try {
            Optional<Path> optionalPath = Optional.ofNullable(Path.of(childResource.getId()).getParent());
            ParentResource parentResource = optionalPath
                    .map(parentPath -> (ParentResource) directoryResource(parentPath.toString()))
                    .orElse(systemResource(""));
            if (parentResource instanceof ChildResource) {
                ChildResource intermediateParent = (ChildResource) parentResource;
                intermediateParent.setParent(parentResource(intermediateParent));
            }
            return parentResource;
        } catch (Exception ex) {
            return systemResource("");
        }
    }
}
