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

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import uk.gov.gchq.palisade.Generated;
import uk.gov.gchq.palisade.User;
import uk.gov.gchq.palisade.resource.Resource;
import uk.gov.gchq.palisade.rule.PredicateRule;
import uk.gov.gchq.palisade.rule.Rule;
import uk.gov.gchq.palisade.rule.Rules;
import uk.gov.gchq.palisade.rule.SerializablePredicate;
import uk.gov.gchq.palisade.rule.SerializableUnaryOperator;

import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

/**
 * This class is used to store the information that is required by the policy
 * service but not needed by the rest of the palisade services. That includes
 * separating the rules that need to be applied at the resource level or the record level.
 *
 * @param <T> The Java class that the rules expect the records of data to be in
 *            the format of, e.g. if T was String then a policy has Rules of type Resource for coarse filtering
 *            and Rules of type String for fine grain filtering of files where each record is a String
 */
public class Policy<T extends Serializable> implements Serializable {
    private static final long serialVersionUID = 1L;

    private Rules<T> recordRules;
    private Rules<Resource> resourceRules;
    private User owner;

    /**
     * Instantiates a new Policy.
     */
    // no-args constructor required
    public Policy() {
        recordRules = new Rules<>();
        resourceRules = new Rules<>();
    }

    /**
     * Generates UUID
     *
     * @return UUID.randomUUID
     */
    @Generated
    private static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * Record rules policy.
     *
     * @param recordRules the record rules
     * @return the policy
     */
    @Generated
    public Policy<T> recordRules(final Rules<T> recordRules) {
        this.setRecordRules(recordRules);
        return this;
    }

    /**
     * Resource rules policy.
     *
     * @param resourceRules the resource rules
     * @return the policy
     */
    @Generated
    public Policy<T> resourceRules(final Rules<Resource> resourceRules) {
        this.setResourceRules(resourceRules);
        return this;
    }

    /**
     * Record level rule policy.
     *
     * @param message the message
     * @param rule    the rule
     * @return the policy
     */
    @Generated
    public Policy<T> recordLevelRule(final String message, final Rule<T> rule) {
        Rules<T> recordLevelRules = getRecordRules();
        recordLevelRules.addRule(generateUUID(), rule);
        addMessage(message, recordLevelRules);
        return this;
    }

    /**
     * Record level predicate rule policy.
     *
     * @param message the message
     * @param rule    the rule
     * @return the policy
     */
    @Generated
    public Policy<T> recordLevelPredicateRule(final String message, final PredicateRule<T> rule) {
        Rules<T> recordLevelRules = getRecordRules();
        recordLevelRules.addPredicateRule(generateUUID(), rule);
        addMessage(message, recordLevelRules);
        return this;
    }

    /**
     * Record level simple predicate rule policy.
     *
     * @param message the message
     * @param rule    the rule
     * @return the policy
     */
    @Generated
    public Policy<T> recordLevelSimplePredicateRule(final String message, final SerializablePredicate<T> rule) {
        Rules<T> recordLevelRules = getRecordRules();
        recordLevelRules.addSimplePredicateRule(generateUUID(), rule);
        addMessage(message, recordLevelRules);
        return this;
    }

    /**
     * Record level simple function rule policy.
     *
     * @param message the message
     * @param rule    the rule
     * @return the policy
     */
    @Generated
    public Policy<T> recordLevelSimpleFunctionRule(final String message, final SerializableUnaryOperator<T> rule) {
        Rules<T> recordLevelRules = getRecordRules();
        recordLevelRules.addSimpleFunctionRule(generateUUID(), rule);
        addMessage(message, recordLevelRules);
        return this;
    }

    /**
     * Resource level rule policy.
     *
     * @param message the message
     * @param rule    the rule
     * @return the policy
     */
    @Generated
    public Policy<T> resourceLevelRule(final String message, final Rule<Resource> rule) {
        Rules<Resource> resourceLevelRules = getResourceRules();
        resourceLevelRules.addRule(generateUUID(), rule);
        addMessage(message, resourceLevelRules);
        return this;
    }

