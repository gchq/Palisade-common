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

package uk.gov.gchq.palisade.resource;

import uk.gov.gchq.palisade.Generated;
import uk.gov.gchq.palisade.ToStringBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public abstract class AbstractLeafResource extends AbstractResource implements LeafResource, ChildResource {

    private String type;
    private String serialisedFormat;
    private ParentResource parent;
    private Map<String, Object> attributes = new HashMap<>();

    public AbstractLeafResource() {
    }

    public AbstractLeafResource type(final String type) {
        requireNonNull(type, "The type of a resource cannot be set to null.");
        this.type = type;
        return this;
    }

    public AbstractLeafResource serialisedFormat(final String serialisedFormat) {
        requireNonNull(serialisedFormat, "The serialised format of a resource cannot be set to null.");
        this.serialisedFormat = serialisedFormat;
        return this;
    }

    public AbstractLeafResource attributes(final Map<String, Object> attributes) {
        requireNonNull(attributes, "The attributes of a resource cannot be set to null.");
        this.attributes.clear();
        this.attributes.putAll(attributes);
        return this;
    }

    public AbstractLeafResource attribute(final String attributeKey, final Object attributeValue) {
        requireNonNull(attributeKey, "The attributeKey cannot be set to null.");
        requireNonNull(attributeKey, "The attributeValue cannot be set to null.");
        this.attributes.put(attributeKey, attributeValue);
        return this;
    }


    @Override
    @Generated
    public String getType() {
        return type;
    }

    @Override
    @Generated
    public void setType(final String type) {
        requireNonNull(type);
        this.type = type;
    }

    @Override
    @Generated
    public String getSerialisedFormat() {
        return serialisedFormat;
    }

    @Override
    @Generated
    public void setSerialisedFormat(final String serialisedFormat) {
        requireNonNull(serialisedFormat);
        this.serialisedFormat = serialisedFormat;
    }

    @Override
    @Generated
    public ParentResource getParent() {
        return parent;
    }

    @Override
    @Generated
    public void setParent(final ParentResource parent) {
        requireNonNull(parent);
        this.parent = parent;
    }

    @Generated
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Generated
    public void setAttributes(final Map<String, Object> attributes) {
        requireNonNull(attributes);
        this.attributes = attributes;
    }

    public Object getAttribute(final String attributeKey) {
        return this.attributes.getOrDefault(attributeKey, null);
    }

    public Boolean isAttributeSet(final String attributeKey) {
        return this.attributes.containsKey(attributeKey);
    }


    public void setAttribute(final String attributeKey, final Object attributeValue) {
        attribute(attributeKey, attributeValue);
    }

    public AbstractLeafResource parent(final ParentResource parent) {
        requireNonNull(parent, "The parent cannot be set to null.");
        this.parent = parent;
        return this;
    }


    @Override
    @Generated
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractLeafResource)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        AbstractLeafResource that = (AbstractLeafResource) o;
        return type.equals(that.type) &&
                serialisedFormat.equals(that.serialisedFormat) &&
                parent.equals(that.parent) &&
                attributes.equals(that.attributes);
    }

    @Override
    @Generated
    public int hashCode() {
        return Objects.hash(super.hashCode(), type, serialisedFormat, parent, attributes);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("type", type)
                .append("serialisedFormat", serialisedFormat)
                .append("parent", parent)
                .append("attributes", attributes)
                .toString();
    }
}
