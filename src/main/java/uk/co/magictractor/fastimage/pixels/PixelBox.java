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

import java.awt.Rectangle;

import com.google.common.base.MoreObjects;

/**
 *
 */
public class PixelBox {

    private final int left;
    private final int right;
    private final int top;
    private final int bottom;

    public static PixelBox ofEdges(int left, int right, int top, int bottom) {
        return new PixelBox(left, right, top, bottom);
    }

    public static PixelBox ofSize(int left, int top, int width, int height) {
        return new PixelBox(left, left + width - 1, top, top + height - 1);
    }

    private PixelBox(int left, int right, int top, int bottom) {
        if (right < left) {
            throw new IllegalArgumentException(
                "left must be less than or equal to right, left=" + left + ", right=" + right);
        }
        if (bottom < top) {
            throw new IllegalArgumentException(
                "top must be less than or equal to bottom, top=" + top + ", bottom=" + bottom);
        }
        if (left < 0) {
            throw new IllegalArgumentException("left must be greater than or equal to zero, left=" + left);
        }
        if (top < 0) {
            throw new IllegalArgumentException("top must be greater than or equal to zero, top=" + top);
        }

        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public int getTop() {
        return top;
    }

    public int getBottom() {
        return bottom;
    }

    public int getWidth() {
        return 1 + right - left;
    }

    public int getHeight() {
        return 1 + bottom - top;
    }

    public int getPixelCount() {
        return getWidth() * getHeight();
    }

    public Rectangle asAwtRectangle() {
        return new Rectangle(getLeft(), getTop(), getWidth() - 1, getHeight() - 1);
    }

    public PixelBox inner() {
        return inner(1);
    }

    public PixelBox inner(int margin) {
        return new PixelBox(left + margin, right - margin, top + margin, bottom - margin);
    }

    public PixelBox outer() {
        return outer(1);
    }

    public PixelBox outer(int margin) {
        return new PixelBox(left - margin, right + margin, top - margin, bottom + margin);
    }

    public PixelBox subBox(float leftRatio, float rightRatio, float topRatio, float bottomRatio) {
        checkRatio(leftRatio);
        checkRatio(rightRatio);
        checkRatio(topRatio);
        checkRatio(bottomRatio);

        int w = getWidth();
        int h = getHeight();

        int subLeft = left + Math.round((leftRatio * w) - 0.5f);
        int subRight = left + Math.round((rightRatio * w) + 0.5f);
        int subTop = top + Math.round((topRatio * h) - 0.5f);
        int subBottom = top + Math.round((bottomRatio * h) + 0.5f);

        return new PixelBox(subLeft, subRight, subTop, subBottom);
    }

    private void checkRatio(float ratio) {
        if (ratio < 0 || ratio > 1) {
            throw new IllegalArgumentException("ratio must be between zero and one (inclusive)");
        }
    }

    public PixelBox shift(float rightRatio, float downRatio) {
        return shift(Math.round(rightRatio * getWidth()), Math.round(downRatio * getHeight()));
    }

    public PixelBox shift(int rightOffset, int downOffset) {
        return new PixelBox(left + rightOffset, right + rightOffset, top + downOffset, bottom + downOffset);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!getClass().equals(obj.getClass())) {
            return false;
        }

        PixelBox other = (PixelBox) obj;
        return left == other.left
                && right == other.right
                && top == other.top
                && bottom == other.bottom;
    }

    // todo! hashcode

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("left", left)
                .add("right", right)
                .add("top", top)
                .add("bottom", bottom)
                .toString();
    }

}
