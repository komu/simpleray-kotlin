package raytracer;

public abstract class Surface {
    
    public final float rougness;
    
    protected Surface(float roughness) {
        assert roughness > 0;
        
        this.rougness = roughness;
    }
    
    public abstract Color diffuse(Vector3 pos);
    public abstract Color specular(Vector3 pos);
    public abstract float reflect(Vector3 pos);
}
