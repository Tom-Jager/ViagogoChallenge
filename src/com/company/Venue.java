package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.StrictMath.abs;

public class Venue extends JLabel {
    private String ident;
    private int xCoord;
    private int yCoord;
    private List<Ticket> tickets;


    void setIdent(String id) {
        this.ident = String.valueOf(id);
    }


    int getDist(int x, int y){
        int dist = abs(xCoord - x) + abs(yCoord - y);
        return dist;
    }

    String getCheapestPrice(){
        //finds the cheapest ticket and outputs it as a correct string
        if(tickets.size()==0){
            return ("No tickets found");
        }
        Ticket minTicket = Collections.min(tickets);
        double minPrice = minTicket.getPrice();
        return "$".concat(String.valueOf(minPrice));
    }
//initialises the venue
    Venue(int x, int y, BufferedImage image){
        ident = "no ident";
        this.xCoord = x;
        this.yCoord = y;
        this.tickets = new ArrayList<>();
        //sets image appropriately
        this.setIcon(new ImageIcon(image));
        //adds a border to each element
        this.setBorder( createBorder());
    }

    private Border createBorder() {
        Border border = BorderFactory.createMatteBorder(3, 3, 3, 3, Color.WHITE);
        return border;
    }

    void addTicket(double price) {
        tickets.add(new Ticket(price));
    }


    public String getIdent() {
        return ident;
    }
}
