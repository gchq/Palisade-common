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

package uk.gov.gchq.palisade.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestUtil.class);

    public static final File TMP_DIRECTORY;

    static {
        final String tmpDirectoryProperty = System.getProperty("java.io.tmpdir");

        if (null != tmpDirectoryProperty) {
            TMP_DIRECTORY = new File(tmpDirectoryProperty);
        } else {
            LOGGER.warn("Could not determine default temporary directory, using current directory.");
            TMP_DIRECTORY = new File(".");
        }
    }

    /**
     * Compare two streams for equality. Each stream must be of the same length and contain the same elements (by
     * value). The streams are sorted beforehand. Therefore T must be naturally comparable.
     *
     * @param expected first stream
     * @param actual   second stream
     * @param <T>      type of list element
     * @return true if streams are equal
     */
    public static <T> boolean streamEqual(final Stream<? extends T> expected, final Stream<? extends T> actual) {
        Stream<? extends T> sortExpected = expected.sorted();
        Stream<? extends T> sortActual = actual.sorted();
        List<? extends T> lhs = sortExpected.collect(Collectors.toList());
        List<? extends T> rhs = sortActual.collect(Collectors.toList());
        return lhs.equals(rhs);
    }
}
