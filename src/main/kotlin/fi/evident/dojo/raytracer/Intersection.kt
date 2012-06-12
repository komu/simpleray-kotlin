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

import fi.evident.dojo.raytracer.math.Vector3
import fi.evident.dojo.raytracer.math.times

class Intersection(val sceneObject: SceneObject, val ray: Ray, val distance: Double) {

    private var _position = lazy { ray.start + ray.direction*distance }
    private var _normal = lazy { sceneObject.normal(position)}
    private var _reflectDirection = lazy { val norm = normal; val dir = ray.direction; dir - norm*(2*norm.dot(dir)) }

    val position: Vector3
        get() = _position()

    val normal: Vector3
        get() = _normal()

    val reflectDirection: Vector3
        get() = _reflectDirection()

    val surface: Surface
        get() = sceneObject.surface
}

fun lazy<T>(f: () -> T): () -> T {
    var computed = false
    var cached: T? = null

    return { () ->
        if (!computed) {
            cached = f()
            computed = true
        }
        cached as T
    }
}
