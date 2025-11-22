package com.amar.lld.snakesladders.models;

import java.util.List;

public record Board(int[] cells, List<Snake> snakes, List<Ladder> ladders) {
} 
