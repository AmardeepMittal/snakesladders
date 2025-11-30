package com.amar.lld.snakesladders.models;

public record Snake (Integer start, Integer end) implements Box {
    @Override
    public int getPosition() {
        return start;
    }

    @Override
    public void applyBoxBehavior(Player player) {
        player.setPosition(end);
        System.out.println("Oops! Player " + player.getName() + " got bitten by a snake from " + start + " to " + end);
    }
} 