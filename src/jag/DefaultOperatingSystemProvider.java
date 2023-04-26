package jag;

public class DefaultOperatingSystemProvider implements OperatingSystemProvider {

    public static String osName;
    public static String aString407;

    int javaMajorVersion;
    int javaMinorVersion;
    int javaPatchVersion;

    void parseOldVersion(String var1) {
        String[] var2 = var1.split("\\.");

        try {
            javaMajorVersion = Integer.parseInt(var2[1]);
            var2 = var2[2].split("_");
            javaMinorVersion = Integer.parseInt(var2[0]);
            javaPatchVersion = Integer.parseInt(var2[1]);
        } catch (Exception ignored) {

        }
    }

    void parseNewVersion(String var1) {
        String[] var2 = var1.split("\\.");

        try {
            javaMajorVersion = Integer.parseInt(var2[0]);
            javaMinorVersion = Integer.parseInt(var2[1]);
            javaPatchVersion = Integer.parseInt(var2[2]);
        } catch (Exception ignored) {

        }
    }

    void parseVersion(String packed) {
        if (packed.startsWith("1.")) {
            parseOldVersion(packed);
        } else {
            parseNewVersion(packed);
        }
    }

    public OperatingSystemNode provide() {
        byte osType;
        if (osName.startsWith("win")) {
            osType = 1;
        } else if (osName.startsWith("mac")) {
            osType = 2;
        } else if (osName.startsWith("linux")) {
            osType = 3;
        } else {
            osType = 4;
        }

        String osArch;
        try {
            osArch = System.getProperty("os.arch").toLowerCase();
        } catch (Exception var22) {
            osArch = "";
        }

        String osVersion;
        try {
            osVersion = System.getProperty("os.version").toLowerCase();
        } catch (Exception var21) {
            osVersion = "";
        }

        String javaVendor = "Unknown";
        String javaVersion = "1.1";

        try {
            javaVendor = System.getProperty("java.vendor");
            javaVersion = System.getProperty("java.version");
        } catch (Exception ignored) {
        }

        boolean x64 = osArch.startsWith("amd64") || osArch.startsWith("x86_64");

        byte osVersionType = 0;
        if (osType == 1) {
            if (osVersion.contains("4.0")) {
                osVersionType = 1;
            } else if (osVersion.contains("4.1")) {
                osVersionType = 2;
            } else if (osVersion.contains("4.9")) {
                osVersionType = 3;
            } else if (osVersion.contains("5.0")) {
                osVersionType = 4;
            } else if (osVersion.contains("5.1")) {
                osVersionType = 5;
            } else if (osVersion.contains("5.2")) {
                osVersionType = 8;
            } else if (osVersion.contains("6.0")) {
                osVersionType = 6;
            } else if (osVersion.contains("6.1")) {
                osVersionType = 7;
            } else if (osVersion.contains("6.2")) {
                osVersionType = 9;
            } else if (osVersion.contains("6.3")) {
                osVersionType = 10;
            } else if (osVersion.contains("10.0")) {
                osVersionType = 11;
            }
        } else if (osType == 2) {
            if (osVersion.contains("10.4")) {
                osVersionType = 20;
            } else if (osVersion.contains("10.5")) {
                osVersionType = 21;
            } else if (osVersion.contains("10.6")) {
                osVersionType = 22;
            } else if (osVersion.contains("10.7")) {
                osVersionType = 23;
            } else if (osVersion.contains("10.8")) {
                osVersionType = 24;
            } else if (osVersion.contains("10.9")) {
                osVersionType = 25;
            } else if (osVersion.contains("10.10")) {
                osVersionType = 26;
            } else if (osVersion.contains("10.11")) {
                osVersionType = 27;
            } else if (osVersion.contains("10.12")) {
                osVersionType = 28;
            } else if (osVersion.contains("10.13")) {
                osVersionType = 29;
            }
        }

        byte javaVendorType;
        if (javaVendor.toLowerCase().contains("sun")) {
            javaVendorType = 1;
        } else if (javaVendor.toLowerCase().contains("microsoft")) {
            javaVendorType = 2;
        } else if (javaVendor.toLowerCase().contains("apple")) {
            javaVendorType = 3;
        } else if (javaVendor.toLowerCase().contains("oracle")) {
            javaVendorType = 5;
        } else {
            javaVendorType = 4;
        }

        parseVersion(javaVersion);

        int heapSize = (int) (Runtime.getRuntime().maxMemory() >> 20) + 1;
        int processorCount = javaMajorVersion > 3 ? Runtime.getRuntime().availableProcessors() : 0;

        //these are all unused but i've named them as what they should be
        byte processorInfoInt = 0;
        String szDescription = "";
        String unusedString = "";
        String directXVersion = "";
        String unusedString2 = "";
        String processorInfoString = "";
        String processorInfoString2 = "";
        int[] cpuInfo = new int[3];
        return new OperatingSystemNode(
                osType, x64, osVersionType,
                javaVendorType, javaMajorVersion, javaMinorVersion, javaPatchVersion,
                heapSize, processorCount, processorInfoInt,
                szDescription, unusedString, directXVersion, unusedString2,
                processorInfoString, processorInfoString2, cpuInfo
        );
    }
}
