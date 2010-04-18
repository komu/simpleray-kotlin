package raytracer;

public final class Raytracer {

    private final Scene scene;
    private final int width;
    private final int height;
    private final int maxDepth = 5;
    private final Color backgroundColor = Color.BLACK;
    private final Color maxDepthColor = new Color(0.5f, 0.5f, 0.5f);
    
    public Raytracer(Scene scene, int width, int height) {
        assert scene != null;
        assert width > 0;
        assert height > 0;
        
        this.scene = scene;
        this.width = width;
        this.height = height;
    }

    public Color colorFor(int x, int y) {
        float recenterY = -(y - (height / 2.0f)) / (2.0f * height);
        float recenterX = (x - (width / 2.0f)) / (2.0f * width);
        Vector3 direction = scene.camera.recenteredDirection(recenterX, recenterY);

        return traceRay(new Ray(scene.camera.position, direction), 0);
    }
    
    private Color traceRay(Ray ray, int depth) {
        Intersection intersection = scene.nearestIntersection(ray);
        if (intersection == null) return backgroundColor;
        
        Vector3 pos = intersection.getPosition();
        
        Color naturalColor = naturalColor(intersection, pos);
        Color reflectColor = reflectColor(intersection, pos, depth);
        
        return naturalColor.add(reflectColor);
    }

    private Color naturalColor(Intersection intersection, Vector3 pos) {
        Color color = Color.BLACK;

        Vector3 normal = intersection.object.normal(pos);
        Vector3 d = intersection.ray.direction;
        Vector3 reflectDir = d.subtract(normal.scale(2 * normal.dotProduct(d))); 
        
        for (Light light : scene.getLights())
            color = color.add(naturalColor(intersection, pos, normal, reflectDir, light));
        
        return color;
    }
    
    private Color naturalColor(Intersection intersection, Vector3 pos, Vector3 normal, Vector3 reflectDir, Light light) {
        if (isInShadow(light, pos))
            return Color.BLACK;

        Vector3 livec = light.vectorFrom(pos).normalize();
        
        float illumination = livec.dotProduct(normal);
        Color lightColor = (illumination > 0) ? light.color.multiply(illumination) : Color.BLACK;

        Surface surface = intersection.object.surface;
        
        float specular = livec.dotProduct(reflectDir.normalize());
        Color specularColor = (specular > 0) ? light.color.multiply((float) Math.pow(specular, surface.rougness)) : Color.BLACK;

        return lightColor.multiply(surface.diffuse(pos)).add(specularColor.multiply(surface.specular(pos)));
    }

    private boolean isInShadow(Light light, Vector3 pos) {
        Vector3 lightDistance = light.vectorFrom(pos);
        Ray testRay = new Ray(pos, lightDistance.normalize());

        Intersection testIsect = scene.nearestIntersection(testRay);
        return (testIsect != null) && (testIsect.distance <= lightDistance.magnitude());
    }

    private Color reflectColor(Intersection intersection, Vector3 pos, int depth) {
        Vector3 dir = intersection.ray.direction;

        Vector3 normal = intersection.object.normal(pos);
        Vector3 reflectDir = dir.subtract(normal.scale(2 * normal.dotProduct(dir)));
        
        Vector3 reflectPos = pos.add(reflectDir.scale(0.001f));
        if (depth >= maxDepth)
            return maxDepthColor;

        float reflectivity = intersection.object.surface.reflect(reflectPos);
        
        Color color = traceRay(new Ray(reflectPos, reflectDir), depth+1);
        return color.multiply(reflectivity);
    }
}
    