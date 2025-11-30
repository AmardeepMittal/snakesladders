package com.amar.lld.snakesladders.models;

public record DefaultBox(Integer position) implements Box {

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void applyBoxBehavior(Player player) {
        player.setPosition(position);
        // No behavior for default box
    }
}
