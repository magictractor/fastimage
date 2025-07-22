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
package uk.co.magictractor.fastimage.rgb;

import com.google.common.base.MoreObjects;

/**
 *
 */
public class NamedRgbRange extends RgbRange {

    private final String name;

    public NamedRgbRange(String name, int redFrom, int redTo, int greenFrom, int greenTo, int blueFrom, int blueTo) {
        super(redFrom, redTo, greenFrom, greenTo, blueFrom, blueTo);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .toString();
    }

}
