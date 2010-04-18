package raytracer;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class RaytracerView extends JComponent {

    private final BufferedImage image = new BufferedImage(600, 600, BufferedImage.TYPE_INT_RGB);
    private final Raytracer raytracer;
    
    public RaytracerView() {
        raytracer = new Raytracer(createDefaultScene(), image.getWidth(), image.getHeight());        
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(image.getWidth(), image.getHeight());
    }
    
    public void startRaytracing() {
        new Thread(new Runnable() {
            public void run() {
                int width = image.getWidth();
                int height = image.getHeight();
                Graphics g = image.createGraphics();
                
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        g.setColor(raytracer.colorFor(x, y).toAWTColor());
                        g.fillRect(x, y, 1, 1);
                    }
                    repaint();
                }
            }
        }).start();
    }
    
    private static Scene createDefaultScene() {
        Camera camera = new Camera(new Vector3(3, 2, 4), new Vector3(-1, 0.5f, 0));
        Scene scene = new Scene(camera);
        
        scene.addObject(new Plane(new Vector3(0, 1, 0), 0, Surfaces.checkerboard));
        scene.addObject(new Sphere(new Vector3(0, 1, 0), 1, Surfaces.shiny));
        scene.addObject(new Sphere(new Vector3(-1, 0.5f, 1.5f), 0.5f, Surfaces.shiny));
        
        scene.addLight(new Vector3(-2,   2.5f,  0),    new Color(.49f, .07f, .07f));
        scene.addLight(new Vector3(1.5f, 2.5f,  1.5f), new Color(.07f, .07f, .49f));
        scene.addLight(new Vector3(1.5f, 2.5f, -1.5f), new Color(.07f, .49f, .071f));
        scene.addLight(new Vector3(0,    3.5f,  0),    new Color(.21f, .21f, .35f));
        
        return scene;
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("raytracer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
        RaytracerView view = new RaytracerView();
        
        frame.add(view);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        view.startRaytracing();
    }
}
