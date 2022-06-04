# MazeRunner

MazeRunner is a 2D game in a 10 x 10 grid maze map. The maze map consists of the following items: 

- An entrance to the maze, which is located at the bottom left grid. 

- A player, who is place at the entrance at the beginning of a game. 

- An exit of the maze, which is randomly place in the map. 

- 5 gold coins, each of which is placed at a random grid. 

- Some traps, each of which is placed at a random grid. Once in a trap, the player needs to pay 1 coin to pass. If no gold left, the player loses the game. 

- Some apples, each of which is placed at a random grid. The layer has an initial stamina which are consumed by each movement. Picking up an apple can increase the stamina. If no stamina left, the player cannot move and the game ends. 

The goal is to move the player from the entrance to the exit while 1) keeping as many gold coins as possible, and 2) maintaining non-negative stamina.  
