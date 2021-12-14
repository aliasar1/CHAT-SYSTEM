package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends JFrame {

    JPanel p1;
    JTextField t1;
    JButton b1;
    static JTextArea a1;

    static ServerSocket ss;
    static Socket sc;
    static DataInputStream din;
    static DataOutputStream dout;

    boolean isTyping;

    Server(){

        p1 = new JPanel();
        p1.setLayout(null);
        p1.setBounds(0,0, 400,60);
        p1.setBackground(new Color(0,106,255));
        add(p1);

        ImageIcon back1 = new ImageIcon(ClassLoader.getSystemResource("com/company/Icons/Arrow.png"));
        Image back2 = back1.getImage().getScaledInstance(20,20, Image.SCALE_DEFAULT);
        ImageIcon back3 = new ImageIcon(back2);
        JLabel backL = new JLabel(back3);
        backL.setBounds(6,18,20,20);
        p1.add(backL);

        backL.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        ImageIcon profile1 = new ImageIcon(ClassLoader.getSystemResource("com/company/Icons/profile1.png"));
        Image profile2 = profile1.getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT);
        ImageIcon profile3 = new ImageIcon(profile2);
        JLabel profile1L = new JLabel(profile3);
        profile1L.setBounds(50,6,50,50);
        p1.add(profile1L);

        JLabel name1 = new JLabel("John Mark");
        name1.setForeground(Color.WHITE);
        name1.setFont(new Font("SAN_SERIF", Font.BOLD, 14));
        name1.setBounds(110,14,100,20);
        p1.add(name1);

        JLabel active1 = new JLabel("Active now");
        active1.setForeground(Color.WHITE);
        active1.setFont(new Font("SAN_SERIF", Font.PLAIN, 11));
        active1.setBounds(110,30,100,20);
        p1.add(active1);

        Timer t = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!isTyping){
                    active1.setText("Active now");
                }
            }
        });

        t.setInitialDelay(2000);

        ImageIcon call1 = new ImageIcon(ClassLoader.getSystemResource("com/company/Icons/call.png"));
        Image call2 = call1.getImage().getScaledInstance(25,25, Image.SCALE_DEFAULT);
        ImageIcon call3 = new ImageIcon(call2);
        JLabel call1L = new JLabel(call3);
        call1L.setBounds(270,18,25,25);
        p1.add(call1L);

        ImageIcon video1 = new ImageIcon(ClassLoader.getSystemResource("com/company/Icons/video.png"));
        Image video2 = video1.getImage().getScaledInstance(25,25, Image.SCALE_DEFAULT);
        ImageIcon video3 = new ImageIcon(video2);
        JLabel video1L = new JLabel(video3);
        video1L.setBounds(310,18,25,25);
        p1.add(video1L);

        ImageIcon info1 = new ImageIcon(ClassLoader.getSystemResource("com/company/Icons/info.png"));
        Image info2 = info1.getImage().getScaledInstance(25,25, Image.SCALE_DEFAULT);
        ImageIcon info3 = new ImageIcon(info2);
        JLabel info1L = new JLabel(info3);
        info1L.setBounds(350,17,25,25);
        p1.add(info1L);

        t1 = new JTextField();
        t1.setBounds(6,510,330,33);
        t1.setFont(new Font("SAN_SERIF", Font.PLAIN, 12));
        add(t1);

        t1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                active1.setText("Typing...");
                t.stop();
                isTyping = true;
            }

            @Override
            public void keyReleased(KeyEvent e) {
                isTyping = false;
                if (!t.isRunning()){
                    t.start();
                }
            }
        });

        b1 = new JButton();
        b1.setBounds(345, 510, 50, 33);
        ImageIcon send1 = new ImageIcon(ClassLoader.getSystemResource("com/company/Icons/send.png"));
        Image send2 = send1.getImage().getScaledInstance(22,20, Image.SCALE_DEFAULT);
        ImageIcon send3 = new ImageIcon(send2);
        b1.setIcon(send3);
        b1.setFocusPainted(false);
        add(b1);

        b1.addActionListener(e -> {
            String str = t1.getText();
            if (!str.isEmpty() && !str.trim().isBlank()) {
                a1.setText(a1.getText() + "\n\t\t\t" + str);
                try {
                    dout.writeUTF(str);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            t1.setText("");
        });

        a1 = new JTextArea();
        a1.setBounds(5, 70, 390, 432);
        a1.setEditable(false);
        a1.setLineWrap(true);
        a1.setWrapStyleWord(false);
        a1.setFont(new Font("SAN_SERIF", Font.PLAIN, 12));
        add(a1);

        getContentPane().setBackground(Color.WHITE);
        setLocation(200, 110);
        setUndecorated(true);
        setLayout(null);
        setSize(400,550);
        setVisible(true);
    }

    public static void main(String [] args){
        new Server().setVisible(true);

        String msg;
        try{
            ss = new ServerSocket(4001);
            sc = ss.accept();
            din = new DataInputStream(sc.getInputStream());
            dout = new DataOutputStream(sc.getOutputStream());

            msg = din.readUTF();
            a1.setText(a1.getText() + "\n" + msg);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}



