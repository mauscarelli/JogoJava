import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.io.*;
import java.util.*;

public class Client extends JFrame {
    private int width;
    private int height;
    private Container contentPane;
    private ClientSideConnection csc;
    private int playerId;
    private int opponent;
    private Player jogador;

    Client(int w, int h) {
        width = w;
        height = h;
        this.setSize(width, height);
        this.setTitle("Civilization XII");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // contentPane.setLayout(new FlowLayout());
        // add(contentPane);
        // pack();
        this.setVisible(true);
    }

    public void connectToServer() {
        csc = new ClientSideConnection();
    }

    private class ClientSideConnection {
        private Socket socket;
        private int port = 44455;
        private Scanner sc;
        private PrintStream ps;

        public ClientSideConnection() {
            System.out.println("---Client---");
            try {
                socket = new Socket("localhost", port);
                sc = new Scanner(socket.getInputStream());
                ps = new PrintStream(socket.getOutputStream());
                playerId = sc.nextInt();
                System.out.println("Id do jogador: " + playerId);
                jogador = new Player(playerId);
                System.out.println("Vida do Centro: " + jogador.getCenterHealth());
                new Thread(t).start();
            } catch (IOException e) {
                System.out.println("Erro de IO: " + e);
            }
        }

        private Runnable t = new Runnable() {

            @Override
            public void run() {
                try {
                    System.out.println("oi");
                    ps.println("texto");
                    ps.println(10);
                    while (true) {
                        System.out.println("eae mens");
                        String s = sc.nextLine();
                        System.out.println(s);
                        if (s.equals("vitoria")) {
                            System.out.println("Vitoria!");
                            break;
                        } else if (s.equals("derrota")) {
                            System.out.println("Derrota.");
                            break;
                        }

                    }
                    ps.close();
                    sc.close();
                    socket.close();
                } catch (IOException ex) {
                    System.out.println("Erro de IO: " + ex);
                }
            }
        };

    }

    public static void main(String[] args) {
        Client c = new Client(500, 300);
        c.connectToServer();
    }
}