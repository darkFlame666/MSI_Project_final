package com.company;

import java.io.*;
import java.util.Scanner;

public class Main {
    static int NUMB_OF_EPOCHS;

    public static void main(String[] args) throws NumberFormatException, IOException{

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("> Please enter number of inputs M of neural network 3/5/7");
        Data data = new Data(Integer.parseInt(bufferedReader.readLine()));
        System.out.println("> Please enter number of epochs I ");
        NUMB_OF_EPOCHS = Integer.parseInt(bufferedReader.readLine());
        System.out.println("> Please enter number of neurons N in the hidden layer ");
        NeuralNetwork neuralNetwork = new NeuralNetwork(Integer.parseInt(bufferedReader.readLine()));
        boolean flag = true;
        while(flag) {
            System.out.println("> What do you want to do (run, train, test, save, exit) ?");
            String command = bufferedReader.readLine();
            switch (command){
                case "run":
                    double[][] result = new double [data.TRAINING_DATA.length][data.TRAINING_DATA[0][1].length];
                    for(int i=0; i<data.TRAINING_DATA.length; i++){
                        for(int j=0; j<data.TRAINING_DATA[0][1].length; j++){
                            result[i][j] = neuralNetwork.forwardprop(data.TRAINING_DATA[i][0]).getLayers()[2].getNeurons()[j].getOutput();
                        }
                    }
                    data.printResult(result);
                    break;
                case "train":
                    neuralNetwork.getMSE_LIST().clear();
                    neuralNetwork.getMSE_FUN1().clear();
                    neuralNetwork.getMSE_FUN2().clear();
                    neuralNetwork.getMSE_FUN3().clear();
                    for(int i=0; i<NUMB_OF_EPOCHS; i++){
                        System.out.println("[epoch "+i+"]");
                        for(int j=0; j<data.TRAINING_DATA.length; j++){
                            System.out.println(neuralNetwork.forwardprop(data.TRAINING_DATA[j][0]).backpropError(data.TRAINING_DATA[j][1]));
                        }

                    }
                    System.out.println("[done training]");
                    System.out.println();
                    //System.out.println("MSE OF ALL FUNCTIONS  =  "+neuralNetwork.MSE_Value(neuralNetwork.getMSE_LIST(), NUMB_OF_EPOCHS));
                    System.out.println("MSE OF FIRST FUNCTIONS =   "+neuralNetwork.MSE_Value(neuralNetwork.getMSE_FUN1(), NUMB_OF_EPOCHS));
                    System.out.println("MSE OF SECOND FUNCTIONS =   "+neuralNetwork.MSE_Value(neuralNetwork.getMSE_FUN2(), NUMB_OF_EPOCHS));
                    System.out.println("MSE OF THIRD FUNCTIONS =   "+neuralNetwork.MSE_Value(neuralNetwork.getMSE_FUN3(), NUMB_OF_EPOCHS));
                    System.out.println("NUMBER OF EPOCHS I = " + NUMB_OF_EPOCHS);
                    System.out.println("NUMBER OF NEURONS IN HIDDEN LAYER N = "+neuralNetwork.getNumbOfHiddenNeurons());
                    System.out.println("NUMBER OF INPUTS M = "+(data.TRAINING_DATA[0][0].length-1));
                    break;
                case "test":
                    double tab [][] = new double [data.TESTING_DATA.length][data.TESTING_DATA[0][1].length];
                    for(int i=0; i<data.TESTING_DATA.length; i++){
                        for(int j=0; j<data.TESTING_DATA[0][1].length; j++){
                            tab[i][j] = neuralNetwork.forwardprop(data.TESTING_DATA[i][0]).getLayers()[2].getNeurons()[j].getOutput();
                        }
                    }
                    for(int i=0; i<data.TESTING_DATA.length; i++){
                        System.out.println("Sample "+i);
                        for(int j=0; j<data.TESTING_DATA[0][1].length; j++){
                            System.out.println("Out "+j+"=\t"+tab[i][j]);
                        }
                    }
                    break;
                case "save":
                    saveData(neuralNetwork.MSE_Value(neuralNetwork.getMSE_FUN1(), NUMB_OF_EPOCHS), neuralNetwork.MSE_Value(neuralNetwork.getMSE_FUN2(), NUMB_OF_EPOCHS), neuralNetwork.MSE_Value(neuralNetwork.getMSE_FUN3(), NUMB_OF_EPOCHS), NUMB_OF_EPOCHS, neuralNetwork.getNumbOfHiddenNeurons(), data.TRAINING_DATA[0][0].length-1);
                    System.out.println("Data successfully saved!");
                    break;

                case "exit":
                    flag = false;
                    break;
            }
        }
        System.exit(0);
    }

    public static void saveData (double MSEval1, double MSEval2, double MSEval3, int epochs, int hiddenNeurons, int inputs){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("test.txt", true));
            writer.append(MSEval1+"\t"+MSEval2+"\t"+MSEval3+"\t"+epochs+"\t"+hiddenNeurons+"\t"+inputs+"\n");
            writer.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
