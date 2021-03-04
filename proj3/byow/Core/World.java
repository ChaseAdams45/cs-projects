package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World implements Serializable {
    /** class that will generate the world with methods like
     * addRoom, addHallway, addWall that will add the positions to
     * the TETile array
     */
    private int width;
    private int height;
    private long seed;
    private Random random;
    private List<Room> rooms;
    private List<Hallway> hallways;
    private TETile[][] tiles;
    private Avatar avatar;
    private Position avatarPos;
    private static final int MAX_ROOM_SIZE = 7;

    public World(int w, int h, long seed) {
        this.width = w;
        this.height = h;
        this.seed = seed;
        this.random = new Random(this.seed);
        this.rooms = generateRooms(random, width, height);
        this.hallways = generateHallways(rooms);
        this.tiles = new TETile[width][height];
        addRooms(this.tiles);
        addHallways(this.tiles);
        addWalls(this.tiles);
        addAvatar(this.tiles);
        fillInBlanks(this.tiles);
    }


    public List<Room> generateRooms(Random r, int width, int height) {
        List<Room> roomList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            int randX = r.nextInt(width - MAX_ROOM_SIZE - 2) + 1;
            int randY = r.nextInt(height - MAX_ROOM_SIZE - 2) + 1;
            int randWidth = r.nextInt(MAX_ROOM_SIZE) + 1;
            int randHeight = r.nextInt(MAX_ROOM_SIZE) + 1;
            Position lowerLeft = new Position(randX, randY);
            Room newRoom = new Room(lowerLeft, randWidth, randHeight);

            if (roomList.size() == 0) {
                roomList.add(newRoom);
            } else {
                boolean roomOverlap = false;
                for (Room room: roomList) {
                    if (newRoom.overlaps(newRoom, room)) {
                        roomOverlap = true;
                    }
                }
                if (!roomOverlap) {
                    roomList.add(newRoom);
                }
            }
        }
        return roomList;
    }

    public List<Hallway> generateHallways(List<Room> roomList) {
        List<Hallway> hallwayList = new ArrayList<>();
        for (int r = 0; r < roomList.size() - 1; r++) {
            Hallway newHallway = new Hallway(roomList.get(r), roomList.get(r + 1), this.random);
            hallwayList.add(newHallway);
        }
        return hallwayList;
    }

    public void addRooms(TETile[][] tiles) {
        for(Room room : rooms) {
            for (Position p : room.getRoomPositions()) {
                tiles[p.getX()][p.getY()] = Tileset.FLOOR;
            }
        }
    }

    public void addHallways(TETile[][] tiles) {
        for (Hallway h : hallways) {
            for (Position p : h.getHallwayPositions()) {
                tiles[p.getX()][p.getY()] = Tileset.FLOOR;
            }
        }
    }

    public void addWalls(TETile[][] tiles) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (tiles[x][y] == Tileset.FLOOR) {
                    for (int x2 = x -1; x2 <= x + 1; x2++) {
                        for (int y2 = y -1; y2 <= y + 1; y2++) {
                            if (tiles[x2][y2] != Tileset.FLOOR) {
                                int r = random.nextInt(30);
                                if (r < 1) {
                                    tiles[x2][y2] = Tileset.MOUNTAIN;
                                } else {tiles[x2][y2] = Tileset.WALL;}
                            }
                        }
                    }
                }
            }
        }
    }

    public void addAvatar(TETile[][] tiles) {
        Room room = rooms.get(random.nextInt(rooms.size() - 1));
        Position p = room.getRoomPositions().get(random.nextInt(room.getRoomPositions().size() - 1));
        tiles[p.getX()][p.getY()] = Tileset.AVATAR;
        Avatar avatar = new Avatar(p.getX(), p.getY(), tiles[p.getX()][p.getY()]);
        this.avatar = avatar;
        this.avatarPos = new Position(avatar.xPos, avatar.yPos);
    }

    public void fillInBlanks(TETile[][] tiles) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if(tiles[x][y] == null) {
                    tiles[x][y] = Tileset.NOTHING;
                }
            }
        }
    }

    public void moveAv(String input, TETile[][] t) {
        if (input.toUpperCase().equals("W")) {
            avatar.moveAvatar(avatar.xPos, avatar.yPos + 1, t);
        }
        if (input.toUpperCase().equals("A")) {
            avatar.moveAvatar(avatar.xPos - 1, avatar.yPos, t);
        }
        if (input.toUpperCase().equals("S")) {
            avatar.moveAvatar(avatar.xPos, avatar.yPos - 1, t);
        }
        if (input.toUpperCase().equals("D")) {
            avatar.moveAvatar(avatar.xPos + 1, avatar.yPos, t);
        }
        avatarPos = new Position(avatar.xPos, avatar.yPos);
    }

    public TETile[][] getTiles() {
        return this.tiles;
    }

    public Position getAvatarPos() {
        return this.avatarPos;
    }
}