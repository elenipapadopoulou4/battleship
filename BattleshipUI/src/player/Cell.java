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
public class Cell {
    private boolean isshoot;
    private boolean isblank;
    private Ship ship;
    
    public Cell(){
        this.isshoot = false;
        this.isblank = true;
        this.ship = null; 
     
    }

    public boolean isIsshoot() {
        return isshoot;
    }

    public void setIsshoot(boolean isshoot) {
        this.isshoot = isshoot;
    }

    public boolean isIsblank() {
        return isblank;
    }

    public void setIsblank(boolean isblank) {
        this.isblank = isblank;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }
    
    }

