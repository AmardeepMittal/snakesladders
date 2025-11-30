package com.amar.lld.snakesladders.models;

public record Ladder (Integer start, Integer end) implements Box {
    @Override
    public int getPosition() {
        return start;
    }

    @Override
    public void applyBoxBehavior(Player player) {
        player.setPosition(end);
        System.out.println("Yay! Player " + player.getName() + " climbed a ladder from " + start + " to " + end);
    }
} 
