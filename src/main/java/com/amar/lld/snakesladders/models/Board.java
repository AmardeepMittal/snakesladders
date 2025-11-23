package com.amar.lld.snakesladders.models;

import java.util.List;

public record Board(int[] cells, List<Snake> snakes, List<Ladder> ladders, List<Coin> coins) {
    
    // Backward compatible constructor - existing code doesn't break
    public Board(int[] cells, List<Snake> snakes, List<Ladder> ladders) {
        this(cells, snakes, ladders, List.of());
    }
} 
