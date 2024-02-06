import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Online {
    public boolean isServer;

    private int port = 5000;
    private String address = "127.0.0.1";
    private boolean reciving;
    public boolean connected;

    Socket socket = null;
    ServerSocket server = null;
    DataInputStream socketIn = null;
    DataOutputStream socketOut = null;

    public void Send(String s){
        try{
            socketOut.writeUTF(s);
        }catch(IOException i){

            System.out.println(i);
        }
    }
    public String Recive(){
        try{
            return socketIn.readUTF();
        }catch(IOException i){
            System.out.println(i);
        }
        return null;
    }
    public void SetConnect(boolean b){
        if(b){
            try{
                // Client
                socket = new Socket(address, port);
                System.out.println("Connection initiated");
                isServer = false;
                reciving = false;
                connected = true;
            }catch(Exception e){
                try{
                    // Server
                    isServer = true;
                    server = new ServerSocket(port);
                    System.out.println("Server initiated");
                    socket = server.accept();
                    System.out.println("Client Entered.");
                    connected = true;
                    reciving = true;
                }catch(IOException i){
                    System.out.println(i);
                }
            }
    
            try{
                // setting up Socket input and Socket output
                socketIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                socketOut = new DataOutputStream(socket.getOutputStream());
            }catch(IOException i){
                System.out.println(i);
            }
        
        }else{
            // Disconnect
            try {
                if(socket == null)
                    return;
                socket.close();
                System.out.println("Disconnected");
                if(isServer){
                    System.out.println("Server closed");
                    server.close();
                }
            } catch (IOException e) {
                System.out.println("Connection Error");
            }
            connected = false;
        }
    }
}
