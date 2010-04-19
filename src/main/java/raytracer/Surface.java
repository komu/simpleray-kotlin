package raytracer;

public abstract class Surface {
    
    public final float roughness;
    
    protected Surface(float roughness) {
        assert roughness > 0;
        
        this.roughness = roughness;
    }
    
    public abstract Color diffuse(Vector3 pos);
    public abstract Color specular(Vector3 pos);
    public abstract float reflect(Vector3 pos);
}
