package com.amar.lld.snakesladders.Client;

import java.util.ArrayList;
import java.util.List;

import com.amar.lld.snakesladders.Rules.IRule;
import com.amar.lld.snakesladders.Rules.LandOnCoinRule;
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
 * Example demonstrating OCP compliance when REMOVING ladders from the board
 * 
 * KEY INSIGHT: No code modification needed!
 * - Just pass empty ladder list to Board
 * - Don't add LandOnLadderRule to rules list
 * 
 * This works because:
 * 1. Board accepts List<Ladder> (can be empty)
 * 2. Rules are injected (can omit ladder rule)
 * 3. GameEngine doesn't know/care about ladders specifically
 */
public class ClientWithoutLadders {
    
    public static void main(String[] args) {
        System.out.println("=== Game WITHOUT Ladders ===\n");
        
        // Create rules list - NOTICE: No LandOnLadderRule!
        List<IRule> rules = new ArrayList<>();
        rules.add(new NoSixToStartRule());
        rules.add(new LandOnSnakeRule());
        // rules.add(new LandOnLadderRule());  ← Simply don't add this rule!
        rules.add(new LandOnCoinRule());
        rules.add(new WonGameRule());
        
        // Create board without ladders
        var board = createBoardWithoutLadders(100);
        var dice = new Dice(6);
        var players = new ArrayList<Player>();
        players.add(new Player("Player_1", "Player_1"));
        players.add(new Player("Player_2", "Player_2"));

        var game = new Game(board, dice, players, rules);
        game.startGame();
        
        System.out.println("\nNotice: No ladder movements occurred during the game!");
    }

    private static Board createBoardWithoutLadders(int size){
        var snakes = new ArrayList<Snake>();
        snakes.add(new Snake(11, 5));
        snakes.add(new Snake(30, 14));
        snakes.add(new Snake(36, 23));
        snakes.add(new Snake(50, 39));
        snakes.add(new Snake(80, 20));
        snakes.add(new Snake(93, 45));
        snakes.add(new Snake(97, 60));
        snakes.add(new Snake(99, 85));

        // NEW REQUIREMENT: No ladders on the board
        var ladders = new ArrayList<Ladder>();  // Empty list!
        // Or use: List.of() for immutable empty list
        
        var coins = new ArrayList<Coin>();
        coins.add(new Coin(10, 5));
        coins.add(new Coin(25, 10));
        coins.add(new Coin(40, 15));
        coins.add(new Coin(60, 20));
        coins.add(new Coin(75, 25));
        coins.add(new Coin(90, 50));

        // Same Board constructor - just passing empty ladder list
        // NO CODE MODIFICATION NEEDED!
        var board = new Board(new int[size], snakes, ladders, coins);
        return board;
    }
    
    // Alternative: Even cleaner with List.of()
    private static Board createBoardWithoutLaddersV2(int size){
        var board = new Board(
            new int[size], 
            List.of(
                new Snake(11, 5),
                new Snake(30, 14),
                new Snake(50, 39)
            ),
            List.of(),  // Empty ladder list - requirement satisfied!
            List.of(
                new Coin(10, 5),
                new Coin(25, 10)
            )
        );
        return board;
    }
}
