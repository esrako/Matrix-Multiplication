// 2016.11.15
// E
import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.lang.Math;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Solution {

    private String inputA;
    private String inputB;
    private String output;
    private int nrThreads;
    
    public static final String SPACE = " ";
    public static final String NL = "\n";

    public Solution(String inputA, String inputB, String output, int nrThreads){
        this.inputA = inputA;
        this.inputB = inputB;
        this.output = output;
        this.nrThreads = nrThreads;
    }

    // For a given Solution object, multiplies the matrix read from inputA
    // with matrix read from inputB, and saves the output matrix to output file.
    // uses nrThreads to do the calculation
    public void multiply() throws Exception{

        try{

            int[][] matrixA = readFileToMatrix(inputA);
            int[][] matrixB = readFileToMatrix(inputB);
            int[][] matrixC = multiply(matrixA, matrixB, nrThreads);

            saveMatrixToFile(matrixC, output);
        }
        catch(Exception e){
            System.out.println("An exception occured: " + e.getMessage());
        }
    }

    // Takes 2 int[][] matrix to multiply,
    // Checks if the dimensions are compatible to multiply, if so,
    // Assigns every cell of the output matrix to the next thread available
    // Once all the threads finish their task, it can calculate the duration of the operation
    // Returns the output matrix
    private int[][] multiply(int[][] m1, int[][] m2, int nrThreads) throws Exception{

       if(!isValid(m1,m2)){
           throw new Exception("Invalid/unmatching matrices were given to multiply");
       }

       //printMatrix(m1);
       //printMatrix(m2);

       int row = m1.length;
       int col = m2[0].length;
       int[][] result = new int[row][col];
       int terms = m2.length;//or m1[0].length

       long start = System.currentTimeMillis(); 
       //start

       ExecutorService executor = Executors.newFixedThreadPool(nrThreads);
       for(int i=0; i<row; i++){
           for(int j=0; j<col; j++){
               Runnable worker = new WorkerThread(m1, m2, result, i, j);
               executor.execute(worker);
           }
       }

       executor.shutdown();
       while (!executor.isTerminated()) {
       }

       //end - all threads are done with their tasks
       long end = System.currentTimeMillis();
       long duration = end-start;

       System.out.format("With %d threads, time spent on matrix multiplication: %s msec for matrices of sizes %dby%d, and %dby%d \n", nrThreads, String.valueOf(duration), row, m2.length, m2.length, col);

       //printMatrix(result);
       return result;
    }

    // Saves a matrix to an output file
    public static void saveMatrixToFile(int[][] matrix, String output) throws Exception{

        try {

            StringBuilder sb = new StringBuilder();
            sb.append(matrix.length + SPACE + matrix[0].length + NL);
            for(int i=0; i<matrix.length; i++){
                for(int j=0; j<matrix[0].length; j++){
                    sb.append(matrix[i][j] + SPACE);
                }
                sb.append(NL);
            }

            File file = new File(output);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(sb.toString());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }  
  
    // Reads an input file and constructs a matrix
    // Returns the matrix int[][]
    public static int[][] readFileToMatrix(String input) throws Exception{

        BufferedReader br = new BufferedReader(new FileReader(input));
        try
        {
            String line = br.readLine();

            //first line: m n
            String[] list = line.split("\\s+");
            int row = Integer.parseInt(list[0]);
            int col = Integer.parseInt(list[1]);

            int[][] result = new int[row][col];
            int curRow = 0;
            int curCol;

            line = br.readLine();
            while (line != null) {
                
                list = line.split("\\s+");

                if(list.length!=col){
                    throw new Exception("Invalid input file: " + input);
                }
                curCol = 0;
                while( curCol<col){
                    result[curRow][curCol] = Integer.parseInt(list[curCol]);
                    curCol++;
                }

                line = br.readLine();
                curRow++;
            }
            if(curRow!=row){
                throw new Exception("Invalid input file: " + input);
            }
            return result;
        }
        finally
        {
            br.close();
        }
    }
    
    // Checks whether the given matrices m1 and m2 are compatible to multiply m1xm2
    // Returns true, if we can multiply m1 by m2
    // Returns false, otherwise
    // Please note isValid(m1,m2) is not necessarily same with isValid(m2,m1)
    private static boolean isValid(int[][] m1, int[][] m2){

        if(m1.length==0 || m1[0].length==0 || m2.length==0 || m2[0].length==0){
            return false;
        }
        int row1 = m1.length;
        int col1 = m1[0].length;
        int row2 = m2.length;
        int col2 = m2[0].length;
        
        return col1==row2;
    }

    // Prints a matrix, for debuggging only
    public static void printMatrix(int[][] matrix){
        if(matrix.length==0 || matrix[0].length==0){
            System.out.println("Cannot print this matrix");
        }
        for(int i=0; i<matrix.length; i++){
            for(int j=0; j<matrix[0].length; j++){
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
    }

    // Creates a matrix with given dimensions n by m
    // Populates the matrix with random numbers from 0 to 9.
    public static int[][] createMatrix(int n, int m){
        int[][] result = new int[n][m];
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                result[i][j] = (int)(Math.random() * 10);
            }
        }
        return result;
    }
}
