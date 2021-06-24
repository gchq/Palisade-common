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

package uk.gov.gchq.palisade.user;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import uk.gov.gchq.palisade.Generated;

import java.io.Serializable;
import java.util.Arrays;
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
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

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
        this.userId = user.getUserId();
        this.roles = user.getRoles();
        this.auths = user.getAuths();
    }

    @Generated
    public User userId(final UserId userId) {
        this.setUserId(userId);
        return this;
    }

    @Generated
    public User userId(final String userId) {
        this.setUserId((new UserId()).id(userId));
        return this;
    }

    @Generated
    public User auths(final String[] auths) {
        this.setAuths(new HashSet<>(Arrays.asList(auths)));
        return this;
    }

    @Generated
    public User auths(final Set<String> auths) {
        this.setAuths(auths);
        return this;
    }

    @Generated
    public User addAuths(final Set<String> auths) {
        requireNonNull(auths, "Cannot add null auths.");
        this.auths.addAll(auths);
        return this;
    }

    @Generated
    public User roles(final String[] roles) {
        this.setRoles(new HashSet<>(Arrays.asList(roles)));
        return this;
    }

    @Generated
    public User addRoles(final Set<String> roles) {
        requireNonNull(auths, "Cannot add null roles.");
        this.roles.addAll(roles);
        return this;
    }

    @Generated
    public User roles(final Set<String> roles) {
        this.setRoles(roles);
        return this;
    }

    @Generated
    public UserId getUserId() {
        return userId;
    }

    @Generated
    public void setUserId(final UserId userId) {
        requireNonNull(userId);
        this.userId = userId;
    }

    @Generated
    public Set<String> getRoles() {
        return Collections.unmodifiableSet(roles);
    }

    @Generated
    public void setRoles(final Set<String> roles) {
        requireNonNull(roles);
        this.roles = Collections.unmodifiableSet(roles);
    }

    @Generated
    public Set<String> getAuths() {
        return Collections.unmodifiableSet(auths);
    }

    @Generated
    public void setAuths(final Set<String> auths) {
        requireNonNull(auths);
        this.auths = Collections.unmodifiableSet(auths);
    }

    @Override
    @Generated
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
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

    @Override
    @Generated
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("userId=" + userId)
                .add("roles=" + roles)
                .add("auths=" + auths)
                .add(super.toString())
                .toString();
    }
}
