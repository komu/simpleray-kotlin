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

import java.lang.Math.sqrt

fun normalize(v: Direction) =
    v / v.magnitude

class Direction(val x: Double, val y: Double, val z: Double) {

    fun plus(v: Direction)  = Direction(x + v.x, y + v.y, z + v.z)
    fun minus()             = Direction(-x, -y, -z)
    fun minus(v: Direction) = Direction(x - v.x, y - v.y, z - v.z)
    fun times(s: Double)    = Direction(s * x, s * y, s * z)
    fun div(s: Double)      = this * (1/s)

    fun dot(v: Direction)   = x*v.x + y*v.y + z*v.z
    fun dot(v: Point)       = x*v.x + y*v.y + z*v.z
    fun cross(v: Direction) = Direction(y*v.z - z*v.y, z*v.x - x*v.z, x*v.y - y*v.x)

    val magnitude: Double
       get() = sqrt(magnitudeSquared)

    val magnitudeSquared: Double
       get() = x*x + y*y + z*z

    fun toString() = "[$x $y $z]"

    fun equals(o: Any?) = o is Direction && x == o.x && y == o.y && z == o.z
    fun hashCode() = ((x.toInt() * 79) + y.toInt()) * 79 + z.toInt()
}

fun Double.times(v: Direction) = v*this
