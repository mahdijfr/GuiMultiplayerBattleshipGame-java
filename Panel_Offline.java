import javax.swing.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Component;
import java.awt.GridLayout;
import java.lang.reflect.Field;

public class Panel_Offline extends JPanel{
    public Tile[][] grid;
    public JPanel gridPanel;

    JButton selectedShipButton = null;
    JButton nextButton;
    JButton directionButton;
    JPanel shipSelectionPanel;
    JLabel selectedShipLable;
    int length = 0;
    int placedShips = 0;
    boolean horizontal;

    String shipkind;


    boolean PlaceShip(Vector v){
        if(length == 0)
            return false;

        // Generating ship points
        Vector[] shipPoints = new Vector[length];
        for(int i = 0; i < length; i++){
            if(horizontal){
                shipPoints[i] = new Vector(v.first + i, v.second);
            }else{
                shipPoints[i] = new Vector(v.first, v.second + i);
            }
        }

        // Check for okay
        for(Vector p : shipPoints){
            if(!(p.first < 10 && p.second < 10)){
                return false;
            }
            else if(!grid[p.first][p.second].isWater()){
                return false;
            }
        }
        
        // Place ships
        for (int i = 0; i < length; i++) {
            grid[shipPoints[i].first][shipPoints[i].second].setIcon(new ImageIcon(shipkind.concat(".png")));
            grid[shipPoints[i].first][shipPoints[i].second].setWater(false);
            grid[shipPoints[i].first][shipPoints[i].second].shiptype = shipkind;

        }
        shipkind = null;
        // Buttons
        selectedShipButton.setEnabled(false);
        selectedShipButton = null;
        selectedShipLable.setText("");
        length = 0;
        shipkind = null;
        placedShips++;
        nextButton.setEnabled(placedShips >= 5);
        return true;
    }



    Panel_Offline(){
        // Grid Panel
        gridPanel = new JPanel();
        gridPanel.setBounds(20, 55, 600, 600);
        gridPanel.setLayout(new GridLayout(10,10));
        grid = new Tile[10][10];
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                Tile t = new Tile();
                t.setWater(true);
                t.setHit(false);
                t.location = new Vector(i, j);
                t.addActionListener(e -> {
                    PlaceShip(t.location);
                });
                gridPanel.add(t);
                grid[i][j] = t;
            }
        }

        // Ship lable
        selectedShipLable = new JLabel();
        selectedShipLable.setBounds(720, 180, 360, 132);
        selectedShipLable.setHorizontalAlignment(JLabel.CENTER);
        selectedShipLable.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
        selectedShipLable.setBackground(Color.PINK);
        selectedShipLable.setOpaque(true);

        // Ship Select Buttons
        shipSelectionPanel = new JPanel();
        shipSelectionPanel.setBounds(720, 55, 507, 100);
        shipSelectionPanel.setLayout(new GridLayout(1,5, 10, 0));
        int[] shipLengths = {5, 4, 3, 3, 2};
        String[] names = {"Cruiser", "Battleship", "Carrier", "Submarine", "Destroyer"};
        for(int i = 0; i < 5; i++){
            int l = shipLengths[i];
            String name = names[i];

            JButton b = new JButton(name);
            b.addActionListener(e -> {
                length = l;
                shipkind = name;
                selectedShipButton = b;
                selectedShipLable.setText("<html>Selected: " + name + "<br/>Length: " + l + "</html>");
            });
            shipSelectionPanel.add(b);
            
        }

        // Vertical Horzontal button
        directionButton = new JButton("Horizontal");
        directionButton.setBounds(1094, 180, 132, 132);
        directionButton.addActionListener(e -> {
            if(horizontal){
                directionButton.setText("Horizontal");
                horizontal = false;
            }else{
                directionButton.setText("Vertical");
                horizontal = true;
            }
        });

        // Button Panel
        JPanel buttomPanel = new JPanel();
        buttomPanel.setBounds(720, 323, 506, 332);
        buttomPanel.setLayout(new GridLayout(2,1,0,10));
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> {
            // Clear
            for(Component c: shipSelectionPanel.getComponents())
                c.setEnabled(true);
            for(Tile[] tt: grid) {
                for (Tile t : tt) {
                    t.setWater(true);
                    t.setIcon(null);
                }
            }
            nextButton.setEnabled(false);
            selectedShipLable.setText("");
            selectedShipButton = null;
            placedShips = 0;
            length = 0;
        });
        nextButton = new JButton("NEXT");
        nextButton.setEnabled(false);
        nextButton.addActionListener(e -> {
            // Done
            for(Tile[] tt: grid)
                for(Tile t: tt)
                    t.setEnabled(false);
            App.gameGUI.GoOnline();
        });
        buttomPanel.add(clearButton);
        buttomPanel.add(nextButton);


        this.add(gridPanel);
        this.add(shipSelectionPanel);
        this.add(selectedShipLable);
        this.add(directionButton);
        this.add(buttomPanel);
        this.setBounds(0,0,1280,720);
        this.setLayout(null);
    }
}