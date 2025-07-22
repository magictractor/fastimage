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

import static uk.co.magictractor.fastimage.hex.HexUtil.toTwoDigitHexString;

import java.awt.Color;

import com.google.common.base.MoreObjects;
import com.google.common.base.MoreObjects.ToStringHelper;
import com.google.common.base.Strings;

/**
 * Interface for representation of 24-bit RGB colours perhaps with 8-bit alpha
 * channel.
 */
public interface Rgb {

    public static Rgb ofRgbInts(int red, int green, int blue) {
        return new RgbImpl(red, green, blue);
    }

    public static Rgb ofRgbaInts(int red, int green, int blue, int alpha) {
        return new RgbaImpl(red, green, blue, alpha);
    }

    public static Rgb ofRgbBytes(byte red, byte green, byte blue) {
        return new RgbImpl(0xff & red, 0xff & green, 0xff & blue);
    }

    public static Rgb ofRgbaBytes(byte red, byte green, byte blue, byte alpha) {
        return new RgbaImpl(0xff & red, 0xff & green, 0xff & blue, 0xff & alpha);
    }

    public static Rgb ofRgbaInt(int rgb) {
        return new RgbaImpl((rgb >> 24) & 0xff, (rgb >> 16) & 0xff, (rgb >> 8) & 0xff, rgb & 0xff);
    }

    public static Rgb ofArgbInt(int rgb) {
        return new RgbaImpl((rgb >> 16) & 0xff, (rgb >> 8) & 0xff, rgb & 0xff, (rgb >> 24) & 0xff);
    }

    public static Rgb ofRgbInt(int rgb) {
        return new RgbImpl((rgb >> 16) & 0xff, (rgb >> 8) & 0xff, rgb & 0xff);
    }

    public static Rgb ofRgb565Short(short rgb) {
        int r5 = (rgb >> 8) & 0xf8;
        int g6 = (rgb >> 3) & 0xfc;
        int b5 = (rgb << 3) & 0xf8;
        // The ors here repeat the highest bits in the low bits of the byte, so we can get 255 etc
        return new RgbImpl(r5 | r5 >> 5, g6 | g6 >> 6, b5 | b5 >> 5);
    }

    public static Rgb ofAwtColor(Color color) {
        // could do something with color.getRGB() instead
        return ofRgbaInts(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    //    public static Rgba ofRgbaInt(int rgb) {
    //        return new RgbImpl((rgb >> 16) & 0xff, (rgb >> 8) & 0xff, rgb & 0xff);
    //    }

    public static String toString(Rgb rgb) {
        ToStringHelper helper = MoreObjects.toStringHelper(rgb.getClass());

        //         .add("rgb", RgbConverter.HASH_PREFIX_LOWER.reverse(rgb));
        int rrggbb = rgb.getRed() << 16 | rgb.getGreen() << 8 | rgb.getBlue();
        helper.add("rbg", "#" + Strings.padStart(Integer.toHexString(rrggbb), 6, '0'));

        if (rgb.hasAlpha()) {
            helper.add("alpha", toTwoDigitHexString(rgb.getAlpha()));
        }

        return helper.toString();
    }

    public static int hashCode(Rgb rgb) {
        return (rgb.getRed() << 24) ^ (rgb.getGreen() << 16) ^ (rgb.getBlue() << 8) ^ rgb.getAlpha();
    }

    public static boolean equals(Rgb p, Rgb q) {
        if (p == null) {
            return q == null;
        }

        if (p.getRed() != q.getRed()) {
            return false;
        }
        if (p.getGreen() != q.getGreen()) {
            return false;
        }
        if (p.getBlue() != q.getBlue()) {
            return false;
        }

        return p.getAlpha() == q.getAlpha();
    }

    int getRed();

    int getGreen();

    int getBlue();

    default int getAlpha() {
        return 255;
    }

    default boolean hasAlpha() {
        return false;
    }

    /**
     * Returns a BufferedImage.TYPE_INT_ARGB value as used with
     * {@link java.awt.image.BufferedImage#getRGB} and
     * {@link java.awt.image.BufferedImage#setRGB}.
     *
     * @return
     */
    default int asAwtRgb() {
        return (getAlpha() << 24) ^ (getRed() << 16) ^ (getGreen() << 8) ^ getBlue();
    }

}
