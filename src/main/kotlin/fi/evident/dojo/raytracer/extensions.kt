/*
 * Copyright (c) 2012 Evident Solutions Oy
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

import java.io.File
import java.io.FileReader
import java.awt.image.BufferedImage
import javax.swing.JFrame
import java.awt.Container

fun StringBuilder.build() =
    this.toString().sure()

fun File.readAsString(): String =
    FileReader(this) foreach { reader ->
        val buffer = CharArray(1024)
        val sb = StringBuilder()

        while (true) {
            val n = reader.read(buffer)
            if (n == -1) break
            sb.append(buffer, 0, n)
        }

        sb.build()
    }

val BufferedImage.width: Int
   get() = this.getWidth()

val BufferedImage.height: Int
   get() = this.getHeight()

val JFrame.contentPane: Container
   get() = this.getContentPane().sure()
