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

import java.lang.Math.pow
import math.Vector3
import math.normalize

class Raytracer(val scene: Scene, val width: Int, val height: Int) {

    val maxDepth = 5
    val backgroundColor = Color.BLACK
    val maxDepthColor = Color(0.5, 0.5, 0.5)

    fun colorFor(x: Int, y: Int): Color {
        val recenterY = -(y - (height / 2.0)) / (2.0 * height)
        val recenterX = (x - (width / 2.0)) / (2.0 * width)
        val direction = scene.camera.recenteredDirection(recenterX, recenterY)

        return traceRay(Ray(scene.camera.position, direction), maxDepth)
    }

    /**
     * Traces a ray into given direction, taking at most maxSteps recursive
     * steps.
     */
    private fun traceRay(ray: Ray, maxSteps: Int): Color {
        val intersection = scene.nearestIntersection(ray)
        if (intersection == null)
            return backgroundColor

        val naturalColor = naturalColor(intersection)
        val reflectColor = reflectColor(intersection, maxSteps)

        return naturalColor + reflectColor
    }

    /**
     * Returns the natural color of given intersection by sum of different
     * lights coming to that position.
     */
    private fun naturalColor(intersection: Intersection): Color {
        var color = Color.BLACK

        for (val light in scene.lights)
            color += naturalColor(intersection, light)

        return color
    }

    /**
     * Returns the natural color given by given light.
     */
    private fun naturalColor(intersection: Intersection, light: Light): Color {
        if (isInShadow(light, intersection.position)) {
            return Color.BLACK
        } else {
            val diffuseColor = diffuseColor(intersection, light)
            val specularColor = specularColor(intersection, light)

            return diffuseColor + specularColor
        }
    }

    /**
     * Calculates the diffuse color at given intersection by Lambertian
     * reflectance. See http://en.wikipedia.org/wiki/Lambertian_reflectance
     */
    private fun diffuseColor(intersection: Intersection, light: Light): Color {
        val pos = intersection.position
        val lightDirection = normalize(light.vectorFrom(pos))

        val illumination = lightDirection dot intersection.normal
        if (illumination <= 0)
            return Color.BLACK

        val surface = intersection.surface
        return light.color * illumination * surface.diffuse(pos)
    }

    /**
     * Calculates the specular reflection of light at given intersection.
     * See http://en.wikipedia.org/wiki/Phong_shading
     * and http://en.wikipedia.org/wiki/Specular_reflection
     */
    private fun specularColor(intersection: Intersection, light: Light): Color {
        val pos = intersection.position
        val vectorToLight = normalize(light.vectorFrom(pos))
        val reflectDir = normalize(intersection.reflectDirection)

        val specular = vectorToLight dot reflectDir
        if (specular <= 0)
            return Color.BLACK

        val surface = intersection.surface
        val specularFactor = pow(specular, surface.roughness)

        return light.color * specularFactor * surface.specular(pos)
    }

    /**
     * Calculates the color which is reflected to given intersection by
     * recursively tracing ray towards the direction of reflection.
     */
    private fun reflectColor(intersection: Intersection, maxSteps: Int): Color {
        if (maxSteps <= 1)
            return maxDepthColor

        val reflectDir = intersection.reflectDirection
        val reflectPos = intersection.position + reflectDir*0.001

        val reflectivity = intersection.surface.reflectivity(reflectPos)
        if (reflectivity == 0.0)
            return Color.BLACK

        val color = traceRay(Ray(reflectPos, reflectDir), maxSteps-1)
        return color * reflectivity
    }

    /**
     * Returns true if given light is not visible from given position.
     */
    private fun isInShadow(light: Light, pos: Vector3): Boolean {
         val vectorToLight = light.vectorFrom(pos)
         val testRay = Ray(pos, normalize(vectorToLight))

         val intersection = scene.nearestIntersection(testRay)
         return intersection != null && intersection.distance <= vectorToLight.magnitude
    }
}
