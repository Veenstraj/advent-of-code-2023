package org.example;

/**
 * Flip-flop modules (prefix %) are either on or off; they are initially off.
 * If a flip-flop module receives a high pulse, it is ignored and nothing happens.
 * However, if a flip-flop module receives a low pulse, it flips between on and off.
 * If it was off, it turns on and sends a high pulse. If it was on, it turns off and sends a low pulse.
 */
public class FlipFlop extends Module {
    private boolean state = false; //true=on, false=off

    public FlipFlop(String name) {
        super(name);
    }

    @Override
    public void receive(Module module, PulseType pulse) {
//        System.out.printf("  %s: receive from %s%n", getName(), module.getName());

        if (pulse == PulseType.LOW) { // flips when low pulse
            this.state = !this.state;
            this.sendPulse(this.state ? PulseType.HIGH : PulseType.LOW); // send high pulse when on, low pulse when off
        }
    }
}
