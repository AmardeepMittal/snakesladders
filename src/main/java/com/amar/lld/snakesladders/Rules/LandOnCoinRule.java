package com.amar.lld.snakesladders.Rules;

import java.util.Objects;

import com.amar.lld.snakesladders.models.GameState;
import com.amar.lld.snakesladders.models.RuleType;

/**
 * Rule to handle coin collection when a player lands on a coin
 * Demonstrates Open/Closed Principle - new functionality added without modifying existing code
 */
public class LandOnCoinRule implements IRule {

    @Override
    public boolean applyRule(GameState gameState) {
        if(gameState == null) {
            throw new IllegalArgumentException("GameState cannot be null");
        }
        
        var currPlayer = gameState.getCurrentPlayer();
        var playerPositions = gameState.getPlayerPositions();
        var currCell = playerPositions.get(currPlayer.getId());
        
        // Check if there's a coin at current position
        var coin = gameState.getBoard()
            .coins()
            .stream()
            .filter(c -> Objects.equals(c.position(), currCell))
            .findFirst();
        
        if(coin.isPresent()) {
            // Coin collected!
            System.out.println(currPlayer.getName() + " collected a coin worth " + 
                             coin.get().points() + " points at position " + currCell);
            
            // In a real implementation, you would:
            // 1. Add points to player score
            // 2. Remove coin from board or mark as collected
            
            return true;
        }
        
        return false;
    }

    @Override
    public RuleType getRuleType() {
        return RuleType.LAND_ON_COIN;
    }
}
