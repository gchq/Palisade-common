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

package uk.gov.gchq.palisade.resource;

import uk.gov.gchq.palisade.Generated;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

import static java.util.Objects.requireNonNull;

public abstract class AbstractLeafResource extends AbstractResource implements LeafResource, ChildResource {

    private String type;
    private String serialisedFormat;
    private ParentResource parent;
    private Map<String, Object> attributes = new HashMap<>();

    public AbstractLeafResource() {
    }

    @Generated
    public AbstractLeafResource type(final String type) {
        this.setType(type);
        return this;
    }

    @Generated
    public AbstractLeafResource serialisedFormat(final String serialisedFormat) {
        this.setSerialisedFormat(serialisedFormat);
        return this;
    }

    @Generated
    public AbstractLeafResource attributes(final Map<String, Object> attributes) {
        this.setAttributes(attributes);
        return this;
    }

    @Generated
    public AbstractLeafResource attribute(final String attributeKey, final Object attributeValue) {
        this.setAttribute(attributeKey, attributeValue);
        return this;
    }

    @Generated
    public AbstractLeafResource parent(final ParentResource parent) {
        this.setParent(parent);
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

    @Generated
    public Object getAttribute(final String attributeKey) {
        return this.attributes.getOrDefault(attributeKey, null);
    }

    @Generated
    public Boolean isAttributeSet(final String attributeKey) {
        return this.attributes.containsKey(attributeKey);
    }


    @Generated
    public void setAttribute(final String attributeKey, final Object attributeValue) {
        requireNonNull(attributeKey, "The attributeKey cannot be set to null.");
        requireNonNull(attributeKey, "The attributeValue cannot be set to null.");
        this.attributes.put(attributeKey, attributeValue);

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
    @Generated
    public String toString() {
        return new StringJoiner(", ", AbstractLeafResource.class.getSimpleName() + "[", "]")
                .add("type='" + type + "'")
                .add("serialisedFormat='" + serialisedFormat + "'")
                .add("parent=" + parent)
                .add("attributes=" + attributes)
                .add(super.toString())
                .toString();
    }
}
