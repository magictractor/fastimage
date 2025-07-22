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

import uk.co.magictractor.fastimage.consumer.FastImageConsumer;
import uk.co.magictractor.fastimage.pixels.PixelBox;
import uk.co.magictractor.fastimage.rgb.Rgb;

/**
 *
 */
public abstract class AbstractFastImage implements FastImage {

    private final BufferedImage image;
    private final int width;
    private final int height;
    private final int pixelLength;
    private final int imageType;

    protected AbstractFastImage(BufferedImage image, int pixelLength) {
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.pixelLength = pixelLength;
        this.imageType = image.getType();
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public int getSize() {
        return width * height;
    }

    @Override
    public Rgb getRgb(int x, int y) {
        if (x >= width) {
            throw new IllegalArgumentException("x must be less than the image width");
        }
        if (y >= height) {
            throw new IllegalArgumentException("y must be less than the image height");
        }
        if (x < 0) {
            throw new IllegalArgumentException("x must not be less than zero, x=" + x);
        }
        if (y < 0) {
            throw new IllegalArgumentException("y must not be less than zero, y=" + y);
        }

        int pos = getPos(x, y);
        return getRgb(pos);
    }

    // hopefully this will be inlined by the compiler
    private int getPos(int x, int y) {
        return (x + (y * width)) * pixelLength;
    }

    @Override
    public abstract Rgb getRgb(int pos);

    @Override
    public void setRgb(int x, int y, Rgb newRgb) {
        int pos = getPos(x, y);
        setRgb(pos, newRgb);
    }

    @Override
    public abstract void setRgb(int pos, Rgb newRgb);

    //    public void transform(RgbTransform... transforms) {
    //        int maxPos = width * height * pixelLength;
    //
    //        for (int pos = 0; pos < maxPos; pos += pixelLength) {
    //            transform(pos, transforms);
    //        }
    //    }
    //
    //    private void transform(int pos, RgbTransform... transforms) {
    //        Rgb originalRgb = getRgb(pos);
    //        Rgb newRgb = originalRgb;
    //        boolean preserve = false;
    //
    //        for (RgbTransform transform : transforms) {
    //            RgbTransformResult transformResult = transform.transform(newRgb);
    //
    //            if (transformResult == null) {
    //                // null means no changes, keep processing
    //                // TODO! make this explicit and not-null?
    //                continue;
    //            }
    //
    //            if (transformResult.preservePixel()) {
    //                // preserve will usually be used with an immediate stop,
    //                // but it could be used to restore the original colour with
    //                // further processing
    //                newRgb = originalRgb;
    //            }
    //            else {
    //                newRgb = transformResult.newRgb();
    //            }
    //
    //            if (transformResult.stop()) {
    //                break;
    //            }
    //        }
    //
    //        if (!preserve) {
    //            setRgb(pos, newRgb);
    //        }
    //
    //    }

    @Override
    public void forAllPixels(FastImageConsumer consumer) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    //
    @Override
    public void forBoxOutlinePixels(PixelBox pixelBox, int thickness, FastImageConsumer consumer) {
        //throw new UnsupportedOperationException("not yet implemented");

        // Top edge
        //        for (int i=pixelBox.getLeft(); i<=pixelBox.getRight()-thickness; i++) {
        //            for (int j=pixelBox.getTop(); j<thickness; j++) {
        //
        //            }
        //        }

        // TODO! investigate infinite loop with PixelBox{left=1338, right=1339, top=299, bottom=342} and thinkness=1
        int pos = getPos(pixelBox.getLeft(), pixelBox.getTop());
        for (int y = pixelBox.getTop(); y <= pixelBox.getBottom(); y++) {
            for (int x = pixelBox.getLeft(); x <= pixelBox.getRight(); x++) {
                if (x == pixelBox.getLeft() + thickness
                        && y >= pixelBox.getTop() + thickness
                        && y <= pixelBox.getBottom() - thickness
                        && pixelBox.getWidth() > 2 * thickness) {
                    // inner, skip over empty space
                    x = pixelBox.getRight() - thickness;
                    continue;
                }

                // todo! increment pos instead of recalculating
                pos = getPos(x, y);

                // System.out.println("x=" + x + ", y=" + y);
                consumer.accept(this, pos, x, y);
            }
        }

    }

    @Override
    public void forBoxPixels(PixelBox pixelBox, FastImageConsumer consumer) {
        int bottom = pixelBox.getBottom();
        int left = pixelBox.getLeft();
        int right = pixelBox.getRight();
        for (int y = pixelBox.getTop(); y <= bottom; y++) {
            int pos = getPos(left, y);
            for (int x = left; x <= right; x++) {
                consumer.accept(this, pos, x, y);
                pos += pixelLength;
            }
        }
    }

    @Override
    public FastImage copy() {
        return FastImage.forImage(copyImage());
    }

    private BufferedImage copyImage() {
        // Use same type so data buffer can be copied
        BufferedImage copy = new BufferedImage(width, height, imageType);
        copyPixelsTo(copy);

        return copy;
    }

    protected abstract void copyPixelsTo(BufferedImage copy);

    @Override
    public BufferedImage asBufferedImage() {
        return image;
    }

}
