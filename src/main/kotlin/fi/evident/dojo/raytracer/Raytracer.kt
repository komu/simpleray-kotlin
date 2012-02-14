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

import fi.evident.dojo.raytracer.MathUtils.pow

class Raytracer(val scene: Scene, val width: Int, val height: Int) {

    val maxDepth = 5
    val backgroundColor = Color.BLACK.sure()
    val maxDepthColor = Color(0.5.flt, 0.5.flt, 0.5.flt)

    fun colorFor(x: Int, y: Int): Color {
        val recenterY = -(y - (height / 2.0)) / (2.0 * height)
        val recenterX = (x - (width / 2.0)) / (2.0 * width)
        val direction = scene.camera.sure().recenteredDirection(recenterX.flt, recenterY.flt)

        return traceRay(Ray(scene.camera.sure().position.sure(), direction), maxDepth);
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

        return naturalColor.add(reflectColor).sure()
    }

    /**
     * Returns the natural color of given intersection by sum of different
     * lights coming to that position.
     */
    private fun naturalColor(intersection: Intersection): Color {
        var color = Color.BLACK.sure()

        for (val light in scene.getLights())
            color = color.add(naturalColor(intersection, light.sure())).sure()

        return color
    }

    /**
     * Returns the natural color given by given light.
     */
    private fun naturalColor(intersection: Intersection, light: Light): Color {
        if (isInShadow(light, intersection.getPosition().sure())) {
            return Color.BLACK.sure()
        } else {
            val diffuseColor = diffuseColor(intersection, light)
            val specularColor = specularColor(intersection, light)

            return diffuseColor.add(specularColor).sure()
        }
    }

    /**
     * Calculates the diffuse color at given intersection by Lambertian
     * reflectance. See http://en.wikipedia.org/wiki/Lambertian_reflectance
     */
    private fun diffuseColor(intersection: Intersection, light: Light): Color {
        val pos = intersection.getPosition().sure()
        val lightDirection = light.vectorFrom(pos).sure().normalize().sure()

        val illumination = lightDirection.dotProduct(intersection.getNormal().sure()).sure()
        if (illumination <= 0)
            return Color.BLACK.sure()

        val surface = intersection.getSurface().sure()
        val color = light.color.sure().multiply(illumination).sure()
        return color.multiply(surface.diffuse(pos)).sure()
    }

    /**
     * Calculates the specular reflection of light at given intersection.
     * See http://en.wikipedia.org/wiki/Phong_shading
     * and http://en.wikipedia.org/wiki/Specular_reflection
     */
    private fun specularColor(intersection: Intersection, light: Light): Color {
        val pos = intersection.getPosition().sure()
        val vectorToLight = light.vectorFrom(pos).sure().normalize().sure()
        val reflectDir = intersection.getReflectDirection().sure()

        val specular = vectorToLight.dotProduct(reflectDir.normalize())
        if (specular <= 0)
            return Color.BLACK.sure()

        val surface = intersection.getSurface().sure()
        val specularFactor = pow(specular, surface.roughness)
        val color = light.color.sure().multiply(specularFactor).sure()

        return color.multiply(surface.specular(pos)).sure()
    }

    /**
     * Calculates the color which is reflected to given intersection by
     * recursively tracing ray towards the direction of reflection.
     */
    private fun reflectColor(intersection: Intersection, maxSteps: Int): Color {
        if (maxSteps <= 1)
            return maxDepthColor

        val reflectDir = intersection.getReflectDirection().sure()
        val reflectPos = intersection.getPosition().sure().add(reflectDir.scale(0.001.flt))

        val reflectivity = intersection.getSurface().sure().reflectivity(reflectPos)
        if (reflectivity == 0.flt)
            return Color.BLACK.sure()

        val color = traceRay(Ray(reflectPos, reflectDir), maxSteps-1)
        return color.multiply(reflectivity).sure()
    }

    /**
     * Returns true if given light is not visible from given position.
     */
    private fun isInShadow(light: Light, pos: Vector3): Boolean {
         val vectorToLight = light.vectorFrom(pos).sure()
         val testRay = Ray(pos, vectorToLight.normalize());

         val testIsect = scene.nearestIntersection(testRay);
         return (testIsect != null) && (testIsect.distance <= vectorToLight.magnitude());
    }
}