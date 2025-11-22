package com.amar.lld.snakesladders.Client;

import java.util.ArrayList;
import java.util.List;

import com.amar.lld.snakesladders.Rules.*;
import com.amar.lld.snakesladders.engine.GameEngine;
import com.amar.lld.snakesladders.models.Board;
import com.amar.lld.snakesladders.models.Dice;
import com.amar.lld.snakesladders.models.MoveOutcome;
import com.amar.lld.snakesladders.models.Player;

public class Game {
    private GameEngine engine;
    private Dice dice;  
    private List<Player> players;
    public Game(Board board, Dice dice, List<Player> players) {
     // Create rules list
        List<IRule> rules = new ArrayList<>();
        rules.add(new NoSixToStartRule());
        rules.add(new LandOnSnakeRule());
        rules.add(new LandOnLadderRule());
        rules.add(new WonGameRule());
        
        // Use the rules list in your game setup
        this.engine = new GameEngine(board, dice, players, rules);
        this.dice = dice;
        this.players = players;
    }

    public void startGame(){

        MoveOutcome result = MoveOutcome.NONE;
        var currPlayer = players.getFirst();
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
