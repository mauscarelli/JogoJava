import java.net.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import javax.swing.Timer;

public class Server implements ActionListener {

    private ServerSocket ss;
    private int numPlayers;
    private int port = 44455;
    private Timer timer;
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
        public Boolean continua;

        public ServerSideConnection(Socket s, int id) {
            socket = s;
            playerID = id;
            p = new Player(playerID);
            playerIndice = id;
            continua = true;
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
                while (continua) {
                   /* if(player1.socket.isClosed() || player2.socket.isClosed()){
                        continua = false;
                        if(socket.isClosed() == false)
                            ps.println("vitoria");
                    }*/
                    // p.tickProd();
                    if(sc.hasNextLine())
                        s = sc.nextLine();
                    else
                        continue;
                    System.out.println("Recebendo de #" + playerID + ": " + s);
                    if (s.equals("addCitizen")) {
                        System.out.println("Adicionar cidadao pra #" + playerID);
                        p.addCitizen();
                        System.out.println("Qtd de cidadao #" + playerID + ": " + p.getCitizen());
                    } else if (s.equals("addSoldier")) {
                        System.out.println("Adicionar Soldado pra #" + playerID);
                        p.addSoldier();
                        System.out.println("Jogador #" + playerID + "  Ataque da tropa: " + p.getTroopAttack()
                                + " Vida: " + p.getTroopHealth());
                    } else if (s.equals("addCitizenGold")) {
                        System.out.println("Adicionar Mineiro pra #" + playerID);
                        p.addCitizenGold();
                        System.out.println("Qtd de Mineiro #" + playerID + ": " + p.getCitizenGold());
                    } else if (s.equals("addCitizenFood")) {
                        System.out.println("Adicionar Coletor pra #" + playerID);
                        p.addCitizenFood();
                        System.out.println("Qtd de Coletor #" + playerID + ": " + p.getCitizenFood());
                    } else if (s.equals("removeCitizenFood")) {
                        System.out.println("Remover Coletor pra #" + playerID);
                        p.removeCitizenFood();
                        System.out.println("Qtd de Coletor #" + playerID + ": " + p.getCitizenFood());
                    } else if (s.equals("removeCitizenGold")) {
                        System.out.println("Remover Mineiro pra #" + playerID);
                        p.removeCitizenGold();
                        System.out.println("Qtd de Mineiro #" + playerID + ": " + p.getCitizenGold());
                    }
                }
                sc.close();
                ps.close();
                socket.close();
                System.out.println("Finalizando conexao #" + playerID);
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
                    new Thread(player1).start();
                    new Thread(player2).start();
                    timer = new Timer(1000, this);
                    timer.setRepeats(true);
                    timer.start();
                }
            }
            System.out.println("Temos 2 jogadores no momento. Nao aceitando mais conexoes.");
        } catch (IOException e) {
            System.out.println("Erro ao aceitar conexoes");
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer) {
            if (player1.p != null && player2.p != null) {
                System.out.println("Tick");
                player1.p.tickProd();
                player2.p.tickProd();
                player1.ps.println("tickProd");
                player2.ps.println("tickProd");
                if (player1.p.getCenterHealth() == 0) {
                    player1.ps.println("derrota");
                    player2.ps.println("vitoria");
                    timer.stop();
                    player1.continua = false;
                    player2.continua = false;
                } else if (player2.p.getCenterHealth() == 0) {
                    player2.ps.println("derrota");
                    player1.ps.println("vitoria");
                    timer.stop();
                    player1.continua = false;
                    player2.continua = false;
                } else {
                    int a,b;
                    a = player2.p.getTroopAttack();
                    b = player1.p.getTroopAttack();
                    player1.p.tickAttack(a);
                    player1.ps.println("defesa");
                    player1.ps.println(a);
                    player2.ps.println("ataque");
                    player2.ps.println(player1.p.getCenterHealth());
                    player2.ps.println(player1.p.getTroopHealth());

                    player2.p.tickAttack(b);
                    player2.ps.println("defesa");
                    player2.ps.println(b);
                    player1.ps.println("ataque");
                    player1.ps.println(player2.p.getCenterHealth());
                    player1.ps.println(player2.p.getTroopHealth());
                }
            }
        }
    }

    public static void main(String[] args) {
        Server sv = new Server();
        sv.acceptConections();
    }

}