package com.amar.lld.snakesladders.models;

/**
 * Represents a coin on the board that gives bonus points when collected
 */
// public record Coin(Integer position, Integer points) {
//     public Coin {
//         if (position == null || position < 0) {
//             throw new IllegalArgumentException("Coin position must be non-negative");
//         }
//         if (points == null || points <= 0) {
//             throw new IllegalArgumentException("Coin points must be positive");
//         }
//     }
// }

public record Coin (Integer position, Integer points) implements Box {
    
    public int getRewardPoints() {
        return points;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void applyBoxBehavior(Player player) {
        player.setPosition(position);
        // Logic to add points to the player can be implemented here
        // For now, we will just print a message
        System.out.println("Player " + player.getName() + " collected a coin worth " + points + " points!");
    }
}
