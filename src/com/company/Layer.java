package com.company;

import java.util.stream.IntStream;

public class Layer {
    static enum LayerType {I, H, O};
    private Neuron[] neurons = null;
    private LayerType layerType;

    public Neuron[] getNeurons()
    {
        return neurons;
    }

    public LayerType getLayerType() {
        return layerType;
    }

    public Layer(NeuralNetwork neuralNetwork, LayerType layerType) {
        this.layerType = layerType;
        switch (layerType){
            case I:
                neurons = new Neuron[NeuralNetwork.NUMB_OF_INPUT_NEURONS];
                for(int i= 0; i<NeuralNetwork.NUMB_OF_INPUT_NEURONS; i++){
                    neurons[i] = new Neuron(layerType, 0);
                }
                break;
            case H:
                neurons = new Neuron[neuralNetwork.getNumbOfHiddenNeurons()];
                for(int i=0; i<neuralNetwork.getNumbOfHiddenNeurons(); i++){
                    neurons[i] = new Neuron(layerType, NeuralNetwork.NUMB_OF_INPUT_NEURONS);
            }
                break;
            case O:
                neurons = new Neuron[NeuralNetwork.NUMB_OF_OUTPUT_NEURONS];
                for(int i=0; i<NeuralNetwork.NUMB_OF_OUTPUT_NEURONS; i++){
                    neurons[i] = new Neuron(layerType, neuralNetwork.getNumbOfHiddenNeurons());
                }
                break;
        }
    }

    public String toString(){
        StringBuffer returnValue = new StringBuffer();
        IntStream.range(0, neurons.length).forEach(x -> returnValue.append(neurons[x] + " "));
        return returnValue.toString();
    }
}
