package com.amar.lld.snakesladders.Client;

import java.util.ArrayList;
import java.util.List;

import com.amar.lld.snakesladders.Rules.IRule;
import com.amar.lld.snakesladders.Rules.LandOnCoinRule;
import com.amar.lld.snakesladders.Rules.LandOnLadderRule;
import com.amar.lld.snakesladders.Rules.LandOnSnakeRule;
import com.amar.lld.snakesladders.Rules.NoSixToStartRule;
import com.amar.lld.snakesladders.Rules.WonGameRule;
import com.amar.lld.snakesladders.models.Board;
import com.amar.lld.snakesladders.models.Coin;
import com.amar.lld.snakesladders.models.Dice;
import com.amar.lld.snakesladders.models.Ladder;
import com.amar.lld.snakesladders.models.Player;
import com.amar.lld.snakesladders.models.Snake;

/**
 * Example demonstrating how to add coins to the game without modifying existing code
 * This shows the Open/Closed Principle in action
 */
public class ClientWithCoins {
    
    public static void main(String[] args) {
        // Create rules list - just add the new rule, no modification to existing rules
        List<IRule> rules = new ArrayList<>();
        rules.add(new NoSixToStartRule());
        rules.add(new LandOnSnakeRule());
        rules.add(new LandOnLadderRule());
        rules.add(new LandOnCoinRule());    // NEW: Just add the coin rule
        rules.add(new WonGameRule());
        
        // Create board with coins
        var board = createBoardWithCoins(100);
        var dice = new Dice(6);
        var players = new ArrayList<Player>();
        players.add(new Player("Player_1", "Player_1"));
        players.add(new Player("Player_2", "Player_2"));

        var game = new Game(board, dice, players, rules);
        game.startGame();
    }

    private static Board createBoardWithCoins(int size){
        var snakes = new ArrayList<Snake>();
        snakes.add(new Snake(11, 5));
        snakes.add(new Snake(30, 14));
        snakes.add(new Snake(36, 23));
        snakes.add(new Snake(50, 39));
        snakes.add(new Snake(80, 20));
        snakes.add(new Snake(93, 45));
        snakes.add(new Snake(97, 60));
        snakes.add(new Snake(99, 85));

        var ladders = new ArrayList<Ladder>();
        ladders.add(new Ladder(15, 21));
        ladders.add(new Ladder(32, 67));
        ladders.add(new Ladder(43, 55));
        ladders.add(new Ladder(70, 85));
        ladders.add(new Ladder(89, 95));

        // NEW: Add coins to the board at strategic positions
        var coins = new ArrayList<Coin>();
        coins.add(new Coin(10, 5));    // 5 points at position 10
        coins.add(new Coin(25, 10));   // 10 points at position 25
        coins.add(new Coin(40, 15));   // 15 points at position 40
        coins.add(new Coin(60, 20));   // 20 points at position 60
        coins.add(new Coin(75, 25));   // 25 points at position 75
        coins.add(new Coin(90, 50));   // 50 points at position 90

        // Use the new Board constructor with coins
        var board = new Board(new int[size], snakes, ladders, coins);
        return board;
    }

    // Demonstrate backward compatibility - old code still works
    private static Board createBoardWithoutCoins(int size){
        var snakes = new ArrayList<Snake>();
        snakes.add(new Snake(11, 5));

        var ladders = new ArrayList<Ladder>();
        ladders.add(new Ladder(15, 21));

        // Old constructor still works! (backward compatible)
        var board = new Board(new int[size], snakes, ladders);
        return board;
    }
}
