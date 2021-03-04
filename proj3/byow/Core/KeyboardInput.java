package byow.Core;

import edu.princeton.cs.introcs.StdDraw;

import java.io.Serializable;

public class KeyboardInput implements Input, Serializable {
    /** referenced hug's demo input source solution */

    public KeyboardInput() {
        StdDraw.text(0.5, 0.7, "CS61B: BYOW");
        StdDraw.text(0.5, 0.5, "New Game (N)");
        StdDraw.text(0.5, 0.45, "Load Game (L)");
        StdDraw.text(0.5, 0.4, "Quit (Q)");
    }

    @Override
    public String getNext() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                String next = Character.toString(c);
                return next;
            }
        }
    }

    @Override
    public boolean hasNext() {
        return true;
    }

}

