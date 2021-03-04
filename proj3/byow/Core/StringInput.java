package byow.Core;

import java.io.Serializable;

public class StringInput implements Input, Serializable {
    private String input;
    private int index;
    /** referenced hug's demo input source solution */

    public StringInput(String input) {
        this.input = input;
        this.index = 0;
    }

    @Override
    public String getNext() {
        char c = input.charAt(this.index);
        String next = Character.toString(c);
        index++;
        return next;
    }

    @Override
    public boolean hasNext() {
        return index < input.length();
    }
}
