package jag.game.relationship;

public class Chatter<T extends Chatter<T>> implements Comparable<T> {

    public NamePair previousName;
    public NamePair displayName;

    public Chatter() {
    }

    public int compare0(T var1) {
        return this.displayName.compare0(var1.displayName);
    }

    public NamePair getDisplayName() {
        return this.displayName;
    }

    public void setName(NamePair displayName, NamePair previousName) {
        if (displayName == null) {
            throw new NullPointerException();
        }
        this.displayName = displayName;
        this.previousName = previousName;
    }

    public String getRawDisplayName() {
        return this.displayName == null ? "" : this.displayName.getRaw();
    }

    public String getRawPreviousName() {
        return this.previousName == null ? "" : this.previousName.getRaw();
    }

    public int compareTo(T var1) {
        return this.compare0(var1);
    }
}
