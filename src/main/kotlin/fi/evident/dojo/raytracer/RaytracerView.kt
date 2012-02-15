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

import javax.swing.*
import java.awt.*
import java.awt.image.BufferedImage
import java.util.concurrent.CountDownLatch
import java.util.concurrent.atomic.AtomicInteger

class RaytracerView(scene: Scene) : JComponent() {

    val image = BufferedImage(600, 600, BufferedImage.TYPE_INT_RGB)
    val raytracer = Raytracer(scene, image.width, image.height)

    override fun paintComponent(g: Graphics?) {
        g?.drawImage(image, 0, 0, getWidth(), getHeight(), this)
    }

    override fun getPreferredSize() =
        Dimension(image.width, image.height)

    fun startRaytracing() {
        val numberOfThreads = Runtime.getRuntime().sure().availableProcessors()
        val latch = CountDownLatch(numberOfThreads)
        val row = AtomicInteger(0)
        val repaintInterval = 20

        for (val i in 1..numberOfThreads)
            Thread(object : Runnable {
                override fun run() {
                    val width = image.width
                    val height = image.height
                    val rgbArray = IntArray(width)

                    while (true) {
                        val y = row.incrementAndGet()
                        if (y >= height) break

                        for (val x in rgbArray.indices)
                            rgbArray[x] = raytracer.colorFor(x, y).toARGB()

                        image.setRGB(0, y, width, 1, rgbArray, 0, 1)

                        if ((y % repaintInterval) == 0)
                            repaint();
                    }

                    latch.countDown();
                }
            }).start()

        Thread(object : Runnable {
            override fun run() {
                val startTime = System.currentTimeMillis()

                try {
                    latch.await()
                    repaint()
                } catch (e: InterruptedException) {
                }

                val elapsed = System.currentTimeMillis() - startTime
                println("elapsed time: $elapsed ms")
            }
        }).start()
    }
}
