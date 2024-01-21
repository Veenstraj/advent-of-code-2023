package org.example;

public class Broadcaster extends Module {
    public Broadcaster(String name) {
        super(name);
    }

    @Override
    public void receive(Module module, PulseType pulse) {
        this.sendPulse(pulse);
        Module.nextToSend();
    }
}
