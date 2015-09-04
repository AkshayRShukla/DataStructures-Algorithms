/**
 * Assignment to teach dynamic programming using 3 simple example problems:
 * 1. Fibonacci numbers
 * 2. Longest common subsequence
 * 3. Edit distance
 * 
 * @author Akshay Shukla(ashukla33@gatech.edu)
 */
public class DynamicProgrammingAssignment {
	public static int num_calls = 0; //DO NOT TOUCH

	/**
	 * Calculates the nth fibonacci number: fib(n) = fib(n-1) + fib(n-2).
	 * Remember that fib(0) = 0 and fib(1) = 1.
	 * 
	 * This should NOT be done recursively - instead, use a 1 dimensional
	 * array so that intermediate fibonacci values are not re-calculated.
	 * 
	 * The running time of this function should be O(n).
	 * 
	 * @param n A number 
	 * @return The nth fibonacci number
	 */
	public static int fib(int n) {
		num_calls++; //DO NOT TOUCH

		int[] fibArray = new int[Math.max(n + 1, 2)];
		fibArray[0] = 0;
		fibArray[1] = 1;
		if (n > 1) {
			int i = 2;
			while (i < fibArray.length) {
				fibArray[i] = fibArray[i - 1] + fibArray[i - 2];
				i++;
			}
		}
		return fibArray[n];
	}

	/**
	 * Calculates the length of the longest common subsequence between a and b.
	 * 
	 * @param a
	 * @param b
	 * @return The length of the longest common subsequence between a and b
	 */
	public static int lcs(String a, String b) {
		num_calls++; //DO NOT TOUCH
		int[][] grid = new int[b.length() + 1][a.length() + 1];
		for (int i = 1; i < grid.length; i++) {
			for (int j = 1; j < grid[0].length; j++) {
				if (a.charAt(j - 1) == b.charAt(i - 1)) {
					grid[i][j] = grid[i - 1][j - 1] + 1;
				} else {
					grid[i][j] = Math.max(grid[i - 1][j], grid[i][j - 1]);
				}
			}
		}
		return grid[b.length()][a.length()];
	}


	/**
	 * Calculates the edit distance between two strings.
	 * 
	 * @param a A string
	 * @param b A string
	 * @return The edit distance between a and b
	 */
	public static int edit(String a, String b) {
		num_calls++; //DO NOT TOUCH
        int[][] grid = new int[b.length() + 1][a.length() + 1];
        for (int i = 0; i < grid[0].length; i++) {
            grid[0][i] = i;
        }
        for (int i = 0; i < grid.length; i++) {
            grid[i][0] = i;
        }
        for (int j = 1; j < grid.length; j++) {
            for (int i = 1; i < grid[0].length; i++) {
//              int temp = Math.min(grid[j][i] + 1, grid[j-1][i]+1);
//              int temp2 = Math.min(temp, grid[j - 1][i - 1] + (a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1));
//              grid[j][i] = temp2;
                int temp1 = grid[j][i - 1] + 1;
                int temp2 = grid[j-1][i] +1;
                int temp3 = grid[j-1][i-1];
                if (a.charAt(i-1) != b.charAt(j-1)) {
                    temp3++;
                }
                grid[j][i] = Math.min(temp1, Math.min(temp2, temp3));
                        
            }
        }
        return grid[b.length()][a.length()];
    }



}
