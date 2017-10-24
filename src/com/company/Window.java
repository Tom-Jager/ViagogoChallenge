package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JFrame implements ActionListener {
    //map class for
    private Map map;
    private JPanel currMap;
    private JTextField xCoord;
    private JTextField yCoord;
    private JTextArea outputArea;

    Window(){
        //create frame and construct Map class
        setTitle("Viagogo Coding Challenge");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        map = new Map();
        //create items inside content pane
        setContentPane(createContentPane());
        pack();
        show();
        setResizable(false);
    }

    private Container createContentPane() {
        //Create the content pane to contain the map panel above the i/o panel
        JPanel pane = new JPanel();
        pane.setLayout(new BorderLayout());
        pane.add(createMapPanel(), BorderLayout.NORTH);
        pane.add(createInputOutput(), BorderLayout.CENTER);
        return pane;
    }

    private Component createInputOutput() {
        //set the i/o panel to place components horizontally
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        //place input section before output section
        panel.add(createInput());
        panel.add(Box.createGlue());
        panel.add(createOutput());
        return panel;
    }

    private JPanel createOutput() {
        //set the output area to be an un-editable text area
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        outputArea = new JTextArea(10, 30);
        outputArea.setEditable(false);
        outputArea.append("Please enter your coordinates\n");
        //add it as a scroll pane to prevent unnecessary box growth
        JScrollPane scrollPane = new JScrollPane(outputArea);

        panel.add(scrollPane,BorderLayout.CENTER);
        panel.show();
        return panel;
    }

    private Component createInput() {
        //create the input panel as two text fields and a submit button
        JPanel panel = new JPanel();
        panel.add(new JLabel("Enter your location"));
        panel.add(Box.createRigidArea(new Dimension(0,5)));
        panel.add(createTextFields());
        panel.add(Box.createRigidArea(new Dimension(0,5)));
        panel.add(createFindButton());
        panel.add(Box.createGlue());
        panel.show();
        return panel;
    }

    private JButton createFindButton() {
        //create a button to find the events on button click
        JButton button = new JButton("Find Events");
        button.setActionCommand("find_event");
        button.addActionListener(this);
        return button;
    }

    private JPanel createTextFields() {
        //create two small text fields
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        xCoord = new JTextField("x");
        xCoord.setColumns(3);
        yCoord = new JTextField("y");
        yCoord.setColumns(3);

        panel.add(xCoord);
        panel.add(new JLabel(","));
        panel.add(yCoord);
        panel.add(Box.createGlue());
        panel.show();
        return panel;
    }

    private JPanel createMapPanel() {
        //generate a new map and add it to the map panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        currMap = map.genNewMap();

        panel.add(currMap);
        panel.add(Box.createGlue());
        //create a generate new map button
        panel.add(createGenMapButton());
        return panel;
    }

    private JButton createGenMapButton() {
        //create a button that fires the gen action when clicked
        JButton button = new JButton("Generate new map");
        button.setActionCommand("gen");
        button.addActionListener(this);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //on press of generate new map button
        if("gen".equals(e.getActionCommand())){
            //redraw the entire frame, use of map.genNewMap ensures a new map is created
            this.getContentPane().removeAll();
            this.getContentPane().add(createContentPane());
            this.revalidate();
            this.repaint();
        }
        else if("find_event".equals(e.getActionCommand())){
            //on press of find events button perform validation on text fields
            String error = performValidation();
            if(error.equals("")){
                //if no error parse the values and find the nearest events
                int x = Integer.parseInt(xCoord.getText());
                int y = Integer.parseInt(yCoord.getText());
                findEvents(x,y);
            }
            else{
                //if error, output the error
                outputArea.append(error);
            }
        }
    }

    private String performValidation() {
        //check the numbers are indeed numbers within the correct range
        String xString = xCoord.getText();
        String yString = yCoord.getText();
        int x;
        int y;
        try{
            x = Integer.parseInt(xString);
            y = Integer.parseInt(yString);
        }
        catch (NumberFormatException e){
            return ("Coordinate boxes did not contain a number\n");
        }
        if(x < -10 | x > 10 ){
            return ("X coordinate was not in range\n");
        }
        if(y < -10 | y > 10 ){
            return ("Y coordinate was not in range\n");
        }
        return("");
    }

    private void findEvents(int x, int y) {
        //output the location your finding the nearby events for
        String string1 = "Current events near ";
        String loc = "(".concat(String.valueOf(x)).concat(",").concat(String.valueOf(y).concat(")"));
        String xString = String.valueOf(x);
        String yString = String.valueOf(y);
        outputArea.append(string1.concat(loc).concat(":\n"));
        //Normalise the given coordinates relative to the placement of the icons
        int yNorm = 10-y;
        int xNorm = x+10;
        //get the correct venue from the map
        Venue ven = (Venue) currMap.getComponent(yNorm*21 + xNorm);
        //find the closest events to this venue
        Pair[] distPairs = map.findClosestEvents(x, y);
        //for the closest 5 venues or less if there are not even 5 events
        for(int i = 0;i < distPairs.length && i < 5; i++){
            //output the distances and cheapest prices
            Venue event = distPairs[i].getEvent();
            outputArea.append("Event ".concat(event.getIdent()).concat(" - "));
            outputArea.append(event.getCheapestPrice().concat(", Distance ").concat(String.valueOf(distPairs[i].getDist())));
            outputArea.append("\n");
        }
    }
}
