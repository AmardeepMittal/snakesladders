# Snakes and Ladders - Low Level Design

A Java implementation of the classic Snakes and Ladders board game demonstrating object-oriented design principles and clean architecture with Strategy Pattern and OCP compliance.

## 📋 Overview

This project implements a configurable Snakes and Ladders game using **Strategy Pattern** for game start and winning conditions, and **Box interface** for polymorphic behavior of board elements (DefaultBox, Snake, Ladder). The design follows SOLID principles, particularly the Open/Closed Principle.

## 🎯 Features

- **Strategy-Based Game Engine**: Pluggable strategies for game start and winning conditions
- **Polymorphic Box Behavior**: Each box type (default, snake, ladder) applies its own behavior
- **Configurable Board**: Customizable board size, snakes, and ladders
- **Multiple Players**: Support for 2+ players with state management
- **Custom Dice**: Configurable dice with any number of sides
- **Game Mechanics**:
  - No Six to Start: Players must roll a 6 to begin (configurable strategy)
  - Auto Snake Slide: Automatically slide down when landing on a snake's head
  - Auto Ladder Climb: Automatically climb up when landing on a ladder's base
  - Win Condition: First player to reach the final position wins (configurable strategy)

## 🏗️ Architecture

### Key Components

```
src/main/java/com/amar/lld/snakesladders/
├── Client/
│   ├── Client.java              # Entry point and game setup
│   └── Game.java                # Game loop and turn management
├── engine/
│   └── GameEngine.java          # Core game logic orchestrator
├── strategies/
│   ├── IGameStartStrategy.java  # Strategy interface for game start
│   ├── NoSixToStartStrategy.java # Requires 6 to start implementation
│   ├── IWinningStrategy.java    # Strategy interface for win condition
│   └── DefaultWinningStrategy.java # Default win condition implementation
└── models/
    ├── Box.java                 # Box interface (polymorphic behavior)
    ├── DefaultBox.java          # Normal box implementation
    ├── Snake.java               # Snake box implementation
    ├── Ladder.java              # Ladder box implementation
    ├── Board.java               # Game board (list of boxes)
    ├── Dice.java                # Dice implementation
    ├── Player.java              # Player entity with state
    ├── PlayerState.java         # Player state enum
    ├── Position.java            # Board position
    └── MoveOutcome.java         # Move result enum
```

### Design Patterns Used

- **Strategy Pattern**: 
  - `IGameStartStrategy` for flexible game start conditions
  - `IWinningStrategy` for customizable win conditions
- **Polymorphism (Box Interface)**: Each box type handles its own behavior
- **State Pattern**: `PlayerState` enum manages player lifecycle (NONE → START_PLAYING → PLAYING)
- **Record Pattern**: Immutable data classes for Snake, Ladder, DefaultBox

## 🎯 Game Flow & Execution

### Step-by-Step Execution Flow

When the game starts, here's the detailed execution flow by class:

#### 1. **Client.java** - Game Initialization
```
Step 1.1: Create Board (via createBoard() method)
  ├─ Create Snake objects with (start, end) positions
  │  └─ Each Snake implements Box interface
  ├─ Create Ladder objects with (start, end) positions
  │  └─ Each Ladder implements Box interface
  ├─ Create DefaultBox objects for remaining positions
  │  └─ Each DefaultBox implements Box interface
  └─ Board.java: Initialize with sorted List<Box>

Step 1.2: Create Game Components
  ├─ Dice.java: Initialize dice with 6 sides
  └─ Player.java: Create Player_1 and Player_2
      └─ Each player starts with position=0, PlayerState.NONE

Step 1.3: Create Strategies
  ├─ NoSixToStartStrategy implements IGameStartStrategy
  └─ DefaultWinningStrategy implements IWinningStrategy

Step 1.4: Initialize Game and GameEngine
  ├─ Game.java: Create with board, dice, players, and strategies
  └─ GameEngine.java: Create with board, players, and strategies
```

#### 2. **Game Loop** - Main Execution (Game.java)
```
While no player has won:

Step 2.1: Dice.roll()
  └─ Generate random number between 1 and dice sides

Step 2.2: GameEngine.ExecuteMove(diceSide, currentPlayer)
  └─ See "Move Execution Details" below

Step 2.3: Check MoveOutcome
  ├─ If NONE: Continue (player couldn't start)
  ├─ If START: Continue (player got 6, will move next turn)
  ├─ If WON: Break loop, announce winner
  └─ If NEXT_MOVE: Switch to next player (round-robin)

Step 2.4: Update current player index: (index + 1) % players.size()
```

