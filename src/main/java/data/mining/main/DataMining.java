package data.mining.main;

import data.mining.processor.WeatherProcessor;

public class DataMining {

    public static void main(String[] args) {
        WeatherProcessor processor = new WeatherProcessor();
        processor.showSmallestTemperatureSpread();
    }

}
