public class OffByN implements CharacterComparator {
    private int offBy;
    public OffByN(int n) {
        offBy = n;
    }
    @Override
    public boolean equalChars(char x, char y) {
        return x - y == offBy || x - y == -offBy;
    }
}
