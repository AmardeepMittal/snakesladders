package com.amar.lld.snakesladders.strategies;

import com.amar.lld.snakesladders.models.Player;

public interface IGameStartStrategy {
    boolean CanStartGame(int diceSide, Player player);
}
