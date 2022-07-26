package jag.audi;

import jag.commons.collection.Node;

public class AudioBuffer extends Node {

    public final byte[] payload;

    public AudioBuffer(byte[] payload) {
        this.payload = payload;
    }
}
