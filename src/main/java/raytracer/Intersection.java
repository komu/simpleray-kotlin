package raytracer;

public final class Intersection {
    
    public final SceneObject object;
    public final Ray ray;
    public float distance;
    
    public Intersection(SceneObject object, Ray ray, float distance) {
        assert object != null;
        assert ray != null;
        assert distance >= 0;
        
        this.object = object;
        this.ray = ray;
        this.distance = distance;
    }

    public Vector3 getPosition() {
        return ray.start.add(ray.direction.scale(distance));
    }
}