    /**
     * Resource level predicate rule policy.
     *
     * @param message the message
     * @param rule    the rule
     * @return the policy
     */
    @Generated
    public Policy<T> resourceLevelPredicateRule(final String message, final PredicateRule<Resource> rule) {
        Rules<Resource> resourceLevelRules = getResourceRules();
        resourceLevelRules.addRule(generateUUID(), rule);
        addMessage(message, resourceLevelRules);
        return this;
    }

    /**
     * Resource level simple predicate rule policy.
     *
     * @param message the message
     * @param rule    the rule
     * @return the policy
     */
    @Generated
    public Policy<T> resourceLevelSimplePredicateRule(final String message, final SerializablePredicate<Resource> rule) {
        Rules<Resource> resourceLevelRules = getResourceRules();
        resourceLevelRules.addSimplePredicateRule(generateUUID(), rule);
        addMessage(message, resourceLevelRules);
        return this;
    }

    /**
     * Resource level simple function rule policy.
     *
     * @param message the message
     * @param rule    the rule
     * @return the policy
     */
    @Generated
    public Policy<T> resourceLevelSimpleFunctionRule(final String message, final SerializableUnaryOperator<Resource> rule) {
        Rules<Resource> resourceLevelRules = getResourceRules();
        resourceLevelRules.addSimpleFunctionRule(generateUUID(), rule);
        addMessage(message, resourceLevelRules);
        return this;
    }


    /**
     * Gets message.
     *
     * @return the message
     */
    @JsonIgnore
    public String getMessage() {
        return "Resource level rules: " + getResourceRules().getMessage() + ", record level rules: " + getRecordRules().getMessage();
    }

    public Policy owner(final User owner) {
        setOwner(owner);
        return this;
    }

    /**
     * Gets owner.
     *
     * @return the owner
     */
    @Generated
    public User getOwner() {
        return owner;
    }

    /**
     * Sets owner.
     *
     * @param owner the owner
     */
    @Generated
    public void setOwner(final User owner) {
        requireNonNull(owner);
        this.owner = owner;
    }


    @Generated
    public Rules<T> getRecordRules() {
        return recordRules;
    }

    /**
     * Sets record rules.
     *
     * @param recordRules the record rules
     */
    @Generated
    public void setRecordRules(final Rules<T> recordRules) {
        requireNonNull(recordRules);
        this.recordRules = recordRules;
    }

    /**
     * Gets resource rules.
     *
     * @return the resource rules
     */
    @Generated
    public Rules<Resource> getResourceRules() {
        return resourceRules;
    }

    /**
     * Sets resource rules.
     *
     * @param resourceRules the resource rules
     */
    @Generated
    public void setResourceRules(final Rules<Resource> resourceRules) {
        requireNonNull(resourceRules);
        this.resourceRules = resourceRules;
    }

    private void addMessage(final String newMessage, final Rules rules) {
        requireNonNull(newMessage, "Cannot add a null message.");
        requireNonNull(rules, "Cannot add a message to a null set of rules.");
        String currentMessage = rules.getMessage();
        if (currentMessage.equals(Rules.NO_RULES_SET)) {
            rules.message(newMessage);
        } else {
            rules.message(currentMessage + ", " + newMessage);
        }
    }



    /**
     * Gets nullable owner.
     *
     * @return the nullable owner
     */
    @JsonGetter("owner")
    User getNullableOwner() {
        return owner;
    }

    @Override
    @Generated
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Policy)) {
            return false;
        }
        Policy<?> policy = (Policy<?>) o;
        return recordRules.equals(policy.recordRules) &&
                resourceRules.equals(policy.resourceRules) &&
                owner.equals(policy.owner);
    }

    @Override
    @Generated
    public int hashCode() {
        return Objects.hash(recordRules, resourceRules, owner);
    }

    @Override
    @Generated
    public String toString() {
        return new StringJoiner(", ", Policy.class.getSimpleName() + "[", "]")
                .add("recordRules=" + recordRules)
                .add("resourceRules=" + resourceRules)
                .add("owner=" + owner)
                .toString();
    }
}
