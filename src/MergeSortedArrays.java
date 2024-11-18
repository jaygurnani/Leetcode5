import java.util.*;

public class MergeSortedArrays {

    public static int[] mergeAndRemoveDuplicates(int[] arr1, int[] arr2) {
        // Initialize a set to keep track of unique elements
        Set<Integer> uniqueElements = new LinkedHashSet<>();

        // Use two pointers to merge the arrays
        int i = 0, j = 0;

        // Traverse both arrays
        while (i < arr1.length && j < arr2.length) {
            if (arr1[i] < arr2[j]) {
                uniqueElements.add(arr1[i]);
                i++;
            } else if (arr1[i] > arr2[j]) {
                uniqueElements.add(arr2[j]);
                j++;
            } else {
                uniqueElements.add(arr1[i]);
                i++;
                j++;
            }
        }

        // Add remaining elements of arr1
        while (i < arr1.length) {
            uniqueElements.add(arr1[i]);
            i++;
        }

        // Add remaining elements of arr2
        while (j < arr2.length) {
            uniqueElements.add(arr2[j]);
            j++;
        }

        // Convert the set back to an array
        int[] result = new int[uniqueElements.size()];
        int index = 0;
        for (int num : uniqueElements) {
            result[index++] = num;
        }

        return result;
    }

    public static void main(String[] args) {
        int[] arr1 = {1, 3, 4, 5};
        int[] arr2 = {2, 3, 5, 6};

        int[] mergedArray = mergeAndRemoveDuplicates(arr1, arr2);

        System.out.println("Merged and sorted array without duplicates:");
        System.out.println(Arrays.toString(mergedArray));
    }
}
