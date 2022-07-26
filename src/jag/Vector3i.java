package jag;

public class Vector3i {

    public int x;
    public int y;
    public int z;
    public int magnitude;

    public Vector3i() {

    }

    public Vector3i(Vector3i src) {
        x = src.x;
        y = src.y;
        z = src.z;
        magnitude = src.magnitude;
    }
}
