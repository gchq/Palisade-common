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
import java.util.Map;
import java.util.ServiceLoader;
import java.util.ServiceLoader.Provider;

public abstract class ResourceBuilder {
    private static final ServiceLoader<ResourceBuilder> LOADER = ServiceLoader.load(ResourceBuilder.class);

    public static void refreshProviders() {
        LOADER.reload();
    }

    public static Resource create(final URI resourceUri) {
        ResourceBuilder resourceBuilder = LOADER.stream()
                .map(Provider::get)
                .filter(builder -> builder.accepts(resourceUri))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("No ResourceBuilder found that accepts " + resourceUri));
        return resourceBuilder.buildNormal(resourceUri);
    }

    public static Resource create(final String uriString) {
        try {
            return create(new URI(uriString));
        } catch (URISyntaxException ex) {
            throw new IllegalArgumentException("URISyntaxException converting string '" + uriString + "' to uri", ex);
        }
    }

    public Resource buildNormal(final URI uri) {
        URI normal = UriBuilder.create(uri)
                .withoutScheme()
                .withoutAuthority()
                .withoutPath()
                .withoutQuery()
                .withoutFragment();
        return build(normal);
    }

    public abstract Resource build(URI resourceUri);

    public abstract boolean accepts(URI resourceUri);
}
