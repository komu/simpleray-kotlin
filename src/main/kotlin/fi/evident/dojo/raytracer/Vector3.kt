/*
 * Copyright (c) 2011 Evident Solutions Oy
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
package fi.evident.dojo.raytracer

import java.lang.Math.sqrt
import fi.evident.dojo.raytracer.math.square

class Vector3(val x: Double, val y: Double, val z: Double) {

    class object {
        val ZERO = Vector3(0.0, 0.0, 0.0)
    }

    fun plus(v: Vector3)  = Vector3(x + v.x, y + v.y, z + v.z)
    fun minus(v: Vector3) = Vector3(x - v.x, y - v.y, z - v.z)
    fun minus()           = this * -1.0
    fun times(s: Double)  = Vector3(s * x, s * y, s * z)
    fun div(s: Double)    = this * (1/s)

    fun dot(v: Vector3)   = x*v.x + y*v.y + z*v.z
    fun cross(v: Vector3) = Vector3(y*v.z - z*v.y, z*v.x - x*v.z, x*v.y - y*v.x)

    fun normalize() = this / magnitude

    val magnitude: Double
       get() = sqrt(magnitudeSquared)

    val magnitudeSquared: Double
       get() = dot(this)

    fun toString() = "[$x $y $z]"
}
