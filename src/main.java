import javax.swing.tree.TreeNode;
import java.util.*;
import java.util.stream.Collectors;

public class main {

    public static void main(String[] args)  {;
        //List<String> palindromes = generatePalindromes("bbccaa");


        //List<String> output = generateParenthesis(3);
        //int[] input = new int[] {1,2,3};
        //List<List<Integer>> output = permute(input);

        // 100 = 4 = true
        // 1100 = 12 =false
        // 10101 = 21 = true
        // 11101 = 29 = false
        //boolean output = checkSparse(29);

//        int[] input = new int[]{1,3,1,2};
//        var output = mostVisited(4, input);

//        char[] chars = new char[]{'a','a','b','b','c','c','c'};
//        var output = compress(chars);

        //int[] nums = {4,2,3,4};
        //int output = triangleNumber(nums);

//        int[][] logs = {
//                {20190101, 0, 1},
//                {20190104, 3, 4},
//                {20190107, 2, 3},
//                {20190211, 1, 5},
//                {20190224, 2, 4},
//                {20190301, 0, 3},
//                {20190312, 1, 2},
//                {20190322, 4, 5}
//        };
//        var output = earliestAcqFixed(logs, 6);


        //int[] input1 = new int[] {2,1,3,3,3,0,0,10};
        //int output = thirdDistinctTree(input1);

        boolean output = closeStrings("abbzzca", "babzzcz");
        System.out.println(output );
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

    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        TreeNode sameElement = findMatchingRoot(root, subRoot);
        boolean walkTreeEquals = walkTree(sameElement, subRoot);
        return walkTreeEquals;
    }

    public TreeNode findMatchingRoot(TreeNode root, TreeNode subRoot) {
        if (root == null) {
            return null;
        }

        if (root.val == subRoot.val) {
            return root;
        } else if (subRoot.val > root.val) {
            return findMatchingRoot(root.left, subRoot);
        } else if (subRoot.val < root.val) {
            return findMatchingRoot(root.right, subRoot);
        }
        return null;
    }

    public boolean walkTree(TreeNode root, TreeNode subRoot) {
        if (root == null && subRoot == null) {
            return true;
        }
        if ((root == null && subRoot != null) || (root != null && subRoot == null)) {
            return false;
        }

        if (root.val == subRoot.val) {
            return walkTree(root.left, subRoot.left) && walkTree(root.right, subRoot.right);
        }
        return false;
    }

    public int kthSmallest(TreeNode root, int k) {
        List<Integer> result = inOrderTraversal(root);

        System.out.println(result);
        return result.get(k-1);
    }

    public List<Integer> inOrderTraversal(TreeNode current) {
        if (current == null) {
            return List.of();
        }
        if (current.left == null && current.right == null) {
            return List.of(current.val);
        }

        List<Integer> leftTree = inOrderTraversal(current.left);
        List<Integer> rightTree = inOrderTraversal(current.right);

        return merge(leftTree, rightTree, current.val);
    }

   public List<Integer> merge(List<Integer> leftTree, List<Integer> rightTree, int val) {
        List<Integer> returnList = new ArrayList<>();

        returnList.addAll(leftTree);
        returnList.addAll(rightTree);
        returnList.add(val);
        Collections.sort(returnList);

        return returnList;
    }

    public int maxDepth(TreeNode root) {
        return maxDepthSize(root, 0);
    }

