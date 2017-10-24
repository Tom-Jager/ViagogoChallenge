package com.company;

public class Pair implements Comparable{
    private int dist;
    private Venue event;

    Pair(Venue event, int dist){
        this.event = event;
        this.dist = dist;
    }

    public Venue getEvent() {
        return event;
    }

    public void setEvent(Venue event) {
        this.event = event;
    }

    public int getDist() {

        return dist;
    }

    public void setDist(int dist) {
        this.dist = dist;
    }

    @Override
    public int compareTo(Object p) {
        if (!(p instanceof Pair))
            throw new ClassCastException("A Pair object expected.");
        int newDist = ((Pair) p).getDist();
        return this.dist - newDist;

    }
}
