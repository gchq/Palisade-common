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

import uk.gov.gchq.palisade.resource.Resource;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ServiceLoader;
import java.util.ServiceLoader.Provider;

/**
 * ResourceBuilder, taking a URI and building a Resource specific to each scheme
 */
public abstract class ResourceBuilder {
    private static final ServiceLoader<ResourceBuilder> LOADER = ServiceLoader.load(ResourceBuilder.class);

    /**
     * Clears this loader's provider cache so that all providers will be reloaded.
     */
    public static void refreshProviders() {
        LOADER.reload();
    }

    /**
     * Taking a resourceUri, create a resource using the ResourceBuilder provided in the LOADER,
     * or throw an exception if the resource scheme is not supported, or no builder exists to build that scheme
     *
     * @param resourceUri the Uri of the resource you want to build.
     * @return a newly created resource
     */
    public static Resource create(final URI resourceUri) {
        ResourceBuilder resourceBuilder = LOADER.stream()
                .map(Provider::get)
                .filter(builder -> builder.accepts(resourceUri))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("No ResourceBuilder found that accepts " + resourceUri));
        return resourceBuilder.buildNormal(resourceUri);
    }

    /**
     * Create a resource from a uri string
     * Throw IllegalArgumentException if invalid uri string or unsupported scheme
     *
     * @param uriString a string value of a url used to create a new resource
     * @return a newly created resource using these parameters.
     */
    public static Resource create(final String uriString) {
        try {
            return create(new URI(uriString));
        } catch (URISyntaxException ex) {
            throw new IllegalArgumentException("URISyntaxException converting string '" + uriString + "' to uri", ex);
        }
    }

    /**
     * Build a resource, using the uri provided by calling {@link UriBuilder}
     *
     * @param uri the uri of the resource you want built
     * @return a newly created resource with the id of the uri.
     */
    public Resource buildNormal(final URI uri) {
        URI normal = UriBuilder.create(uri)
                .withoutScheme()
                .withoutAuthority()
                .withoutPath()
                .withoutQuery()
                .withoutFragment();
        return build(normal);
    }

    /**
     * An abstract method used in building a resource
     *
     * @param resourceUri the uri of the resource you want built
     * @return a newly created Resource with the id of the the resourceUri.
     */
    protected abstract Resource build(URI resourceUri);

    /**
     * A abstract method used in building a resource, to check if the Builders provided can accept the resourceUri scheme
     *
     * @param resourceUri the uri of the resource you want built
     * @return a true/false value if a builder exists that supports the uri scheme.
     */
    public abstract boolean accepts(URI resourceUri);
}