#### 3. **GameEngine.ExecuteMove()** - Move Execution Details
```
Step 3.1: Validate player (not null)

Step 3.2: Check Player State
  ├─ If PlayerState.NONE:
  │   └─ Call gameStartStrategy.CanStartGame(diceSide, player)
  │       └─ NoSixToStartStrategy checks:
  │           ├─ If player.position != 0: Return true (already started)
  │           ├─ If diceSide == 6: Return true (can start)
  │           └─ Else: Return false (need 6 to start)
  │       └─ If true: Set PlayerState.START_PLAYING, return MoveOutcome.START
  │       └─ If false: Return MoveOutcome.NONE
  │
  └─ If PlayerState.START_PLAYING:
      └─ Change to PlayerState.PLAYING

Step 3.3: Calculate New Position
  ├─ Get currentPos from player.getPosition()
  ├─ Calculate: newPos = currentPos + diceSide
  └─ Clamp to board: newPos = min(newPos, board.boxes().size() - 1)

Step 3.4: Apply Box Behavior (Polymorphism!)
  ├─ Get box at newPos: box = board.boxes().get(newPos)
  └─ Call box.applyBoxBehavior(player)
      ├─ If DefaultBox: player.setPosition(position)
      ├─ If Snake: player.setPosition(end) + print snake message
      └─ If Ladder: player.setPosition(end) + print ladder message

Step 3.5: Check Win Condition
  └─ Call winningStrategy.hasPlayerWon(player.position, board.size-1)
      └─ DefaultWinningStrategy: return position >= winningPosition
      └─ If won: Return MoveOutcome.WON

Step 3.6: Return MoveOutcome.NEXT_MOVE
```

#### 4. **Box Behavior Pattern (Polymorphism)**

Each Box type implements its own behavior:

```
Box Interface:
  ├─ int getPosition()
  └─ void applyBoxBehavior(Player player)

DefaultBox:
  └─ applyBoxBehavior(): player.setPosition(position)

Snake:
  └─ applyBoxBehavior():
      ├─ player.setPosition(end)
      └─ print("Player got bitten by snake from {start} to {end}")

Ladder:
  └─ applyBoxBehavior():
      ├─ player.setPosition(end)
      └─ print("Player climbed ladder from {start} to {end}")
```

#### 5. **Class Interaction Diagram**

```
Client
  ↓ creates
  ├─→ Board (List<Box>)
  │    ├─ Snake (implements Box)
  │    ├─ Ladder (implements Box)
  │    └─ DefaultBox (implements Box)
  ├─→ Dice
  ├─→ Player[] (position, PlayerState)
  ├─→ NoSixToStartStrategy (implements IGameStartStrategy)
  ├─→ DefaultWinningStrategy (implements IWinningStrategy)
  └─→ Game
       ├─ contains → GameEngine
       │              ├─ references → Board
       │              ├─ uses → IGameStartStrategy
       │              └─ uses → IWinningStrategy
       └─ manages → game loop and turn rotation
```

### Execution Example

