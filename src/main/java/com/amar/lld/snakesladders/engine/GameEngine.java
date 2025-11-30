package com.amar.lld.snakesladders.engine;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.amar.lld.snakesladders.models.Board;
import com.amar.lld.snakesladders.models.MoveOutcome;
import com.amar.lld.snakesladders.models.Player;
import com.amar.lld.snakesladders.models.PlayerState;
import com.amar.lld.snakesladders.strategies.IGameStartStrategy;
import com.amar.lld.snakesladders.strategies.IWinningStrategy;


public class GameEngine {
    private Board board;
    private IGameStartStrategy gameStartStrategy;
    private IWinningStrategy winningStrategy;   
    public GameEngine(Board board, List<Player> players, IGameStartStrategy gameStartStrategy, IWinningStrategy winningStrategy){
        this.board = board;
        this.gameStartStrategy = gameStartStrategy;
        this.winningStrategy = winningStrategy;
    }

    public MoveOutcome ExecuteMove(int diceSide, Player player) throws Exception{
        if(player == null) 
            throw new NullPointerException();
        
        if(player.getPlayerState() == PlayerState.NONE){
            if(gameStartStrategy.CanStartGame(diceSide, player)){
                player.setPlayerState(PlayerState.START_PLAYING);
                return MoveOutcome.START;
            }
            else return MoveOutcome.NONE;
        }

        if(player.getPlayerState() == PlayerState.START_PLAYING){
            player.setPlayerState(PlayerState.PLAYING);
        }

        var currPos = player.getPosition();
        var newPos = currPos + diceSide;
        newPos = Math.min(newPos, board.boxes().size() -1);

        var box = board.boxes().get(newPos );
        box.applyBoxBehavior(player);

        if(winningStrategy.hasPlayerWon(player.getPosition(), board.boxes().size()-1)){
            return MoveOutcome.WON;
        }

        return MoveOutcome.NEXT_MOVE;
       
    }
}
