import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class main {

    public static void main(String[] args)  {;
        List<String> palindromes = generatePalindromes("bbccaa");
        System.out.println(palindromes);

    }

    public static List<String> generatePalindromes(String input) {
        List<String> returnList = new ArrayList<>();
        HashMap<Character, Integer> charCount = new HashMap<>();
        char[] inputCharArray = input.toCharArray();

        for(int i = 0; i < inputCharArray.length; i++) {
            int item = charCount.computeIfAbsent(inputCharArray[i], x -> 0);
            item++;
            charCount.put(inputCharArray[i], item);
        }

        int oddCount = 0;
        char oddChar = 0;
        StringBuilder sb = new StringBuilder();

        for (Character c: charCount.keySet()) {
            int value = charCount.get(c);

            // VFind the middle character
            if (value % 2 != 0) {
                oddCount++;
                oddChar = c;
            }

            // Validation
            if (oddCount > 1) {
                return returnList;
            }

            // Generate me half of the combinations I need
            for (int i = 0; i < value / 2; i++) {
                sb.append(c);
            }
        }

        List<String> halfPermutations = new ArrayList<>();
        permute(sb.toString().toCharArray(), 0, halfPermutations);

        // For each of those permutations, reverse and add the odd char
        for(String halfPerm : halfPermutations) {
            StringBuilder palindrome = new StringBuilder(halfPerm);
            if (oddCount == 1) {
                palindrome.append(oddChar);
            }
            palindrome.append(new StringBuilder(halfPerm).reverse());
            returnList.add(palindrome.toString());

        }

        return returnList;
    }

    private static void permute(char[] chars, int index, List<String> permutations) {
        if (index == chars.length) {
            permutations.add(new String(chars));
            return;
        }

        for (int i = index; i < chars.length; i++) {
            swap(chars, index, i);
            permute(chars, index + 1, permutations);
            swap(chars, index, i);
        }
    }

    private static void swap(char[] chars, int i, int j) {
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
    }
}
