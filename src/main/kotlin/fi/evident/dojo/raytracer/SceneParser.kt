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

import java.io.File

import java.lang.Character.isLetter
import java.lang.Character.isWhitespace
import fi.evident.dojo.raytracer.math.Vector3

class SceneParser(private val input: String) {

    private var pos = 0

    class object {
        fun parseScene(file: String): Scene =
            parseScene(File(file))

        fun parseScene(file: File): Scene =
            SceneParser(file.readText()).parseScene()
    }

    fun parseScene(): Scene {
        val scene = Scene(parseCamera())

        while (hasMore()) {
            val symbol = readSymbol()
            when (symbol) {
                "background" -> scene.backgroundColor = parseColor()
                "plane"      -> scene.objects.add(parsePlane())
                "sphere"     -> scene.objects.add(parseSphere())
                "light"      -> scene.lights.add(parseLight())
                else         -> throw fail("unexpected symbol $symbol")
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
        val name = parseString()

        val surface = Surfaces[name]
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
        expectChar('[')
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

    fun parseNumber(): Double {
        skipWhitespace()

        if (!hasMore())
            throw fail("expected number, but got EOF")

        val token = readTokenFromAlphabet("-.0123456789")
        try {
            return Double.parseDouble(token)
        } catch (e: NumberFormatException) {
            throw fail("expected number, but got $token")
        }
    }

    fun parseString(): String {
        skipWhitespace()

        if (!hasMore())
            throw fail("expected number, but got EOF")

        val sb = StringBuilder()
        expectChar('"')
        while (true) {
            val c = readChar()
            if (c == '"')
                break;
            sb.append(c)
        }

        return String(sb)
    }

    fun expectChar(expected: Char) {
        skipWhitespace()

        val ch = readChar()
        if (ch != expected)
            throw fail("expected char $expected, but got $ch")
    }

    fun expectSymbol(expected: String) {
        val symbol = readSymbol()
        if (expected != symbol)
            throw fail("expected symbol $expected, but got: $symbol")
    }

    fun readSymbol(): String {
        skipWhitespace()

        val sb = StringBuilder()
        while (pos < input.size && isLetter(input[pos]))
            sb.append(input[pos++])

        return String(sb)
    }

    fun hasMore(): Boolean {
        skipWhitespace()
        return pos < input.size
    }

    private fun readTokenFromAlphabet(alphabet: String): String {
        val sb = StringBuilder()

        while (pos < input.size && input[pos] in alphabet)
            sb.append(readChar())

        return String(sb)
    }

    private fun readChar(): Char =
        if (pos < input.size)
            input[pos++]
        else
            throw fail("unexpected EOF")

    private fun skipWhitespace() {
        while (pos < input.size) {
            val ch = input[pos]
            if (ch == ';') {
                skipEndOfLine()
            } else if (!isWhitespace(ch))
                break

            pos++
        }
    }

    private fun skipEndOfLine() {
        while (pos < input.size && input[pos] != '\n')
            pos++
    }

    private fun fail(message: String): ParseException =
        ParseException(pos, message)
}

class ParseException(pos: Int, message: String) : RuntimeException("$pos: $message")
