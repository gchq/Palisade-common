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

package uk.gov.gchq.palisade;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

import static java.util.Objects.requireNonNull;

/**
 * <p>
 * A {@link User} contains the details about a user of Palisade. It contains
 * their unique user ID {@link UserId}, their roles and their auths.
 * </p>
 * <p>
 * The user roles are based on the role or function of the user's job. For example it could be used for deciding what actions users are allowed to perform, such as READ/WRITE.
 * </p>
 * <p>
 * The user auths are used specifically to decide what visibilities users can see.
 * </p>
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "class"
)
public class User {
    private UserId userId;

    private Set<String> roles = new HashSet<>();
    private Set<String> auths = new HashSet<>();

    /**
     * Constructs an empty {@link User}.
     */
    public User() {
        //no-args constructor needed for serialization only
    }

    /**
     * Copy constructor for a {@link User}.
     *
     * @param user the {@link User} that will be copied.
     */
    User(final User user) {
        requireNonNull(user, "User to be cloned cannot be null");
        userId = user.getUserId();
        roles = user.getRoles();
        auths = user.getAuths();
    }

    /**
     * Sets the userId to a {@link UserId} with the given userId string.
     *
     * @param userId the unique user ID string.
     * @return this User instance.
     */
    public User userId(final String userId) {
        requireNonNull(userId, "The user id cannot be set to null.");
        return userId(new UserId().id(userId));
    }

    /**
     * Sets the userId.
     *
     * @param userId the userId
     * @return this User instance.
     */
    public User userId(final UserId userId) {
        requireNonNull(userId, "The user id cannot be set to null.");
        this.userId = userId;
        return this;
    }

    @Generated
    public UserId getUserId() {
        return userId;
    }

    @Override
    @Generated
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("userId=" + userId)
                .add("roles=" + roles)
                .add("auths=" + auths)
                .toString();
    }


    @Generated
    public void setUserId(final UserId userId) {
        requireNonNull(userId);
        this.userId = userId;
    }

    /**
     * Adds the user auths.
     *
     * @param auths the user auths to add
     * @return this User instance.
     */
    public User auths(final String... auths) {
        requireNonNull(auths, "Cannot add null auths.");
        this.auths.clear();
        Collections.addAll(this.auths, auths);
        return this;
    }

    /**
     * Adds the user auths.
     *
     * @param auths the user auths to add
     * @return this User instance.
     */
    public User auths(final Set<String> auths) {
        requireNonNull(auths, "Cannot add null auths.");
        this.auths.clear();
        this.auths.addAll(auths);
        return this;
    }

    public void setAuths(final Set<String> auths) {
        auths(auths);
    }

    /**
     * Return the user auths.
     *
     * @return the authorisations
     */
    public Set<String> getAuths() {
        return auths;
    }

    public User addAuths(final Set<String> auths) {
        requireNonNull(auths, "Cannot add null auths.");
        this.auths.addAll(auths);
        return this;
    }

    /**
     * Adds the user roles.
     *
     * @param roles the user roles to add
     * @return this User instance.
     */
    public User roles(final String... roles) {
        requireNonNull(roles, "Cannot add null roles.");
        this.roles.clear();
        Collections.addAll(this.roles, roles);
        return this;
    }

    /**
     * Adds the user roles.
     *
     * @param roles the user roles to add
     */
    public void setRoles(final Set<String> roles) {
        roles(roles);
    }

    /**
     * Adds the user roles.
     *
     * @param roles the user roles to add
     * @return this User instance.
     */
    public User roles(final Set<String> roles) {
        requireNonNull(roles, "Cannot add null roles.");
        this.roles.clear();
        this.roles.addAll(roles);
        return this;
    }

    /**
     * Return the user roles.
     *
     * @return the roles
     */
    public Set<String> getRoles() {
        // roles cannot be null
        return roles;
    }

    public User addRoles(final Set<String> roles) {
        requireNonNull(roles, "Cannot add null roles.");
        this.roles.addAll(roles);
        return this;
    }

    @JsonGetter("class")
    public String _getClass() {
        return getClass().getName();
    }

    @Override
    @Generated
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return userId.equals(user.userId) &&
                roles.equals(user.roles) &&
                auths.equals(user.auths);
    }

    @Override
    @Generated
    public int hashCode() {
        return Objects.hash(userId, roles, auths);
    }
}
