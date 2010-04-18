package raytracer;

import static java.lang.Math.floor;

public final class Surfaces {

    public static final Surface checkerboard = new Surface(150) {
        @Override
        public Color diffuse(Vector3 pos) {
            return ((floor(pos.z) + floor(pos.x)) % 2 != 0) ? Color.WHITE : Color.BLACK;
        }

        @Override
        public Color specular(Vector3 pos) {
            return Color.WHITE;
        }

        @Override
        public float reflect(Vector3 pos) {
            return ((floor(pos.z) + floor(pos.x)) % 2 != 0) ? 0.1f : 0.7f;
        }
    };
   
    public static final Surface shiny = new Surface(50) {
        
        @Override
        public Color specular(Vector3 pos) {
            return new Color(0.5f, 0.5f, 0.5f);
        }
  
        @Override
        public Color diffuse(Vector3 pos) {
            return Color.WHITE;
        }
  
        @Override
        public float reflect(Vector3 pos) {
            return 0.6f;
        }
    };
}
