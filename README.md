# Snakes and Ladders - Low Level Design

A Java implementation of the classic Snakes and Ladders board game demonstrating object-oriented design principles and clean architecture.

## 📋 Overview

This project implements a configurable Snakes and Ladders game with a rule-based engine that allows flexible game mechanics. The design follows SOLID principles and uses design patterns for extensibility.

## 🎯 Features

- **Rule-Based Game Engine**: Extensible rule system for game mechanics
- **Configurable Board**: Customizable board size, snakes, and ladders
- **Multiple Players**: Support for 2+ players
- **Custom Dice**: Configurable dice with any number of sides
- **Game Rules**:
  - No Six to Start: Players must roll a 6 to begin
  - Land on Snake: Slide down when landing on a snake's head
  - Land on Ladder: Climb up when landing on a ladder's base
  - Win Condition: First player to reach the final position wins

## 🏗️ Architecture

### Key Components

```
src/main/java/com/amar/lld/snakesladders/
├── Client/
│   └── Client.java              # Entry point and game setup
├── engine/
│   └── GameEngine.java          # Core game logic orchestrator
├── factory/
│   └── RulesFactory.java        # Rule creation factory
├── models/
│   ├── Board.java               # Game board representation
│   ├── Dice.java                # Dice implementation
│   ├── GameState.java           # Current game state
│   ├── Ladder.java              # Ladder entity
│   ├── Snake.java               # Snake entity
│   ├── Player.java              # Player entity
│   ├── PlayerState.java         # Player state enum
│   ├── Position.java            # Board position
│   ├── MoveOutcome.java         # Move result enum
│   ├── Rule.java                # Abstract rule base
│   └── RuleType.java            # Rule type enum
└── Rules/
    ├── IRule.java               # Rule interface
    ├── NoSixToStartRule.java    # Starting rule
    ├── LandOnSnakeRule.java     # Snake rule
    ├── LandOnLadderRule.java    # Ladder rule
    └── WonGameRule.java         # Win condition rule
```

### Design Patterns Used

- **Strategy Pattern**: Rule interface for interchangeable game rules
- **Factory Pattern**: RulesFactory for rule creation
- **State Pattern**: Player and game state management
- **Command Pattern**: Move execution through GameEngine

## 🎯 Game Flow & Execution

### Step-by-Step Execution Flow

When the game starts, here's the detailed execution flow by class:

#### 1. **Client.java** - Game Initialization
```
Step 1.1: Create rule list
  └─ Instantiate: NoSixToStartRule, LandOnSnakeRule, LandOnLadderRule, WonGameRule

Step 1.2: Create Board (via createBoard() method)
  └─ Board.java: Initialize board with size, snakes, and ladders
      ├─ Snake.java: Create 8 snake objects with start/end positions
      └─ Ladder.java: Create 5 ladder objects with start/end positions

Step 1.3: Create Game Components
  ├─ Dice.java: Initialize dice with 6 sides
  └─ Player.java: Create Player_1 and Player_2 with PlayerState.NONE

Step 1.4: Initialize GameEngine
  └─ GameEngine.java: Create with board, dice, players, and rules
      └─ GameState.java: Initialize game state with empty player positions map
```

#### 2. **Game Loop** - Main Execution (Client.java)
```
While no player has won:

Step 2.1: Dice.roll()
  └─ Generate random number between 1 and dice sides

Step 2.2: GameEngine.ExecuteMove(diceSide, currentPlayer)
  └─ See "Move Execution Details" below

Step 2.3: Check MoveOutcome
  ├─ If START: Continue to next iteration (player waiting for 6)
  ├─ If WON: Break loop, announce winner
  └─ If NEXT_MOVE: Switch to next player

Step 2.4: Update current player index (round-robin)
```

#### 3. **GameEngine.ExecuteMove()** - Move Execution Details
```
Step 3.1: Validate player (not null)

Step 3.2: Update GameState
  ├─ GameState.setCurrentPlayer(player)
  └─ GameState.setDiceSide(diceSide)

Step 3.3: Check Player State
  ├─ If PlayerState.NONE:
  │   └─ Call CanStartGame(diceSide, player)
  │       └─ NoSixToStartRule.applyRule(gameState)
  │           ├─ If dice == 6: Return true
  │           └─ If dice != 6: Return false
  │       └─ If true: Set PlayerState.START_PLAYING, return MoveOutcome.START
  │       └─ If false: Return MoveOutcome.NONE
  │
  └─ If PlayerState.START_PLAYING:
      └─ Change to PlayerState.PLAYING

Step 3.4: Update Player Position
  ├─ Get current position from gameState.playerPositions map
  ├─ Calculate new position: currentPos + diceSide
  └─ Update position in map

Step 3.5: Check Win Condition
  └─ Call hasWonGame(player)
      └─ WonGameRule.applyRule(gameState)
          └─ Check if player position >= board size
              └─ If yes: Return MoveOutcome.WON

Step 3.6: Apply Game Rules (in order)
  ├─ NoSixToStartRule.applyRule() - Skip (only for start)
  ├─ LandOnSnakeRule.applyRule()
  │   └─ Check if current position has snake head
  │       └─ If yes: Move player to snake tail position
  ├─ LandOnLadderRule.applyRule()
  │   └─ Check if current position has ladder base
  │       └─ If yes: Move player to ladder top position
  └─ WonGameRule.applyRule() - Already checked above

Step 3.7: Return MoveOutcome.NEXT_MOVE
```

