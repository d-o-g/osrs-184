package jagex.oldscape;

import jagex.datastructure.Node;
import jagex.messaging.Buffer;

public class OperatingSystemNode extends Node {

  final int osType;
  final int osVersion;
  final int javaVendor;
  final int javaMajorVersion;
  final int javaMinorVersion;
  final int javaPatchVersion;
  final int heapSize;
  final int processorCount;
  final int processorInfoInt;
  final int processorInfoInt2;
  final int szDriverDateP2;
  final int szDriverDateP1;
  final int processorInfoInt3;
  final int processorInfoInt4;
  final int processorInfoInt5;
  final String szDescription;
  final String unusedString;
  final String directXVersion;
  final String unusedString2;
  final String processorInfoString;
  final String processorInfoString2;
  final String unusedString3;
  final boolean unusedBool;
  final boolean x64;
  int[] cpuInfo;

  OperatingSystemNode(
      int osType, boolean x64, int osVersion,
      int javaVendor, int javaMajorVersion, int javaMinorVersion, int javaPatchVersion,
      int heapSize, int processorCount,
      int processorInfoInt,
      String szDescription,
      String unusedString,
      String directXVersion,
      String unusedString2, String processorInfoString, String processorInfoString2, int[] cpuInfo) {
    this.osType = osType;
    this.x64 = x64;
    this.osVersion = osVersion;
    this.javaVendor = javaVendor;
    this.javaMajorVersion = javaMajorVersion;
    this.javaMinorVersion = javaMinorVersion;
    this.javaPatchVersion = javaPatchVersion;
    this.unusedBool = false;
    this.heapSize = heapSize;
    this.processorCount = processorCount;
    this.processorInfoInt = processorInfoInt;
    this.processorInfoInt2 = 0;
    this.szDescription = szDescription;
    this.unusedString = unusedString;
    this.directXVersion = directXVersion;
    this.unusedString2 = unusedString2;
    this.szDriverDateP2 = 0;
    this.szDriverDateP1 = 0;
    this.processorInfoInt3 = 0;
    this.processorInfoInt4 = 0;
    this.processorInfoString = processorInfoString;
    this.processorInfoString2 = processorInfoString2;
    this.cpuInfo = cpuInfo;
    this.processorInfoInt5 = 0;
    this.unusedString3 = "";
  }

  public int getPayloadSize() {
    byte base = 39;
    int size = base + Buffer.stringLengthPlusTwo(this.szDescription);
    size += Buffer.stringLengthPlusTwo(this.unusedString);
    size += Buffer.stringLengthPlusTwo(this.directXVersion);
    size += Buffer.stringLengthPlusTwo(this.unusedString2);
    size += Buffer.stringLengthPlusTwo(this.processorInfoString);
    size += Buffer.stringLengthPlusTwo(this.processorInfoString2);
    size += Buffer.stringLengthPlusTwo(this.unusedString3);
    return size;
  }

  public void writeTo(Buffer buffer) {
    buffer.p1(8);
    buffer.p1(this.osType);
    buffer.p1(this.x64 ? 1 : 0);
    buffer.p2(this.osVersion);
    buffer.p1(this.javaVendor);
    buffer.p1(this.javaMajorVersion);
    buffer.p1(this.javaMinorVersion);
    buffer.p1(this.javaPatchVersion);
    buffer.p1(this.unusedBool ? 1 : 0);
    buffer.p2(this.heapSize);
    buffer.p1(this.processorCount);
    buffer.p3(this.processorInfoInt);
    buffer.p2(this.processorInfoInt2);
    buffer.pstr(this.szDescription);
    buffer.pstr(this.unusedString);
    buffer.pstr(this.directXVersion);
    buffer.pstr(this.unusedString2);
    buffer.p1(this.szDriverDateP1);
    buffer.p2(this.szDriverDateP2);
    buffer.pstr(this.processorInfoString);
    buffer.pstr(this.processorInfoString2);
    buffer.p1(this.processorInfoInt3);
    buffer.p1(this.processorInfoInt4);

    for (int i : this.cpuInfo) {
      buffer.p4(i);
    }

    buffer.p4(this.processorInfoInt5);
    buffer.pstr(this.unusedString3);
  }
}
