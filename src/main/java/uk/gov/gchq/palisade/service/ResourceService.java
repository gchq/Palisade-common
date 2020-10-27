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

package uk.gov.gchq.palisade.service;

import uk.gov.gchq.palisade.resource.LeafResource;

/**
 * The resource service is the Palisade component that determines what resources are available that meet a specific
 * request and how they should be accessed. The get method of this service returns a {@link LeafResource} with a
 * {@link ConnectionDetail} object. The ${@link ConnectionDetail} objects contain information on how to set up a connection to
 * retrieve a particular resource. Implementations of this service do not deal with the filtering or application of security policy
 * to the resources. Therefore, a result returned from a method call on this interface doesn't guarantee that the user will be
 * allowed to access it by policy. Other components of the Palisade system will enforce the necessary policy controls to
 * prevent access to resources by users without the necessary access rights.
 */
public interface ResourceService extends Service {

    /**
     * Retrieve resource and connection details by resource ID. The request object allows the client to specify the
     * resource ID and obtain the connection details once the returned future has completed.
     *
     * @param resourceId the ID to request
     * @return {@link LeafResource}, each with an appropriate {@link ConnectionDetail}
     */
    LeafResource getResourcesById(final String resourceId);

    /**
     * Informs Palisade about a specific resource that it may return to users. This lets Palisade clients request access
     * to that resource and allows Palisade to provide policy controlled access to it via the other methods in this
     * interface.
     *
     * @param resource         the resource that Palisade can manage access to
     * @return whether or not the addResource call completed successfully
     */
    Boolean addResource(final LeafResource resource);

}
