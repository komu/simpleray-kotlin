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

import java.lang.Math.floor

object Surfaces {

    val checkerboard = object : Surface(150.0) {
        override fun specular(pos: Vector3) =
            Color.WHITE

        override fun reflectivity(pos: Vector3) =
            if (isWhite(pos)) 0.1 else 0.7

        override fun diffuse(pos: Vector3) =
            if (isWhite(pos)) Color.WHITE else Color.BLACK

        private fun isWhite(pos: Vector3) =
            (floor(pos.z) + floor(pos.x)) % 2 != 0.0
    }

    val shiny = object : Surface(150.0) {
        override fun diffuse(pos: Vector3) = Color.WHITE
        override fun specular(pos: Vector3) = Color(0.5, 0.5, 0.5)
        override fun reflectivity(pos: Vector3) = 0.6
    }

    val mirror = object : Surface(50.0) {
        override fun reflectivity(pos: Vector3) = 1.0
        override fun diffuse(pos: Vector3) = Color.BLACK
        override fun specular(pos: Vector3) = Color.BLACK
    }

    fun findSurfaceByName(name: String): Surface? =
       when (name) {
           "checkerboard" -> checkerboard
           "shiny"        -> shiny
           "mirror"       -> mirror
           else -> null
       }
}
