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

import javafx.scene.image.PixelFormat
import javafx.scene.image.WritableImage
import java.util.concurrent.atomic.AtomicInteger

class RaytracerView(scene: Scene) {
    val width = 1200
    val height = 1200
    val image = WritableImage(width, height)
    private val raytracer = Raytracer(scene, width, height)

    fun startRaytracing() {
        val row = AtomicInteger(0)
        val startTime = System.currentTimeMillis()
        val pixelWriter = image.pixelWriter
        val format = PixelFormat.getIntArgbInstance()

        distribute {
            val rgbArray = IntArray(width)
            while (true) {
                val y = row.incrementAndGet()
                if (y >= height) break

                for (x in rgbArray.indices)
                    rgbArray[x] = raytracer.colorFor(x, y).toARGB()

                pixelWriter.setPixels(0, y, width, 1, format, rgbArray, 0, 0)
            }
        } onFinish {
            val elapsed = System.currentTimeMillis() - startTime
            println("elapsed time: $elapsed ms")
        }
    }
}
