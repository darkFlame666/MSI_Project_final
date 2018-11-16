package com.company;

import java.util.stream.IntStream;

public class NeuralNetwork {
    static final double LEARNING_RATE = 0.8;
    final static int NUMB_OF_INPUT_NEURONS = Main.TRAINING_DATA[0][0].length;
    final static int NUMB_OF_OUTPUT_NEURONS = 1;
    private int numbOfHiddenNeurons;
    private Layer [] layers = new Layer[Layer.LayerType.values().length];

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
            switch (layers[i].getLayerType()){
                case I:
                    for (int j=0; j<layers[i].getNeurons().length; j++)
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
                        double weightedSum = 0;
                        for (int k = 0; k < layers[i].getNeurons()[0].getWeights().length; k++)
                            weightedSum += layers[i].getNeurons()[j].getWeights()[k]*layers[i-1].getNeurons()[k].getOutput();
                        layers[i].getNeurons()[j].applyActivationFunction(weightedSum);
                    }
                    break;
            }
        }
        return this;
    }

    public NeuralNetwork backpropError(double targetResult){
        Neuron[] iNeuron = layers[0].getNeurons();
        Neuron[] hNeuron = layers[1].getNeurons();
        Neuron oNeuron = layers[layers.length-1].getNeurons()[0];
        oNeuron.setError((targetResult - oNeuron.getOutput())*oNeuron.derivative());
        for (int j=0; j<oNeuron.getWeights().length; j++)
            oNeuron.getWeights()[j] = oNeuron.getWeights()[j] + LEARNING_RATE * oNeuron.getError()*hNeuron[j].getOutput();
        for (int i = 0; i<hNeuron.length; i++){
            hNeuron[i].setError((oNeuron.getWeights()[i]*oNeuron.getError())*hNeuron[i].derivative());
            for (int j=0; j<hNeuron[0].getWeights().length; j++)
                hNeuron[i].getWeights()[j] = hNeuron[i].getWeights()[j] + LEARNING_RATE * hNeuron[i].getError()*iNeuron[j].getOutput();
        }
        return this;
    }
}
