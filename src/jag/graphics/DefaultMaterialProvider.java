package jag.graphics;

import jag.commons.collection.NodeDeque;
import jag.js5.ReferenceTable;
import jag.opcode.Buffer;

public class DefaultMaterialProvider implements MaterialProvider {

    final Material[] materials;
    final int capacity;
    final ReferenceTable sprites;
    int size;
    double brightness;
    NodeDeque<Material> deque;
    int remaining;

    public DefaultMaterialProvider(ReferenceTable textures, ReferenceTable sprites, int capacity, double brightness, int size) {
        this.brightness = 1.0D;
        this.size = 128;
        this.sprites = sprites;
        this.capacity = capacity;
        this.remaining = capacity;
        this.brightness = brightness;
        this.size = size;

        deque = new NodeDeque<>();
        materials = new Material[textures.getFileCount(0)];

        int[] ids = textures.getFileIds(0);
        for (int id : ids) {
            Buffer buffer = new Buffer(textures.unpack(0, id));
            materials[id] = new Material(buffer);
        }
    }

    public void clear() {
        for (Material material : materials) {
            if (material != null) {
                material.method254();
            }
        }

        deque = new NodeDeque<>();
        remaining = capacity;
    }

    public int rgb(int id) {
        return materials[id] != null ? materials[id].rgb : 0;
    }

    public int[] pixels(int id) {
        Material material = materials[id];
        if (material != null) {
            if (material.pixels != null) {
                deque.insert(material);
                material.loaded = true;
                return material.pixels;
            }

            boolean var3 = material.index(brightness, size, sprites);
            if (var3) {
                if (remaining == 0) {
                    Material var4 = deque.popLast();
                    var4.method254();
                } else {
                    --remaining;
                }

                deque.insert(material);
                material.loaded = true;
                return material.pixels;
            }
        }

        return null;
    }

    public boolean isLowDetail() {
        return size == 64;
    }

    public boolean method1423(int id) {
        return materials[id].aBoolean571;
    }

    public int loadPercent() {
        int total = 0;
        int loaded = 0;

        for (Material material : materials) {
            if (material != null && material.files != null) {
                total += material.files.length;
                int[] files = material.files;

                for (int file : files) {
                    if (sprites.loadDynamic(file)) {
                        ++loaded;
                    }
                }
            }
        }

        if (total == 0) {
            return 0;
        }
        return loaded * 100 / total;
    }

    public void setBrightness(double brightness) {
        this.brightness = brightness;
        clear();
    }

    public void rotate(int dir) {
        for (Material material : materials) {
            if (material != null && material.direction != 0 && material.loaded) {
                material.rotate(dir);
                material.loaded = false;
            }
        }

    }
}
