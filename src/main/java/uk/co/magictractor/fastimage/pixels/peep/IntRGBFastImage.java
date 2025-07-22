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
package uk.co.magictractor.fastimage.pixels.peep;

import java.awt.image.BufferedImage;

import uk.co.magictractor.fastimage.rgb.Rgb;

/**
 * Fast access for BufferedImage type 1.
 */
public class IntRGBFastImage extends AbstractIntFastImage {

    protected IntRGBFastImage(BufferedImage image) {
        super(image, 1);
    }

    @Override
    public Rgb getRgb(int pos) {
        return Rgb.ofRgbInt(ints[pos]);
    }

    @Override
    public void setRgb(int pos, Rgb newRgb) {
        ints[pos] = (newRgb.getRed() << 16) | (newRgb.getGreen() << 8) | newRgb.getBlue();
    }

}
