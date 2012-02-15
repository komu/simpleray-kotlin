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

import java.lang.Math.max
import java.lang.Math.min

class Color(val r: Float, val g: Float, val b: Float) {

    class object {
        val BLACK = Color(0.flt, 0.flt, 0.flt)
        val WHITE = Color(1.flt, 1.flt, 1.flt)
    }

    fun times(n: Float) = Color(n*r, n*g, n*b)
    fun times(c: Color) = Color(r*c.r, g*c.g, b*c.b)
    fun plus(c: Color) = Color(r+c.r, g+c.g, b+c.b)
    fun minus(c: Color) = Color(r-c.r, g-c.g, b-c.b)

    fun toARGB(): Int {
        fun norm(x: Float) = (max(0.flt, min(x, 1.flt))*255+0.5).int

        val aa = 0xFF;
        val rr = norm(r)
        val gg = norm(g)
        val bb = norm(b)

        return ((aa and 0xFF) shl 24) or
               ((rr and 0xFF) shl 16) or
               ((gg and 0xFF) shl 8)  or
               ((bb and 0xFF) shl 0)
    }

    fun toString() = "($r $g $b)"
}


