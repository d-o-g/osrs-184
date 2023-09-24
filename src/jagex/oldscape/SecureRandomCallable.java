package jagex.oldscape;

import jagex.jagex3.js5.ReferenceTable;

import java.security.SecureRandom;
import java.util.concurrent.Callable;

public class SecureRandomCallable implements Callable<SecureRandom> {

  public static ReferenceTable aReferenceTable382;

  public static int anInt1341;

  public SecureRandomCallable() {

  }

  public SecureRandom call() {
    return SecureRandomService.provide();
  }
}
