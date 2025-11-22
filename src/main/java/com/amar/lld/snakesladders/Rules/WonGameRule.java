package com.amar.lld.snakesladders.Rules;

import com.amar.lld.snakesladders.models.GameState;
import com.amar.lld.snakesladders.models.RuleType;

public class WonGameRule implements IRule {
 
    @Override
    public boolean applyRule(GameState gameState) {
        if(gameState == null)
            throw new IllegalArgumentException();
        var currentPlayer = gameState.getCurrentPlayer();
        var postions = gameState.getPlayerPositions();

        int currPos = postions.get(currentPlayer.getId());
        if(currPos >= gameState.getBoard().cells().length) return true;

        return false;
    }

    @Override
    public RuleType getRuleType() {
        return RuleType.WON_GAME;
    }
}