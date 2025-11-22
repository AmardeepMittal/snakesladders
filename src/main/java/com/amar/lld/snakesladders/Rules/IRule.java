package com.amar.lld.snakesladders.Rules;

import com.amar.lld.snakesladders.models.GameState;
import com.amar.lld.snakesladders.models.RuleType;

public interface IRule {
    boolean applyRule(GameState gameState);
    RuleType getRuleType();
}
