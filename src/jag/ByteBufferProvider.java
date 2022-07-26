package jag;

public abstract class ByteBufferProvider {

    ByteBufferProvider() {

    }

    public static Object allocateDirect(byte[] data) {
        if (data == null) {
            return null;
        }

        if (data.length > 136) {
            DirectByteBufferProvider provider = new DirectByteBufferProvider();
            provider.allocate(data);
            return provider;
        }

        return data;
    }

    public abstract byte[] get();

    public abstract void allocate(byte[] var1);
}
