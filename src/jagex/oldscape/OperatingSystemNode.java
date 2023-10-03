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
  final int clockSpeed;
  final int szDriverDateP2;
  final int szDriverDateP1;
  final int unk10;
  final int unk11;
  final int unk15;
  final String szDescription;
  final String glRenderer;
  final String directXVersion;
  final String glVersion;
  final String cpuId;
  final String cpuName;
  final String mobileDeviceName;
  final boolean unusedBool;
  final boolean x64;
  int[] cpuInfo;

  OperatingSystemNode(
      int osType, boolean x64, int osVersion,
      int javaVendor, int javaMajorVersion, int javaMinorVersion, int javaPatchVersion,
      int heapSize, int processorCount,
      int processorInfoInt,
      String szDescription,
      String glRenderer,
      String directXVersion,
      String glVersion, String cpuId, String cpuName, int[] cpuInfo) {
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
    this.clockSpeed = 0;
    this.szDescription = szDescription;
    this.glRenderer = glRenderer;
    this.directXVersion = directXVersion;
    this.glVersion = glVersion;
    this.szDriverDateP2 = 0;
    this.szDriverDateP1 = 0;
    this.unk10 = 0;
    this.unk11 = 0;
    this.cpuId = cpuId;
    this.cpuName = cpuName;
    this.cpuInfo = cpuInfo;
    this.unk15 = 0;
    this.mobileDeviceName = "";
  }

  public int getPayloadSize() {
    byte base = 39;
    int size = base + Buffer.stringLengthPlusTwo(this.szDescription);
    size += Buffer.stringLengthPlusTwo(this.glRenderer);
    size += Buffer.stringLengthPlusTwo(this.directXVersion);
    size += Buffer.stringLengthPlusTwo(this.glVersion);
    size += Buffer.stringLengthPlusTwo(this.cpuId);
    size += Buffer.stringLengthPlusTwo(this.cpuName);
    size += Buffer.stringLengthPlusTwo(this.mobileDeviceName);
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
    buffer.p2(this.clockSpeed);
    buffer.pstr(this.szDescription);
    buffer.pstr(this.glRenderer);
    buffer.pstr(this.directXVersion);
    buffer.pstr(this.glVersion);
    buffer.p1(this.szDriverDateP1);
    buffer.p2(this.szDriverDateP2);
    buffer.pstr(this.cpuId);
    buffer.pstr(this.cpuName);
    buffer.p1(this.unk10);
    buffer.p1(this.unk11);

    for (int i : this.cpuInfo) {
      buffer.p4(i);
    }

    buffer.p4(this.unk15);
    buffer.pstr(this.mobileDeviceName);
  }
}
