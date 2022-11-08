/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package player;

/**
 *
 * @author eleni
 */
public class Player {

    private Cell[][] Board;  //board of the oppoenet 

    private int Points;      //points of player
    private int unsank_ships; //unsank_ships of opponent
    private float hit_perc;   //hit percentage of opponent= times_hit/times_played 
    private int times_played;
    private int times_hit;
    private boolean played_first;     //active== true if turn to play

    private int shoot_row, shoot_column;        //enemy's selected coordinates
    private int last_hit_row = -1, last_hit_column = -1;     //enemy's prev hit, initialize with -1

    //setters&getters
    public Cell[][] getBoard() {
        return Board;
    }

    public int getPoints() {
        return Points;
    }

    public float getHit_perc() {
        return hit_perc;
    }

    public boolean isPlayed_first() {
        return played_first;
    }

    public void setPlayed_first(boolean played_first) {
        this.played_first = played_first;
    }

    public int getShoot_row() {
        return shoot_row;
    }

    public int getShoot_column() {
        return shoot_column;
    }

    public int getLast_hit_row() {
        return last_hit_row;
    }

    public int getLast_hit_column() {
        return last_hit_column;
    }

    //constructor
    public Player(Cell[][] Board) {
        //int id,row,column,or;
        this.Board = Board;
        this.Points = 0;
        this.unsank_ships = 5;
        this.hit_perc = 0;
        this.times_hit = 0;
        this.times_played = 0;
        this.played_first = false;
    }
    

    public int getUnsank_ships() {
        return unsank_ships;
    }

    public void hitcell(int row, int column) throws alreadyshotException {
        Cell cell = this.Board[row][column];
        this.times_played++;
        
        if (cell.isIsshoot() == true) {
            throw new alreadyshotException();
        } else {
            cell.setIsshoot(true);
        }
        if (cell.isIsblank() == true) {
            System.out.println("MISSED");
        } else {
            System.out.println("HIT" + row + " ," + column);
            this.times_hit++;
            Ship cell_ship = cell.getShip();
            this.Points = this.Points + cell_ship.Hit();
            //check at each hit if a ship has sank 
            if (cell_ship.getIssank() == true) {
                this.unsank_ships = this.unsank_ships - 1;
            }
        }
        this.hit_perc = 100 * (float) this.times_hit / this.times_played;
        this.hit_perc = (float) (Math.round(this.hit_perc * 100.0) / 100.0);

    }

