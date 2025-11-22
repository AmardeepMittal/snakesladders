package com.amar.lld.snakesladders.models;


public record Dice(int sides) {
    public int roll(){
        return (int) (Math.random() * sides) + 1;
    }
}
