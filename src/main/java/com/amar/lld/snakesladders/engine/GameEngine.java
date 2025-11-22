package com.amar.lld.snakesladders.engine;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.amar.lld.snakesladders.Rules.IRule;
import com.amar.lld.snakesladders.models.Board;
import com.amar.lld.snakesladders.models.Dice;
import com.amar.lld.snakesladders.models.GameState;
import com.amar.lld.snakesladders.models.MoveOutcome;
import com.amar.lld.snakesladders.models.Player;
import com.amar.lld.snakesladders.models.PlayerState;
import com.amar.lld.snakesladders.models.RuleType;

public class GameEngine {
    private static final PlayerState NONE = null;
    private GameState gameState;
    private List<IRule> rules;
    public GameEngine(Board board, Dice dice, List<Player> players, List<IRule> rules){
        gameState = new GameState(board, 0,null, new HashMap<>());
        this.rules = rules;
    }

    public MoveOutcome ExecuteMove(int diceSide, Player player) throws Exception{
        if(player == null) 
            throw new NullPointerException();
        
        gameState.setCurrentPlayer(player);
        gameState.setDiceSide(diceSide);

        if(player.getPlayerState() == PlayerState.NONE){
            if(CanStartGame(diceSide, player)){
                player.setPlayerState(PlayerState.START_PLAYING);
                return MoveOutcome.START;
            }
            else return MoveOutcome.NONE;
        }

        if(player.getPlayerState() == PlayerState.START_PLAYING){
            player.setPlayerState(PlayerState.PLAYING);
        }

        var postions = gameState.getPlayerPositions();
        var currPos = postions.get(player.getId()) == null ? 0 : postions.get(player.getId());
        postions.put(player.getId(), currPos + diceSide);

        if(hasWonGame(player)){
            return MoveOutcome.WON;
        }

        for(IRule rule : rules){
            rule.applyRule(gameState);
        }

        return MoveOutcome.NEXT_MOVE;
       
    }

    private boolean CanStartGame(int diceSide, Player player) throws Exception{
        if(player == null) 
            throw new NullPointerException();
        
         var startRule = getRule(RuleType.NO_SIX_TO_START);
        if(!startRule.isPresent()){
            throw  new Exception("No start rule present");
        }
        return startRule.get().applyRule(gameState);
    }

    private boolean hasWonGame(Player player) throws Exception{
       var wonGameRule = getRule(RuleType.WON_GAME);
       if(!wonGameRule.isPresent()){
            throw  new Exception("No game wining rule present !!");
        }
        return wonGameRule.get().applyRule(gameState);
    }



    private Optional<IRule> getRule(RuleType type){
        return rules.stream().filter(x -> x.getRuleType() == type).findFirst();
    }
}