    public void enemyPlays() {

        int wanted = -1;

        //coordinates for 4 possible cells 
        int down_row = 0, down_column = 0;
        int up_row = 0, up_column = 0;
        int left_row = 0, left_column = 0;
        int right_row = 0, right_column = 0;
        // value of each cell
        int left = -1, right = -1, up = -1, down = -1; //this means not suitable cell

        //last shoot was hit --> check if shipped sank
        if (!this.Board[shoot_row][shoot_column].isIsblank()) {
            if (this.Board[shoot_row][shoot_column].getShip().getIssank()) {
                last_hit_row = -1;
                last_hit_column = -1;
            }
        }
        //last shoot was a hit  
        if (last_hit_row != -1 && last_hit_column != -1) {
            //create 4 possible cells left,right,up,down
            if (this.last_hit_column != 0) {
                left_row = last_hit_row;
                left_column = last_hit_column - 1;
                left = hitHistory(left_row, left_column);
            }
            if (this.last_hit_column != 9) {
                right_row = last_hit_row;
                right_column = last_hit_column + 1;
                right = hitHistory(right_row, right_column);
            }
            if (this.last_hit_row != 0) {
                up_row = last_hit_row - 1;
                up_column = last_hit_column;
                up = hitHistory(up_row, up_column);
            }
            if (this.last_hit_row != 9) {
                down_row = last_hit_row + 1;
                down_column = last_hit_column;
                down = hitHistory(down_row, down_column);
            }
            //no hit in 4 possible cells --> take first no shoot cell out of 4(value==0)
            if (firstX(left, right, up, down, 2) == 0) {
                wanted = firstX(left, right, up, down, 0);
            } //hit in 4 possible cells 
            else {
                wanted = firstX(left, right, up, down, 2); //take the hit cell (value==2)
                if (wanted == 1) { // the cell on the left is hit --> horizontal ship
                    while (hitHistory(left_row, left_column) == 2 && left_column != 9) //continue TO THE RIGHT until no hit or margin
                    {
                        ++left_column;
                    }
                    if (!(hitHistory(left_row, left_column) == 0)) { //if 0 return
                        if (hitHistory(left_row, left_column) == 1 || left_column == 9) {//if 1 is found(no hit)or margin -->change direction LEFT
                            --left_column;
                            while (hitHistory(left_row, left_column) == 2 && left_column != 0) //continue TO THE LEFT until no shoot or margin
                            {
                                --left_column;
                            }
                        }
                    }
                    //0 is found(no shoot) --> shoot_row, shoot_column are now updated
                }
                if (wanted == 2) { // the cell on the right is hit --> horizontal ship
                    while (hitHistory(right_row, right_column) == 2 && right_column != 0) {
                        --right_column;
                    }
                    if (!(hitHistory(right_row, right_column) == 0)) {
                        if (hitHistory(right_row, right_column) == 1 || right_column == 0) {
                            ++right_column;
                            while (hitHistory(right_row, right_column) == 2 && right_column != 9) {
                                ++right_column;
                            }
                        }
                    }
                }
                if (wanted == 3) { // the cell up is hit --> vertical ship
                    while (hitHistory(up_row, up_column) == 2 && up_row != 0) {
                        --up_row;
                    }
                    if (!(hitHistory(up_row, up_column) == 0)) {
                        if (hitHistory(up_row, up_column) == 1 || up_row == 0) {
                            ++up_row;
                            while (hitHistory(up_row, up_column) == 2 && up_row != 9) {
                                ++up_row;
                            }
                        }
                    }
                }
                if (wanted == 4) { // the cell down is hit --> vertical ship
                    while (hitHistory(down_row, down_column) == 2 && down_row != 9) {
                        ++down_row;
                    }
                    if (!(hitHistory(down_row, down_column) == 0)) {
                        if (hitHistory(down_row, down_column) == 1 || down_row == 9) {
                            --down_row;
                            while (hitHistory(down_row, down_column) == 2 && down_row != 0) {
                                --down_row;
                            }
                        }
                    }
                }
            }
            //return shoot coordinated
            if (wanted == 1) {
                shoot_row = left_row;
                shoot_column = left_column;
            }
            if (wanted == 2) {
                shoot_row = right_row;
                shoot_column = right_column;
            }
            if (wanted == 3) {
                shoot_row = up_row;
                shoot_column = up_column;
            }
            if (wanted == 4) {
                shoot_row = down_row;
                shoot_column = down_column;
            }
            //update last cell --> info about Isblank is given because the cell is to be played
            if (!this.Board[shoot_row][shoot_column].isIsblank()) {
                last_hit_row = shoot_row;
                last_hit_column = shoot_column;
            }

        } else {
            //last was not hit --> randomly
            System.out.println("random");
            int i = (int) (10 * Math.random());
            int j = (int) (10 * Math.random());
            while (this.Board[i][j].isIsshoot()) {
                i = (int) (10 * Math.random());
                j = (int) (10 * Math.random());
            }
            System.out.println(i + " " + j);
            shoot_row = i;
            shoot_column = j;
            //update last cell --> info about Isblank is given because the cell is to be played
            if (!this.Board[shoot_row][shoot_column].isIsblank()) {
                last_hit_row = shoot_row;
                last_hit_column = shoot_column;
            }
        }

    }

    public int hitHistory(int row, int column) {
        // returns the state of each possible block
        //show info about Isblank only when cell Isshoot
        //0 is not shoot
        //1 is shoot but no hit ship
        //2 is shoot and hit 
        if (this.Board[row][column].isIsshoot() && this.Board[row][column].isIsblank()) {
            return 1;
        } else if (this.Board[row][column].isIsshoot() && !this.Board[row][column].isIsblank()) {
            return 2;
        } else {
            return 0;
        }
    }

    public int firstX(int left, int right, int up, int down, int x) {
        //returns 0->none 1->left 2->right 3->up 4->down 
        if (left == x) {
            return 1;
        } else if (right == x) {
            return 2;
        } else if (up == x) {
            return 3;
        } else if (down == x) {
            return 4;
        } else {
            return 0;
        }
    }

}

/**
 * @param args the command line arguments
 */
