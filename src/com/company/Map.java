package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
//class holding the map, generating it and finding nearby events in it, also contains preloaded images of field and concert
public class Map {
    private Random rand;
    private ArrayList<Venue> eventList;
    private BufferedImage concert;
    private BufferedImage field;

    Map() throws IOException {
        rand = new Random();
        concert = ImageIO.read(getClass().getResource("/resources/concert.jpg"));
        field = ImageIO.read(getClass().getResource("/resources/field.jpg"));
    }

    JPanel genNewMap(){
        //generates and seeds a new map
        JPanel newMap = new JPanel(new GridLayout(21,21));
        //counter for determining the identifier of each event
        int identCount = 1;
        eventList = new ArrayList<Venue>();
        Venue v;
        Boolean isEvent;
        for (int i = 10; i>-11; i--){
            for (int j = -10; j<11; j++){
                //randomly determines if the venue has an event on and creates is appropriately
                isEvent = isEvent();
                if (isEvent){
                    v = new Venue(j, i, concert);
                    //adds a random number of tickets if the venue has an event
                    v = addTickets(v);
                    //generates identifier for the event
                    String ident = generateIdent(identCount);
                    identCount++;
                    v.setIdent(ident);
                    eventList.add(v);
                }
                else{
                    v = new Venue(j, i, field);
                }
                newMap.add(v);
            }
        }
        return newMap;
    }

    private String generateIdent(int identCount) {
        //ensures the identifier is outputted correctly
        if(identCount<10){
            return("00".concat(String.valueOf(identCount)));
        }
        else if(identCount<100){
            return("0".concat(String.valueOf(identCount)));
        }
        else{
            return(String.valueOf(identCount));
        }
    }

    private Venue addTickets(Venue v) {
        //adds a random number of tickets between 0 and 10
        int num = rand.nextInt(11);
        double price;
        //generatesd a random price for the tickets
        for(int i = 0; i < num;i++){
            price = rand.nextDouble()*100 + 20.00;
            price = Math.round(price*100.00)/100.00;
            v.addTicket(price);
        }
        return v;
    }

//generates a 1/63 chance that there will be an event at the coordinate
    private boolean isEvent() {
        int num = rand.nextInt(40);
        if(num==0) return true;
        return false;
    }

    Pair[] findClosestEvents(int x,int y){
        //creates a list of (distance, event) pairs for each event
        Pair[] distPairs = new Pair[eventList.size()];
        for (int i = 0; i < eventList.size(); i++)
             {
            distPairs[i] = new Pair(eventList.get(i),eventList.get(i).getDist(x, y));
        }
        //sorts the list of pairs
        Arrays.sort(distPairs);
        return distPairs;
    }

}