#### 4. **Rule Application Flow**

Each rule follows this pattern:

```
IRule.applyRule(GameState gameState):
  
  Step 4.1: Get current player from gameState
  Step 4.2: Get player's current position
  Step 4.3: Apply rule-specific logic:
  
    NoSixToStartRule:
      └─ Check if diceSide == 6
    
    LandOnSnakeRule:
      ├─ Get all snakes from board
      ├─ Find snake at current position
      └─ If found: Update player position to snake's end
    
    LandOnLadderRule:
      ├─ Get all ladders from board
      ├─ Find ladder at current position
      └─ If found: Update player position to ladder's end
    
    WonGameRule:
      └─ Check if position >= board size
```

#### 5. **Class Interaction Diagram**

```
Client
  ↓ creates
  ├─→ Board (contains Snake[], Ladder[])
  ├─→ Dice
  ├─→ Player[] (with PlayerState)
  ├─→ IRule[] (NoSixToStartRule, LandOnSnakeRule, etc.)
  └─→ GameEngine
       ├─ contains → GameState
       │              ├─ references → Board
       │              ├─ references → currentPlayer
       │              ├─ stores → playerPositions (Map)
       │              └─ stores → diceSide
       └─ uses → IRule[]
                  └─ each rule reads/modifies GameState
```

### Execution Example

```
Turn 1 (Player_1):
  1. Dice.roll() → 3
  2. GameEngine.ExecuteMove(3, Player_1)
     - Player_1 is in NONE state
     - NoSixToStartRule: 3 != 6 → false
     - Return MoveOutcome.NONE
  3. Player_1 stays at position 0

Turn 2 (Player_2):
  1. Dice.roll() → 6
  2. GameEngine.ExecuteMove(6, Player_2)
     - Player_2 is in NONE state
     - NoSixToStartRule: 6 == 6 → true
     - Set PlayerState.START_PLAYING
     - Return MoveOutcome.START
  3. Player_2 can now start playing

Turn 3 (Player_1):
  1. Dice.roll() → 6
  2. GameEngine.ExecuteMove(6, Player_1)
     - NoSixToStartRule: 6 == 6 → true
     - Set PlayerState.START_PLAYING
     - Return MoveOutcome.START

Turn 4 (Player_2):
  1. Dice.roll() → 5
  2. GameEngine.ExecuteMove(5, Player_2)
     - Player_2 state: START_PLAYING → PLAYING
     - Position: 0 + 5 = 5
     - WonGameRule: 5 < 100 → not won
     - LandOnSnakeRule: No snake at 5
     - LandOnLadderRule: No ladder at 5
     - Return MoveOutcome.NEXT_MOVE
  3. Player_2 moves to position 5

... game continues until someone reaches position 100
```

## 🚀 Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.6+

### Building the Project

```bash
# Compile the project
.\mvnw.cmd compile

# Clean and compile
.\mvnw.cmd clean compile

# Package as JAR
.\mvnw.cmd package
```

### Running the Game

```bash
# Using Maven
.\mvnw.cmd compile exec:java "-Dexec.mainClass=com.amar.lld.snakesladders.Client.Client"

# Using Java (after compilation)
java -cp target/classes com.amar.lld.snakesladders.Client.Client
```

## 🎮 Game Configuration

### Default Board Setup

- **Board Size**: 100 squares
- **Dice**: 6 sides
- **Snakes**: 8 snakes at positions (11→5, 30→14, 36→23, 50→39, 80→20, 93→45, 97→60, 99→85)
- **Ladders**: 5 ladders at positions (15→21, 32→67, 43→55, 70→85, 89→95)
- **Players**: 2 players (Player_1, Player_2)

### Customizing the Game

Modify the `Client.java` file to customize:

```java
// Change board size
var board = createBoard(100);

// Change dice sides
var dice = new Dice(6);

// Add more players
players.add(new Player("Player_3", "Player_3"));

// Add custom snakes/ladders in createBoard()
snakes.add(new Snake(startPos, endPos));
ladders.add(new Ladder(startPos, endPos));
```

## 📝 Rules Implementation

Each rule implements the `IRule` interface:

```java
public interface IRule {
    boolean applyRule(GameState gameState) throws Exception;
    RuleType getRuleType();
}
```

Add new rules by:
1. Creating a new class implementing `IRule`
2. Defining the rule logic in `applyRule()`
3. Adding the rule to the rules list in `Client.java`

## 🧪 Testing

```bash
# Run tests
.\mvnw.cmd test

# Run with coverage
.\mvnw.cmd test jacoco:report
```

## 📦 Project Structure

```
snakesladders/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/amar/lld/snakesladders/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
├── target/
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

## 🔧 Technologies

- **Java 21**: Core programming language
- **Spring Boot 4.0.0**: Application framework
- **Maven**: Build and dependency management
- **Lombok**: Reduce boilerplate code

## 🎯 Design Principles

- **Single Responsibility**: Each class has one clear purpose
- **Open/Closed**: Extensible through rules without modifying core engine
- **Liskov Substitution**: All rules are interchangeable
- **Interface Segregation**: Focused interfaces for specific behaviors
- **Dependency Inversion**: Depends on abstractions (IRule interface)

## 📄 License

This project is a demonstration of Low Level Design concepts.

## 👤 Author

Amardeep Mittal

## 🙏 Acknowledgments

- Inspired by Udit Agarwal's Low Level Design lectures
- Classic Snakes and Ladders board game
