import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame{
    
    Panel_Menu menuPanel;
    Panel_Offline offlinePanel;
    Panel_Online onlinePanel;
    ImageIcon icon = new ImageIcon("icon.png");


    public void GoToMainMenu(){
        App.online.SetConnect(false);
        menuPanel.setVisible(true);
        offlinePanel.setVisible(false);
        onlinePanel.setVisible(false);
    }
    public void StartGame(){
        System.out.println("StartGame");
        menuPanel.setVisible(false);
        offlinePanel.setVisible(true);
        onlinePanel.setVisible(false);
    }

    public void GoOnline(){
        menuPanel.setVisible(false);
        offlinePanel.setVisible(false);
        App.online.SetConnect(true);
        onlinePanel.SetMyGrid(offlinePanel.grid, offlinePanel.gridPanel);
        onlinePanel.setVisible(true);
    }

    public void Restart(){
        offlinePanel.setVisible(false);
        onlinePanel.setVisible(false);
        offlinePanel = new Panel_Offline();
        onlinePanel = new Panel_Online();
        this.add(offlinePanel);
        this.add(onlinePanel);
        GoToMainMenu();
    }

    GUI(){
        menuPanel = new Panel_Menu();
        menuPanel.setMenuText("Battle Ship");
        offlinePanel = new Panel_Offline();
        onlinePanel = new Panel_Online();
        this.add(offlinePanel);
        this.add(onlinePanel);
        this.add(menuPanel);
        GoToMainMenu();
        this.setSize(1280, 720);
        this.setTitle("My Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.setIconImage(icon.getImage());
        this.setVisible(true);
    }
}
