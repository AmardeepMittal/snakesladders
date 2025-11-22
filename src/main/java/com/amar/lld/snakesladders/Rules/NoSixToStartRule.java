package com.amar.lld.snakesladders.Rules;


import com.amar.lld.snakesladders.models.GameState;
import com.amar.lld.snakesladders.models.RuleType;

public class NoSixToStartRule implements IRule{

    @Override
    public boolean applyRule(GameState gameState) {
        if(gameState == null)
            throw new IllegalArgumentException();
        var currentPlayer = gameState.getCurrentPlayer();
        var postions = gameState.getPlayerPositions();
       
        if(postions.containsKey(currentPlayer.getId())) return true;

        int diceSide = gameState.getDiceSide();
        if(diceSide == 6){
            return true;
        }
        return false;
    }

    @Override
    public RuleType getRuleType() {
        return RuleType.NO_SIX_TO_START;
    }
    
}
