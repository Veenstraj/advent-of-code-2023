package org.example;

import java.util.*;

/**
 * Modules communicate using pulses. Each pulse is either a high pulse or a low pulse.
 * When a module sends a pulse, it sends that type of pulse to each module in its list of destination modules.
 */
public class Module {
    public static Map<String, Module> modules = new HashMap<>();
    private static LinkedList<Module> sendQ = new LinkedList<>();
    private final String name;
    protected static long nrOfLowPulses = 0;
    protected static long nrOfHighPulses = 0;
    protected List<String> destinations = new ArrayList<>();
    private PulseType pulseTypeToSend = PulseType.LOW;

    public Module(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addDestination(String moduleName) {
        destinations.add(moduleName);
    }

    /**
     * @param module The sending module
     * @param pulse  pulse type
     */
    public void receive(Module module, org.example.PulseType pulse) {
    }

    @Override
    public String toString() {
        return "Module{" +
                "type=" + this.getClass().getName() +
                ", destinations=" + destinations +
                ", name='" + name + '\'' +
                '}';
    }

    /**
     * @param pulse When a module sends a pulse, it sends that type of pulse to each module in its list of destination modules.
     */
    protected void sendPulse(PulseType pulse) {
        this.pulseTypeToSend = pulse;
        addToQueue(this);
    }

    protected void doSendPulse() {
        // first send the pulses
        destinations.stream()
                .map(d -> Module.modules.get(d))
                .filter(Objects::nonNull)
                .forEach(d -> {
                            //System.out.printf("%s %s %s%n", this.getName(), (pulseTypeToSend == PulseType.HIGH ? "-high->" : "-low->"), d.getName());
                            d.receive(this, pulseTypeToSend);
                            if (pulseTypeToSend == PulseType.LOW) nrOfLowPulses++;
                            if (pulseTypeToSend == PulseType.HIGH) nrOfHighPulses++;
                        }
                );
        Module.nextToSend();
    }

    private static void addToQueue(Module module) {
//        System.out.printf("  %s add to queue%n", module.getName());
        sendQ.add(module);
    }

    protected static void nextToSend() {
        while (sendQ.size() > 0) {
//            System.out.printf("  %s gaat zenden: ", sendQ.peek().getName());
            sendQ.remove().doSendPulse();
        }
    }

}
