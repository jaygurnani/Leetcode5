import java.util.*;

public class main {

    public static void main(String[] args)  {;
        //List<String> palindromes = generatePalindromes("bbccaa");


        //List<String> output = generateParenthesis(3);

        int[] input = new int[] {1,2,3};
        List<List<Integer>> output = permute(input);
        System.out.println(output);
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

    public static List<String> generateParenthesis(int n) {
        List<String> resultList = new ArrayList<>();
        backtrack(resultList, "", 0, 0, n);
        return resultList;
    }

    public static void backtrack(List<String> resultList, String str, int open, int close, int max) {
        if (str.length() == max * 2) {
            resultList.add(str);
            return;
        }

        if (open < max) {
            backtrack(resultList, str + "(", open+1, close, max);
        }
        if (close < open) {
            backtrack(resultList, str + ")", open, close +1, max);

        }
    }

    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();

        permuteRecursive(nums, 0, result);

        return result;
    }

    public static void permuteRecursive(int[] nums, int index, List<List<Integer>> resultList) {
        if (index == nums.length) {
            resultList.add(Arrays.stream(nums).boxed().toList());
        }

        for(int i = index; i < nums.length; i++) {
            swapInt(nums, index, i);
            permuteRecursive(nums, index + 1 , resultList);
            swapInt(nums, index, i);
        }
    }

    public static void swapInt(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
