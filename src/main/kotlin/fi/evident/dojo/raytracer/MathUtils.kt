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
package fi.evident.dojo.raytracer.MathUtils

fun square(x: Float) = x*x
fun sqrt(x: Float) = Math.sqrt(x.dbl).flt
fun pow(a: Float, b: Float) = Math.pow(a.dbl, b.dbl).flt

/**
 * Returns the roots roots of ax^2+bx+c = 0.
 */
fun rootsOfQuadraticEquation(a: Float, b: Float, c: Float): FloatArray {
    val disc = square(b) - 4 * a * c;
    val divisor = 2*a;

    if (disc < 0)
        return FloatArray(0)

    if (disc == 0.flt) {
        val result = FloatArray(1)
        result[0] = -b / divisor
         return result
    }

    val discSqrt = sqrt(disc)
    val result = FloatArray(2)
    result[0] = (-b + discSqrt) / divisor
    result[1] = (-b - discSqrt) / divisor
    return result
}
