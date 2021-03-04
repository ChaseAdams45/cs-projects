package byow.Core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Hallway implements Serializable {
    private List<Position> hallwayPositions;
    private Room r1;
    private Room r2;


    public Hallway(Room r1, Room r2, Random random) {
        this.r1 = r1;
        this.r2 = r2;
        this.hallwayPositions = generateHallway(this.r1, this.r2, random);
    }

    public List<Position> generateHallway(Room r1, Room r2, Random random) {
        /** make hallways out of centers, start randomly with vert and horiz
         * @Source https://www.geeksforgeeks.org/java-util-random-nextint-java/ */
        List<Position> positions = new ArrayList<>();
        Position currCenter = r1.center();
        Position prevCenter = r2.center();
        if (random.nextInt(2) == 0) {
            vertHallway(prevCenter.getY(), currCenter.getY(), prevCenter.getX(), positions);
            horizHallway(prevCenter.getX(), currCenter.getX(), currCenter.getY(), positions);
        }
        else {
            horizHallway(prevCenter.getX(), currCenter.getX(), prevCenter.getY(), positions);
            vertHallway(prevCenter.getY(), currCenter.getY(), currCenter.getX(), positions);
        }
        return positions;
    }


    public void horizHallway(int centerRoom1X, int centerRoom2X, int y, List<Position> ps) {
        for (int x = (Math.min(centerRoom1X, centerRoom2X)); x < Math.max(centerRoom1X, centerRoom2X) + 1; x++) {
            /** mine path for hallway */
            ps.add(new Position(x, y));
        }
    }

    public void vertHallway(int centerRoom1Y, int centerRoom2Y, int x, List<Position> ps) {
        for (int y = (Math.min(centerRoom1Y, centerRoom2Y)); y < Math.max(centerRoom1Y, centerRoom2Y) + 1; y++) {
            /** mine path for hallway */
            ps.add(new Position(x, y));
        }
    }

    public List<Position> getHallwayPositions() {
        return this.hallwayPositions;
    }
}

