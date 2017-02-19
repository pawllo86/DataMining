package data.mining.main;

import data.mining.processor.FootballProcessor;
import data.mining.processor.WeatherProcessor;

public class DataMining {

    public static void main(String[] args) {
        WeatherProcessor processor = new WeatherProcessor();
        processor.showSmallestTemperatureSpread();

        FootballProcessor processor2 = new FootballProcessor();
        processor2.showSmallestGoalsDifference();
    }

}
