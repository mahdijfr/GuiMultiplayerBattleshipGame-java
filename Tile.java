import java.awt.Color;

import javax.swing.*;

public class Tile extends JButton{
    public Vector location;
    private boolean water;
    private boolean hit;

    public String shiptype;


    Tile(){
        this.setDisabledIcon(this.getIcon());
    }

    void UpdateColor(){
        if(water){
            if(hit){
                this.setBackground(Color.BLUE);
            }else{
                this.setBackground(Color.CYAN);
            }
        }else{
            if(hit){
                this.setIcon(new ImageIcon("bomb.png"));
            }else{
                this.setBackground(Color.gray);
            }
        }
    }

    // Setter and getter for water
    public boolean isWater() {
        return water;
    }
    public void setWater(boolean water) {
        this.water = water;
        UpdateColor();
    }

    // Setter and getter for hit
    public boolean isHit() {
        return hit;
    }
    public void setHit(boolean hit) {
        this.hit = hit;
        if(hit)
            this.setEnabled(false);
        UpdateColor();
    }

}
