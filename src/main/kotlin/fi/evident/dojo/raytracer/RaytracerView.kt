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

import java.awt.image.BufferedImage
import java.util.concurrent.atomic.AtomicInteger

class RaytracerView(scene: Scene) {
    val image = BufferedImage(600, 600, BufferedImage.TYPE_INT_RGB)
    val component = ImagePanel(image)
    val raytracer = Raytracer(scene, image.width, image.height)
    val repaintInterval = 20

    fun startRaytracing() {
        val row = AtomicInteger(0)
        val startTime = System.currentTimeMillis()

        distribute {
            val width = image.width
            val height = image.height
            val rgbArray = IntArray(width)

            while (true) {
                val y = row.incrementAndGet()
                if (y >= height) break

                for (x in rgbArray.indices)
                    rgbArray[x] = raytracer.colorFor(x, y).toARGB()

                image.setRGB(0, y, width, 1, rgbArray, 0, 1)

                if ((y % repaintInterval) == 0)
                    component.repaint()
            }
        } onFinish {
            component.repaint()
            val elapsed = System.currentTimeMillis() - startTime
            println("elapsed time: $elapsed ms")
        }
    }
}
