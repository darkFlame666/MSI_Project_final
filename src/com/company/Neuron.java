package com.company;

import java.util.stream.IntStream;

public class Neuron {
    private double[] weights = null;
    private double output = 0;
    private double error = 0;
    private Layer.LayerType layerType = null;

    public double getOutput() {
        return output;
    }

    public void setOutput(double output) {
        this.output = output;
    }

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }

    public double[] getWeights() {
        return weights;
    }

    public String toString() {
        StringBuffer returnValue = new StringBuffer("("+layerType+", ");
        if (layerType == Layer.LayerType.I)
            returnValue.append(String.format("%.2f", output)+")");
        else{
            IntStream.range(0, weights.length).forEach(x->
                returnValue.append(String.format("%.2f", weights[x])+", "));
            if (layerType == Layer.LayerType.H)
                returnValue.append(String.format("%.2f", output)+")");
            else
                returnValue.append(String.format("%.5f", output)+")");
        }
        return returnValue.toString();
    }

    public Neuron(Layer.LayerType layerType, int numbOfWeights) {
        this.layerType = layerType;
        if (layerType != Layer.LayerType.I){
            weights =new double [numbOfWeights];
            IntStream.range(0, numbOfWeights).forEach(x -> weights[x] = 0.5 - Math.random());
        }
    }

    public  void applyActivationFunction(double weightedSum){
        output = 1.0/(1+Math.exp(-1.0*weightedSum));
        //output = 0.02*weightedSum;
    }
    public double derivative(){
        return output*(1.0-output);
    }
}
