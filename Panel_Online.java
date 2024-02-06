import javax.swing.*;

import java.awt.GridLayout;
import java.lang.reflect.Field;

public class Panel_Online extends JPanel{
    Tile[][] myGrid;
    Tile[][] otherGrid;

    int hitCount = 0;
    Ally AlleyLeft = new Ally();
    Ally AlleyLeft2 = new Ally();
    Class aClass = AlleyLeft.getClass();
    Class bClass = AlleyLeft2.getClass();
    Field field;
    Field field2;
    int aShips;
    int bShips;

    void SetEnabledOtherGrid(boolean b){
        for(Tile[] tt: otherGrid)
            for(Tile t: tt)
                if(!t.isHit())
                    t.setEnabled(b);
        System.out.println("the map must be " + b);
    }

    void SendHit(Vector v){
        System.out.println("Trying to hit: " + v.first + " " + v.second);
        App.online.Send(v.first + "" + v.second);
        String ans = App.online.Recive();
        Tile t = otherGrid[v.first][v.second];
        if(ans.equals("water")){
            t.setWater(true);
            SetEnabledOtherGrid(false);
            GetHit();
        }else if(ans.split("-")[0].equals("ship")){
            // try catch below is to find out if a ship is compeletly destroyed using reflection
            try {
                field2 = bClass.getField(ans.split("-")[1]);
                int allyleft = field2.getInt(AlleyLeft2);
                field2.setInt(AlleyLeft2, allyleft-1);
                bShips = field2.getInt(AlleyLeft2);
            }catch (Exception e){
                System.out.println(e);
                System.out.println(bClass.getFields().toString());
            }

            t.setWater(false);
            hitCount++;
            if(bShips<=0){
                JFrame f=new JFrame();
                JOptionPane.showMessageDialog(f,ans.split("-")[1] + " Destroyed!");
            }
            else if(hitCount >= 17){
                App.gameGUI.menuPanel.setMenuText("You Won!");
                App.gameGUI.Restart();
                return;
            }
            //System.out.println("bruh, we hit the ship,why ARE we FREEZEING?");
        }else{
            //System.out.println("DIIIVAANEEE SHOOODAM AAAAAAAAAAAAAA");
        }
        t.setHit(true);
    }

    boolean CheckForLost(){
        for(Tile[] tt : myGrid)
            for(Tile t : tt)
                if(!t.isWater() && !t.isHit())
                    return false;
        return true;
    }

    void GetHit(){
        System.out.println("we are waiting to get hit");
        Thread t = new Thread(){
            public void run(){
                String req = App.online.Recive();
                Vector v = new Vector(req.charAt(0) - '0', req.charAt(1) - '0');
                System.out.println("we got hit req: " + v.first + " " + v.second);
                Tile t = myGrid[v.first][v.second];
                t.setHit(true);
                if(t.isWater()){
                    App.online.Send("water");
                    SetEnabledOtherGrid(true);
                }
                else{
                     String g = t.shiptype;
                    App.online.Send("ship-" + g);
                    // try catch below is to find out if a ship is compeletly destroyed using reflection
                    try {
                        field = aClass.getField(g);
                        int allyleft = field.getInt(AlleyLeft);
                        field.setInt(AlleyLeft, allyleft-1);
                        aShips = field.getInt(AlleyLeft);
                    }catch (Exception e){
                        System.out.println(e);
                        System.out.println(aClass.getFields().toString());
                    }

                    if(aShips<=0){
                        JFrame f=new JFrame();
                        JOptionPane.showMessageDialog(f,g + " Destroyed!");
                    }
                    if(CheckForLost()){
                        App.gameGUI.menuPanel.setMenuText("you lost.");
                        App.gameGUI.Restart();
                    }else{
                        GetHit();
                    }
                }
            }
        };
        t.start();
    }

    public void SetMyGrid(Tile[][] myg, JPanel myGridPanel){
        myGrid = myg;
        this.add(myGridPanel);
        if(App.online.isServer){
            System.out.println("WE ARE SERVER");
            SetEnabledOtherGrid(false);
            GetHit();
        }
    }

    Panel_Online(){

        // other grid
        JPanel otherGridPanel = new JPanel();
        otherGridPanel.setBounds(640, 55, 600, 600);
        otherGridPanel.setLayout(new GridLayout(10,10));
        otherGrid = new Tile[10][10];
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                Tile t = new Tile();
                t.setWater(true);
                t.setHit(false);
                t.location = new Vector(i, j);
                t.addActionListener(e -> {
                    SendHit(t.location);
                    if(hitCount >= 17){
                        App.gameGUI.menuPanel.setMenuText("You Won!");
                        App.gameGUI.Restart();
                    }
                });
                otherGridPanel.add(t);
                otherGrid[i][j] = t;
            }
        }

        this.add(otherGridPanel);
        this.setLayout(null);
        this.setBounds(0,0,1280,720);
    }
}