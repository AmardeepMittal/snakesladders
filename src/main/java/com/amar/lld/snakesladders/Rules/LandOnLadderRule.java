package com.amar.lld.snakesladders.Rules;

import java.util.Objects;

import com.amar.lld.snakesladders.models.GameState;
import com.amar.lld.snakesladders.models.RuleType;

public class LandOnLadderRule implements IRule {

    @Override
    public boolean applyRule(GameState gameState) {
        if(gameState == null)
            throw new IllegalArgumentException();
        var currPlayer = gameState.getCurrentPlayer();
        var playerPositions = gameState.getPlayerPositions();
        var currCell = playerPositions.get(currPlayer.getId());
        var ladder = gameState.getBoard()
            .ladders()
            .stream()
            .filter(l -> Objects.equals(l.start(), currCell))
            .findFirst();
        if(ladder.isPresent()){
            playerPositions.put(currPlayer.getId(), ladder.get().end());
            return true;
        }
        return false;
    }

    @Override
    public RuleType getRuleType() {
        return RuleType.LAND_ON_LADDER;
    }
    
}
