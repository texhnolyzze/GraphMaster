package graphmaster.utils.trie_map;

import java.util.Arrays;
import java.util.Random;

public class Alphabet {
    
    private static final Random random = new Random();
    
    private final int alphaLength;
    
    private final char[] alphabet;
    private final int[] correspondence;    
    
    public static final String DECIMAL = "0123456789";
    public static final String BASE64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    
    public Alphabet(String alphaString) {
        this(alphaString.toCharArray());
    }
    
    public Alphabet(char[] alpha) {
        boolean[] unic = new boolean[Character.MAX_VALUE];
        correspondence = new int[Character.MAX_VALUE];
        Arrays.fill(correspondence, -1);
        for (char c : alpha) {
            if (unic[c]) throw new IllegalArgumentException("Character " + c + " not unic!");
            unic[c] = true;
            correspondence[c] = c;
        }
        alphabet = alpha;
        alphaLength = alpha.length;
        for (int i = 0; i < alphabet.length; i++) {
            correspondence[alphabet[i]] = i;
        }
    }
    
    public Alphabet(int radix) {
        if (radix < 1 || radix > Character.MAX_VALUE) 
            throw new IllegalArgumentException("Radix must be from 1 to 65535");
        alphaLength = radix;
        alphabet = new char[radix];
        correspondence = new int[radix];
        for (int i = 0; i < radix; i++) {
            alphabet[i] = (char) i;
            correspondence[i] = i;
        }
    }
    
    public int length() {
        return alphaLength;
    }
    
    public boolean contains(char c) {
        return c < correspondence.length && correspondence[c] != -1;
    }
    
    public int toIndex(char c) {
        if (!contains(c)) throw new IllegalArgumentException("Character " + c + " not in alphabet!");
        return correspondence[c];
    }
    
    public char toChar(int index) {
        return alphabet[index];
    }
    
    public boolean isStringInAlphabet(String s) {
        for (char c : s.toCharArray()) if (!contains(c)) return false;
        return true;
    }
    
    public String randomString(int approximateStringLength) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < random.nextInt(approximateStringLength); i++) {
            char c = toChar(random.nextInt(alphaLength));
            builder.append(c);
        }
        return builder.toString();
    }
    
}
