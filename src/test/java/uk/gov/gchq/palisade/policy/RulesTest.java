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
package uk.gov.gchq.palisade.policy;

import org.junit.Before;
import org.junit.Test;

import uk.gov.gchq.palisade.jsonserialisation.JSONSerialiser;
import uk.gov.gchq.palisade.rule.Rules;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class RulesTest {

    private Rules<String> rules;
    private byte[] json;

    @Before
    public void setUp() {
        rules = new Rules<String>()
                .message("Age off and visibility filtering")
                .addRule("ageOffRule", new TestRule()
                );
        json = JSONSerialiser.serialise(rules, true);
    }

    @Test
    public void shouldNotEquals() {
        //given
        final Rules<String> one = new Rules<>();
        one.addRule("one", new TestRule());

        final Rules<String> two = new Rules<>();
        two.addRule("two", new TestRule());

        //then
        assertNotEquals("The 2 different rules should not match", one, two);
    }

    @Test
    public void shouldEquals() {
        final Rules<String> one1 = new Rules<>();
        one1.addRule("one", new TestRule());

        final Rules<String> one2 = new Rules<>();
        one2.addRule("one", new TestRule());

        assertEquals("The two rules should match", one1, one2);
    }

    @Test
    public void shouldSerialiseToEqualObject() {
        Rules deserialise = JSONSerialiser.deserialise(json, Rules.class);
        final String thisSerialise = new String(JSONSerialiser.serialise(this.rules, true));
        final String thatSerialise = new String(JSONSerialiser.serialise(deserialise, true));

        assertEquals("Using the deserliased object in the JSONSerialiser should mean that the two strings are the same", thisSerialise, thatSerialise);
        assertEquals("The rules object that has been serialised and deserialised should match the original object", rules, deserialise);
    }

    @Test
    public void shouldSerialiseTo() {
        final String text = String.format("{%n" +
                "  \"message\" : \"Age off and visibility filtering\",%n" +
                "  \"rules\" : {%n" +
                "    \"ageOffRule\" : {%n" +
                "      \"class\" : \"uk.gov.gchq.palisade.policy.TestRule\"%n" +
                "    }%n" +
                "  }%n" +
                "}");

        assertEquals("The String json rule object should match the json serialised rule object", text, new String(json));
    }

    @Test
    public void shouldDeserialiseText() {
        final String text = String.format("{%n" +
                "  \"message\" : \"Age off and visibility filtering\",%n" +
                "  \"rules\" : {%n" +
                "    \"ageOffRule\" : {%n" +
                "      \"class\" : \"uk.gov.gchq.palisade.policy.TestRule\"%n" +
                "    }%n" +
                "  }%n" +
                "}");

        final Rules deserialise = JSONSerialiser.deserialise(text, Rules.class);
        assertEquals("When the String json object has been deserialised it should match the original rule object", rules, deserialise);

    }
}
