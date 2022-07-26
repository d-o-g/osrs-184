package jag.game.relationship;

public class Associate extends Chatter {

    public int rank;
    public int world;
    public int index;

    public Associate() {
        this.world = -1;
    }

    public boolean isLoggedIn() {
        return world > 0;
    }

    public int getWorld() {
        return world;
    }

    public void set(int var1, int var2) {
        this.world = var1;
        this.index = var2;
    }
}
