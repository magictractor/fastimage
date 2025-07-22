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

import java.awt.Color;

import uk.co.magictractor.fastimage.pixels.peep.FastImage;
import uk.co.magictractor.fastimage.rgb.Rgb;

/**
 * Used for drawing lines and single colour fills.
 */
public class SetColourFastImageConsumer implements FastImageConsumer {

    public static SetColourFastImageConsumer ofRgb(Rgb rgb) {
        return new SetColourFastImageConsumer(rgb);
    }

    public static SetColourFastImageConsumer ofAwtColor(Color color) {
        return new SetColourFastImageConsumer(Rgb.ofAwtColor(color));
    }

    private final Rgb colour;

    private SetColourFastImageConsumer(Rgb colour) {
        this.colour = colour;
    }

    @Override
    public void accept(FastImage image, int pos, int x, int y) {
        image.setRgb(pos, colour);
    }

}
