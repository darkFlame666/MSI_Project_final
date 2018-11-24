package com.company;

import java.util.stream.IntStream;

public class Data {
    static double TRAINING_DATA[][][];
    static double TESTING_DATA[][][];
    public Data(int NumberOfInputs) {
    if(NumberOfInputs == 3){
        this.TRAINING_DATA = new double [][][] {{{1,0.000,0.000,0.000},{0.000,0.000,0.000}},
                                                {{1, 0.111, 0.111, 0.111},{0.008,0.015,0.039}},
                                                {{1, 0.222, 0.222, 0.222},{0.026,0.045,0.096}},
                                                {{1, 0.333, 0.333, 0.333},{0.062,0.094,0.171}},
                                                {{1, 0.444, 0.444, 0.444},{0.121,0.165,0.264}},
                                                {{1, 0.556, 0.556, 0.556},{0.210,0.263,0.375}},
                                                {{1, 0.667, 0.667, 0.667},{0.337,0.391,0.505}},
                                                {{1, 0.778, 0.778, 0.778},{0.510,0.554,0.652}},
                                                {{1, 0.889, 0.889, 0.889},{0.725,0.756,0.817}},
                                                {{1,1.00,1.00,1.00},{1,1,1}}};

        this.TESTING_DATA = new double [][][] {{{1, 0.4,0.4,0.4}, {0,0,0}},
                                               {{1, 0.5, 0.5, 0.5}, {0,0,0}},
                                                {{1, 0.6, 0.6, 0.6}, {0,0,0}}};
    }
    if(NumberOfInputs == 5){
        this.TRAINING_DATA = new double [][][] {{{1,0.000, 0.000,0.000, 0, 0},{0.000,0.000,0.000}},
                        {{1, 0.111, 0.111, 0.111, 0.111, 0.111},{0.008,0.029,0.009}},
                        {{1, 0.222, 0.222, 0.222, 0.222, 0.222},{0.027,0.079,0.030}},
                        {{1, 0.333, 0.333, 0.333, 0.333, 0.333},{0.064,0.149,0.068}},
                        {{1, 0.444, 0.444, 0.444, 0.444, 0.444},{0.124,0.239,0.130}},
                        {{1, 0.556, 0.556, 0.556, 0.556, 0.556},{0.214,0.350,0.221}},
                        {{1, 0.667, 0.667, 0.667, 0.667, 0.667},{0.339,0.482,0.348}},
                        {{1, 0.778, 0.778, 0.778, 0.778, 0.778},{0.509,0.634,0.516}},
                        {{1, 0.889, 0.889, 0.889, 0.889, 0.889},{0.726,0.807,0.732}},
                        {{1,1.00,1.00,1.00, 1.00, 1.00},{1,1,1}}};

        this.TESTING_DATA = new double [][][] {{{1, 0.4,0.4,0.4, 0.4, 0.4}, {0,0,0}},
                {{1, 0.5, 0.5, 0.5, 0.5, 0.5}, {0,0,0}},
                {{1, 0.6, 0.6, 0.6, 0.6, 0.6}, {0,0,0}}};
    }

    }
    static void printResult(double[][] result){

        IntStream.range(0, TRAINING_DATA[0][0].length).forEach(x -> System.out.print("  Input "+x+"  |"));
        System.out.println("    Target Result     |       Result  ");
        IntStream.range(0, TRAINING_DATA[0][0].length).forEach(x -> System.out.print("--------------"));
        System.out.println("---------------------------------------------------");
        for(int i=0; i<TRAINING_DATA.length; i++){
            for(int j=0; j<TRAINING_DATA[0][0].length; j++){
                System.out.print("  " + String.format("%.2f", TRAINING_DATA[i][0][j]) + "    |");
            }
            System.out.print("  "+String.format("%.3f", TRAINING_DATA[i][1][0]) + "  "+String.format("%.3f", TRAINING_DATA[i][1][1]) +"  "+String.format("%.3f", TRAINING_DATA[i][1][2])+"    |   "+String.format("%.5f",result[i][0])+" "+String.format("%.5f",result[i][1])+" "+String.format("%.5f",result[i][2])+"   \n");
        }
    }
}
