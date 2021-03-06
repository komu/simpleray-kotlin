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
package fi.evident.dojo.raytracer

import fi.evident.dojo.raytracer.math.Direction
import fi.evident.dojo.raytracer.math.Point
import fi.evident.dojo.raytracer.math.normalize
import fi.evident.dojo.raytracer.math.times

class Camera(val position: Point, lookAt: Point) {

    val down    = Direction(0.0, -1.0, 0.0)
    val forward = normalize(lookAt - position)
    val right   = 1.5 * normalize(forward cross down)
    val up      = 1.5 * normalize(forward cross right)

    fun recenteredDirection(recenterX: Double, recenterY: Double): Direction =
        normalize(forward + right*recenterX + up*recenterY)
}
