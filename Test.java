// 2016.11.15
// E
public class Test{

    public static void main(String[] args){

        if(args.length<4){
            //Usage is: java Test inputA inputb output 3
            System.out.println("Missing args");
            return;
        }

        String inputA = args[0];
        String inputB = args[1];
        String output = args[2];
        int nrThreads = Integer.parseInt(args[3]);
        if(nrThreads<1){
            System.out.println("Please enter a positive integer for number of threads.");
            return;
        }

        try{
            Solution s = new Solution(inputA, inputB, output, nrThreads);
            s.multiply();

            //runTestCases();
        }
        catch(Exception e){
            System.out.println("Exception occured: " + e.getMessage());
        }
    }

    public static void runTestCases() throws Exception{

        /*
        for(int i=1; i<=500;){
            int[][] matrixA = Solution.createMatrix((i==1)?i+1:i,200);
            int[][] matrixB = Solution.createMatrix(200,i+1);
            String inputA = "files/matrixA" + String.valueOf((i==1)?i+1:i) + "by200.csv";
            String inputB = "files/matrixB" + "200by" + String.valueOf(i+1) + ".csv";
            Solution.saveMatrixToFile(matrixA,inputA);
            Solution.saveMatrixToFile(matrixB,inputB);

            for(int j=1; j<=25;){
                String output = "files/output" + String.valueOf((i==1)?i+1:i) + "by" + String.valueOf(i+1) + "with" + j + "threads.csv";
                Solution s = new Solution(inputA, inputB, output, j);
                s.multiply();
                if(j==1){j=5;}else{j+=5;}
            }
            if(i==1){i=100;}else{i+=100;}
        }*/

        try{
            //matrix dimensions not suitable to multiply
            //Expected output:
            //Invalid/unmatching matrices were given to multiply
            Solution s = new Solution("files/mA.csv", "files/mA.csv", "files/mC.csv", 5);
            s.multiply();
        }
        catch(Exception e){
            System.out.println("Exception: " + e.getMessage());
        }
        try{
            //input file is not valid - matrix dimensions given in 1st line does not match number of rows and cols in the file
            //Expected output:
            //Invalid input file: files/ina2.csv
            Solution s = new Solution("files/ina2.csv", "files/inb.csv", "files/inc.csv", 1);
            s.multiply();
        }
        catch(Exception e){
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
