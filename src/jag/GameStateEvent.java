package jag;

import jag.commons.time.Clock;
import jag.opcode.Buffer;

public class GameStateEvent {

    final long alwaysZero2;

    final int anInt1527;

    public boolean shouldProcess;

    long logoutTime;
    long lastPacketReceived;
    long loginTime;
    long gameStateChangeTime;

    int alwaysZero;
    int loginCount;
    int gameState;

    public GameStateEvent() {
        lastPacketReceived = -1L;
        gameStateChangeTime = -1L;
        shouldProcess = false;
        loginTime = 0L;
        logoutTime = 0L;
        alwaysZero2 = 0L;
        gameState = 0;
        alwaysZero = 0;
        loginCount = 0;
        anInt1527 = 0;
    }

    public void completeLogin() {
        if (gameStateChangeTime != -1L) {
            loginTime = Clock.now() - gameStateChangeTime;
            gameStateChangeTime = -1L;
        }

        ++loginCount;
        shouldProcess = true;
    }

    public void dropConnection() {
        if (lastPacketReceived != -1L) {
            logoutTime = Clock.now() - lastPacketReceived;
            lastPacketReceived = -1L;
        }

    }

    public void updateGameState(int gameState) {
        gameStateChangeTime = Clock.now();
        this.gameState = gameState;
    }

    public void updateTime() {
        lastPacketReceived = Clock.now();
    }

    public void writeTo(Buffer buffer) {
        long var2 = logoutTime;
        var2 /= 10L;
        if (var2 < 0L) {
            var2 = 0L;
        } else if (var2 > 65535L) {
            var2 = 65535L;
        }

        buffer.p2((int) var2);
        long var4 = loginTime;
        var4 /= 10L;
        if (var4 < 0L) {
            var4 = 0L;
        } else if (var4 > 65535L) {
            var4 = 65535L;
        }

        buffer.p2((int) var4);
        long var6 = alwaysZero2;
        var6 /= 10L;
        if (var6 < 0L) {
            var6 = 0L;
        } else if (var6 > 65535L) {
            var6 = 65535L;
        }

        buffer.p2((int) var6);
        buffer.p2(gameState);
        buffer.p2(alwaysZero);
        buffer.p2(loginCount);
        buffer.p2(anInt1527);
    }

    public void method1118() {
        shouldProcess = false;
        alwaysZero = 0;
    }

    public void method1116() {
        completeLogin();
    }
}
