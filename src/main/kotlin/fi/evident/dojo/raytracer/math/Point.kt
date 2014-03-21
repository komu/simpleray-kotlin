/*
 * Copyright (c) 2011-2012 Evident Solutions Oy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package fi.evident.dojo.raytracer.math

class Point(val x: Double, val y: Double, val z: Double) {

    class object {
        val ZERO = Point(0.0, 0.0, 0.0)
    }

    fun plus(v: Direction)  = Point(x + v.x, y + v.y, z + v.z)
    fun minus(v: Direction) = Point(x - v.x, y - v.y, z - v.z)
    fun minus(v: Point)     = Direction(x - v.x, y - v.y, z - v.z)

    override fun toString() = "[$x $y $z]"

    override fun equals(other: Any?) = other is Point && x == other.x && y == other.y && z == other.z
    override fun hashCode() = ((x.toInt() * 79) + y.toInt()) * 79 + z.toInt()
}
