public class App {
    public static GUI gameGUI;
    public static Online online;

    public static void main(String[] args) throws Exception {
        online = new Online();
        gameGUI = new GUI();
    }
}
