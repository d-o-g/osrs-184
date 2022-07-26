package jag.audi.vorbis;

public class VorbisMap {
    final int submaps;
    final int[] subfloor;
    final int[] subresidue;
    int mux;

    VorbisMap() {
        VorbisSample.read(16);
        submaps = VorbisSample.read() != 0 ? VorbisSample.read(4) + 1 : 1;
        if (VorbisSample.read() != 0) {
            VorbisSample.read(8);
        }

        VorbisSample.read(2);
        if (submaps > 1) {
            mux = VorbisSample.read(4);
        }

        subfloor = new int[submaps];
        subresidue = new int[submaps];

        for (int i = 0; i < submaps; ++i) {
            VorbisSample.read(8);
            subfloor[i] = VorbisSample.read(8);
            subresidue[i] = VorbisSample.read(8);
        }

    }
}
