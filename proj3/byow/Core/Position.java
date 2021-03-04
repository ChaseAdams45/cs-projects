package byow.Core;

import java.io.Serializable;

public class Position implements Serializable {
    /** class for storing position Objects to be added to TETile array */
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}
