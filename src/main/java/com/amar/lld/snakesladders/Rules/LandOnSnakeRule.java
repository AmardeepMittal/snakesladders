package com.amar.lld.snakesladders.Rules;

import java.util.Objects;

import com.amar.lld.snakesladders.models.GameState;
import com.amar.lld.snakesladders.models.RuleType;

public class LandOnSnakeRule implements IRule {

    @Override
    public boolean applyRule(GameState gameState) {
        if(gameState == null)
            throw new IllegalArgumentException();
        var currPlayer = gameState.getCurrentPlayer();
        var playerPositions = gameState.getPlayerPositions();
        var currCell = playerPositions.get(currPlayer.getId());
        var snake = gameState.getBoard()
            .snakes()
            .stream()
            .filter(l -> Objects.equals(l.start(), currCell))
            .findFirst();
        if(snake.isPresent()){
            playerPositions.put(currPlayer.getId(), snake.get().end());
            return true;
        }
        return false;
    }

    @Override
    public RuleType getRuleType() {
        return RuleType.LAND_ON_SNAKE;
    }
    
}
