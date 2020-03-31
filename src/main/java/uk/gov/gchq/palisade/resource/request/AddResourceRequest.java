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
package uk.gov.gchq.palisade.resource.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import uk.gov.gchq.palisade.Generated;
import uk.gov.gchq.palisade.RequestId;
import uk.gov.gchq.palisade.exception.ForbiddenException;
import uk.gov.gchq.palisade.resource.LeafResource;
import uk.gov.gchq.palisade.service.ConnectionDetail;
import uk.gov.gchq.palisade.service.request.Request;

import java.util.Objects;
import java.util.StringJoiner;

import static java.util.Objects.requireNonNull;

/**
 * This class is used to request that details about a resource is added to the resource-service.
 */
@JsonIgnoreProperties(value = {"originalRequestId"})
public class AddResourceRequest extends Request {
    private LeafResource resource;
    private ConnectionDetail connectionDetail;

    public AddResourceRequest() {
        //no-args constructor needed for serialization only
    }

    /**
     * @param resource The {@link LeafResource} to be added.
     * @return the {@link AddResourceRequest}
     */
    public AddResourceRequest resource(final LeafResource resource) {
        requireNonNull(resource, "The resource cannot be set to null.");
        this.resource = resource;
        return this;
    }

    /**
     * @param connectionDetail Details of how to get to the data from the {@code DataService}.
     * @return the {@link AddResourceRequest}
     */
    public AddResourceRequest connectionDetail(final ConnectionDetail connectionDetail) {
        requireNonNull(connectionDetail, "The connection details cannot be set to null.");
        this.connectionDetail = connectionDetail;
        return this;
    }


    @Override
    public RequestId getOriginalRequestId() {
        throw new ForbiddenException("Should not call AddResourceRequest.getOriginalRequestId()");
    }

    @Override
    public void setOriginalRequestId(final RequestId originalRequestId) {
        throw new ForbiddenException("Should not call AddResourceRequest.setOriginalRequestId()");
    }

    @Generated
    public LeafResource getResource() {
        return resource;
    }

    @Generated
    public void setResource(final LeafResource resource) {
        requireNonNull(resource);
        this.resource = resource;
    }

    @Generated
    public ConnectionDetail getConnectionDetail() {
        return connectionDetail;
    }

    @Generated
    public void setConnectionDetail(final ConnectionDetail connectionDetail) {
        requireNonNull(connectionDetail);
        this.connectionDetail = connectionDetail;
    }

    @Override
    @Generated
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AddResourceRequest)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        AddResourceRequest that = (AddResourceRequest) o;
        return resource.equals(that.resource) &&
                connectionDetail.equals(that.connectionDetail);
    }

    @Override
    @Generated
    public int hashCode() {
        return Objects.hash(super.hashCode(), resource, connectionDetail);
    }

    @Override
    @Generated
    public String toString() {
        return new StringJoiner(", ", AddResourceRequest.class.getSimpleName() + "[", "]")
                .add("resource=" + resource)
                .add("connectionDetail=" + connectionDetail)
                .add(super.toString())
                .toString();
    }
}
