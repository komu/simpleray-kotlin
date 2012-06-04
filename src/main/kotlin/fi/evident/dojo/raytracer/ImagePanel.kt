package fi.evident.dojo.raytracer

import javax.swing.*
import java.awt.*
import java.awt.image.BufferedImage

class ImagePanel(val image: BufferedImage) : JComponent() {

    override fun getPreferredSize(): Dimension = Dimension(image.getWidth(), image.getHeight())

    override fun paintComponent(g: Graphics?) {
        g?.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
}
