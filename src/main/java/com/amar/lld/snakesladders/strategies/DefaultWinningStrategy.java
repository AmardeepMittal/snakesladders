package com.amar.lld.snakesladders.strategies;


public class DefaultWinningStrategy implements IWinningStrategy {
 
    @Override
    public boolean hasPlayerWon(int playerPosition, int winningPosition) {
        return playerPosition >= winningPosition;
    }
}