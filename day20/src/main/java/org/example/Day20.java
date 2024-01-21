package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Day20 {
    private static final String inputfile = "./day20/src/main/resources/input.txt";

    public static void main(String[] args) {

        opgave1();
    }

    public static void opgave1() {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(inputfile));
            String line = reader.readLine();

            int sum = 0;
            while (line != null) {
                if (line.trim().length() > 0) {
                    createObjects(line);
                }

                // read next line
                line = reader.readLine();
            }
            reader.close();

            System.out.println("Number of modules: " + Module.modules.size());
            for (Module module : Module.modules.values()) {
                System.out.println(module);
            }
            long nrOfLowPulses = 0;
            long nrOfHighPulses = 0;
            for (int i = 0; i < 1000; i++) {
                // druk op de knop
                Module.modules.get("button").doSendPulse();
                nrOfHighPulses += Module.nrOfHighPulses;
                nrOfLowPulses += Module.nrOfLowPulses;
                //System.out.printf("nrHigh=%d, nrLow=%d%n%n", Module.nrOfHighPulses, Module.nrOfLowPulses);
                Module.nrOfHighPulses = 0;
                Module.nrOfLowPulses = 0;
            }
            //812721756

            System.out.printf("nrOfHighPulses=%d, nrOfLowPulses=%d, total=%d%n", nrOfHighPulses, nrOfLowPulses, nrOfHighPulses * nrOfLowPulses);

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private static void createObjects(String line) {
        final String BROAD_CASTER = "broadcaster";

        String objectName = line.substring(0, line.indexOf("->")).trim();
        String[] destinationModules = line.substring(line.indexOf("->") + 2).trim().split(",");
        if (objectName.equals(BROAD_CASTER)) {
            Broadcaster broadcaster = new Broadcaster(BROAD_CASTER);
            Arrays.stream(destinationModules).forEach(s -> broadcaster.addDestination(s.trim()));
            Module.modules.put(BROAD_CASTER, broadcaster);

            Module output = new Module("output");
            Module.modules.put("output", output);

            Module button = new Module("button");
            button.addDestination(BROAD_CASTER);
            Module.modules.put("button", button);

            Module rx = new Module("rx");
            Module.modules.put("rx", button);
        }
        if (objectName.startsWith("%")) {
            String name = objectName.substring(1);
            FlipFlop flipFlop = new FlipFlop(name);
            Arrays.stream(destinationModules).forEach(s -> flipFlop.addDestination(s.trim()));
            Module.modules.put(name, flipFlop);
        }
        if (objectName.startsWith("&")) {
            String name = objectName.substring(1);
            Conjunction conjunction = new Conjunction(name);
            Arrays.stream(destinationModules).forEach(s -> conjunction.addDestination(s.trim()));
            Module.modules.put(name, conjunction);
        }

    }
}
