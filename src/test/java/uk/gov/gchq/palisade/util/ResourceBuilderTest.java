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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import uk.gov.gchq.palisade.resource.ChildResource;
import uk.gov.gchq.palisade.resource.Resource;
import uk.gov.gchq.palisade.resource.impl.DirectoryResource;
import uk.gov.gchq.palisade.resource.impl.FileResource;
import uk.gov.gchq.palisade.resource.impl.SystemResource;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.LinkedList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

@RunWith(JUnit4.class)
public class ResourceBuilderTest {

    @Test(expected = IllegalArgumentException.class)
    public void invalidSchemaThrowsException() throws URISyntaxException {
        // Given
        final URI invalidSchema = new URI("badschema:/path/to/resource");

        // Then
        assertFalse("The ResourceBuilder shouldn't be able to create a URI with a bad schema", ResourceBuilder.canCreate(invalidSchema));

        // When
        ResourceBuilder.create(invalidSchema);
        // Then throw exception
    }

    @Test
    public void shouldCreateSystemResource() {
        // A file schema uri for a system root should return a SystemResource
        // eg. "file:/" = System "/"

        // Given
        final File root = new File("/");

        // When
        SystemResource systemResource = (SystemResource) ResourceBuilder.create(root.toURI());

        // Then
        LinkedList<Resource> parents = getAllParents(systemResource);
        // System at the 'top'
        assertThat(parents.getFirst(), instanceOf(SystemResource.class));
        parents.removeFirst();
        // Nothing else
        parents.forEach(resource -> fail("parents should be empty so cannot create a system resource"));
    }

    @Test
    public void shouldCreateDirectoryResource() {
        // A file schema uri for a directory should return a DirectoryResource
        // The parents of this DirectoryResource should be zero or more DirectoryResources up-to a root SystemResource
        // eg. "file:/dev/Palisade-common/" = System "/" -> Directory "/dev/" -> Directory "/dev/Palisade-common/"

        // Given
        final File userDir = new File(System.getProperty("user.dir"));

        // When
        DirectoryResource directoryResource = (DirectoryResource) ResourceBuilder.create(userDir.toURI());

        // Then
        LinkedList<Resource> parents = getAllParents(directoryResource);
        // System at the 'top'
        assertThat(parents.getFirst(), instanceOf(SystemResource.class));
        parents.removeFirst();
        // Directories at the 'bottom'
        parents.forEach(resource -> assertThat(resource, instanceOf(DirectoryResource.class)));
    }

    @Test
    public void shouldCreateFileResource() {
        // A file schema uri for a file should return a FileResource
        // The parents of this FileResource should be zero or more DirectoryResources up-to a root SystemResource
        // eg. "file:/dev/Palisade-common/pom.xml" = System "/" -> Directory "/dev/" -> Directory "/dev/Palisade-common/" -> File "/dev/Palisade-common/pom.xml"

        // Given
        final File pom = new File(System.getProperty("user.dir") + "/pom.xml");

        // When
        FileResource fileResource = (FileResource) ResourceBuilder.create(pom.toURI());

        // Then
        LinkedList<Resource> parents = getAllParents(fileResource);
        // System at the 'top'
        assertThat(parents.getFirst(), instanceOf(SystemResource.class));
        parents.removeFirst();
        // File at the 'bottom'
        assertThat(parents.getLast(), instanceOf(FileResource.class));
        parents.removeLast();
        // Directories in the 'middle'
        parents.forEach(resource -> assertThat(resource, instanceOf(DirectoryResource.class)));
    }

    @Test
    public void shouldNormaliseRelativePaths() {
        // A file schema uri for a file with a relative path should return a FileResource with an absolute resource id
        // The parents of this FileResource should be zero or more DirectoryResources up-to a root SystemResource
        // eg. "file:/dev/Palisade-common/pom.xml" = System "/" -> Directory "/dev/" -> Directory "/dev/Palisade-common/" -> File "/dev/Palisade-common/pom.xml"

        // Given
        final URI absolutePom = new File(System.getProperty("user.dir") + "/pom.xml").toURI();
        final URI relativePom = UriBuilder.create(absolutePom)
                .withoutScheme()
                .withoutAuthority()
                .withPath(absolutePom.getPath() + "/../pom.xml")
                .withoutQuery()
                .withoutFragment();

        // When
        FileResource relativeFile = (FileResource) ResourceBuilder.create(relativePom);
        FileResource absoluteFile = (FileResource) ResourceBuilder.create(absolutePom);

        // Then
        assertThat(relativeFile, equalTo(absoluteFile));
    }

    private LinkedList<Resource> getAllParents(final Resource resource) {
        if (resource instanceof ChildResource) {
            final LinkedList<Resource> parents = getAllParents(((ChildResource) resource).getParent());
            parents.addLast(resource);
            return parents;
        } else {
            return new LinkedList<>(Collections.singleton(resource));
        }
    }
}
