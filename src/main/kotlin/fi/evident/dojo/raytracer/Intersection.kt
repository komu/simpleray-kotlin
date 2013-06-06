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
import kotlin.properties.Delegates

class Intersection(val sceneObject: SceneObject, val ray: Ray, val distance: Double) {

    val position: Point by Delegates.lazy {
        ray.pointAtDistance(distance)
    }

    val normal: Direction by Delegates.lazy {
        sceneObject.normal(position)
    }

    val reflectDirection: Direction by Delegates.lazy {
        val norm = normal
        val dir = ray.direction
        dir - (norm*2.0*(norm dot dir))
    }

    val surface: Surface
        get() = sceneObject.surface
}
