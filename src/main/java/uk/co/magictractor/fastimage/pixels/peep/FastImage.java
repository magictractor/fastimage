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
package uk.co.magictractor.fastimage.pixels.peep;

import java.awt.image.BufferedImage;

import uk.co.magictractor.fastimage.consumer.FastImageConsumer;
import uk.co.magictractor.fastimage.pixels.PixelBox;
import uk.co.magictractor.fastimage.rgb.Rgb;

/**
 * <p>
 * Fast access to RGB data arrays in BufferedImages.
 * </p>
 * <p>
 * See
 * https://stackoverflow.com/questions/6524196/java-get-pixel-array-from-image.
 * </p>
 */
public interface FastImage {

    public static FastImage forImage(BufferedImage image) {
        if (image == null) {
            throw new IllegalArgumentException("image must not be null");
        }

        switch (image.getType()) {
            case BufferedImage.TYPE_INT_RGB:
                return new IntRGBFastImage(image);
            case BufferedImage.TYPE_INT_ARGB:
            case BufferedImage.TYPE_INT_ARGB_PRE:
                return new IntARGBFastImage(image);
            case BufferedImage.TYPE_3BYTE_BGR:
                return new ByteBGRFastImage(image);
            case BufferedImage.TYPE_4BYTE_ABGR:
            case BufferedImage.TYPE_4BYTE_ABGR_PRE:
                return new ByteABGRFastImage(image);
            default:
                throw new IllegalStateException("Code must be modified to handle image type " + image.getType());
        }

    }

    public int getWidth();

    public int getHeight();

    Rgb getRgb(int x, int y);

    /**
     * <p>
     * Get an RGB colour from a position. If the position is unknown, use
     * {@link #getRgb(int, int)}.
     * </p>
     * <p>
     * Generally used in {@link FastImageConsumer} implementations.
     * </p>
     */
    Rgb getRgb(int pos);

    void setRgb(int x, int y, Rgb newColour);

    /**
     * <p>
     * Set an RGB colour at a position. If the position is unknown, use
     * {@link #setRgb(int, int, Rgb)}.
     * </p>
     * <p>
     * Generally used in {@link FastImageConsumer} implementations.
     * </p>
     */
    void setRgb(int pos, Rgb newColour);

    void forAllPixels(FastImageConsumer consumer);

    void forBoxPixels(PixelBox pixelBox, FastImageConsumer consumer);

    default void forBoxOutlinePixels(PixelBox pixelBox, FastImageConsumer consumer) {
        forBoxOutlinePixels(pixelBox, 1, consumer);
    }

    void forBoxOutlinePixels(PixelBox pixelBox, int thickness, FastImageConsumer consumer);

    FastImage copy();

    /**
     * <p>
     * Returns the BufferedImage that owns the data buffer used by this
     * FastImage instance.
     * </p>
     * <p>
     * This is typically used to perform tasks available with BufferedImages
     * that cannot be performed on FastImages, such as saving to file using
     * ImageIO, or more complexes graphics operations.
     * </p>
     */
    BufferedImage asBufferedImage();

}
