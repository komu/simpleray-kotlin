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

import java.io.File
import java.io.FileReader
import java.io.IOException

import java.lang.Character.isLetter
import java.lang.Character.isWhitespace

fun parseScene(file: String): Scene =
    parseScene(File(file))

fun parseScene(file: File): Scene =
    SceneParser(readContents(file)).parseScene()

fun readContents(file: File): String {
    val reader = FileReader(file)
    try {
        val buffer = CharArray(1024)
        val sb = StringBuilder()

        while (true) {
            val n = reader.read(buffer)
            if (n == -1) break
            sb.append(buffer, 0, n)
        }

        return sb.toString().sure()

    } finally {
        reader.close()
    }
}

class SceneParser(input: String) {
    private val input = input.toCharArray().sure()
    private var pos = 0

    fun parseScene(): Scene {
        val scene = Scene(parseCamera())

        while (hasMore()) {
            val symbol = readSymbol()
            when (symbol) {
                "plane"  -> scene.addObject(parsePlane())
                "sphere" -> scene.addObject(parseSphere())
                "light"  -> scene.addLight(parseLight())
                else     -> throw fail("unexpected symbol ${symbol}")
            }
        }

        return scene
    }

    fun parsePlane(): SceneObject {
        val normal = parseVector()
        val offset = parseNumber()
        val surface = parseSurface()

        return Plane(normal, offset, surface)
    }

    fun parseSphere(): SceneObject {
        val center = parseVector()
        val radius = parseNumber()
        val surface = parseSurface()

        return Sphere(center, radius, surface)
    }

    fun parseSurface(): Surface {
        val name = readSymbol()

        val surface = Surfaces.findSurfaceByName(name)
        if (surface != null)
            return surface
        else
            throw fail("invalid surface $name")
    }

    fun parseLight(): Light {
        val position = parseVector()
        val color = parseColor()

        return Light(position, color)
    }

    fun parseCamera(): Camera {
        expectSymbol("camera")

        val position = parseVector()
        val lookAt = parseVector()

        return Camera(position, lookAt)
    }

    fun parseVector(): Vector3 {
        expectChar('[');
        val x = parseNumber()
        val y = parseNumber()
        val z = parseNumber()
        expectChar(']')

        return Vector3(x, y, z)
    }

    fun parseColor(): Color {
        expectChar('(')
        val r = parseNumber()
        val g = parseNumber()
        val b = parseNumber()
        expectChar(')')

        return Color(r, g, b)
    }

    fun parseNumber(): Float {
        skipWhitespace()

        if (!hasMore())
            throw fail("expected number, but got EOF")

        val token = readTokenFromAlphabet("-.0123456789")
        try {
            return Float.parseFloat(token)
        } catch (e: NumberFormatException) {
            throw fail("expected number, but got $token")
        }
    }

    fun expectChar(expected: Char): Unit {
        skipWhitespace()

        if (pos < input.size) {
            val ch = input[pos++]
            if (ch != expected)
                throw fail("expected char $expected, but got $ch");
        } else
            throw fail("expected char $expected, but got EOF");
    }

    fun expectSymbol(expected: String): Unit {
        val symbol = readSymbol()
        if (!expected.equals(symbol))
            throw fail("expected symbol $expected, but got: $symbol")
    }

    fun readSymbol(): String {
        skipWhitespace()

        val sb = StringBuilder()
        while (pos < input.size && isLetter(input[pos]))
            sb.append(input[pos++])

        return sb.toString().sure()
    }

    fun hasMore(): Boolean {
        skipWhitespace()
        return pos < input.size
    }

    private fun readTokenFromAlphabet(alphabet: String): String {
        val sb = StringBuilder()
        while (pos < input.size && alphabet.lastIndexOf(input[pos]) != -1)
            sb.append(input[pos++])
        return sb.toString().sure()
    }

    fun skipWhitespace(): Unit {
        while (pos < input.size) {
            val ch = input[pos]
            if (ch == ';') {
                while (pos < input.size && input[pos] != '\n')
                    pos++;
            } else if (!isWhitespace(input[pos]))
                break;

            pos++;
        }
    }

    fun fail(message: String): ParseException =
        ParseException(pos, message)
}

class ParseException(pos: Int, message: String) : RuntimeException("${pos}: ${message}")