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
import java.awt.image.DataBufferInt;

/**
 *
 */
public abstract class AbstractIntFastImage extends AbstractFastImage {

    protected final int[] ints;

    protected AbstractIntFastImage(BufferedImage image, int pixelLength) {
        super(image, pixelLength);
        ints = getData(image);
    }

    private int[] getData(BufferedImage image) {
        return ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    }

    @Override
    protected void copyPixelsTo(BufferedImage copy) {
        int[] copyInts = getData(copy);
        // Copy data can be slightly smaller.
        // TODO! find out what the extra ints are used for and determine whether they need to be copied.
        System.arraycopy(ints, 0, copyInts, 0, copyInts.length);
    }

}
