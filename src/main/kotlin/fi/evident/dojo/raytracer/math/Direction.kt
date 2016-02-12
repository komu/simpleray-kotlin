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

    operator fun plus(v: Direction)  = Direction(x + v.x, y + v.y, z + v.z)
    operator fun unaryMinus()             = Direction(-x, -y, -z)
    operator fun minus(v: Direction) = Direction(x - v.x, y - v.y, z - v.z)
    operator fun times(s: Double)    = Direction(s * x, s * y, s * z)
    operator fun div(s: Double)      = this * (1/s)

    infix fun dot(v: Direction)   = x*v.x + y*v.y + z*v.z
    infix fun dot(v: Point)       = x*v.x + y*v.y + z*v.z
    infix fun cross(v: Direction) = Direction(y*v.z - z*v.y, z*v.x - x*v.z, x*v.y - y*v.x)

    val magnitude: Double
       get() = sqrt(magnitudeSquared)

    val magnitudeSquared: Double
       get() = x*x + y*y + z*z

    override fun toString() = "[$x $y $z]"

    override fun equals(other: Any?) = other is Direction && x == other.x && y == other.y && z == other.z
    override fun hashCode() = ((x.toInt() * 79) + y.toInt()) * 79 + z.toInt()
}

operator fun Double.times(v: Direction) = v*this
