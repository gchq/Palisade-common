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
package uk.gov.gchq.palisade.policy;

import org.junit.Assert;
import org.junit.Test;

import uk.gov.gchq.palisade.rule.SerializableUnaryOperator;
import uk.gov.gchq.palisade.rule.WrappedRule;

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

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotConstruct0Arguments() {
        //When
        new WrappedRule<>(null, null, null);
        //Then it should throw an IllegalArgumentException
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotConstructNullPredicate() {
        //When
        new WrappedRule<>(new TestRule(), o -> o, null);
        // Then it should throw an IllegalArgumentException
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotConstructNullFunction() {
        //When
        new WrappedRule<>(new TestRule(), null, o -> true);
        //Then it should throw an IllegalArgumentException
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotConstructNullRule() {
        //When
        new WrappedRule<>(null, (SerializableUnaryOperator<String>) String::toString, o -> true);
        //Then it should throw an IllegalArgumentException
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotConstruct3Arguments() {
        //When
        new WrappedRule<>(new TestRule(), o -> o, o -> true);
        //Then it should throw an IllegalArgumentException
    }

}
