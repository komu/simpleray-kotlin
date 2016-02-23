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

import javafx.application.Application
import javafx.scene.image.ImageView
import javafx.scene.layout.BorderPane
import javafx.stage.Stage
import javafx.scene.Scene as JXScene

class MyApp : Application() {
    override fun start(stage: Stage) {
        val sceneFile = parameters.unnamed.elementAtOrElse(0) { "scenes/simple.scene" }
        val scene = SceneParser.parseScene(sceneFile)

        val raytracerView = RaytracerView(scene)

        stage.scene = JXScene(BorderPane(ImageView(raytracerView.image)), raytracerView.width.toDouble(), raytracerView.height.toDouble())
        stage.title = "raytracer"
        stage.show()

        raytracerView.startRaytracing()
    }
}

fun main(args : Array<String>) {
    Application.launch(MyApp::class.java, *args)
}
