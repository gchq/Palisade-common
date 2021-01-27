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


import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * Convenience wrapper around the {@link URI} constructors.
 * Allows constructing new URIs from an immutable baseUri, with appropriate optionals for each part.
 */
public class UriBuilder {

    private Optional<URI> baseUri = Optional.empty();

    public static class AuthorityBuilder {
        private Optional<URI> baseUri = Optional.empty();

        public static IUserInfo create(final URI baseUri) {
            AuthorityBuilder builder = new AuthorityBuilder();
            builder.baseUri = Optional.of(baseUri);
            return builder.build();
        }

        public static IUserInfo create() {
            return new AuthorityBuilder().build();
        }

        private IUserInfo build() {
            return userInfo -> host -> port -> String.format(
                    "//%s%s%s",
                    Optional.ofNullable(userInfo).or(() -> baseUri.map(URI::getUserInfo)).map(str -> str + "@").orElse(null),
                    Optional.ofNullable(host).or(() -> baseUri.map(URI::getHost)).orElseThrow(),
                    Optional.ofNullable(port).or(() -> baseUri.map(URI::getPort)).map(str -> ":" + port).orElse(null)
            );
        }

        public interface IUserInfo {
            IHost withUserInfo(String userInfo);

            default IHost withoutUserInfo() {
                return withUserInfo(null);
            }
        }

        public interface IHost {
            IPort withHost(String host);

            default IPort withoutHost() {
                return withHost(null);
            }
        }

        public interface IPort {
            String withPort(Integer port);

            default String withoutPort() {
                return withPort(null);
            }
        }

    }

    public static IScheme create(final URI baseUri) {
        UriBuilder builder = new UriBuilder();
        builder.baseUri = Optional.of(baseUri);
        return builder.build();
    }

    public static IScheme create() {
        return new UriBuilder().build();
    }

    private IScheme build() {
        return scheme -> authority -> path -> query -> fragment -> {
            String thisScheme = Optional.ofNullable(scheme).or(() -> baseUri.map(URI::getScheme)).orElseThrow();
            String thisAuth = Optional.ofNullable(authority).or(() -> baseUri.map(URI::getAuthority)).orElse(null);
            String thisPath = Optional.ofNullable(path).or(() -> baseUri.map(URI::getPath)).orElseThrow();
            String thisQuery = Optional.ofNullable(query).or(() -> baseUri.map(URI::getQuery)).orElse(null);
            String thisFrag = Optional.ofNullable(fragment).or(() -> baseUri.map(URI::getFragment)).orElse(null);
            try {
                return new URI(
                        thisScheme,
                        thisAuth,
                        thisPath,
                        thisQuery,
                        thisFrag
                );
            } catch (URISyntaxException e) {
                throw new IllegalArgumentException("Invalid URI: " + thisScheme + ":" + thisAuth + "" + thisPath + "?" + thisQuery + "#" + thisFrag, e);
            }
        };
    }

    public interface IScheme {
        IAuthority withScheme(String scheme);

        default IAuthority withoutScheme() {
            return withScheme(null);
        }
    }

    public interface IAuthority {
        IPath withAuthority(String authority);

        default IPath withoutAuthority() {
            return withAuthority(null);
        }
    }

    public interface IPath {
        IQuery withPath(String path);

        default IQuery withoutPath() {
            return withPath(null);
        }
    }

    public interface IQuery {
        IFragment withQuery(String query);

        default IFragment withoutQuery() {
            return withQuery(null);
        }
    }

    public interface IFragment {
        URI withFragment(String fragment);

        default URI withoutFragment() {
            return withFragment(null);
        }
    }

}
