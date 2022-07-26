package jag.game.relationship;

public class Chatter implements Comparable {

    public NamePair previousName;
    public NamePair displayName;

    public Chatter() {
    }

    public int compare0(Chatter var1) {
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

    public int compareTo(Object var1) {
        return this.compare0((Chatter) var1);
    }
}
