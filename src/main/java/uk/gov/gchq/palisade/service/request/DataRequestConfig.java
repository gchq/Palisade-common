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

package uk.gov.gchq.palisade.service.request;


import uk.gov.gchq.palisade.Context;
import uk.gov.gchq.palisade.Generated;
import uk.gov.gchq.palisade.User;
import uk.gov.gchq.palisade.resource.LeafResource;
import uk.gov.gchq.palisade.rule.Rules;

import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

import static java.util.Objects.requireNonNull;

/**
 * This is the high level API for the object that contains all the information
 * that the data service will require from the palisade, for the data service to
 * respond to requests for access to data.
 */
public class DataRequestConfig extends Request {
    private User user;
    private Context context;
    private Map<LeafResource, Rules> rules;

    public DataRequestConfig() {
        //no-args constructor needed for serialization only
    }

    @Generated
    public DataRequestConfig user(final User user) {
        this.setUser(user);
        return this;
    }

    @Generated
    public DataRequestConfig context(final Context context) {
        this.setContext(context);
        return this;
    }

    @Generated
    public DataRequestConfig rules(final Map<LeafResource, Rules> rules) {
        this.setRules(rules);
        return this;
    }


    @Generated
    public User getUser() {
        return user;
    }

    @Generated
    public void setUser(final User user) {
        requireNonNull(user);
        this.user = user;
    }

    @Generated
    public Context getContext() {
        return context;
    }

    @Generated
    public void setContext(final Context context) {
        requireNonNull(context);
        this.context = context;
    }

    @Generated
    public Map<LeafResource, Rules> getRules() {
        return rules;
    }

    @Generated
    public void setRules(final Map<LeafResource, Rules> rules) {
        requireNonNull(rules);
        this.rules = rules;
    }

    @Override
    @Generated
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DataRequestConfig)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        DataRequestConfig that = (DataRequestConfig) o;
        return user.equals(that.user) &&
                context.equals(that.context) &&
                rules.equals(that.rules);
    }

    @Override
    @Generated
    public int hashCode() {
        return Objects.hash(super.hashCode(), user, context, rules);
    }

    @Override
    @Generated
    public String toString() {
        return new StringJoiner(", ", DataRequestConfig.class.getSimpleName() + "[", "]")
                .add("user=" + user)
                .add("context=" + context)
                .add("rules=" + rules)
                .add(super.toString())
                .toString();
    }
}
