package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static java.lang.Long.MAX_VALUE;
import static java.lang.Long.parseLong;

public class Day5 {
    private static List<Long> _seeds = new ArrayList<>();
    private static final HashMap<Source, Long> _seedToSoil = new HashMap<>();
    private static final HashMap<Source, Long> _soilToFertilizer = new HashMap<>();
    private static final HashMap<Source, Long> _fertilizerToWater = new HashMap<>();
    private static final HashMap<Source, Long> _waterToLight = new HashMap<>();
    private static final HashMap<Source, Long> _lightToTemperature = new HashMap<>();
    private static final HashMap<Source, Long> _temperatureToHumidity = new HashMap<>();
    private static final HashMap<Source, Long> _humidityToLocation = new HashMap<>();

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
                    long destination = parseLong(parts[0]);
                    Source source = new Source(Long.parseLong(parts[1]), Long.parseLong(parts[2]));
                    if (seedToSoil) _seedToSoil.put(source, destination);
                    if (soilToFertilizer) _soilToFertilizer.put(source, destination);
                    if (fertilizerToWater) _fertilizerToWater.put(source, destination);
                    if (waterToLight) _waterToLight.put(source, destination);
                    if (lightToTemperature) _lightToTemperature.put(source, destination);
                    if (temperatureToHumidity) _temperatureToHumidity.put(source, destination);
                    if (humidityToLocation) _humidityToLocation.put(source, destination);
                }

                // read next line
                line = reader.readLine();
            }
            long kleinste = MAX_VALUE;
            boolean lengte = false;
            long start = 0;
            long length = 0;
            for (Long seed : _seeds) {
                if (!lengte) {
                    start = seed;
                    lengte = true;
                } else {
                    length = seed;
                    System.out.println("start=" + start + ", length=" + length);
                    for (long zaadje = start; zaadje < start + length; zaadje++) {

                        long soil = readMap(zaadje, _seedToSoil);
                        long fertilizer = readMap(soil, _soilToFertilizer);
                        long water = readMap(fertilizer, _fertilizerToWater);
                        long light = readMap(water, _waterToLight);
                        long temperature = readMap(light, _lightToTemperature);
                        long humidity = readMap(temperature, _temperatureToHumidity);
                        long location = readMap(humidity, _humidityToLocation);
                        if (kleinste > location) kleinste = location;
                        //if (location % 10000 == 0) System.out.printf("\r" + zaadje);
                        //System.out.println("Seed=" + seed + ", location=" + location);
                    }
                    lengte = false;
                }
            }
            System.out.println("kleinste=" + kleinste);

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private static long readMap(long input, Map<Source, Long> map) {
        for (Source source : map.keySet()) {
            if (input >= source.start && input < (source.start + source.length)) {
                return map.get(source) + input - source.start;

            }
        }
        return input;
    }

    static class Source {
        public long start;
        public long length;

        public Source(long start, long length) {
            this.start = start;
            this.length = length;
        }
    }
}
