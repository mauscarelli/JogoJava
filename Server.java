import java.net.*;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class Server {

    private ServerSocket ss;
    private int numPlayers;
    private int port = 44455;
    private ServerSideConnection player1;
    private ServerSideConnection player2;

    public Server() {
        System.out.println("--- Abrindo Servidor ---");
        numPlayers = 0;
        try {
            ss = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Erro ao abrir servidor: " + e);
            System.exit(1);
        }
    }

    private class ServerSideConnection implements Runnable {

        private Socket socket;
        private Scanner sc;
        private PrintStream ps;
        private int playerID;
        private int playerIndice;
        private int opponentID;
        private Player p;

        public ServerSideConnection(Socket s, int id) {
            socket = s;
            playerID = id;
            p = new Player(playerID);
            playerIndice = id;
            try {
                sc = new Scanner(socket.getInputStream());
                ps = new PrintStream(socket.getOutputStream());
            } catch (IOException e) {
                System.out.println("Erro de IO: " + e);
            }
        }

        public void run() {
            try {
                String s;
                ps.println(playerID);
                while (true) {
                    p.tickProd();
                    if(p.getCenterHealth() == 0){
                        ps.println("derrota");
                        break;
                    }
                    if (playerIndice == 1) {
                        if (player2.p.getCenterHealth() == 0) {
                            ps.println("vitoria");
                            break;
                        }
                        player2.p.tickAttack(p.getTroopAttack());
                    } else if (playerIndice == 2) {
                        if (player1.p.getCenterHealth() == 0) {
                            ps.println("vitoria");
                            break;
                        }
                        player1.p.tickAttack(p.getTroopAttack());
                    }
                    s = sc.nextLine();
                    if (s.equals("addCitizen"))
                        p.addCitizen();
                    else if (s.equals("addSoldier"))
                        p.addSoldier();
                    else if (s.equals("addCitizenGold"))
                        p.addCitizenGold();
                    else if (s.equals("addCitizenFood"))
                        p.addCitizenFood();
                    else if (s.equals("removeCitizenFood"))
                        p.removeCitizenFood();
                    else if (s.equals("removeCitizenGold"))
                        p.removeCitizenGold();
                }
                ps.close();
                sc.close();
                socket.close();
                System.out.println("Finalizando conexao #"+playerID);
            } catch (IOException e) {
                System.out.println("Erro de IO: " + e);
            }
        }

    }

    public void acceptConections() {
        try {
            System.out.println("Esperando conexoes...");
            while (numPlayers < 2) {
                Socket s = ss.accept();
                numPlayers++;
                System.out.println("Jogador #" + numPlayers + " conectou.");
                ServerSideConnection ssc = new ServerSideConnection(s, numPlayers);
                if (numPlayers == 1) {
                    player1 = ssc;
                } else {
                    player2 = ssc;
                    // Thread t = new Thread(player1);
                    // t.start();
                    new Thread(player1).start();
                    new Thread(player2).start();
                }
            }
            System.out.println("Temos 2 jogadores no momento. Nao aceitando mais conexoes.");
        } catch (IOException e) {
            System.out.println("Erro ao aceitar conexoes");
        }
    }

    public static void main(String[] args) {
        Server sv = new Server();
        sv.acceptConections();
    }

}