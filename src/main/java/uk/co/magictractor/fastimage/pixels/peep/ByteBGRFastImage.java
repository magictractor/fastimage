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
 * Fast access for BufferedImage type 5.
 */
public class ByteBGRFastImage extends AbstractByteFastImage {

    protected ByteBGRFastImage(BufferedImage image) {
        super(image, 3);
    }

    @Override
    public Rgb getRgb(int pos) {
        return Rgb.ofRgbBytes(bytes[pos + 2], bytes[pos + 1], bytes[pos]);
    }

    @Override
    public void setRgb(int pos, Rgb newRgb) {
        bytes[pos] = (byte) newRgb.getBlue();
        bytes[pos + 1] = (byte) newRgb.getGreen();
        bytes[pos + 2] = (byte) newRgb.getRed();
    }

}
