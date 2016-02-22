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

import fi.evident.dojo.raytracer.math.Point
import java.lang.Math.floor

object Surfaces {

    object checkerboard : Surface(150.0) {
        override fun specular(pos: Point) =
            Color.WHITE

        override fun reflectivity(pos: Point) =
            if (isWhite(pos)) 0.1 else 0.7

        override fun diffuse(pos: Point) =
            if (isWhite(pos)) Color.WHITE else Color.BLACK

        private fun isWhite(pos: Point) =
            (floor(pos.z) + floor(pos.x)) % 2 != 0.0
    }

    object shiny : Surface(150.0) {
        override fun diffuse(pos: Point) = Color.WHITE
        override fun specular(pos: Point) = Color(0.5, 0.5, 0.5)
        override fun reflectivity(pos: Point) = 0.6
    }

    object mirror : Surface(50.0) {
        override fun reflectivity(pos: Point) = 1.0
        override fun diffuse(pos: Point) = Color.BLACK
        override fun specular(pos: Point) = Color.BLACK
    }

    operator fun get(name: String): Surface? =
       when (name) {
           "checkerboard" -> checkerboard
           "shiny"        -> shiny
           "mirror"       -> mirror
           else           -> null
       }
}
