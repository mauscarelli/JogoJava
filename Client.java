import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import java.awt.event.*;
import java.awt.image.*;

public class Client extends JFrame implements MouseListener, ActionListener {
  private int width;
  private int height;
  private Container contentPane;
  private ClientSideConnection csc;
  private int playerId;
  private int opponent;
  private Player jogador;
  public Espera esp;
  public JButton btADDcidadao, btADDsoldado, btADDcdComida, btSUBcdComida, btADDcdOuro, btSUBcdOuro, btFIGHT;
  public JLabel qntOuro, qntComida, qntSoldado, qntCidadao, vidaCentroA, vidaCentroI, vidaTropasA, vidaTropasI, qntCidadaoC,
      qntCidadaoO;

  Client(int w, int h) {
    width = w;
    height = h;
    this.setSize(width, height);
    this.setTitle("Civilization XII");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    // contentPane.setLayout(new FlowLayout());
    // add(contentPane);
    // pack();
    des = new Desenho();
    des.setLayout(null);
    des.addMouseListener(this);

    btADDcidadao = new JButton();
    btADDcidadao.setSize(40, 40);
    btADDcidadao.setLocation(50, 560);
    btADDcidadao.setIcon(new ImageIcon("imagens/addCidadaoV1.png"));
    des.add(btADDcidadao);

    btADDsoldado = new JButton();
    btADDsoldado.setSize(40, 40);
    btADDsoldado.setLocation(50, 610);
    btADDsoldado.setIcon(new ImageIcon("imagens/addSoldadoV2.png"));
    des.add(btADDsoldado);

    btADDcdComida = new JButton();
    btADDcdComida.setSize(40, 40);
    btADDcdComida.setLocation(115, 560);
    btADDcdComida.setIcon(new ImageIcon("imagens/ADDCidadaoComida.png"));
    des.add(btADDcdComida);

    btSUBcdComida = new JButton();
    btSUBcdComida.setSize(40, 40);
    btSUBcdComida.setLocation(115, 610);
    btSUBcdComida.setIcon(new ImageIcon("imagens/SUBCidadaoComida.png"));
    des.add(btSUBcdComida);

    btADDcdOuro = new JButton();
    btADDcdOuro.setSize(40, 40);
    btADDcdOuro.setLocation(180, 560);
    btADDcdOuro.setIcon(new ImageIcon("imagens/ADDCidadaoOuro.png"));
    des.add(btADDcdOuro);

    btSUBcdOuro = new JButton();
    btSUBcdOuro.setSize(40, 40);
    btSUBcdOuro.setLocation(180, 610);
    btSUBcdOuro.setIcon(new ImageIcon("imagens/SubCidadaoOuro.png"));
    des.add(btSUBcdOuro);

    btFIGHT = new JButton();
    btFIGHT.setSize(60, 60);
    btFIGHT.setLocation(245, 575);
    btFIGHT.setIcon(new ImageIcon("imagens/LUTAR.png"));
    //des.add(btFIGHT);

    btADDcidadao.addActionListener(this);

    btADDsoldado.addActionListener(this);

    btADDcdComida.addActionListener(this);

    btSUBcdComida.addActionListener(this);

    btADDcdOuro.addActionListener(this);

    btSUBcdOuro.addActionListener(this);

    btFIGHT.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // selectionButtonPressed();
        System.out.println("ENVIAR AS TROPAS");
      }
    });

    qntCidadao = new JLabel("4");
    qntCidadao.setBounds(745, 19, 100, 30);
    des.add(qntCidadao);

    qntSoldado = new JLabel("0");
    qntSoldado.setBounds(745, 70, 100, 30);
    des.add(qntSoldado);

    qntComida = new JLabel("10");
    qntComida.setBounds(860, 19, 100, 30);
    des.add(qntComida);

    qntOuro = new JLabel("10");
    qntOuro.setBounds(860, 70, 100, 30);
    des.add(qntOuro);

    qntCidadaoC = new JLabel("0");
    qntCidadaoC.setBounds(970, 19, 100, 30);
    des.add(qntCidadaoC);

    qntCidadaoO = new JLabel("0");
    qntCidadaoO.setBounds(970, 70, 100, 30);
    des.add(qntCidadaoO);

    vidaTropasA = new JLabel("0");
    vidaTropasA.setBounds(1090, 19, 100, 30);
    des.add(vidaTropasA);

    vidaTropasI = new JLabel("0");
    vidaTropasI.setBounds(1090, 70, 100, 30);
    des.add(vidaTropasI);

    vidaCentroA = new JLabel("1000");
    vidaCentroA.setBounds(1200, 19, 100, 30);
    des.add(vidaCentroA);

    vidaCentroI = new JLabel("1000");
    vidaCentroI.setBounds(1200, 70, 100, 30);
    des.add(vidaCentroI);

    add(des);

    validate();
    repaint();
    setVisible(false);
    esp = new Espera();
  }
  class Espera extends JFrame{
    Espera(){
      super("Sala de espera");
      setSize(300,300);
      setLayout(null);
      setLocationRelativeTo(null);
      JLabel lbl = new JLabel("Aguardando outro jogador.");
      lbl.setBounds(75, 100, 200, 30);
      add(lbl);
      setVisible(true);
    }
  }

  public void connectToServer() {
    csc = new ClientSideConnection();
  }

  Image[] img = new Image[8];
  Desenho des;
  final int FUNDO = 0;
  final int BOTAO1 = 1;
  final int BOTAO2 = 2;
  final int BOTAO3 = 3;
  final int BOTAO4 = 4;
  final int BOTAO5 = 5;
  final int AUMENTA = 6;
  final int DIMINUI = 7;

  class Desenho extends JPanel {

    Desenho() {
      try {
        // setPreferredSize(new Dimension(10}00, 600));
        img[FUNDO] = ImageIO.read(new File("imagens/FUNDO1.jpg"));

      } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "A imagem nao pode ser carregada!\n" + e, "Erro",
            JOptionPane.ERROR_MESSAGE);
        System.exit(1);
      }
    }

    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(img[FUNDO], 0, 0, getSize().width, getSize().height, this);
      g.drawImage(img[BOTAO1], 100, 100, 50, 50, this);

      Toolkit.getDefaultToolkit().sync();

    }

  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == btADDcidadao) {
      System.out.println("Cria Cidadão");
      csc.enviaComando("addCitizen");
      jogador.addCitizen();
      qntCidadao.setText("" + jogador.getCitizen());
      repaint();
    } else if (e.getSource() == btADDsoldado) {
      System.out.println("Cria Soldado");
      csc.enviaComando("addSoldier");
      jogador.addSoldier();
      qntSoldado.setText("" + jogador.getTroopAttack());
      vidaTropasA.setText("" + jogador.getTroopHealth());
      repaint();
    } else if (e.getSource() == btADDcdComida) {
      System.out.println("Adiciona Coletor");
      csc.enviaComando("addCitizenFood");
      jogador.addCitizenFood();
      qntCidadaoC.setText("" + jogador.getCitizenFood());
      repaint();
    } else if (e.getSource() == btADDcdOuro) {
      System.out.println("Adiciona Mineiro");
      csc.enviaComando("addCitizenGold");
      jogador.addCitizenGold();
      qntCidadaoO.setText("" + jogador.getCitizenGold());
      repaint();
    } else if (e.getSource() == btSUBcdComida) {
      System.out.println("Remove Coletor");
      csc.enviaComando("removeCitizenFood");
      jogador.removeCitizenFood();
      qntCidadaoC.setText("" + jogador.getCitizenFood());
      repaint();
    } else if (e.getSource() == btSUBcdOuro) {
      System.out.println("Remove Mineiro");
      csc.enviaComando("removeCitizenGold");
      jogador.removeCitizenGold();
      qntCidadaoO.setText("" + jogador.getCitizenGold());
      repaint();
    }
  }

  public void mouseClicked(MouseEvent e) {

    // if (e.getSource() == img[BOTAO1]) {
    // System.out.println("botao1 pressionado");
    // }

  }

  public void mousePressed(MouseEvent e) {

  }

  public void mouseReleased(MouseEvent e) {

  }

  public void mouseEntered(MouseEvent e) {

  }

  public void mouseExited(MouseEvent e) {

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
        JOptionPane.showMessageDialog(null, "Servidor fora do ar, tente novamente mais tarde.", "Erro",
            JOptionPane.ERROR_MESSAGE);
        System.exit(0);
      }
    }

    public void enviaComando(String s) {
      System.out.println("Enviando: " + s);
      ps.println(s);
    }

    private Runnable t = new Runnable() {

      @Override
      public void run() {
        try {
          setVisible(true);
          esp.dispose();
          qntCidadao.setText(Integer.toString(jogador.getCitizen()));
          while (true) {
            String s = sc.nextLine();
            //System.out.println(s);
            if (s.equals("tickProd")) {
              jogador.tickProd();
              qntOuro.setText("" + jogador.getGold());
              qntComida.setText("" + jogador.getFood());
              repaint();
            } else if (s.equals("defesa")) {
              int d = Integer.parseInt(sc.nextLine());
              jogador.tickAttack(d);
              vidaCentroA.setText("" + jogador.getCenterHealth());
              vidaTropasA.setText("" + jogador.getTroopHealth());
              qntSoldado.setText("" + jogador.getTroopAttack());
              repaint();
            } else if (s.equals("ataque")) {
              int d = Integer.parseInt(sc.nextLine());
              vidaCentroI.setText("" + d);
              d = Integer.parseInt(sc.nextLine());
              vidaTropasI.setText("" + d);
              repaint();
            } else if (s.equals("vitoria")) {
              System.out.println("Vitoria!");
              JOptionPane.showMessageDialog(null, "Vitoria! Parabens pela conquista.", "Vitoria",
                  JOptionPane.INFORMATION_MESSAGE);
              break;
            } else if (s.equals("derrota")) {
              System.out.println("Derrota.");
              JOptionPane.showMessageDialog(null, "Derrota. Seu imperio caiu.", "Derrota",
                  JOptionPane.INFORMATION_MESSAGE);
              break;
            }

          }
          sc.close();
          ps.close();
          socket.close();
        } catch (IOException ex) {
          System.out.println("Erro de IO: " + ex);
        }
        System.exit(0);
      }
    };

  }

  public static void main(String[] args) {
    Client c = new Client(1280, 720);
    c.connectToServer();
  }
}