package fi.evident.dojo.raytracer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

// TODO: as of the moment, Kotlin compiler crashes if we inherit from JComponent. Therefore
// this class is implemented in Java
public final class ImagePanel extends JComponent {

    private final BufferedImage image;

    public ImagePanel(BufferedImage image) {
        this.image = image;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(image.getWidth(), image.getHeight());
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
}
