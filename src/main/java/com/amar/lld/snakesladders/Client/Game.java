package com.amar.lld.snakesladders.Client;

import java.util.List;

import com.amar.lld.snakesladders.engine.GameEngine;
import com.amar.lld.snakesladders.models.Board;
import com.amar.lld.snakesladders.models.Dice;
import com.amar.lld.snakesladders.models.MoveOutcome;
import com.amar.lld.snakesladders.models.Player;
import com.amar.lld.snakesladders.strategies.*;

public class Game {
    private GameEngine engine;
    private Dice dice;  
    private List<Player> players;
    
    public Game(Board board, Dice dice, List<Player> players, IGameStartStrategy gameStartStrategy, IWinningStrategy winningStrategy) {
         this.engine = new GameEngine(board, players, gameStartStrategy, winningStrategy);
        this.dice = dice;
        this.players = players;
    }
    
    public void startGame(){

        MoveOutcome result = MoveOutcome.NONE;
        var currPlayer = players.get(0);
        int currentPlayerIndex = 0;

        while (result != MoveOutcome.WON){
            int diceSide = dice.roll();
            try
            {
                result = engine.ExecuteMove(diceSide, currPlayer);
                System.out.println("Player "+ currPlayer.getName() + " rolled a "+ diceSide + " and got outcome "+ result);
                if(result == MoveOutcome.START){
                    continue;
                }

                if(result == MoveOutcome.WON){
                    break;
                }

                // Move to next player (with wraparound)
                currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
                currPlayer = players.get(currentPlayerIndex);
            }
            catch(Exception ex){
                System.err.println(ex.getMessage());
            }
        }

        System.out.println("Player "+ currPlayer.getName() + " won the game");
    }

}
