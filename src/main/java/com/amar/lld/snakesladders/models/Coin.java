package com.amar.lld.snakesladders.models;

/**
 * Represents a coin on the board that gives bonus points when collected
 */
public record Coin(Integer position, Integer points) {
    public Coin {
        if (position == null || position < 0) {
            throw new IllegalArgumentException("Coin position must be non-negative");
        }
        if (points == null || points <= 0) {
            throw new IllegalArgumentException("Coin points must be positive");
        }
    }
}
