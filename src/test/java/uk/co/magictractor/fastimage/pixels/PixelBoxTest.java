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
package uk.co.magictractor.fastimage.pixels;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 *
 */
public class PixelBoxTest {

    @Test
    public void testOfEdges() {
        PixelBox box = PixelBox.ofEdges(10, 20, 5, 8);

        assertThat(box.getLeft()).isEqualTo(10);
        assertThat(box.getRight()).isEqualTo(20);
        assertThat(box.getTop()).isEqualTo(5);
        assertThat(box.getBottom()).isEqualTo(8);

        assertThat(box.getWidth()).isEqualTo(11);
        assertThat(box.getHeight()).isEqualTo(4);
    }

    @Test
    public void testOfSize() {
        PixelBox box = PixelBox.ofSize(20, 10, 5, 3);

        assertThat(box.getLeft()).isEqualTo(20);
        assertThat(box.getTop()).isEqualTo(10);
        assertThat(box.getWidth()).isEqualTo(5);
        assertThat(box.getHeight()).isEqualTo(3);

        assertThat(box.getRight()).isEqualTo(24);
        assertThat(box.getBottom()).isEqualTo(12);
    }

    @Test
    public void testInner() {
        PixelBox box = PixelBox.ofEdges(10, 20, 5, 8).inner();

        assertThat(box.getLeft()).isEqualTo(11);
        assertThat(box.getRight()).isEqualTo(19);
        assertThat(box.getTop()).isEqualTo(6);
        assertThat(box.getBottom()).isEqualTo(7);

        assertThat(box.getWidth()).isEqualTo(9);
        assertThat(box.getHeight()).isEqualTo(2);
    }

    @Test
    public void testOuter() {
        PixelBox box = PixelBox.ofEdges(10, 20, 5, 8).outer();

        assertThat(box.getLeft()).isEqualTo(9);
        assertThat(box.getRight()).isEqualTo(21);
        assertThat(box.getTop()).isEqualTo(4);
        assertThat(box.getBottom()).isEqualTo(9);

        assertThat(box.getWidth()).isEqualTo(13);
        assertThat(box.getHeight()).isEqualTo(6);
    }

    @Test
    public void testHorizontalLine() {
        PixelBox box = PixelBox.ofEdges(20, 30, 10, 10);

        assertThat(box.getWidth()).isEqualTo(11);
        assertThat(box.getHeight()).isEqualTo(1);
    }

    @Test
    public void testVerticalLine() {
        PixelBox box = PixelBox.ofEdges(50, 50, 100, 200);

        assertThat(box.getHeight()).isEqualTo(101);
        assertThat(box.getWidth()).isEqualTo(1);
    }

    @Test
    public void testPoint() {
        PixelBox box = PixelBox.ofEdges(40, 40, 40, 40);

        assertThat(box.getHeight()).isEqualTo(1);
        assertThat(box.getWidth()).isEqualTo(1);
    }

}
