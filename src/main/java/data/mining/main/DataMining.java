package data.mining.main;

import data.mining.processor.FootballProcessor;
import data.mining.processor.WeatherProcessor;

public class DataMining {

    public static void main(String[] args) {
        WeatherProcessor weatherProcessor = new WeatherProcessor();
        System.out.println(weatherProcessor.findSmallestTemperatureSpread());

        FootballProcessor footballProcessor = new FootballProcessor();
        System.out.println(footballProcessor.findSmallestGoalsDifference());
    }

}
