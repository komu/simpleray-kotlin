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

class Intersection(val sceneObject: SceneObject, val ray: Ray, val distance: Double) {

    private var _position: Vector3? = null
    private var _normal: Vector3? = null
    private var _reflectDirection: Vector3? = null

    val position: Vector3
        get() {
            if (_position == null)
                _position = ray.start + ray.direction*distance
            return _position.sure()
        }

    val normal: Vector3
        get() {
            if (_normal == null)
                _normal = sceneObject.normal(position)
            return _normal.sure()
        }

    val reflectDirection: Vector3
        get() {
            if (_reflectDirection == null) {
                val norm = normal
                val dir = ray.direction
                _reflectDirection = dir - norm*(2*norm.dot(dir))
            }
            return _reflectDirection.sure()
        }

    val surface: Surface
        get() = sceneObject.surface
}
