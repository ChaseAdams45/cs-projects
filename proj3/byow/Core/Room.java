package byow.Core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Room implements Serializable {
    /** class that will create a list of floor positions of a room
     * given a lower left corner, width, and height
     */
    private List<Position> positions;
    private int width;
    private int height;
    private Position upperLeft;
    private Position upperRight;
    private Position lowerLeft;
    private Position lowerRight;
    private Position center;

    public Room(Position lowerLeft, int width, int height) {
        this.width = width;
        this.height = height;
        this.lowerLeft = lowerLeft;
        this.upperLeft = new Position(lowerLeft.getX(), lowerLeft.getY() + height);
        this.lowerRight = new Position(lowerLeft.getX() + width, lowerLeft.getY());
        this.upperRight = new Position(lowerLeft.getX() + width, lowerLeft.getY() + height);
        this.positions = roomPositions();
        this.center = new Position((int) Math.floor((2*lowerLeft.getX() + width)/2), (int) Math.floor(((2*lowerLeft.getY() + height)/2)));
    }

    public List<Position> roomPositions() {
        List<Position> roomCoords = new ArrayList<>();
        for (int x = lowerLeft.getX(); x <= width + lowerLeft.getX(); x++) {
            for (int y = lowerLeft.getY(); y <= height + lowerLeft.getY(); y++) {
                Position p = new Position(x, y);
                roomCoords.add(p);
            }
        }
        return roomCoords;
    }
    public boolean overlaps(Room r1, Room r2) {
        return r1.lowerLeft.getX() >= r2.lowerLeft.getX() &&
                r1.lowerLeft.getY() >= r2.lowerLeft.getY() &&
                r1.lowerLeft.getX() <= (r2.lowerLeft.getX() + r2.height) &&
                r1.lowerLeft.getY() <= (r2.lowerLeft.getY() + r2.width)
                || (r1.lowerLeft.getX() + r1.height) >= r2.lowerLeft.getX() &&
                (r1.lowerLeft.getY() + r1.width) >= r2.lowerLeft.getY() &&
                (r1.lowerLeft.getX() + r1.height) <= (r2.lowerLeft.getX() + r2.height) &&
                (r1.lowerLeft.getY() + r1.width) <= (r2.lowerLeft.getY() + r2.width);
    }

    public Position center() {
        return this.center;
    }
    public List<Position> getRoomPositions() {
        return this.positions;
    }
}
