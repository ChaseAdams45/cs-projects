public class Palindrome {

    public Deque<Character> wordToDeque(String word) {
        Deque<Character> palDeque = new ArrayDeque<Character>();
        for (int i = 0; i < word.length(); i++) {
            palDeque.addLast(word.charAt(i));
        }
        return palDeque;
    }

    public boolean isPalindrome(String word) {
        if (word == null) {
            return false;
        }
        Deque<Character> wordDeque = wordToDeque(word);

        while (wordDeque.size() > 1) {
            if (wordDeque.removeLast() != wordDeque.removeFirst()) {
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word == null) {
            return false;
        }
        Deque<Character> wordDeque = wordToDeque(word);
        while (wordDeque.size() > 1) {
            Character first = wordDeque.removeFirst();
            Character last = wordDeque.removeLast();
            if (!(cc.equalChars(first, last))) {
                return false;
            }
        }
        return true;
    }
}