```
Turn 1 (Player_1):
  1. Dice.roll() → 3
  2. GameEngine.ExecuteMove(3, Player_1)
     - Player_1.state = NONE, position = 0
     - NoSixToStartStrategy.CanStartGame(3, Player_1)
       → position == 0 && dice != 6 → false
     - Return MoveOutcome.NONE
  3. Player_1 stays at position 0, output: "Player Player_1 rolled a 3 and got outcome NONE"

Turn 2 (Player_2):
  1. Dice.roll() → 6
  2. GameEngine.ExecuteMove(6, Player_2)
     - Player_2.state = NONE, position = 0
     - NoSixToStartStrategy.CanStartGame(6, Player_2)
       → dice == 6 → true
     - Set Player_2.state = START_PLAYING
     - Return MoveOutcome.START
  3. Output: "Player Player_2 rolled a 6 and got outcome START"
     Player_2 can now move next turn

Turn 3 (Player_1):
  1. Dice.roll() → 6
  2. GameEngine.ExecuteMove(6, Player_1)
     - NoSixToStartStrategy → true
     - Set Player_1.state = START_PLAYING
     - Return MoveOutcome.START

Turn 4 (Player_2):
  1. Dice.roll() → 4
  2. GameEngine.ExecuteMove(4, Player_2)
     - Player_2.state = START_PLAYING → PLAYING
     - newPos = 0 + 4 = 4
     - box = board.boxes().get(4) → DefaultBox(4)
     - DefaultBox.applyBoxBehavior() → player.setPosition(4)
     - hasPlayerWon(4, 99) → false
     - Return MoveOutcome.NEXT_MOVE
  3. Player_2 moves to position 4

Turn 5 (Player_1):
  1. Dice.roll() → 5
  2. GameEngine.ExecuteMove(5, Player_1)
     - Player_1.state = START_PLAYING → PLAYING
     - newPos = 0 + 5 = 5
     - box = board.boxes().get(5) → DefaultBox(5)
     - Player_1 position = 5
     - Return MoveOutcome.NEXT_MOVE

Turn N (Player_1 at position 9):
  1. Dice.roll() → 2
  2. GameEngine.ExecuteMove(2, Player_1)
     - newPos = 9 + 2 = 11
     - box = board.boxes().get(11) → Snake(11, 5)
     - Snake.applyBoxBehavior():
       ├─ player.setPosition(5)
       └─ Print: "Oops! Player Player_1 got bitten by a snake from 11 to 5"
     - Player_1 slides to position 5
     - Return MoveOutcome.NEXT_MOVE

Turn M (Player_2 at position 13):
  1. Dice.roll() → 2
  2. GameEngine.ExecuteMove(2, Player_2)
     - newPos = 13 + 2 = 15
     - box = board.boxes().get(15) → Ladder(15, 21)
     - Ladder.applyBoxBehavior():
       ├─ player.setPosition(21)
       └─ Print: "Yay! Player Player_2 climbed a ladder from 15 to 21"
     - Player_2 climbs to position 21
     - Return MoveOutcome.NEXT_MOVE

... game continues until someone reaches position 99
```

## 🚀 Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.6+

### Building the Project

```powershell
# Compile the project
.\mvnw.cmd compile

# Clean and compile
.\mvnw.cmd clean compile

# Package as JAR
.\mvnw.cmd package
```

### Running the Game

```powershell
# Using Maven
.\mvnw.cmd compile exec:java "-Dexec.mainClass=com.amar.lld.snakesladders.Client.Client"

# Using Java (after compilation)
java -cp target/classes com.amar.lld.snakesladders.Client.Client

# Using VS Code
# Click "Run" above the main method in Client.java
```

## 🎮 Game Configuration

### Default Board Setup

- **Board Size**: 100 squares (positions 0-99)
- **Dice**: 6 sides
- **Snakes**: 6 snakes at positions
  - 11 → 5
  - 30 → 14
  - 36 → 23
  - 50 → 39
  - 80 → 20
  - 93 → 45
- **Ladders**: 5 ladders at positions
  - 15 → 21
  - 32 → 67
  - 43 → 55
  - 70 → 85
  - 89 → 95
- **Players**: 2 players (Player_1, Player_2)
- **Start Strategy**: NoSixToStartStrategy (need 6 to start)
- **Winning Strategy**: DefaultWinningStrategy (reach position 99)

### Customizing the Game

#### Change Board Configuration
Modify the `createBoard()` method in `Client.java`:

```java
// Change board size
var board = createBoard(100);  // Use 50, 100, 200, etc.

// Add custom snakes
snakes.add(new Snake(startPos, endPos));

// Add custom ladders
ladders.add(new Ladder(startPos, endPos));
```

#### Change Dice
```java
var dice = new Dice(6);  // Change to 4, 8, 12, etc.
```

#### Add More Players
```java
players.add(new Player("Player_1", "Player_1"));
players.add(new Player("Player_2", "Player_2"));
players.add(new Player("Player_3", "Player_3"));
```

