/**
 * Autogenerated by Avro
 * <p>
 * DO NOT EDIT DIRECTLY
 */
package uk.gov.gchq.palisade.data.service.impl.serialiser;

import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.SchemaStore;
import org.apache.avro.specific.SpecificData;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class TestObj extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
    private static final long serialVersionUID = 7843905661986499514L;
    public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"TestObj\",\"namespace\":\"uk.gov.gchq.palisade.data.service.impl.serialiser\",\"fields\":[{\"name\":\"fieldStr1\",\"type\":\"string\"},{\"name\":\"fieldInt\",\"type\":[\"int\",\"null\"]},{\"name\":\"fieldStr2\",\"type\":[\"string\",\"null\"]}]}");

    public static org.apache.avro.Schema getClassSchema() {
        return SCHEMA$;
    }

    private static SpecificData MODEL$ = new SpecificData();

    private static final BinaryMessageEncoder<TestObj> ENCODER =
            new BinaryMessageEncoder<TestObj>(MODEL$, SCHEMA$);

    private static final BinaryMessageDecoder<TestObj> DECODER =
            new BinaryMessageDecoder<TestObj>(MODEL$, SCHEMA$);

    /**
     * Return the BinaryMessageDecoder instance used by this class.
     */
    public static BinaryMessageDecoder<TestObj> getDecoder() {
        return DECODER;
    }

    /**
     * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
     *
     * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
     */
    public static BinaryMessageDecoder<TestObj> createDecoder(SchemaStore resolver) {
        return new BinaryMessageDecoder<TestObj>(MODEL$, SCHEMA$, resolver);
    }

    /**
     * Serializes this TestObj to a ByteBuffer.
     */
    public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
        return ENCODER.encode(this);
    }

    /**
     * Deserializes a TestObj from a ByteBuffer.
     */
    public static TestObj fromByteBuffer(
            java.nio.ByteBuffer b) throws java.io.IOException {
        return DECODER.decode(b);
    }

    @Deprecated
    public java.lang.CharSequence fieldStr1;
    @Deprecated
    public java.lang.Integer fieldInt;
    @Deprecated
    public java.lang.CharSequence fieldStr2;

    /**
     * Default constructor.  Note that this does not initialize fields
     * to their default values from the schema.  If that is desired then
     * one should use <code>newBuilder()</code>.
     */
    public TestObj() {
    }

    /**
     * All-args constructor.
     *
     * @param fieldStr1 The new value for fieldStr1
     * @param fieldInt  The new value for fieldInt
     * @param fieldStr2 The new value for fieldStr2
     */
    public TestObj(java.lang.CharSequence fieldStr1, java.lang.Integer fieldInt, java.lang.CharSequence fieldStr2) {
        this.fieldStr1 = fieldStr1;
        this.fieldInt = fieldInt;
        this.fieldStr2 = fieldStr2;
    }

    public org.apache.avro.Schema getSchema() {
        return SCHEMA$;
    }

    // Used by DatumWriter.  Applications should not call.
    public java.lang.Object get(int field$) {
        switch (field$) {
            case 0:
                return fieldStr1;
            case 1:
                return fieldInt;
            case 2:
                return fieldStr2;
            default:
                throw new org.apache.avro.AvroRuntimeException("Bad index");
        }
    }

    // Used by DatumReader.  Applications should not call.
    @SuppressWarnings(value = "unchecked")
    public void put(int field$, java.lang.Object value$) {
        switch (field$) {
            case 0:
                fieldStr1 = (java.lang.CharSequence) value$;
                break;
            case 1:
                fieldInt = (java.lang.Integer) value$;
                break;
            case 2:
                fieldStr2 = (java.lang.CharSequence) value$;
                break;
            default:
                throw new org.apache.avro.AvroRuntimeException("Bad index");
        }
    }

    /**
     * Gets the value of the 'fieldStr1' field.
     *
     * @return The value of the 'fieldStr1' field.
     */
    public java.lang.CharSequence getFieldStr1() {
        return fieldStr1;
    }

    /**
     * Sets the value of the 'fieldStr1' field.
     *
     * @param value the value to set.
     */
    public void setFieldStr1(java.lang.CharSequence value) {
        this.fieldStr1 = value;
    }

    /**
     * Gets the value of the 'fieldInt' field.
     *
     * @return The value of the 'fieldInt' field.
     */
    public java.lang.Integer getFieldInt() {
        return fieldInt;
    }

    /**
     * Sets the value of the 'fieldInt' field.
     *
     * @param value the value to set.
     */
    public void setFieldInt(java.lang.Integer value) {
        this.fieldInt = value;
    }

    /**
     * Gets the value of the 'fieldStr2' field.
     *
     * @return The value of the 'fieldStr2' field.
     */
    public java.lang.CharSequence getFieldStr2() {
        return fieldStr2;
    }

    /**
     * Sets the value of the 'fieldStr2' field.
     *
     * @param value the value to set.
     */
    public void setFieldStr2(java.lang.CharSequence value) {
        this.fieldStr2 = value;
    }

    /**
     * Creates a new TestObj RecordBuilder.
     *
     * @return A new TestObj RecordBuilder
     */
    public static uk.gov.gchq.palisade.data.service.impl.serialiser.TestObj.Builder newBuilder() {
        return new uk.gov.gchq.palisade.data.service.impl.serialiser.TestObj.Builder();
    }

    /**
     * Creates a new TestObj RecordBuilder by copying an existing Builder.
     *
     * @param other The existing builder to copy.
     * @return A new TestObj RecordBuilder
     */
    public static uk.gov.gchq.palisade.data.service.impl.serialiser.TestObj.Builder newBuilder(uk.gov.gchq.palisade.data.service.impl.serialiser.TestObj.Builder other) {
        return new uk.gov.gchq.palisade.data.service.impl.serialiser.TestObj.Builder(other);
    }

    /**
     * Creates a new TestObj RecordBuilder by copying an existing TestObj instance.
     *
     * @param other The existing instance to copy.
     * @return A new TestObj RecordBuilder
     */
    public static uk.gov.gchq.palisade.data.service.impl.serialiser.TestObj.Builder newBuilder(uk.gov.gchq.palisade.data.service.impl.serialiser.TestObj other) {
        return new uk.gov.gchq.palisade.data.service.impl.serialiser.TestObj.Builder(other);
    }

    /**
     * RecordBuilder for TestObj instances.
     */
    public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<TestObj>
            implements org.apache.avro.data.RecordBuilder<TestObj> {

        private java.lang.CharSequence fieldStr1;
        private java.lang.Integer fieldInt;
        private java.lang.CharSequence fieldStr2;

        /**
         * Creates a new Builder
         */
        private Builder() {
            super(SCHEMA$);
        }

        /**
         * Creates a Builder by copying an existing Builder.
         *
         * @param other The existing Builder to copy.
         */
        private Builder(uk.gov.gchq.palisade.data.service.impl.serialiser.TestObj.Builder other) {
            super(other);
            if (isValidValue(fields()[0], other.fieldStr1)) {
                this.fieldStr1 = data().deepCopy(fields()[0].schema(), other.fieldStr1);
                fieldSetFlags()[0] = true;
            }
            if (isValidValue(fields()[1], other.fieldInt)) {
                this.fieldInt = data().deepCopy(fields()[1].schema(), other.fieldInt);
                fieldSetFlags()[1] = true;
            }
            if (isValidValue(fields()[2], other.fieldStr2)) {
                this.fieldStr2 = data().deepCopy(fields()[2].schema(), other.fieldStr2);
                fieldSetFlags()[2] = true;
            }
        }

        /**
         * Creates a Builder by copying an existing TestObj instance
         *
         * @param other The existing instance to copy.
         */
        private Builder(uk.gov.gchq.palisade.data.service.impl.serialiser.TestObj other) {
            super(SCHEMA$);
            if (isValidValue(fields()[0], other.fieldStr1)) {
                this.fieldStr1 = data().deepCopy(fields()[0].schema(), other.fieldStr1);
                fieldSetFlags()[0] = true;
            }
            if (isValidValue(fields()[1], other.fieldInt)) {
                this.fieldInt = data().deepCopy(fields()[1].schema(), other.fieldInt);
                fieldSetFlags()[1] = true;
            }
            if (isValidValue(fields()[2], other.fieldStr2)) {
                this.fieldStr2 = data().deepCopy(fields()[2].schema(), other.fieldStr2);
                fieldSetFlags()[2] = true;
            }
        }

        /**
         * Gets the value of the 'fieldStr1' field.
         *
         * @return The value.
         */
        public java.lang.CharSequence getFieldStr1() {
            return fieldStr1;
        }

        /**
         * Sets the value of the 'fieldStr1' field.
         *
         * @param value The value of 'fieldStr1'.
         * @return This builder.
         */
        public uk.gov.gchq.palisade.data.service.impl.serialiser.TestObj.Builder setFieldStr1(java.lang.CharSequence value) {
            validate(fields()[0], value);
            this.fieldStr1 = value;
            fieldSetFlags()[0] = true;
            return this;
        }

        /**
         * Checks whether the 'fieldStr1' field has been set.
         *
         * @return True if the 'fieldStr1' field has been set, false otherwise.
         */
        public boolean hasFieldStr1() {
            return fieldSetFlags()[0];
        }


        /**
         * Clears the value of the 'fieldStr1' field.
         *
         * @return This builder.
         */
        public uk.gov.gchq.palisade.data.service.impl.serialiser.TestObj.Builder clearFieldStr1() {
            fieldStr1 = null;
            fieldSetFlags()[0] = false;
            return this;
        }

        /**
         * Gets the value of the 'fieldInt' field.
         *
         * @return The value.
         */
        public java.lang.Integer getFieldInt() {
            return fieldInt;
        }

        /**
         * Sets the value of the 'fieldInt' field.
         *
         * @param value The value of 'fieldInt'.
         * @return This builder.
         */
        public uk.gov.gchq.palisade.data.service.impl.serialiser.TestObj.Builder setFieldInt(java.lang.Integer value) {
            validate(fields()[1], value);
            this.fieldInt = value;
            fieldSetFlags()[1] = true;
            return this;
        }

        /**
         * Checks whether the 'fieldInt' field has been set.
         *
         * @return True if the 'fieldInt' field has been set, false otherwise.
         */
        public boolean hasFieldInt() {
            return fieldSetFlags()[1];
        }


        /**
         * Clears the value of the 'fieldInt' field.
         *
         * @return This builder.
         */
        public uk.gov.gchq.palisade.data.service.impl.serialiser.TestObj.Builder clearFieldInt() {
            fieldInt = null;
            fieldSetFlags()[1] = false;
            return this;
        }

        /**
         * Gets the value of the 'fieldStr2' field.
         *
         * @return The value.
         */
        public java.lang.CharSequence getFieldStr2() {
            return fieldStr2;
        }

        /**
         * Sets the value of the 'fieldStr2' field.
         *
         * @param value The value of 'fieldStr2'.
         * @return This builder.
         */
        public uk.gov.gchq.palisade.data.service.impl.serialiser.TestObj.Builder setFieldStr2(java.lang.CharSequence value) {
            validate(fields()[2], value);
            this.fieldStr2 = value;
            fieldSetFlags()[2] = true;
            return this;
        }

        /**
         * Checks whether the 'fieldStr2' field has been set.
         *
         * @return True if the 'fieldStr2' field has been set, false otherwise.
         */
        public boolean hasFieldStr2() {
            return fieldSetFlags()[2];
        }


        /**
         * Clears the value of the 'fieldStr2' field.
         *
         * @return This builder.
         */
        public uk.gov.gchq.palisade.data.service.impl.serialiser.TestObj.Builder clearFieldStr2() {
            fieldStr2 = null;
            fieldSetFlags()[2] = false;
            return this;
        }

        @Override
        @SuppressWarnings("unchecked")
        public TestObj build() {
            try {
                TestObj record = new TestObj();
                record.fieldStr1 = fieldSetFlags()[0] ? this.fieldStr1 : (java.lang.CharSequence) defaultValue(fields()[0]);
                record.fieldInt = fieldSetFlags()[1] ? this.fieldInt : (java.lang.Integer) defaultValue(fields()[1]);
                record.fieldStr2 = fieldSetFlags()[2] ? this.fieldStr2 : (java.lang.CharSequence) defaultValue(fields()[2]);
                return record;
            } catch (java.lang.Exception e) {
                throw new org.apache.avro.AvroRuntimeException(e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static final org.apache.avro.io.DatumWriter<TestObj>
            WRITER$ = (org.apache.avro.io.DatumWriter<TestObj>) MODEL$.createDatumWriter(SCHEMA$);

    @Override
    public void writeExternal(java.io.ObjectOutput out)
            throws java.io.IOException {
        WRITER$.write(this, SpecificData.getEncoder(out));
    }

    @SuppressWarnings("unchecked")
    private static final org.apache.avro.io.DatumReader<TestObj>
            READER$ = (org.apache.avro.io.DatumReader<TestObj>) MODEL$.createDatumReader(SCHEMA$);

    @Override
    public void readExternal(java.io.ObjectInput in)
            throws java.io.IOException {
        READER$.read(this, SpecificData.getDecoder(in));
    }

}
