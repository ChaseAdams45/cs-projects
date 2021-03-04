package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;


import java.io.*;
import java.awt.*;
import java.util.Random;

public class Game implements Serializable {
    private World world;
    private TETile[][] tiles;
    private TETile[][] hiddenTiles;
    private int width;
    private int height;
    private String userInput;
    private Input input;
    private String seedStr;
    private long seed;
    private boolean usingKeyboard;
    private boolean playing = true;

    public Game(int w, int h, String s) {
        this.width = w;
        this.height = h;
        this.userInput = s;
        this.input = new StringInput(s);
        this.usingKeyboard = false;
        this.seedStr = "";
        this.tiles = new TETile[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }

    public Game(int w, int h) {
        this.width = w;
        this.height = h;
        this.userInput = "";
        this.input = new KeyboardInput();
        this.usingKeyboard = true;
        this.seedStr = "";
        this.tiles = new TETile[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }

    public void startGame() {
        String curr = input.getNext().toUpperCase();
        if (curr.equals("N")) {
            newGame();
        } else if (curr.equals("L")) {
            loadGame();
        } else if (curr.equals("C")) {
            changeAv();
        } else { quitGame();}
    }

    private void newGame() {
        StdDraw.clear();
        StdDraw.text(0.5, 0.5, "Enter Seed, (S) to start");
        while (input.hasNext()) {
            String curr = input.getNext();
            if (usingKeyboard) {
                userInput += curr;
                StdDraw.clear();
                StdDraw.text(0.5, 0.5, "Enter Seed, (S) to start");
                StdDraw.text(0.5, 0.25, userInput);
            }
            if (curr.toUpperCase().equals("S")) {
                this.seed = Long.parseLong(seedStr);
                this.world = new World(width, height, seed);
                this.hiddenTiles = world.getTiles();
                this.tiles[world.getAvatarPos().getX()][world.getAvatarPos().getY()] = Tileset.AVATAR;
                this.updateTiles();
                break;
            } else {
                this.seedStr += curr;
            }
        }

    }

    public void playGame(TERenderer ter) {
        while (input.hasNext()) {
            //updateTiles();
            showHUD(ter);
            if (StdDraw.hasNextKeyTyped() && usingKeyboard || !usingKeyboard) {
                String curr = input.getNext();
                if (usingKeyboard) {
                    userInput += curr;
                }
                if (curr.equals(":")) {
                    String next = input.getNext();
                    if (next.toUpperCase().equals("Q")) {
                        saveGame();
                        quitGame();
                    }
                }
                else if (curr.toUpperCase().equals("T")) {
                    turnOnLight();
                } else {
                    world.moveAv(curr, this.tiles);
                    updateTiles();
                    tiles[world.getAvatarPos().getX()][world.getAvatarPos().getY()] = Tileset.AVATAR;
                    ter.renderFrame(this.tiles);
                }
            }
        }
    }

    private void loadGame() {
        File file = new File("./saved_game.txt");
        if (file.exists()) {
            try {
                FileInputStream fs = new FileInputStream(file);
                ObjectInputStream os = new ObjectInputStream(fs);
                if (!usingKeyboard) {
                    String saved = (String) os.readObject();
                    this.userInput = saved + this.userInput.substring(1);
                    this.input = new StringInput(this.userInput);
                    startGame();
                } else {
                    this.world = (World) os.readObject();
                    this.tiles = world.getTiles();
                }

                os.close();
                fs.close();
            } catch (IOException | ClassNotFoundException e) {
                System.exit(0);
            }
        }
    }

    private void quitGame() {
        System.exit(0);
    }

    private void showHUD(TERenderer ter) {
        ter.renderFrame(this.tiles);
        int x = (int) StdDraw.mouseX();
        int y = (int) StdDraw.mouseY();
        if (x < width && y < height) {
            StdDraw.setPenColor(StdDraw.WHITE);
            String text = hiddenTiles[x][y].description();
            if (text.equals("mountain") || text.equals("sand")) {text = "light"; }
            if (tiles[x][y] == Tileset.AVATAR) {
                text = "you";
            }
            if (tiles[x][y] != Tileset.AVATAR && text == "you"){
                text = "floor";
            }
            StdDraw.text(10, height - 2, text);
            StdDraw.show();
        }
    }

    private void saveGame() {
        try {
            File file = new File("./saved_game.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(file);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            if (!usingKeyboard) {
                os.writeObject(this.userInput.substring(0, userInput.length() - 2));
            } else {
                os.writeObject(this.getWorld());
            }
            os.close();
            fs.close();
            return;
        } catch (IOException e) {
            System.exit(0);
        }
    }

    private void turnOnLight() {
        int x = world.getAvatarPos().getX();
        int y = world.getAvatarPos().getY();
        if (tiles[x][y + 1].description().equals("mountain")) {
            tiles[x][y + 1] = Tileset.SAND;
        }
        if (tiles[x][y - 1].description().equals("mountain")) {
            tiles[x][y - 1] = Tileset.SAND;
        }
        if (tiles[x + 1][y].description().equals("mountain")) {
            tiles[x + 1][y] = Tileset.SAND;
        }
        if (tiles[x - 1][y].description().equals("mountain")) {
            tiles[x - 1][y] = Tileset.SAND;
        }
    }

    private void updateTiles() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (!tiles[x][y].description().equals("sand")) {
                    tiles[x][y] = Tileset.NOTHING;
                }
            }
        }
        int x = world.getAvatarPos().getX();
        int y = world.getAvatarPos().getY();
        for (int x2 = x - 2; x2 < x + 3; x2++) {
            for (int y2 = y - 2; y2 < y + 3; y2++) {
                if ((x2 >= 0 && y2 >= 0) || (x2 > width - 1 && y2 > height - 1)) {
                    String desc = hiddenTiles[x2][y2].description();
                    if (desc.equals("floor")) {
                        this.tiles[x2][y2] = Tileset.FLOOR;
                    }
                    else if (desc.equals("wall")) {
                        this.tiles[x2][y2] = Tileset.WALL;
                    }
                    else if (desc.equals("mountain")) {
                        this.tiles[x2][y2] = Tileset.MOUNTAIN;
                    }
                    else if (desc.equals("you")) {
                        this.tiles[x2][y2] = Tileset.FLOOR;
                        this.tiles[x][y] = Tileset.AVATAR;
                    }
                }
            }
        }
        return;
    }

    private void changeAv() {
        StdDraw.clear();
        StdDraw.text(0.5, 0.5, "Enter your character");
        if (input.hasNext()) {
            char in = input.getNext().charAt(0);
            Tileset.AVATAR = new TETile(in, Color.white, Color.black, "you");
            if (input.hasNext()) {
                StdDraw.clear();
                StdDraw.text(0.5, 0.5, "Press (N) to play");
                if (input.getNext().toUpperCase().equals("N")) {
                    newGame();
                } else {
                    quitGame();
                }
            }
        }

    }

    public World getWorld() {
        return world;
    }

    public long getSeed() {
        return seed;
    }

    public Input getInput() {
        return this.input;
    }

    public boolean isPlaying() {
        return this.playing;
    }

    public TETile[][] getTiles() {
        return this.tiles;
    }
}
