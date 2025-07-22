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
package uk.co.magictractor.fastimage.hex;

import com.google.common.base.Strings;

/**
 *
 */
public final class HexUtil {

    private HexUtil() {
    }

    public static String toTwoDigitHexString(int colour) {
        // check size?
        return "0x" + Strings.padStart(Integer.toHexString(colour), 2, '0');
    }

    public static String toHexString(int colour) {
        return "0x" + Strings.padStart(Integer.toHexString(colour), 8, '0');
    }

}
