package org.eda2.DP;

public class KnapSack {
  //METODOS RECURSIVOS  
	// Returns the maximum value that can be put in a knapsack of capacity W
    public static int knapSackRecursive(int W, int wt[], int val[], int n) {
        if (n == 0 || W == 0)
            return 0;
        if (wt[n - 1] > W)
            return knapSackRecursive(W, wt, val, n - 1);
        else
            return Math.max(knapSackRecursive(W - wt[n - 1], wt, val, n - 1) + val[n - 1],
                       knapSackRecursive(W, wt, val, n - 1));
    }
    
    // Returns the value of maximum profit using memoization
    public static int knapSackRec(int W, int wt[], int val[], int n, int[][] dp) {
         if (n == 0 || W == 0)
            return 0;
        if (dp[n][W] != -1)
            return dp[n][W];
        if (wt[n - 1] > W)
            return dp[n][W] = knapSackRec(W, wt, val, n - 1, dp);
        else
            return dp[n][W] = Math.max((knapSackRec(W - wt[n - 1], wt, val, n - 1, dp) + val[n - 1]),
                      knapSackRec(W, wt, val, n - 1, dp));
    }
 
    public static int knapSackMem(int W, int wt[], int val[], int N) {
        int dp[][] = new int[N + 1][W + 1];
        for (int i = 0; i < N + 1; i++)
            for (int j = 0; j < W + 1; j++)
                dp[i][j] = -1;
        return knapSackRec(W, wt, val, N, dp);
    }
    
    // Returns the maximum value that can be put in a knapsack of capacity W - Dynamic Programming
    public static int knapSackDP(int W, int wt[], int val[]) {
        int i, w;
        int n = val.length;
        int B[][] = new int[n + 1][W + 1];
      
        for (i = 0; i <= n; i++) {
            for (w = 0; w <= W; w++) {
                if (i == 0 || w == 0)
                    B[i][w] = 0;
                else if (wt[i - 1] <= w)
                    B[i][w] = Math.max(B[i - 1][w - wt[i - 1]] + val[i - 1],
                              B[i - 1][w]);
                else
                    B[i][w] = B[i - 1][w];
            }
        }
        
        int [] Sol = new int[n];
        int auxW = W;
        for (i = n; i >= 2; i--) {
        	if (B[i - 1][auxW] == B[i][auxW]) {
        		Sol[i - 1] = 0;
        	}
        	else {
        		Sol[i - 1] = 1;
        		auxW -= wt[i - 1];
        	}
        }
        if (B[1][auxW] != 0)
        	Sol[0] = 1;
        else
        	Sol[0] = 0;
        
        System.out.print("Solution: ");
        for (i = 0; i < Sol.length; i++)
        	System.out.print(Sol[i] + ", ");
        System.out.println();
 
        return B[n][W];
    }

    public static int knapSackDP2(int W, int wt[], int val[]) {
        int k, w;
        int n = val.length;
        int B[][] = new int[n + 1][W + 1];
        
        for (w = 0; w <= W; w++)
        	B[0][w] = 0;
 
        for (k = 1; k <= n; k++) {
            for (w = 0; w <= wt[k - 1] - 1; w++) {
            	B[k][w] = B[k - 1][w];
            }
            for (w = wt[k - 1]; w <= W; w++) {
            	B[k][w] = Math.max(B[k - 1][w - wt[k - 1]] + val[k - 1], B[k - 1][w]);
            }
        }
        
        int [] Sol = new int[n];
        int auxW = W;
        for (int i = n; i >= 2; i--) {
        	if (B[i - 1][auxW] == B[i][auxW]) {
        		Sol[i - 1] = 0;
        	}
        	else {
        		Sol[i - 1] = 1;
        		auxW -= wt[i - 1];
        	}
        }
        if (B[1][auxW] != 0)
        	Sol[0] = 1;
        else
        	Sol[0] = 0;
        
        System.out.print("Solution: ");
        for (int i = 0; i < Sol.length; i++)
        	System.out.print(Sol[i] + ", ");
        System.out.println();
        
        System.out.println();
        test(n, W, wt, val, B);
        System.out.println("\n");
        
        return B[n][W];
    }

    public static void test(int j, int c, int wt[], int val[], int B[][]) {
    	if (j > 0) {
    		if (c < wt[j - 1]) {
    			test(j - 1, c, wt, val, B);
    		}
    		else {
    			if (B[j - 1][c - wt[j - 1]] + val[j - 1] > B[j - 1][c]) {
    				test(j - 1, c - wt[j - 1], wt, val, B);
    				System.out.print(j - 1 + ", ");
    			}
    			else {
    				test(j - 1, c, wt, val, B);
    			}
    		}
    	}
    }

    public static int knapSackDPSpaceOptimized(int W, int wt[], int val[]) {
    	int n = val.length;
      int[] dp = new int[W + 1];
        for (int i = 1; i < n + 1; i++) {
            for (int w = W; w >= 0; w--) {
                if (wt[i - 1] <= w)
                    dp[w] = Math.max(dp[w - wt[i - 1]] + val[i - 1], dp[w]);
            }
        }
      return dp[W];
    }
    
	public static void main(String[] args) {
		//int profit[] = new int[] { 60, 100, 120 };
        //int weight[] = new int[] { 10, 20, 30 };
        //int W = 50;
        //int n = profit.length;
		
		//int profit[] = new int[] { 1, 6, 18, 22, 28 };
        //int weight[] = new int[] { 1, 2, 5, 6, 7 };
        //int W = 11;
        //int n = profit.length;

		//int profit[] = new int[] { 10, 10, 12, 18 };
        //int weight[] = new int[] { 2, 4, 6, 9 };
        //int W = 15;
        //int n = profit.length;

		//int profit[] = new int[] { 10, 5, 15, 7, 6, 18, 3 };
        //int weight[] = new int[] { 2, 3, 5, 7, 1, 4, 1 };
        //int W = 15;
        //int n = profit.length;

		//int profit[] = new int[] { 8, 8, 2, 3, 4, 3, 2, 5 };
        //int weight[] = new int[] { 3, 6, 7, 1, 4, 5, 1, 3 };
        //int W = 20;
        //int n = profit.length;

		//int profit[] = new int[] { 12, 10, 20, 15 };
        //int weight[] = new int[] { 2, 1, 3, 2 };
        //int W = 5;
        //int n = profit.length;

		int profit[] = new int[] { 24, 13, 23, 15, 16 };
        int weight[] = new int[] { 12, 7, 11, 8, 9 };
        int W = 26;
        int n = profit.length;

        System.out.println("0/1 Knapsack Problem:");

        System.out.println("Recursive version: " + knapSackRecursive(W, weight, profit, n));
        
        System.out.println("Memoization version: " + knapSackMem(W, weight, profit, n));
        
        System.out.println("Dynamic Programming version 1: " + knapSackDP(W, weight, profit));
        
        System.out.println("Dynamic Programming version 2: " + knapSackDP2(W, weight, profit));
        
        System.out.println("Dynamic Programming version 3: " + knapSackDPSpaceOptimized(W, weight, profit));
	}

}
