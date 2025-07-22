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

/**
 *
 */
public class RgbRange {

    private final int redFrom;
    private final int redTo;
    private final int greenFrom;
    private final int greenTo;
    private final int blueFrom;
    private final int blueTo;

    public RgbRange(int redFrom, int redTo, int greenFrom, int greenTo, int blueFrom, int blueTo) {
        this.redFrom = redFrom;
        this.redTo = redTo;
        this.greenFrom = greenFrom;
        this.greenTo = greenTo;
        this.blueFrom = blueFrom;
        this.blueTo = blueTo;
    }

    public int getRedFrom() {
        return redFrom;
    }

    public int getRedTo() {
        return redTo;
    }

    public int getGreenFrom() {
        return greenFrom;
    }

    public int getGreenTo() {
        return greenTo;
    }

    public int getBlueFrom() {
        return blueFrom;
    }

    public int getBlueTo() {
        return blueTo;
    }

    public boolean contains(Rgb rgb) {
        return redFrom <= rgb.getRed()
                && redTo >= rgb.getRed()
                && greenFrom <= rgb.getGreen()
                && greenTo >= rgb.getGreen()
                && blueFrom <= rgb.getBlue()
                && blueTo >= rgb.getBlue();
    }

    /**
     * Not recommended for general use, this is intended for finding pixels
     * close to a range but not in it during development.
     */
    public double distanceTo(Rgb rgb) {
        double r = distanceTo(rgb.getRed(), redFrom, redTo);
        double g = distanceTo(rgb.getGreen(), greenFrom, greenTo);
        double b = distanceTo(rgb.getBlue(), blueFrom, blueTo);

        return Math.sqrt((r * r) + (g * g) + (b * b));
    }

    private int distanceTo(int value, int min, int max) {
        if (value < min) {
            return min - value;
        }
        else if (value > max) {
            return value - max;
        }
        else {
            return 0;
        }
    }

}
