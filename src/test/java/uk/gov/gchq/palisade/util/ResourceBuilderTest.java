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
import java.util.Collections;
import java.util.LinkedList;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class ResourceBuilderTest {
    @Test
    public void createFileResourceTest() {
        // Given
        final File pom = new File(System.getProperty("user.dir") + File.pathSeparator + "pom.xml");

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
