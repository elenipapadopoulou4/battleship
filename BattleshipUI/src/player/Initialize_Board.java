/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package player;
//INITIALIZATION METHODS ALL HERE
//THIS IS CALLED BEFORE THE START OF THE GAME

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


/**
 *
 * @author eleni
 */
public class Initialize_Board {
    //initialization of board --> BLANK CELLS
    public static Cell[][] initBoard() {
        Cell[][] Board = new Cell[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Cell cell = new Cell();
                Board[i][j] = cell;
            }
        }
        return Board;
    }

      public static Cell[][] readFile(String file_name) throws FileNotFoundException, IOException, OversizeException, OverlapTilesException, AdjacentTilesException, InvalidCountException {
        Cell[][] Board = initBoard();
        File file = new File(file_name);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        //pass all the lines
        while (line != null) {
            String[] chars = line.split(",");
            Board = addShiptoBoard(Board, Integer.parseInt(chars[0]), Integer.parseInt(chars[1]), Integer.parseInt(chars[2]), Integer.parseInt(chars[3]));
            line = br.readLine(); //next line
        }
        return Board;
    }
      
    //SHIPS INIT POSITION
    //gives CORRECT readfile args --> Cell object
    public static Cell[][] addShiptoBoard(Cell[][] Board, int ship_id, int row, int column, int orientation) throws OversizeException, OverlapTilesException, AdjacentTilesException, InvalidCountException {
        //invalidCountE
        if (ship_id > 5 || ship_id < 1) {
            throw new InvalidCountException();
        }
        Ship ship = createShip(ship_id);
        //vertically
        if (orientation == 2) {
            //oversizeE
            if ((row + ship.getLength()) > 10) {
                throw new OversizeException();
            }
            //overlapTilesE
            for (int i = row; i < row+ ship.getLength(); i++) {
                if (Board[i][column].isIsblank() != true) {
                    throw new OverlapTilesException();
                }
            }
            //AdjacentTilesE
            for (int i = row; i < row+ship.getLength(); i++) {
                if (column != 0) {
                    if (Board[i][column - 1].isIsblank() != true) {
                        throw new AdjacentTilesException();
                    }
                }
            }
            for (int i = row; i < row+ship.getLength(); i++) {
                if (column != 9) {
                    if (Board[i][column + 1].isIsblank() != true) {
                        throw new AdjacentTilesException();
                    }
                }
            }
            if (row != 0) {
                if (Board[row - 1][column].isIsblank() != true) {
                    throw new AdjacentTilesException();
                }
            }
            if (row + ship.getLength()!= 10) {
                if (Board[row + ship.getLength()][column].isIsblank() != true) {
                    throw new AdjacentTilesException();
                }
            }
        } //horizontall
        else {
            //oversizeE
            if ((column + ship.getLength()) > 10) {
                throw new OversizeException();
            }
            //overlapTilesE
            for (int j = column; j < column+ship.getLength(); j++) {
                if (Board[row][j].isIsblank() != true) {
                    throw new OverlapTilesException();
                }
            }
            //AdjacentTilesE
            for (int j = column; j < column+ship.getLength(); j++) {
                if (row != 0) {
                    if (Board[row - 1][j].isIsblank() != true) {
                        throw new AdjacentTilesException();
                    }
                }
            }
            for (int j = column; j < column+ship.getLength(); j++) {
                if (row != 9) {
                    if (Board[row + 1][j].isIsblank() != true) {
                        throw new AdjacentTilesException();
                    }
                }
            }
            if (column != 0) {
                if (Board[row][column - 1].isIsblank() != true) {
                    throw new AdjacentTilesException();
                }
            }
            if (column+ ship.getLength() != 10) {
                if (Board[row][column + ship.getLength()].isIsblank() != true) {
                    throw new AdjacentTilesException();
                }
            }
        }

        //everythink OK
        if (orientation == 1) {
            int i = row;
            for (int j = column; j < column+ship.getLength(); j++) {
                Board[i][j].setIsblank(false);
                Board[i][j].setShip(ship);
            }
        } else {
            int j = column;
            for (int i = row; i < row+ship.getLength(); i++) {
                Board[i][j].setIsblank(false);
                Board[i][j].setShip(ship);
            }
        }
        return Board;
    }

    // INFO ABOUT SHIP
    //gives ship id --> Ship object
    public static Ship createShip(int id) {
        Ship ship;
        switch (id) {
            case 1: {
                ship = new Ship(1, "Carrier", 5, 350, 1000);
                return ship;
            }
            case 2: {
                ship = new Ship(2, "Battleship", 4, 250, 500);
                return ship;
            }
            case 3: {
                ship = new Ship(3, "Cruiser", 3, 100, 250);
                return ship;
            }
            case 4: {
                ship = new Ship(4, "Submarine", 3, 100, 0);
                return ship;
            }
            case 5: {
                ship = new Ship(5, "Destroyer", 2, 50, 0);
                return ship;
            }
            default: {
                ship = null;
                return ship;
            }
        }
    }
}
