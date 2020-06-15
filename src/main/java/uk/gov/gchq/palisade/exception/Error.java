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

package uk.gov.gchq.palisade.exception;


import uk.gov.gchq.palisade.Generated;
import uk.gov.gchq.palisade.util.DebugUtil;

import java.util.Objects;
import java.util.StringJoiner;

import static java.util.Objects.requireNonNull;

/**
 * Simple serialisable POJO for containing details of errors.
 * An {@link uk.gov.gchq.palisade.exception.Error} object is typically
 * created automatically by a Jersey ExceptionMapper and should not be created
 * manually.
 */
public final class Error {

    private int statusCode;
    private Status status;
    private String simpleMessage;
    private String detailMessage;
    private Class<? extends RuntimeException> exceptionClass;

    public Error() {
    }

    private Error(final ErrorBuilder builder) {
        this.setStatusCode(builder.statusCode);
        this.setStatus(builder.status);
        this.setSimpleMessage(builder.simpleMessage);
        this.setDetailMessage(builder.detailMessage);
        this.setExceptionClass(builder.exceptionClass);
    }

    @Generated
    public int getStatusCode() {
        return statusCode;
    }

    @Generated
    public void setStatusCode(final int statusCode) {
        requireNonNull(statusCode);
        this.statusCode = statusCode;
    }

    @Generated
    public Status getStatus() {
        return status;
    }

    @Generated
    public void setStatus(final Status status) {
        requireNonNull(status);
        this.status = status;
    }

    @Generated
    public String getSimpleMessage() {
        return simpleMessage;
    }

    @Generated
    public void setSimpleMessage(final String simpleMessage) {
        requireNonNull(simpleMessage);
        this.simpleMessage = simpleMessage;
    }

    @Generated
    public String getDetailMessage() {
        return detailMessage;
    }

    @Generated
    public void setDetailMessage(final String detailMessage) {
        requireNonNull(detailMessage);
        this.detailMessage = detailMessage;
    }

    @Generated
    public Class<? extends RuntimeException> getExceptionClass() {
        return exceptionClass;
    }

    @Generated
    public void setExceptionClass(final Class<? extends RuntimeException> exceptionClass) {
        requireNonNull(exceptionClass);
        this.exceptionClass = exceptionClass;
    }

    @Override
    @Generated
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Error)) {
            return false;
        }
        Error error = (Error) o;
        return statusCode == error.statusCode &&
                status == error.status &&
                simpleMessage.equals(error.simpleMessage) &&
                detailMessage.equals(error.detailMessage) &&
                exceptionClass.equals(error.exceptionClass);
    }

    @Override
    @Generated
    public int hashCode() {
        return Objects.hash(statusCode, status, simpleMessage, detailMessage, exceptionClass);
    }

    @Override
    @Generated
    public String toString() {
        return new StringJoiner(", ", Error.class.getSimpleName() + "[", "]")
                .add("statusCode=" + statusCode)
                .add("status=" + status)
                .add("simpleMessage='" + simpleMessage + "'")
                .add("detailMessage='" + detailMessage + "'")
                .add("exceptionClass=" + exceptionClass)
                .toString();
    }


    public static final class ErrorBuilder {
        private int statusCode;
        private Status status;
        private String simpleMessage;
        private String detailMessage;
        private Class<? extends RuntimeException> exceptionClass;

        public ErrorBuilder() {
            // Empty
        }

        public ErrorBuilder statusCode(final int statusCode) {
            this.statusCode = statusCode;
            this.status = Status.fromStatusCode(statusCode);
            return this;
        }

        public ErrorBuilder status(final Status status) {
            this.status = status;
            this.statusCode = status.getStatusCode();
            return this;
        }

        public ErrorBuilder simpleMessage(final String simpleMessage) {
            this.simpleMessage = simpleMessage;
            return this;
        }

        public ErrorBuilder detailMessage(final String detailMessage) {
            this.detailMessage = detailMessage;
            return this;
        }

        public ErrorBuilder exceptionClass(final RuntimeException exception) {
            requireNonNull(exception, "exception is required");
            this.exceptionClass = exception.getClass();
            return this;
        }

        public ErrorBuilder exceptionClass(final Exception exception) {
            requireNonNull(exception, "exception is required");
            if (exception instanceof RuntimeException) {
                this.exceptionClass = exception.getClass().asSubclass(RuntimeException.class);
            } else {
                this.exceptionClass = PalisadeRuntimeException.class;
            }
            return this;
        }

        public ErrorBuilder exceptionClass(final Class<? extends Exception> exceptionClass) {
            requireNonNull(exceptionClass, "exceptionClass is required");
            if (RuntimeException.class.isAssignableFrom(exceptionClass)) {
                this.exceptionClass = exceptionClass.asSubclass(RuntimeException.class);
            } else {
                this.exceptionClass = PalisadeRuntimeException.class;
            }
            return this;
        }

        public Error build() {
            if (DebugUtil.checkDebugMode()) {
                return new Error(this);
            } else {
                return new Error(this.detailMessage(null));

            }
        }
    }
}