    public int maxDepthSize(TreeNode root, int currentSize) {
        if (root == null) {
            return currentSize;
        }

        int leftSize = maxDepthSize(root.left, currentSize) + 1;
        int rightSize = maxDepthSize(root.right, currentSize) + 1;

        if (leftSize > rightSize) {
            return leftSize;
        } else {
            return rightSize;
        }
    }

    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }

        if (p == null || q == null || p.val != q.val) {
            return false;
        }
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    public boolean hasAlternatingBits(int n) {
        String bits = Integer.toBinaryString(n);
        for (int i = 0; i < bits.length() - 1; i++) {
            if (bits.charAt(i) == bits.charAt(i+1)) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkSparse(int n)
    {
        // n is not sparse if there
        // is set in AND of n and n/2
        if ((n & (n >> 1)) >= 1)
            return false;

        return true;
    }

    public static List<Integer> mostVisited(int n, int[] rounds) {
        var start = rounds[0];
        var end = rounds[rounds.length - 1];
        var res = new ArrayList<Integer>();
        if (start <= end) {
            for (int i = start; i <= end; i++) res.add(i);
        } else {
            for (int i = 1; i <= end; i++) res.add(i);
            for (int i = start; i <= n; i++) res.add(i);
        }
        return res;
    }

    public static boolean isValid(String word) {
        if (word.length() < 3) {
            return false;
        }

        if (!word.matches("^[a-zA-Z0-9]+$")) {
            return false;
        }

        if (!word.toLowerCase().matches(".*[aeiou].*")) {
            return false;
        }

        if (!word.toLowerCase().matches(".*[bcdfghjklmnpqrstvwxyz].*")) {
            return false;
        }

        return true;
    }

    public static int compress(char[] chars) {
       int ans = 0;

       for (int i = 0; i < chars.length;) {
           char letter = chars[i];
           int count = 0;

           while (i < chars.length && chars[i] == letter) {
               count++;
               i++;
           }

           chars[ans] = letter;
           ans++;

           if (count > 1) {
               for (final char c : String.valueOf(count).toCharArray()) {
                   chars[ans] = c;
                   ans++;
               }
           }
       }

       return ans;
    }

    public static int triangleNumber(int[] nums) {
        int ans = 0;
        Arrays.sort(nums);

        for(int i = nums.length-1; i > 0; i--) {
           int lo = 0;
           int hi = i - 1;

           while (lo < hi) {
              int result = nums[lo] + nums[hi];
              if (result > nums[i]) {
                  ans += hi - lo;
                  hi--;
              } else {
                  lo++;
              }
           }
        }


        return ans;
    }

    // This does work but is too complicated. We will use the new union / find data structure
    public static int earliestAcq(int[][] logs, int n) {
        int largestCount = 0;
        Arrays.sort(logs, Comparator.comparingInt(a -> a[0]));
        Map<Integer, Set<Integer>> map = new HashMap<>();

        for(int i = 0; i < logs.length; i++) {
            int[] item = logs[i];
            Set<Integer> list = map.computeIfAbsent(item[1], x -> new HashSet<>());
            Stack<Integer> stack = new Stack<>();
            stack.push(item[2]);

            while(!stack.isEmpty()) {
                var toCheck = stack.pop();
                list.add(toCheck);
                Set<Integer> toCheckList = map.computeIfAbsent(toCheck, x -> new HashSet<>());
                for(int j : toCheckList) {
                    stack.push(j);
                }
            }

            largestCount = list.size();
            if (largestCount == n) {
                return item[0];
            }
        }

        return -1;
    }

    public static int earliestAcqFixed(int[][] logs, int n) {
        Arrays.sort(logs, Comparator.comparingInt(a -> a[0]));
        int groupCount = n;
        UnionFind uf = new UnionFind(n);

        for(int i = 0; i < logs.length; i++) {
            var log = logs[i];
            int timestamp = log[0];
            int friendA = log[1];
            int friendB = log[2];

            if (uf.union(friendA, friendB)) {
                groupCount -= 1;
            }

            if (groupCount == 1) {
                return timestamp;
            }
        }

        return -1;
    }

    public static int minSwaps(int[] data) {
        int windowSize = 0;
        for (int num: data) {
            windowSize += num;
        }

        int currOnes = 0;
        int maxOnes = 0;

        for(int i = 0; i < data.length; i++) {
            currOnes += data[i];

            if (i >= windowSize) {
                currOnes -= data[i-windowSize];
            }
            maxOnes = Math.max(maxOnes, currOnes);
        }

        return windowSize - maxOnes;
    }


    public static int thirdDistinct(int[] input) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < input.length; i++) {
            set.add(input[i]);
        }

        set.remove(Collections.max(set));
        set.remove(Collections.max(set));
        return Collections.max(set);

    }

    public static int thirdDistinctTree(int[] input) {
        TreeSet<Integer> distinctNums = new TreeSet<>();

        for (int i = 0; i < input.length; i++) {
            distinctNums.add(input[i]);
            if (distinctNums.size() > 3) {
                distinctNums.pollFirst();
            }
        }

        if (distinctNums.size() < 3) {
            return distinctNums.last();
        }

        return distinctNums.first();
    }

    public static boolean closeStrings(String word1, String word2) {
        if (word1.length() != word2.length()) {
            return false;
        }

        Map<Character, Integer> word1Map = new HashMap<>();
        Map<Character, Integer> word2Map = new HashMap<>();
        for (char c : word1.toCharArray()) {
            word1Map.put(c, word1Map.getOrDefault(c, 0) + 1);
        }
        for (char c : word2.toCharArray()) {
            word2Map.put(c, word2Map.getOrDefault(c, 0) + 1);
        }
        if (!word1Map.keySet().equals(word2Map.keySet())) {
            return false;
        }
        List<Integer> word1FrequencyList = new ArrayList<>(word1Map.values());
        List<Integer> word2FrequencyList = new ArrayList<>(word2Map.values());
        Collections.sort(word1FrequencyList);
        Collections.sort(word2FrequencyList);
        return word1FrequencyList.equals(word2FrequencyList);
    }


    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
