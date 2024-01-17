package org.example;

import java.util.List;

public record Workflow(String name, List<Rule> rules) {
}
