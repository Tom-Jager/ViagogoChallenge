package com.company;

import java.util.Collection;
import java.util.Iterator;

public class Ticket implements Comparable<Ticket>{
    private double price;
//Allows lists of tickets to be sorted by price
    Ticket(double price){
        this.price = price;
    }


    double getPrice() {
        return price;
    }

    @Override
    public int compareTo(Ticket ticket) {
        int val = 0;

        if (this.price < ticket.getPrice()){
            val = -1;
        }
        else if(this.price == ticket.getPrice()){
            val = 0;
        }
        else{
            val = 1;
        }
        return val;
    }
}
