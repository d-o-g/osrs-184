package jag.game.relationship;

import jag.ClientParameter;
import jag.LoginScreenEffect;
import jag.script.ScriptEvent;

public class NamePair implements Comparable {

    public final String raw;
    public final String formatted;

    public NamePair(String raw, ClientParameter nameLengthParameter) {
        this.raw = raw;
        this.formatted = ScriptEvent.format(raw, nameLengthParameter);
    }

    public int compare0(NamePair other) {
        if (this.formatted == null) {
            return other.formatted == null ? 0 : 1;
        }

        return other.formatted == null ? -1 : this.formatted.compareTo(other.formatted);
    }

    public boolean isFormattedPresent() {
        return this.formatted != null;
    }

    public String getRaw() {
        return this.raw;
    }

    public boolean equals(Object o) {
        if (!(o instanceof NamePair)) {
            return false;
        }

        NamePair other = (NamePair) o;
        if (this.formatted == null) {
            return other.formatted == null;
        }

        if (other.formatted == null) {
            return false;
        }

        return this.hashCode() == other.hashCode() && this.formatted.equals(other.formatted);
    }

    public int hashCode() {
        return this.formatted == null ? 0 : this.formatted.hashCode();
    }

    public String toString() {
        return this.getRaw();
    }

    public int compareTo(Object var1) {
        return this.compare0((NamePair) var1);
    }
}
