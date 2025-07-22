/**
 * Copyright 2019 Ken Dobson
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
package uk.co.magictractor.fastimage.rgb;

/**
 *
 */
public class RgbRangeTest {

    public static void checkIntersections(RgbRange... ranges) {
        for (int i = 0; i < ranges.length; i++) {
            for (int j = i + 1; j < ranges.length; j++) {
                if (intersects(ranges[i], ranges[j])) {
                    throw new AssertionError("ranges intersect: " + ranges[i] + " and " + ranges[j]);
                }
            }
        }
    }

    private static boolean intersects(RgbRange rgb1, RgbRange rgb2) {
        return intersects(rgb1.getRedFrom(), rgb1.getRedTo(), rgb2.getRedFrom(), rgb2.getRedTo())
                && intersects(rgb1.getGreenFrom(), rgb1.getGreenTo(), rgb2.getGreenFrom(), rgb2.getGreenTo())
                && intersects(rgb1.getBlueFrom(), rgb1.getBlueTo(), rgb2.getBlueFrom(), rgb2.getBlueTo());
    }

    private static boolean intersects(int rgb1From, int rgb1To, int rgb2From, int rgb2To) {
        return !(rgb1To < rgb2From || rgb2To < rgb1From);
    }

}