#### Change Game Strategies
```java
// Option 1: No six to start (current default)
var game = new Game(board, dice, players, 
    new NoSixToStartStrategy(), 
    new DefaultWinningStrategy());

// Option 2: Create custom strategy
public class AlwaysCanStartStrategy implements IGameStartStrategy {
    @Override
    public boolean CanStartGame(int diceSide, Player player) {
        return true;  // Any roll can start
    }
}

var game = new Game(board, dice, players, 
    new AlwaysCanStartStrategy(), 
    new DefaultWinningStrategy());
```

### Creating Custom Strategies

#### Custom Game Start Strategy
```java
public class DoubleOddToStartStrategy implements IGameStartStrategy {
    @Override
    public boolean CanStartGame(int diceSide, Player player) {
        if(player.getPosition() != 0) return true;
        // Need two consecutive odd numbers to start
        return diceSide % 2 == 1;  // Your logic here
    }
}
```

#### Custom Winning Strategy
```java
public class ExactLandingStrategy implements IWinningStrategy {
    @Override
    public boolean hasPlayerWon(int playerPosition, int winningPosition) {
        // Must land exactly on final position
        return playerPosition == winningPosition;
    }
}
```

### Adding New Box Types (OCP Compliant)

Create new box types without modifying existing code:

```java
// Example: Coin box that gives extra points
public record Coin(Integer position, int points) implements Box {
    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void applyBoxBehavior(Player player) {
        player.setPosition(position);
        player.addPoints(points);  // If Player has points field
        System.out.println("Player " + player.getName() + 
                         " collected " + points + " coins!");
    }
}

// Example: Teleport box
public record Teleport(Integer from, Integer to) implements Box {
    @Override
    public int getPosition() {
        return from;
    }

    @Override
    public void applyBoxBehavior(Player player) {
        player.setPosition(to);
        System.out.println("Player " + player.getName() + 
                         " teleported from " + from + " to " + to);
    }
}

// Add to board in createBoard()
var coins = new ArrayList<Coin>();
coins.add(new Coin(25, 10));
coins.add(new Coin(50, 20));
boxes.addAll(coins);
```

## 📝 Design Patterns & Principles

### 1. **Strategy Pattern** ⭐
Two strategy interfaces allow pluggable behavior:

```java
// Game Start Strategy
public interface IGameStartStrategy {
    boolean CanStartGame(int diceSide, Player player);
}

// Winning Strategy
public interface IWinningStrategy {
    boolean hasPlayerWon(int playerPosition, int winningPosition);
}
```

**Benefits**: Easy to add new game rules without modifying GameEngine

### 2. **Polymorphism (Box Interface)** ⭐
```java
public interface Box {
    int getPosition();
    void applyBoxBehavior(Player player);
}
```

Each box type (DefaultBox, Snake, Ladder) implements its own behavior.

**Benefits**: Adding new box types (Coin, Teleport, etc.) requires no changes to existing code

### 3. **State Pattern**
```java
public enum PlayerState {
    NONE,           // Player hasn't started
    START_PLAYING,  // Player got 6, ready to move
    PLAYING         // Player is actively playing
}
```

**Benefits**: Clear player lifecycle management

### 4. **Record Pattern (Java 14+)**
Immutable data classes for Snake, Ladder, DefaultBox:
```java
public record Snake(Integer start, Integer end) implements Box { ... }
public record Ladder(Integer start, Integer end) implements Box { ... }
```

**Benefits**: Concise, immutable, with built-in equals/hashCode/toString

## 🎯 SOLID Principles

### ✅ Single Responsibility Principle
- `GameEngine`: Manages move execution logic
- `Game`: Handles game loop and turn rotation
- `Board`: Stores box configuration
- Each `Box` type: Handles its own behavior

### ✅ Open/Closed Principle
**Open for extension, closed for modification:**

```java
// Add new strategies without modifying GameEngine
public class MustLandExactlyStrategy implements IWinningStrategy { ... }

// Add new box types without modifying Board or GameEngine
public record Teleport(...) implements Box { ... }
```

### ✅ Liskov Substitution Principle
All `Box` implementations can be used interchangeably:
```java
Box box = board.boxes().get(position);
box.applyBoxBehavior(player);  // Works for any Box type
```

### ✅ Interface Segregation Principle
- `IGameStartStrategy`: Only game start logic
- `IWinningStrategy`: Only winning condition logic
- `Box`: Only position and behavior methods

