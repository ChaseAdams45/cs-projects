package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.io.Serializable;

public class Avatar implements Serializable {
    int xPos;
    int yPos;
    private TETile tiles;

    public Avatar(int x, int y, TETile tile) {
        this.xPos = x;
        this.yPos = y;
        this.tiles = tile;
    }

    public void moveAvatar(int x, int y, TETile[][] t) {
        if (t[x][y].description().equals("floor")) {
            t[xPos][yPos] = Tileset.FLOOR;
            t[x][y] = Tileset.AVATAR;
            xPos = x;
            yPos = y;
        }
    }
}
