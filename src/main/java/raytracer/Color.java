package raytracer;

import static java.lang.Math.max;
import static java.lang.Math.min;

public final class Color {
    
    public static final Color BLACK = new Color(0, 0, 0);
    public static final Color WHITE = new Color(1, 1, 1);
    
    private final float r;
    private final float g;
    private final float b;

    public Color(float r, float g, float b) {
        this.r = r; 
        this.g = g; 
        this.b = b;
    }

    public Color multiply(float n) {
        return new Color(n*r, n*g, n*b);
    }

    public Color multiply(Color c) {
        return new Color(r*c.r, g*c.g, b*c.b);
    }

    public Color add(Color c) {
        return new Color(r+c.r, g+c.g, b+c.b);
    }
    
    public Color subtract(Color c) {
        return new Color(r-c.r, g-c.g, b-c.b);
    }

    public java.awt.Color toAWTColor() {
        return new java.awt.Color(clamp(r), clamp(g), clamp(b));
    }
    
    private static float clamp(float d) {
        return max(0, min(d, 1));
    }
    
    @Override
    public String toString() {
        return "(" + r + " " + g + " " + b + ")";
    }
}