### ✅ Dependency Inversion Principle
`GameEngine` depends on abstractions (interfaces), not concrete implementations:
```java
public GameEngine(Board board, List<Player> players, 
                  IGameStartStrategy gameStartStrategy,  // ← Interface
                  IWinningStrategy winningStrategy) { ... } // ← Interface
```

## 🔄 Future Extensions (OCP Compliant)

### Adding More Strategies

You can easily add new strategies without modifying existing code:

```java
// New Game Start Strategy: Must roll an even number to start
public class EvenNumberToStartStrategy implements IGameStartStrategy {
    @Override
    public boolean CanStartGame(int diceSide, Player player) {
        if (player.getPosition() != 0) return true;
        return diceSide % 2 == 0;
    }
}

// New Winning Strategy: Must land exactly on last position
public class ExactLandingStrategy implements IWinningStrategy {
    @Override
    public boolean hasPlayerWon(int playerPosition, int winningPosition) {
        return playerPosition == winningPosition;
    }
}

// Use in Client.java
var game = new Game(board, dice, players, 
    new EvenNumberToStartStrategy(), 
    new ExactLandingStrategy());
```

### Adding New Box Types

The Box interface allows unlimited extensions:

```java
// Power-up box that gives extra points
public record PowerUp(Integer position, int points) implements Box {
    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void applyBoxBehavior(Player player) {
        player.setPosition(position);
        player.addPoints(points);
        System.out.println("Player " + player.getName() + 
                         " collected " + points + " power points!");
    }
}

// Trap box that skips next turn
public record Trap(Integer position) implements Box {
    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void applyBoxBehavior(Player player) {
        player.setPosition(position);
        player.setSkipNextTurn(true);
        System.out.println("Player " + player.getName() + 
                         " fell into a trap! Skip next turn.");
    }
}
```

### Possible Future Enhancements

Without breaking existing code, you can add:

- **Multiple Dice Strategy**: Roll two dice and add them
- **Bonus Turn Strategy**: Get extra turn on rolling max number
- **Team Play Strategy**: Players can form teams
- **Safe Zones**: Certain box types immune to snakes
- **Mystery Boxes**: Random effects when landed on
- **Checkpoints**: Save positions at certain milestones

## 🧪 Testing

```powershell
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
│   │   │       ├── Client/
│   │   │       │   ├── Client.java
│   │   │       │   └── Game.java
│   │   │       ├── engine/
│   │   │       │   └── GameEngine.java
│   │   │       ├── strategies/
│   │   │       │   ├── IGameStartStrategy.java
│   │   │       │   ├── NoSixToStartStrategy.java
│   │   │       │   ├── IWinningStrategy.java
│   │   │       │   └── DefaultWinningStrategy.java
│   │   │       └── models/
│   │   │           ├── Box.java
│   │   │           ├── DefaultBox.java
│   │   │           ├── Snake.java
│   │   │           ├── Ladder.java
│   │   │           ├── Board.java
│   │   │           ├── Dice.java
│   │   │           ├── Player.java
│   │   │           ├── PlayerState.java
│   │   │           └── MoveOutcome.java
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

- **Java 21**: Core programming language with Records support
- **Spring Boot 4.0.0**: Application framework
- **Maven**: Build and dependency management
- **Lombok**: Reduce boilerplate code (if used)

## 📊 Sample Output

```
Player Player_1 rolled a 3 and got outcome NONE
Player Player_2 rolled a 6 and got outcome START
Player Player_1 rolled a 2 and got outcome NONE
Player Player_2 rolled a 4 and got outcome NEXT_MOVE
Player Player_1 rolled a 6 and got outcome START
Player Player_2 rolled a 3 and got outcome NEXT_MOVE
Player Player_1 rolled a 5 and got outcome NEXT_MOVE
Player Player_2 rolled a 6 and got outcome NEXT_MOVE
Yay! Player Player_2 climbed a ladder from 15 to 21
Player Player_1 rolled a 4 and got outcome NEXT_MOVE
Oops! Player Player_1 got bitten by a snake from 11 to 5
Player Player_2 rolled a 5 and got outcome NEXT_MOVE
...
Player Player_2 won the game
```

## 📄 License

This project is a demonstration of Low Level Design concepts.

## 👤 Author

Amardeep Mittal

## 🙏 Acknowledgments

- Inspired by Udit Agarwal's Low Level Design lectures
- Classic Snakes and Ladders board game
