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

import org.junit.Assert;
import org.junit.Test;

import uk.gov.gchq.palisade.rule.WrappedRule;

import java.util.function.UnaryOperator;

import static org.junit.Assert.assertEquals;

public class WrappedRuleTest {

    @Test
    public void shouldConstruct1ArgumentWithNoErrors() {
        // Given
        WrappedRule<String> rule1 = new WrappedRule<>(new TestRule(), null, null);
        WrappedRule<String> rule2 = new WrappedRule<>(null, o -> o, null);
        WrappedRule<String> rule3 = new WrappedRule<>(null, null, o -> true);

        // Then
        Assert.assertNotNull("rule1 rule should not be null", rule1.getRule());
        Assert.assertNotNull("rule2 function should not be null", rule2.getFunction());
        Assert.assertNotNull("rule3 predicate should not be null", rule3.getPredicate());
    }

    @Test
    public void shouldNotConstruct0Arguments() {
        try {
            // When
            new WrappedRule<>(null, null, null);
            Assert.fail("exception expected");
        } catch (IllegalArgumentException e) {
            // Then
            assertEquals("The IllegalArgumentException should be thrown and show a non null error", "Only one constructor parameter can be non-null", e.getMessage());
        }
    }

    @Test
    public void shouldNotConstruct2Arguments() {
        try {
            //when
            new WrappedRule<>(new TestRule(), o -> o, null);
            Assert.fail("exception expected");
        } catch (IllegalArgumentException e) {
            //then
            assertEquals("The IllegalArgumentException should be thrown and show a non null error", "Only one constructor parameter can be non-null", e.getMessage());
        }
        try {
            //when
            new WrappedRule<>(new TestRule(), null, o -> true);
            Assert.fail("exception expected");
        } catch (IllegalArgumentException e) {
            //then
            assertEquals("The IllegalArgumentException should be thrown and show a non null error", "Only one constructor parameter can be non-null", e.getMessage());
        }
        try {
            //when
            new WrappedRule<>(null, (UnaryOperator<Object>) Object::toString, o -> true);
            Assert.fail("exception expected");
        } catch (IllegalArgumentException e) {
            //then
            assertEquals("The IllegalArgumentException should be thrown and show a non null error", "Only one constructor parameter can be non-null", e.getMessage());
        }
    }

    @Test
    public void shouldNotConstruct3Arguments() {
        try {
            //when
            new WrappedRule<>(new TestRule(), o -> o, o -> true);
            Assert.fail("exception expected");
        } catch (IllegalArgumentException e) {
            //then
            assertEquals("The IllegalArgumentException should be thrown and show a non null error", "Only one constructor parameter can be non-null", e.getMessage());
        }

    }
}
