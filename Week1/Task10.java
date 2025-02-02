

	import java.util.Scanner;
	import java.util.concurrent.*;

	public class Task10 {

	    public static int[][] multiplyMatrices(int[][] matrixA, int[][] matrixB) {
	        int rowsA = matrixA.length;
	        int colsA = matrixA[0].length;
	        int colsB = matrixB[0].length;

	        int[][] result = new int[rowsA][colsB];

	        ExecutorService executor = Executors.newFixedThreadPool(rowsA * colsB);

	        for (int i = 0; i < rowsA; i++) {
	            for (int j = 0; j < colsB; j++) {
	                final int row = i;
	                final int col = j;

	                executor.submit(() -> {
	                    result[row][col] = 0;
	                    for (int k = 0; k < colsA; k++) {
	                        result[row][col] += matrixA[row][k] * matrixB[k][col];
	                    }
	                });
	            }
	        }

	        executor.shutdown();
	        try {
	        
	            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }

	        return result;
	    }

	    public static void printMatrix(int[][] matrix) {
	        for (int[] row : matrix) {
	            for (int element : row) {
	                System.out.print(element + " ");
	            }
	            System.out.println();
	        }
	    }

	    public static void main(String[] args) {
	        Scanner scanner = new Scanner(System.in);

	        System.out.println("Enter the number of rows and columns for matrix A:");
	        int rowsA = scanner.nextInt();
	        int colsA = scanner.nextInt();

	        int[][] matrixA = new int[rowsA][colsA];
	        System.out.println("Enter the elements of matrix A:");
	        for (int i = 0; i < rowsA; i++) {
	            for (int j = 0; j < colsA; j++) {
	                matrixA[i][j] = scanner.nextInt();
	            }
	        }

	        System.out.println("Enter the number of rows and columns for matrix B:");
	        int rowsB = scanner.nextInt();
	        int colsB = scanner.nextInt();

	        if (colsA != rowsB) {
	            System.out.println("Matrix multiplication is not possible. Number of columns of A must be equal to the number of rows of B.");
	            return;
	        }

	        int[][] matrixB = new int[rowsB][colsB];
	        System.out.println("Enter the elements of matrix B:");
	        for (int i = 0; i < rowsB; i++) {
	            for (int j = 0; j < colsB; j++) {
	                matrixB[i][j] = scanner.nextInt();
	            }
	        }

	        System.out.println("Matrix A:");
	        printMatrix(matrixA);

	        System.out.println("Matrix B:");
	        printMatrix(matrixB);
	        
	        int[][] result = multiplyMatrices(matrixA, matrixB);

	        System.out.println("Result of multiplication:");
	        printMatrix(result);

	        scanner.close();
	    }
	}


