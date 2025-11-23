package com.amar.lld.snakesladders.Client;

import java.util.ArrayList;
import java.util.List;

import com.amar.lld.snakesladders.Rules.IRule;
import com.amar.lld.snakesladders.Rules.LandOnLadderRule;
import com.amar.lld.snakesladders.Rules.LandOnSnakeRule;
import com.amar.lld.snakesladders.Rules.NoSixToStartRule;
import com.amar.lld.snakesladders.Rules.WonGameRule;
import com.amar.lld.snakesladders.models.Board;
import com.amar.lld.snakesladders.models.Dice;
import com.amar.lld.snakesladders.models.Ladder;
import com.amar.lld.snakesladders.models.Player;
import com.amar.lld.snakesladders.models.Snake;

public class Client {
    
    public static void main(String[] args) {        
        
        // Use the rules list in your game setup
        var board = createBoard(100);
        var dice = new Dice(6);
        var players = new ArrayList<Player>();
        players.add(new Player("Player_1", "Player_1"));
        players.add(new Player("Player_2", "Player_2"));

        var game = new Game(board, dice, players);
        game.startGame();
    }

    private static Board createBoard(int size){
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

        var board = new Board(new int[size], snakes, ladders);
        return board;

    }

}
