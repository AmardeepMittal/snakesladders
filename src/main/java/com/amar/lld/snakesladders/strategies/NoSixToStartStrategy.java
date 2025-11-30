package com.amar.lld.snakesladders.strategies;

import com.amar.lld.snakesladders.models.Player;


public class NoSixToStartStrategy implements IGameStartStrategy{

    @Override
    public boolean CanStartGame(int diceSide, Player player) {
        if(player == null)
            throw new IllegalArgumentException();
       
        if(player.getPosition() != 0) return true;

        if(diceSide == 6){
            return true;
        }
        return false;
    }
    
}
