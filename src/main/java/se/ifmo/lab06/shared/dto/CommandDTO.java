package se.ifmo.lab06.shared.dto;

import java.io.Serializable;

public record CommandDTO(
        String name,
        String description,
        Class<?>[] args,
        boolean modelRequired
) implements Serializable {
}
