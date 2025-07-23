/**
 * Copyright 2021 Ken Dobson
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

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.Color;

import org.junit.jupiter.api.Test;

/**
 *
 */
public class RgbTest {

    @Test
    public void testOfAwtColor() {
        Color awtColor = new Color(255, 100, 50, 128);
        Rgb actualRgb = Rgb.ofAwtColor(awtColor);
        Rgb expectedRgb = Rgb.ofRgbaInts(255, 100, 50, 128);
        assertThat(actualRgb).isEqualTo(expectedRgb);
    }

    // Used in Unity images (DXT1 and DXT5) but not in BuffereredImage,
    // so no FastImage implementation. Other RGB types tested via FastImage tests.
    @Test
    public void testOf565Short() {
        check565Short(0xf800, 255, 0, 0);
        check565Short(0x07e0, 0, 255, 0);
        check565Short(0x001f, 0, 0, 255);
    }

    private void check565Short(int shortValue, int expectedRed, int expectedGreen, int expectedBlue) {
        Rgb actualRgb = Rgb.ofRgb565Short((short) shortValue);
        Rgb expectedRgb = Rgb.ofRgbInts(expectedRed, expectedGreen, expectedBlue);
        assertThat(actualRgb).isEqualTo(expectedRgb);
    }

}
