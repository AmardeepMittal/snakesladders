package com.amar.lld.snakesladders.factory;

import java.util.List;

import com.amar.lld.snakesladders.Rules.IRule;
import com.amar.lld.snakesladders.Rules.LandOnLadderRule;
import com.amar.lld.snakesladders.Rules.LandOnSnakeRule;
import com.amar.lld.snakesladders.Rules.NoSixToStartRule;
import com.amar.lld.snakesladders.Rules.WonGameRule;

public class RulesFactory {
    public static List<IRule> createDefaultRules() {
        return List.of(
            new NoSixToStartRule(),
            new LandOnSnakeRule(),
            new LandOnLadderRule(),
            new WonGameRule()
        );
    }
}
