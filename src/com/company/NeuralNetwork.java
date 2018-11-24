package com.company;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class NeuralNetwork {
    static final double LEARNING_RATE = 0.8;
    final static int NUMB_OF_INPUT_NEURONS = Data.TRAINING_DATA[0][0].length;
    final static int NUMB_OF_OUTPUT_NEURONS = 3;
    private int numbOfHiddenNeurons;
    private Layer [] layers = new Layer[Layer.LayerType.values().length];
    private int counter = 0;

    public ArrayList<Double> getMSE_LIST() {
        return MSE_LIST;
    }

    private ArrayList<Double> MSE_LIST = new ArrayList<Double>();
    private ArrayList<Double>MSE_FUN1 = new ArrayList<Double>();
    private ArrayList<Double>MSE_FUN2 = new ArrayList<Double>();
    private ArrayList<Double>MSE_FUN3 = new ArrayList<Double>();

    public ArrayList<Double> getMSE_FUN1() {
        return MSE_FUN1;
    }

    public ArrayList<Double> getMSE_FUN2() {
        return MSE_FUN2;
    }

    public ArrayList<Double> getMSE_FUN3() {
        return MSE_FUN3;
    }

    public int getNumbOfHiddenNeurons() {
        return numbOfHiddenNeurons;
    }

    public NeuralNetwork(int numbOfHiddenNeurons) {
        this.numbOfHiddenNeurons = numbOfHiddenNeurons;
        layers[0] = new Layer(this, Layer.LayerType.I);
        layers[1] = new Layer(this, Layer.LayerType.H);
        layers[2] = new Layer(this, Layer.LayerType.O);
    }

    public Layer[] getLayers() {
        return layers;
    }

    public String toString(){
        StringBuffer returnValue = new StringBuffer();
        IntStream.range(0, layers.length).forEach(x->returnValue.append(layers[x] + " "));
        return returnValue.toString();
    }


    public NeuralNetwork forwardprop (double input[]){
        for (int i=0; i<layers.length; i++){
            switch (layers[i].getLayerType()) {
                case I:
                    for (int j = 0; j < layers[i].getNeurons().length; j++)
                    layers[i].getNeurons()[j].setOutput(input[j]);
                    break;
                case H:
                    for (int j=0; j<layers[i].getNeurons().length; j++){
                        double weightedSum =0;
                        for (int k=0; k<layers[i].getNeurons()[0].getWeights().length; k++)
                            weightedSum += layers[i].getNeurons()[j].getWeights()[k]*layers[i-1].getNeurons()[k].getOutput();
                        layers[i].getNeurons()[j].applyActivationFunction(weightedSum);
                    }
                    break;
                case O:
                    for(int j=0; j<layers[i].getNeurons().length; j++) {
                        double weightedSumm = 0;
                        for (int k = 0; k < layers[i].getNeurons()[0].getWeights().length; k++)
                            weightedSumm += layers[i].getNeurons()[j].getWeights()[k]*layers[i-1].getNeurons()[k].getOutput();
                        layers[i].getNeurons()[j].applyActivationFunction(weightedSumm);
                    }
                    break;
            }
        }
        return this;
    }

    public NeuralNetwork backpropError(double targetResult[]) {
        Neuron[] iNeuron = layers[0].getNeurons();
        Neuron[] hNeuron = layers[1].getNeurons();
        Neuron[] oNeurons = layers[layers.length - 1].getNeurons();
            for (int i=0; i<oNeurons.length; i++) {
                oNeurons[i].setError((targetResult[i] - oNeurons[i].getOutput()) * oNeurons[i].derivative());
            }
            for(int i=0; i<oNeurons.length; i++) {
                for (int j = 0; j < oNeurons[i].getWeights().length; j++)
                    oNeurons[i].getWeights()[j] = oNeurons[i].getWeights()[j] + LEARNING_RATE * oNeurons[i].getError() * hNeuron[j].getOutput();
            }
            for(int i=0; i<hNeuron.length; i++){
                hNeuron[i].setError((oNeurons[0].getWeights()[i]*oNeurons[0].getError()+oNeurons[1].getWeights()[i]*oNeurons[1].getError()+oNeurons[2].getWeights()[i]*oNeurons[2].getError())*hNeuron[i].derivative());
                    for (int j=0; j<hNeuron[0].getWeights().length; j++) {
                        hNeuron[i].getWeights()[j] = hNeuron[i].getWeights()[j] + LEARNING_RATE * hNeuron[i].getError() * iNeuron[j].getOutput();
                    }
            }
            MSE_FUN1.add(Math.pow(targetResult[0]-oNeurons[0].getOutput(),2));
            MSE_FUN2.add(Math.pow(targetResult[1]-oNeurons[1].getOutput(),2));
            MSE_FUN3.add(Math.pow(targetResult[2]-oNeurons[2].getOutput(),2));

            double MSE [] = new double[3];
            for(int i=0; i<MSE.length; i++){
                MSE[i] = Math.pow(targetResult[i]-oNeurons[i].getOutput(),2);
            }
            double MSE_sum = 0;
            for(int i=0; i<MSE.length; i++){
                MSE_sum += MSE[i];
            }
        ArrayList<Double> lista = new ArrayList<Double>();
            lista.add(MSE_sum);
        counter++;
            if(counter == Data.TRAINING_DATA.length){
                double suma = 0.0;
                for(Double d:lista){
                    suma+=d;
                }
                double elem = suma/Data.TRAINING_DATA.length;
                MSE_LIST.add(elem);
                lista.clear();
                counter=0;
            }



        return this;
    }
    public double MSE_Value (ArrayList<Double> list, int a){
        double sum_of_MSE = 0.0;
        for(Double d: list){
            sum_of_MSE += d;
        }
        double result = sum_of_MSE/a;
        return result;
    }
}
