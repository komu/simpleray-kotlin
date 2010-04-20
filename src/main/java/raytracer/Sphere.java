package raytracer;

import static raytracer.MathUtils.sqrt;
import static raytracer.MathUtils.square;

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
        
        float disc = square(radius) - (eo.dotProduct(eo) - square(v));
        if (disc < 0)
            return null;
        
        float distance = v - sqrt(disc);
        
        if (distance <= 0)
            return null;
        
        return new Intersection(this, ray, distance); 
    }
    
    @Override
    public Vector3 normal(Vector3 pos) {
        return pos.subtract(center).normalize();
    }
}
