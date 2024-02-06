import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Panel_Menu extends JPanel{
    JLabel menuTextLable;

    public void setMenuText(String s){
        menuTextLable.setText(s);
    }

    Panel_Menu(){
        JButton startGameButton = new JButton("Start Game");
        startGameButton.setBounds(490, 475, 300, 200);
        startGameButton.addActionListener(e -> {
            App.gameGUI.StartGame();
        });

        menuTextLable = new JLabel();
        menuTextLable.setBounds(0,0,1280,475);
        menuTextLable.setVerticalAlignment(JLabel.CENTER);
        menuTextLable.setHorizontalAlignment(JLabel.CENTER);
        menuTextLable.setFont(new Font("Comic Sans MS", Font.BOLD, 105));
        
        this.add(menuTextLable);
        this.add(startGameButton);
        this.setBounds(0,0,1280,720);
        this.setLayout(null);
        //this.set;
    }

}