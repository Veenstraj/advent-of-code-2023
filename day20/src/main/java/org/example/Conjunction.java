package org.example;

import java.util.HashMap;
import java.util.Map;

/**
 * Conjunction modules (prefix &) remember the type of the most recent pulse received from each of their
 * connected input modules; they initially default to remembering a low pulse for each input.
 * When a pulse is received, the conjunction module first updates its memory for that input.
 * Then, if it remembers high pulses for all inputs, it sends a low pulse; otherwise, it sends a high pulse.
 */
public class Conjunction extends Module {

    Map<Module, PulseType> inputModules = new HashMap<>();

    public Conjunction(String name) {
        super(name);

    }

    @Override
    public void receive(Module module, PulseType pulse) {
//        System.out.printf("  %s: receive from %s%n", getName(), module.getName());
        if (inputModules.size() == 0) {
            // they initially default to remembering a low pulse for each input.
            Module.modules.values().stream()
                    .filter(m -> m.destinations.contains(this.getName()))
                    .forEach(m -> inputModules.put(m, PulseType.LOW));
        }
        inputModules.put(module, pulse); // When a pulse is received, the conjunction module first updates its memory for that input.

        // Then, if it remembers high pulses for all inputs, it sends a low pulse; otherwise, it sends a high pulse.
        if (inputModules.keySet().stream().anyMatch(m -> inputModules.get(m) == PulseType.LOW)) {
            this.sendPulse(PulseType.HIGH);
        } else {
            this.sendPulse(PulseType.LOW);
        }
    }
}
