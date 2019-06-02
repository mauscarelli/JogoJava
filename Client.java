import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.io.*;
import java.util.*;

public class Client extends JFrame{
    private int width;
    private int height;
    private Container contentPane;
    private ClientSideConnection csc;
    private int playerId;
    private int opponent;
    private Player jogador;

    Client(int w,int h){
        width = w;
        height = h;
        this.setSize(width, height);
        this.setTitle("Civilization XII");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //contentPane.setLayout(new FlowLayout());
        //add(contentPane);
        //pack();
        this.setVisible(true);
    }

    public void connectToServer(){
        csc = new ClientSideConnection();
    }

    private class ClientSideConnection{
        private Socket socket;
        private int port = 44455;
        private DataInputStream dataIn;
        private DataOutputStream dataOut;

        public ClientSideConnection(){
            System.out.println("---Client---");
            try{
                socket = new Socket("localhost", port);
                dataIn = new DataInputStream(socket.getInputStream());
                dataOut = new DataOutputStream(socket.getOutputStream());
                playerId = dataIn.readInt();
                System.out.println("Id do jogador: " + playerId);
                jogador = new Player(playerId);
                System.out.println("Vida do Centro: " + jogador.getCenterHealth());
            }catch(IOException e){
                System.out.println("Erro de IO: " + e);
            }
        }
    }

    public static void main(String[] args){
        Client c = new Client(500, 300);
        c.connectToServer();
    }
}