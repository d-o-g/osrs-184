package jag;

public enum GameType implements EnumType {

    RUNESCAPE("runescape", 0),
    STELLARDAWN("stellardawn", 1),
    GAME3("game3", 2),
    GAME4("game4", 3),
    GAME5("game5", 4),
    OSRS("oldscape", 5);

    public final String name;
    final int index;

    GameType(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public int getOrdinal() {
        return index;
    }
}
