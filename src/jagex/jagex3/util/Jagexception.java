package jagex.jagex3.util;

import jagex.jagex3.sound.AudioRunnable;

import java.applet.Applet;

public class Jagexception extends RuntimeException {

  public static Applet applet;
  public static String localPlayerName;
  public static AudioRunnable anAudioRunnable1880;

  public final Throwable throwable;
  public String message;

  public Jagexception(Throwable throwable, String message) {
    this.throwable = throwable;
    this.message = message;
  }
}
