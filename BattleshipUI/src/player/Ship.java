/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package player;

/**
 * Class SHIP
 *
 * @author eleni
 * @version 1.0
 */
public class Ship {

    private int id;
    private String name;
    private int length;
    private int shoot_points;
    private int sink_points;
    private int count_hits;
    private boolean issank;

    /**
     * creates ship info
     *
     * @param id ship identity number
     * @param name ship name
     * @param length ship length
     * @param shoot_points points earned if ship is shoot
     * @param sink_points points earned if ship is sank
     */
    public Ship(int id, String name, int length, int shoot_points, int sink_points) {
        this.count_hits = 0;
        this.issank = false;
        this.id = id;
        this.name = name;
        this.length = length;
        this.shoot_points = shoot_points;
        this.sink_points = sink_points;

    }

    /**
     * returns points of Hit, checks if sank and updates
     *
     * @return points of Hit
     */
    public int Hit() {
        int Points;
        if (issank == true) {
            System.out.println("Already sank!");
            return -1;
        } else {
            ++this.count_hits;
            Points = this.shoot_points;
            if (this.count_hits == this.length) {
                Points = Points + this.sink_points;
                this.issank = true;
            }
        }
        return Points;
    }

    /**
     *
     * @return id of ship
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return name of ship
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return length of ship
     */
    public int getLength() {
        return length;
    }

    /**
     *
     * @return shoot points of ship
     */
    public int getShoot_points() {
        return shoot_points;
    }

    /**
     *
     * @return sink points of ship
     */
    public int getSink_points() {
        return sink_points;
    }

    /**
     *
     * @return true if ship is sank, false if it is not
     */
    public boolean getIssank() {
        return issank;
    }
}
