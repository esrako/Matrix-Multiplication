// 2016.11.15
// E
public class WorkerThread implements Runnable {
  
    private int[][] matrixA;
    private int[][] matrixB;
    private int[][] output;
    private int row;
    private int col;
    
    public WorkerThread(int[][] matrixA, int[][] matrixB, int[][] output, int row, int col){
        this.matrixA=matrixA;
        this.matrixB=matrixB;
        this.output=output;
        this.row=row;
        this.col=col;
    }

    @Override
    public void run() {
        //System.out.println(Thread.currentThread().getName()+" Start. row: " + row + ", col: " + col);
        processCommand();
        //System.out.println(Thread.currentThread().getName()+" End.");
    }

    // This is the worker thread's task:
    // Calculate single cell of output array: output[row][col]
    private void processCommand() {
        try {

            int sum = 0;
            for(int term=0; term<matrixB.length; term++){
                sum += matrixA[row][term]*matrixB[term][col];
            }
            output[row][col] = sum;
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public String toString(){
        return "thread for row: " + row + ", col: " + col;
    }
}
