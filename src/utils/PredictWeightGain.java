package utils;

public class PredictWeightGain {
    public static double predict(double feedConsumption, double waterConsumption, double age){
        return 153.850 + (-0.4843 * feedConsumption) +(605.0373*waterConsumption) +(-76.8358*age);
    }
}
