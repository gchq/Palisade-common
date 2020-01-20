/*
 * Copyright 2019 Crown Copyright
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

package uk.gov.gchq.palisade.service;

import uk.gov.gchq.palisade.ToStringBuilder;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * A simple implementation of the {@link ConnectionDetail} that holds an instance
 * of {@link Service}
 */
public class SimpleConnectionDetail implements ConnectionDetail {

    private String uri;

    public SimpleConnectionDetail() {
        //no-args constructor needed for serialization only
    }

    public SimpleConnectionDetail uri(final String uri) {
        requireNonNull(uri, "The host value can not be set to null");
        this.uri = uri;
        return this;
    }

    public String getUri() {
        requireNonNull(uri, "The host has not been set.");
        return uri;
    }

    public void setHost(final String uri) {
        uri(uri);
    }

    @Override
    public String createConnection() {
        return uri;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SimpleConnectionDetail)) {
            return false;
        }
        final SimpleConnectionDetail that = (SimpleConnectionDetail) o;
        return uri.equals(that.getUri());
    }

    @Override
    public int hashCode() {
        return Objects.hash(uri);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("uri", uri)
                .toString();
    }
}
