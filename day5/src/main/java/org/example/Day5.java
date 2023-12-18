package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Day5 {
    private static List<Long> _seeds = new ArrayList<>();
    private static HashMap<Long, Long> _seedToSoil = new HashMap<>();
    private static HashMap<Long, Long> _soilToFertilizer = new HashMap<>();
    private static HashMap<Long, Long> _fertilizerToWater = new HashMap<>();
    private static HashMap<Long, Long> _waterToLight = new HashMap<>();
    private static HashMap<Long, Long> _lightToTemperature = new HashMap<>();
    private static HashMap<Long, Long> _temperatureToHumidity = new HashMap<>();
    private static HashMap<Long, Long> _humidityToLocation = new HashMap<>();

    public static void main(String[] args) {

        opgave1();
    }

    public static void opgave1() {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("./day5/target/classes/input.txt"));
            String line = reader.readLine();

            long sum = 0;
            boolean seedToSoil = false;
            boolean soilToFertilizer = false;
            boolean fertilizerToWater = false;
            boolean waterToLight = false;
            boolean lightToTemperature = false;
            boolean temperatureToHumidity = false;
            boolean humidityToLocation = false;
            while (line != null) {
                boolean values = true;
                System.out.println();
                if (line.startsWith("seeds:")) {
                    String[] seedsArray = line.substring(6).trim().split(" ");
                    _seeds = Arrays.stream(seedsArray)
                            .filter(s -> !s.trim().isEmpty())
                            .map(Long::parseLong)
                            .toList();
                    values = false;
                }
                if (line.startsWith("seed-to-soil map:")) {
                    seedToSoil = true;
                    values = false;
                }
                if (line.startsWith("soil-to-fertilizer map:")) {
                    seedToSoil = false;
                    soilToFertilizer = true;
                    values = false;
                }
                if (line.startsWith("fertilizer-to-water map:")) {
                    soilToFertilizer = false;
                    fertilizerToWater = true;
                    values = false;
                }
                if (line.startsWith("water-to-light map:")) {
                    fertilizerToWater = false;
                    waterToLight = true;
                    values = false;
                }
                if (line.startsWith("light-to-temperature map:")) {
                    waterToLight = false;
                    values = false;
                    lightToTemperature = true;
                }
                if (line.startsWith("temperature-to-humidity map:")) {
                    lightToTemperature = false;
                    temperatureToHumidity = true;
                    values = false;
                }
                if (line.startsWith("humidity-to-location map:")) {
                    temperatureToHumidity = false;
                    humidityToLocation = true;
                    values = false;
                }
                if (values && line.trim().length() > 0) {
                    String[] parts = line.split(" ");
                    long destination = Long.parseLong(parts[0]);
                    long source = Long.parseLong(parts[1]);
                    long length = Long.parseLong(parts[2]);
                    for (long i = 0; i < length; i++) {
                        if (seedToSoil) _seedToSoil.put(source + i, destination + i);
                        if (soilToFertilizer) _soilToFertilizer.put(source + i, destination + i);
                        if (fertilizerToWater) _fertilizerToWater.put(source + i, destination + i);
                        if (waterToLight) _waterToLight.put(source + i, destination + i);
                        if (lightToTemperature) _lightToTemperature.put(source + i, destination + i);
                        if (temperatureToHumidity) _temperatureToHumidity.put(source + i, destination + i);
                        if (humidityToLocation) _humidityToLocation.put(source + i, destination + i);
                    }
                }

                // read next line
                line = reader.readLine();
            }
            for (Long seed : _seeds) {
                long soil = seed;
                if (_seedToSoil.containsKey(seed)) soil = _seedToSoil.get(seed);
                long fertilizer = soil;
                if (_soilToFertilizer.containsKey(soil)) fertilizer = _soilToFertilizer.get(soil);
                long water = fertilizer;
                if (_fertilizerToWater.containsKey(fertilizer)) water = _fertilizerToWater.get(fertilizer);
                long light = water;
                if (_waterToLight.containsKey(water)) light = _waterToLight.get(water);
                long temperature = light;
                if (_lightToTemperature.containsKey(light)) temperature = _lightToTemperature.get(light);
                long humidity = temperature;
                if (_temperatureToHumidity.containsKey(temperature)) humidity = _temperatureToHumidity.get(temperature);
                long location = humidity;
                if (_humidityToLocation.containsKey(humidity)) location = _humidityToLocation.get(humidity);
                System.out.println("Seed=" + seed + ", location=" + location);
            }
            System.out.println("sum=" + sum);

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}