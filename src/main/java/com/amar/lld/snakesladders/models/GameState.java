
package com.amar.lld.snakesladders.models;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GameState {
    private Board board;
    private int diceSide;
    private Player currentPlayer;
    private Map<String, Integer> playerPositions;
}
