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

package uk.gov.gchq.palisade.service.request;

import uk.gov.gchq.palisade.Generated;
import uk.gov.gchq.palisade.resource.LeafResource;
import uk.gov.gchq.palisade.service.ConnectionDetail;

import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.TreeMap;

import static java.util.Objects.requireNonNull;

/**
 * This is the high level API object that is used to pass back to the client the information it requires to connect to
 * the correct data service implementations and to decide how best to parallelise their job.
 * <p>
 * It is also the object that the client then passes to the data service to access the data. When it is passed to the
 * data service the resources field might have been changed to be a subset of the resources.
 */
public class DataRequestResponse extends Request {
    private String token;
    private Map<LeafResource, ConnectionDetail> resources;

    public DataRequestResponse() {
        //no-args constructor needed for serialization only
    }

    @Generated
    public DataRequestResponse token(final String token) {
        this.setToken(token);
        return this;
    }

    @Generated
    public DataRequestResponse resource(final LeafResource resource, final ConnectionDetail connectionDetail) {
        this.addResource(resource, connectionDetail);
        return this;
    }

    @Generated
    public DataRequestResponse resources(final Map<LeafResource, ConnectionDetail> resources) {
        this.setResources(resources);
        return this;
    }


    public void addResource(final LeafResource resource, final ConnectionDetail connectionDetail) {
        requireNonNull(resource);
        requireNonNull(connectionDetail);
        if (resources == null) {
            resources = new TreeMap<>();
        }
        resources.put(resource, connectionDetail);
    }

    @Generated
    public String getToken() {
        return token;
    }

    @Generated
    public void setToken(final String token) {
        requireNonNull(token);
        this.token = token;
    }

    @Generated
    public Map<LeafResource, ConnectionDetail> getResources() {
        return resources;
    }

    @Generated
    public void setResources(final Map<LeafResource, ConnectionDetail> resources) {
        requireNonNull(resources);
        this.resources = resources;
    }

    @Override
    @Generated
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DataRequestResponse)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        DataRequestResponse that = (DataRequestResponse) o;
        return token.equals(that.token) &&
                resources.equals(that.resources);
    }

    @Override
    @Generated
    public int hashCode() {
        return Objects.hash(super.hashCode(), token, resources);
    }

    @Override
    @Generated
    public String toString() {
        return new StringJoiner(", ", DataRequestResponse.class.getSimpleName() + "[", "]")
                .add("token='" + token + "'")
                .add("resources=" + resources)
                .add(super.toString())
                .toString();
    }
}
