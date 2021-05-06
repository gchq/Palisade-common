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

import uk.gov.gchq.palisade.resource.Resource;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class ResourceBuilder {
    private static final Map<String, Function<URI, Resource>> SCHEME_REGISTRY = new ConcurrentHashMap<>();

    private ResourceBuilder() {
        // Empty Constructor
    }

    public static void registerBuilder(final String scheme, final Function<URI, Resource> builder) {
        if (SCHEME_REGISTRY.containsKey(scheme)) {
            throw new IllegalArgumentException("Scheme registry already has entry for " + scheme);
        }
        SCHEME_REGISTRY.put(scheme, builder);
    }

    /**
     * Create a resource from a uri
     * Throw IllegalArgumentException if unsupported scheme
     *
     * @param uri the id of the resource
     * @return a new resource created using this uri
     */
    public static Resource create(final URI uri) {
        // The hostname is all in the connectionDetail, so we never have a case of file://hostname/some/uri
        // A lot of this is trying to normalize file:///some/uri (file://<no-hostname>/some/uri) to file:/some/uri
        URI normal = UriBuilder.create(uri)
                .withoutScheme()
                .withoutAuthority()
                .withoutPath()
                .withoutQuery()
                .withoutFragment();
        return Optional.ofNullable(SCHEME_REGISTRY.get(normal.getScheme()))
                .map(builder -> builder.apply(uri))
                .orElseThrow(() -> new IllegalArgumentException("No builder registered for scheme " + uri.getScheme()));
    }

    public static boolean canCreate(final URI uri) {
        return SCHEME_REGISTRY.containsKey(uri.getScheme());
    }

    /**
     * Create a resource from a uri string and attribute map
     * Throw IllegalArgumentException if invalid uri string or unsupported scheme
     *
     * @param uriString a string value of a url used to create a new resource
     * @return a newly created resource using these parameters
     */
    public static Resource create(final String uriString) {
        try {
            return create(new URI(uriString));
        } catch (URISyntaxException ex) {
            throw new IllegalArgumentException("URISyntaxException converting string '" + uriString + "' to uri", ex);
        }
    }
}
