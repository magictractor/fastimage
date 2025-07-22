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

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

import com.google.common.base.MoreObjects;

import org.junit.jupiter.api.Test;

import uk.co.magictractor.fastimage.consumer.FastImageConsumer;
import uk.co.magictractor.fastimage.hex.HexUtil;
import uk.co.magictractor.fastimage.pixels.PixelBox;
import uk.co.magictractor.fastimage.rgb.Rgb;

/**
 *
 */
public abstract class AbstractFastImageTest {

    public static final Rgb RED = Rgb.ofRgbInts(255, 0, 0);
    public static final Rgb GREEN = Rgb.ofRgbInts(0, 255, 0);
    public static final Rgb BLUE = Rgb.ofRgbInts(0, 0, 255);
    public static final Rgb BLACK = Rgb.ofRgbInts(0, 0, 0);
    public static final Rgb WHITE = Rgb.ofRgbInts(255, 255, 255);

    private final BufferedImage image;
    private final FastImage fastImage;
    private final int pixelLength;

    protected AbstractFastImageTest(int imageType, int pixelLength) {
        this.image = new BufferedImage(16, 16, imageType);
        //        if (image.getColorModel().hasAlpha()) {
        //            Graphics2D g = image.createGraphics();
        //            g.setComposite(AlphaComposite.SrcOver);
        //            g.setPaint(Color.lightGray);
        //            System.out.println(g.getPaint());
        //            g.fillRect(0, 0, image.getWidth(), image.getHeight());
        //            g.dispose();
        //        }

        this.fastImage = FastImage.forImage(image);
        this.pixelLength = pixelLength;
    }

    @Test
    public void testGetPixel() {
        image.setRGB(2, 5, RED.asAwtRgb());
        image.setRGB(3, 6, GREEN.asAwtRgb());
        image.setRGB(4, 7, BLUE.asAwtRgb());

        assertThat(fastImage.getRgb(2, 5)).isEqualTo(RED);
        assertThat(fastImage.getRgb(3, 6)).isEqualTo(GREEN);
        assertThat(fastImage.getRgb(4, 7)).isEqualTo(BLUE);
    }

    @Test
    public void testSetPixel() {
        fastImage.setRgb(2, 5, RED);
        fastImage.setRgb(3, 6, GREEN);
        fastImage.setRgb(4, 7, BLUE);

        if ((image.getRGB(2, 5)) == RED.asAwtRgb()
                && (image.getRGB(3, 6)) == GREEN.asAwtRgb()
                && (image.getRGB(4, 7)) == BLUE.asAwtRgb()) {
            return;
        }

        for (int x = 0; x <= 15; x++) {
            for (int y = 0; y <= 15; y++) {
                int rgb = image.getRGB(x, y) & 0x00ffffff;
                if (rgb != 0) {
                    throw new RuntimeException(
                        "x=" + x + ", y=" + y + " -> " + HexUtil.toHexString(rgb));
                }
            }
        }
    }

    @Test
    public void testSupportsAlpha() {
        Rgb rgb = fastImage.getRgb(0, 0);

        assertThat(rgb.hasAlpha()).isEqualTo(image.getColorModel().hasAlpha());
    }

    @Test
    public void testForBoxPixels() {
        Consumer consumer = new Consumer();

        PixelBox box = PixelBox.ofSize(1, 5, 4, 3);
        fastImage.forBoxPixels(box, consumer);

        consumer.assertContains(1, 5);
        consumer.assertContains(2, 5);
        consumer.assertContains(3, 5);
        consumer.assertContains(4, 5);
        consumer.assertContains(1, 6);
        consumer.assertContains(2, 6);
        consumer.assertContains(3, 6);
        consumer.assertContains(4, 6);
        consumer.assertContains(1, 7);
        consumer.assertContains(2, 7);
        consumer.assertContains(3, 7);
        consumer.assertContains(4, 7);
        consumer.assertDone();
    }

    @Test
    public void testForBoxPixels_dot() {
        Consumer consumer = new Consumer();

        PixelBox box = PixelBox.ofSize(1, 5, 1, 1);
        fastImage.forBoxPixels(box, consumer);

        consumer.assertContains(1, 5);
        consumer.assertDone();
    }

    @Test
    public void testForBoxOutlinePixels() {
        Consumer consumer = new Consumer();

        PixelBox box = PixelBox.ofSize(1, 5, 4, 3);
        fastImage.forBoxOutlinePixels(box, consumer);

        consumer.assertContains(1, 5);
        consumer.assertContains(2, 5);
        consumer.assertContains(3, 5);
        consumer.assertContains(4, 5);
        consumer.assertContains(1, 6);
        consumer.assertContains(4, 6);
        consumer.assertContains(1, 7);
        consumer.assertContains(2, 7);
        consumer.assertContains(3, 7);
        consumer.assertContains(4, 7);
        consumer.assertDone();
    }

    @Test
    public void testForBoxOutlinePixels_dot() {
        Consumer consumer = new Consumer();

        PixelBox box = PixelBox.ofSize(1, 5, 1, 1);
        fastImage.forBoxOutlinePixels(box, consumer);

        consumer.assertContains(1, 5);
        consumer.assertDone();
    }

    @Test
    public void testForBoxOutlinePixels_noSpaceInsideMin() {
        Consumer consumer = new Consumer();

        PixelBox box = PixelBox.ofSize(1, 5, 2, 2);
        fastImage.forBoxOutlinePixels(box, consumer);

        consumer.assertContains(1, 5);
        consumer.assertContains(2, 5);
        consumer.assertContains(1, 6);
        consumer.assertContains(2, 6);
        consumer.assertDone();
    }

    @Test
    public void testForBoxOutlinePixels_noSpaceInsideTall() {
        Consumer consumer = new Consumer();

        PixelBox box = PixelBox.ofSize(1, 5, 2, 6);
        fastImage.forBoxOutlinePixels(box, consumer);

        consumer.assertContains(1, 5);
        consumer.assertContains(2, 5);
        consumer.assertContains(1, 6);
        consumer.assertContains(2, 6);
        consumer.assertContains(1, 7);
        consumer.assertContains(2, 7);
        consumer.assertContains(1, 8);
        consumer.assertContains(2, 8);
        consumer.assertContains(1, 9);
        consumer.assertContains(2, 9);
        consumer.assertContains(1, 10);
        consumer.assertContains(2, 10);
        consumer.assertDone();
    }

    private class Consumer implements FastImageConsumer {
        private final Set<PosXY> actual = new HashSet<>();

        @Override
        public void accept(FastImage image, int pos, int x, int y) {
            PosXY posXY = new PosXY(pos, x, y);
            if (!actual.add(posXY)) {
                throw new AssertionError("Duplicate: " + posXY);
            }
            // temp
            // System.out.println(posXY);
        }

        public void assertContains(int x, int y) {
            int pos = (x + (y * 16)) * pixelLength;
            PosXY expected = new PosXY(pos, x, y);

            if (!actual.remove(expected)) {
                throw new AssertionError("Unseen: " + expected);
            }
        }

        public void assertDone() {
            if (!actual.isEmpty()) {
                throw new AssertionError("Untested values remain: " + actual);
            }
        }
    }

    private static final class PosXY {
        private final int pos;
        private final int x;
        private final int y;

        /* default */ PosXY(int pos, int x, int y) {
            this.pos = pos;
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            return pos;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (!PosXY.class.equals(obj.getClass())) {
                return false;
            }

            PosXY other = (PosXY) obj;

            return other.pos == pos && other.x == x && other.y == y;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("pos", pos)
                    .add("x", x)
                    .add("y", y)
                    .toString();
        }
    }

}
