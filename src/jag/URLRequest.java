package jag;

import java.net.URL;

public class URLRequest {

    public static int audioSampleRate;

    final URL target;

    volatile byte[] data;

    volatile boolean complete;

    URLRequest(URL target) {
        this.target = target;
    }

    public boolean isComplete() {
        return complete;
    }

    public byte[] getData() {
        return data;
    }
}
