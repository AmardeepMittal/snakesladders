package com.amar.lld.snakesladders.Client;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


import com.amar.lld.snakesladders.models.Board;
import com.amar.lld.snakesladders.models.DefaultBox;
import com.amar.lld.snakesladders.models.Dice;
import com.amar.lld.snakesladders.models.Ladder;
import com.amar.lld.snakesladders.models.Player;
import com.amar.lld.snakesladders.models.Snake;
import com.amar.lld.snakesladders.models.Box;
import com.amar.lld.snakesladders.strategies.DefaultWinningStrategy;
import com.amar.lld.snakesladders.strategies.NoSixToStartStrategy;

public class Client {
    
    public static void main(String[] args) {        
        
        // Use the rules list in your game setup
        var board = createBoard(100);
        var dice = new Dice(6);
        var players = new ArrayList<Player>();
        players.add(new Player("Player_1", "Player_1"));
        players.add(new Player("Player_2", "Player_2"));

        var game = new Game(board, dice, players, new NoSixToStartStrategy(), new DefaultWinningStrategy());
        game.startGame();
    }

    private static Board createBoard(int size){
        HashSet<Integer> occupiedPositions = new HashSet<>();
        var snakes = new ArrayList<Snake>();
        snakes.add(new Snake(11, 5));
        snakes.add(new Snake(30, 14));
        snakes.add(new Snake(36, 23));
        snakes.add(new Snake(50, 39));
        snakes.add(new Snake(80, 20));
        snakes.add(new Snake(93, 45));
        occupiedPositions.add(11);
        occupiedPositions.add(30);      
        occupiedPositions.add(36);
        occupiedPositions.add(50);
        occupiedPositions.add(80);
        occupiedPositions.add(93);

        var ladders = new ArrayList<Ladder>();
        ladders.add(new Ladder(15, 21));
        ladders.add(new Ladder(32, 67));
        ladders.add(new Ladder(43, 55));
        ladders.add(new Ladder(70, 85));
        ladders.add(new Ladder(89, 95));
        occupiedPositions.add(15);
        occupiedPositions.add(32);
        occupiedPositions.add(43);
        occupiedPositions.add(70);
        occupiedPositions.add(89);

        var defaultBoxes = new ArrayList<DefaultBox>();
        for(int i=0; i<size; i++){
            if(occupiedPositions.contains(i)) continue;
            defaultBoxes.add(new DefaultBox(i));
        }
        
        List<Box> boxes = new ArrayList<>();
        boxes.addAll(defaultBoxes);
        boxes.addAll(snakes);
        boxes.addAll(ladders);
        boxes.sort((b1, b2) -> Integer.compare(b1.getPosition(), b2.getPosition()));
        var board = new Board(boxes);
        return board;
    }

}
