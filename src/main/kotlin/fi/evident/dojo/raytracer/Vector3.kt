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

import fi.evident.dojo.raytracer.MathUtils.sqrt
import fi.evident.dojo.raytracer.MathUtils.square

class Vector3(val x: Float, val y: Float, val z: Float) {

    class object {
        val ZERO = Vector3(0.flt, 0.flt, 0.flt)
    }

    fun plus(v: Vector3) = Vector3(x + v.x, y + v.y, z + v.z)
    fun minus(v: Vector3) = Vector3(x - v.x, y - v.y, z - v.z)
    fun times(s: Float) = Vector3(s * x, s * y, s * z)

    fun div(s: Float): Vector3 {
        if (s == 0.flt) throw ArithmeticException("division by zero");

        return Vector3(x / s, y / s, z / s);
    }

    fun minus() = this / -1.flt

    fun dot(v: Vector3) = x*v.x + y*v.y + z*v.z

    fun cross(v: Vector3) = Vector3(y*v.z - z*v.y, z*v.x - x*v.z, x*v.y - y*v.x)

    fun normalize() = this / magnitude

    val magnitude: Float
       get() = sqrt(magnitudeSquared)

    val magnitudeSquared: Float
       get() = dot(this)

    fun distance(v: Vector3): Float {
        val dx = x - v.x
        val dy = y - v.y
        val dz = z - v.z
        return sqrt(square(dx) + square(dy) + square(dz))
    }

    fun toString() = "[$x $y $z]"
}

