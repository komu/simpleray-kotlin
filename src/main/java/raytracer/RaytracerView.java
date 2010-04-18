package raytracer;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class RaytracerView extends JComponent {

    private final BufferedImage image = new BufferedImage(600, 600, BufferedImage.TYPE_INT_RGB);
    private final Raytracer raytracer;
    
    public RaytracerView(Scene scene) {
        raytracer = new Raytracer(scene, image.getWidth(), image.getHeight());        
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
    
    public static void main(String[] args) {
        try {
            String sceneFile = 
                (args.length == 1) ? args[0] : "scenes/simple.scene";
            Scene scene = SceneParser.parse(sceneFile);
            
            JFrame frame = new JFrame("raytracer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
            RaytracerView view = new RaytracerView(scene);
            
            frame.add(view);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            
            view.startRaytracing();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
