# Multi-threaded Matrix Multiplication

This program implements a multi-threaded matrix multiplication. (https://en.wikipedia.org/wiki/Matrix_multiplication).
It computes C = A * B where A, B are the input matrices and C is the result output matrix. It is assumed that A,B and C fit into main memory.
* [x] To run:

java -jar multiply.jar inputAFileName inputBFileName outputFileNme nrThreads, or

java Test inputAFileName inputBFileName outputFileName nrThreads

{to generate jar: jar cvfm multiply.jar manifest.txt *.class}

# Guidelines
* [x] The application accepts inputs in the form of files. Contents of each file is a space-delimited matrix. The first line contains the dimensions of the matrix. Consider this 5x5 matrix file as an example. 

$ cat matA.csv

5 5              <======== The first line contains matrix dimensions

4 5 6 7 8

1 2 3 4 5

9 8 7 6 5

4 3 3 4 3

1 1 1 1 1

* [x] The output is stored in a file in the same format
* [x] The program prints the time it took to run the multiplication (without the time it took to load/save files).

# Testing & Performance Study

* [x] The output for several different matrix sizes and various number of threads can be found under files/output*.txt.

* [x] During the performance testing, I used 2 matrices, that are filled with random numbers. First matrix is of size x by 200, where second matrix is of size 200 by x+1 where x ranges from 2 to 500. I tested for number of threads n varying between 1 to 25. I treated each cell of the output matrix(x by x+1) as a task and assigned it to the next available thread. So there would be ~x^2 tasks and n threads. This means while n<(x^2), we can improve the performance significantly by just increasing the thread size.

* [x] The attached graph(graph 1) shows the performance when the thread size is increased. Ideally this should show the performance is increased(decrease in running time), but the result of my testing does not reflect that. This might be due to the environment or because of the overhead of assigning task to threads compared to the time spent on a task. 

* [x] The second attached graph(graph 2) clearly shows for the same number of threads, if we increase the matrix size then performance decreases(increase in running time).

# Edge cases

* [x] Cases where input file does not have the correct format(i.e. matrix dimension at first line not matching number of cols or rows) Handled.
* [x] Cases where input matrixA and matrixB cannot be multiplied because matrixA.col != matrixB.row: Handled via isValid() method
* [x] Integer overflow, for this case, we can simply change the code to use long[][] instead of int[][]

![alt tag](https://github.com/esrako/Matrix-Multiplication/blob/master/files/graph1.png)

![alt tag](https://github.com/esrako/Matrix-Multiplication/blob/master/files/graph2.png)

## License

    Copyright [2015] [Esra Kucukoguz]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
