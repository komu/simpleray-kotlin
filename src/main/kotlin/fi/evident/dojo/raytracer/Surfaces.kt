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

    val checkerboard = object : Surface(150.flt) {
        override fun specular(pos: Vector3) =
            Color.WHITE.sure()

        override fun reflectivity(pos: Vector3) =
            if ((floor(pos.z.dbl) + floor(pos.x.dbl)) % 2 != 0.0) 0.1.flt else 0.7.flt

        override fun diffuse(pos: Vector3) =
            if ((floor(pos.z.dbl) + floor(pos.x.dbl)) % 2 != 0.0) Color.WHITE.sure() else Color.BLACK.sure()
    }

    val shiny = object : Surface(150.flt) {
        override fun diffuse(pos: Vector3) = Color.WHITE.sure()
        override fun specular(pos: Vector3) = Color(0.5.flt, 0.5.flt, 0.5.flt)
        override fun reflectivity(pos: Vector3) = 0.6.flt
    }

    val mirror = object : Surface(50.flt) {
        override fun reflectivity(pos: Vector3) = 1.flt
        override fun diffuse(pos: Vector3) = Color.BLACK.sure()
        override fun specular(pos: Vector3) = Color.BLACK.sure()
    }

    fun findSurfaceByName(name: String): Surface? =
       when (name) {
           "checkerboard" -> checkerboard
           "shiny"        -> shiny
           "mirror"       -> mirror
           else -> null
       }
}
