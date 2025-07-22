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
package uk.co.magictractor.fastimage.consumer;

import java.util.Objects;

import uk.co.magictractor.fastimage.pixels.peep.FastImage;

/**
 *
 */
//public static RgbTransformResult monochrome(Rgb rgb) {
//    // TODO! rounding/helper
//    int gray = (rgb.getRed() + rgb.getGreen() + rgb.getBlue()) / 3;
//
//    return RgbTransformResult.transformTo(Rgb.ofRgbInts(gray, gray, gray));
//}
//
//public static RgbTransformResult lighten(Rgb rgb, float factor) {
//    int lightRed = lighten(rgb.getRed(), factor);
//    int lightGreen = lighten(rgb.getGreen(), factor);
//    int lightBlue = lighten(rgb.getBlue(), factor);
//
//    return RgbTransformResult.transformTo(Rgb.ofRgbInts(lightRed, lightGreen, lightBlue));
//}
//
//private static int lighten(int colourChannel, float factor) {
//    return colourChannel + Math.round((255 - colourChannel) * factor);
//}
@FunctionalInterface
public interface FastImageConsumer {

    /**
     * Implementations should prefer to use {@link FastImage#getRgb} and
     * {@link FastImage#setRgb} with pos, ignoring x and y values. Doing so is
     * more efficient.
     */
    void accept(FastImage image, int pos, int x, int y);

    default FastImageConsumer andThen(FastImageConsumer after, FastImageConsumer... moreAfter) {
        Objects.requireNonNull(after);
        return (image, pos, x, y) -> {
            accept(image, pos, x, y);
            after.accept(image, pos, x, y);
            for (FastImageConsumer anotherAfter : moreAfter) {
                anotherAfter.accept(image, pos, x, y);
            }
        };
    }

}
