package raytracer;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public final class Sphere extends SceneObject {

    private final Vector3 center;
    private final float radius;
    
    public Sphere(Vector3 center, float radius, Surface surface) {
        super(surface);
        
        assert center != null;
        assert radius > 0;
        
        this.center = center;
        this.radius = radius;
    }
    
    @Override
    public Intersection intersect(Ray ray) {
        Vector3 eo = center.subtract(ray.start);
        float v = eo.dotProduct(ray.direction);
        
        if (v < 0)
            return null;
        
        double disc = pow(radius, 2) - (eo.dotProduct(eo) - pow(v, 2));
        float dist = disc < 0 ? 0 : v - (float) sqrt(disc);
        
        return (dist <= 0) ? null : new Intersection(this, ray, dist); 
    }
    
    @Override
    public Vector3 normal(Vector3 pos) {
        return pos.subtract(center).normalize();
    }
}
