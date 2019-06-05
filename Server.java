import java.net.*;
import java.io.*;
import java.util.*;


public class Server{

    private ServerSocket ss;
    private int numPlayers;
    private int port = 44455;
    private ServerSideConnection player1;
    private ServerSideConnection player2;
    public Server(){
        System.out.println("--- Abrindo Servidor ---");
        numPlayers = 0;
        try{
            ss = new ServerSocket(port);
        }catch(IOException e){
            System.out.println("Erro ao abrir servidor: " + e);
            System.exit(1);
        }
    }

    private class ServerSideConnection implements Runnable{

        private Socket socket;
        private Scanner sc;
        private PrintStream ps;
        private int playerID;

        public ServerSideConnection(Socket s, int id){
            socket = s;
            playerID = id;
            try{
                sc = new Scanner(socket.getInputStream());
                ps = new PrintStream(socket.getOutputStream());
            }catch(IOException e){
                System.out.println("Erro de IO: " + e);
            }
        }

        public void run(){
            //try{
                ps.println("ID");
                ps.println(playerID);

                while(true){

                }
            //}catch(IOException e){
              //  System.out.println("Erro de IO: " + e);
            //}
        }

    }

    public void acceptConections(){
        try{
            System.out.println("Esperando conexoes...");
            while(numPlayers < 2){
                Socket s = ss.accept();
                numPlayers++;
                System.out.println("Jogador #" + numPlayers + " conectou.");
                ServerSideConnection ssc = new ServerSideConnection(s, numPlayers);
                if(numPlayers == 1){
                    player1 = ssc;
                }else{
                    player2 = ssc;
                }
                Thread t = new Thread(ssc);
                t.start();
            }
            System.out.println("Temos 2 jogadores no momento. Nao aceitando mais conexoes.");
        }catch(IOException e){
            System.out.println("Erro ao aceitar conexoes");
        }
    }

    public static void main(String[] args) {
        Server sv = new Server();
        sv.acceptConections();
    }

}