package viginia;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.sound.sampled.LineListener;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class progress extends JFrame {

    private JPanel contentPane;
    private JTextField text_keys;
    private JTextField text_write;
    private JTextField text_cipher;
    private JTextField text_answer;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    progress frame = new progress();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public progress() {
        setTitle("\u7EF4\u5409\u5C3C\u4E9A\u52A0\u5BC6\u89E3\u5BC6\u7A0B\u5E8F");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(650, 300, 550, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel label = new JLabel("");
        label.setBounds(217, 10, 0, 0);
        contentPane.add(label);

        JLabel label_1 = new JLabel("\u5BC6  \u94A5 \uFF1A");
        label_1.setHorizontalAlignment(SwingConstants.CENTER);
        label_1.setBounds(10, 27, 75, 15);
        contentPane.add(label_1);

        text_keys = new JTextField();
        text_keys.setBounds(83, 24, 414, 21);
        contentPane.add(text_keys);
        text_keys.setColumns(10);

        JLabel label_2 = new JLabel("\u660E  \u6587 \uFF1A");
        label_2.setHorizontalAlignment(SwingConstants.CENTER);
        label_2.setBounds(10, 62, 75, 15);
        contentPane.add(label_2);

        text_write = new JTextField();
        text_write.setColumns(10);
        text_write.setBounds(83, 59, 414, 21);
        contentPane.add(text_write);

        JLabel label_3 = new JLabel("\u5BC6  \u6587 \uFF1A");
        label_3.setHorizontalAlignment(SwingConstants.CENTER);
        label_3.setBounds(10, 98, 75, 15);
        contentPane.add(label_3);

        text_cipher = new JTextField();
        text_cipher.setColumns(10);
        text_cipher.setBounds(83, 95, 414, 21);
        contentPane.add(text_cipher);

        JButton btnEncrypt = new JButton("\u52A0  \u5BC6");
        btnEncrypt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String keys, write;
                keys = text_keys.getText().replaceAll("[^a-zA-Z]", "");
                write = text_write.getText().replaceAll("[^a-zA-Z]", "");
                if (keys.length()<=0&&write.length()>0) {
                    JOptionPane.showConfirmDialog(null,"请输入密钥","提示",0,1);
                }else if (write.length()<=0&&keys.length()>0) {
                    JOptionPane.showConfirmDialog(null, "请输入明文","提示",0,1);
                }else if (keys.length()<=0&&write.length()<=0) {
                    JOptionPane.showConfirmDialog(null, "请输入密钥和明文","提示",0,1);
                }else if (keys.length() != write.length()) {
                    JOptionPane.showConfirmDialog(null, "密钥和明文长度不匹配，请输入正确的密钥或明文","提示",0,1);
                }else{
                    alphabet alpha = new alphabet();
                    int num_keys = 0, num_write = 0;
                    String s = "";
                    for (int i = 0; i < keys.length(); i++) {
                        num_keys = alpha.findPrim(keys.charAt(i));
                        num_write = alpha.findPrim(write.charAt(i));
                        s += alpha.codePrim(num_keys,num_write);
                    }
                    text_answer.setText(s);
                }

            }
        });
        btnEncrypt.setBounds(128, 147, 93, 23);
        contentPane.add(btnEncrypt);

        JButton btnDecode = new JButton("\u89E3  \u5BC6");
        btnDecode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String keys, cipher;
                keys = text_keys.getText().replaceAll("[^a-zA-Z]", "");
                cipher = text_cipher.getText().replaceAll("[^a-zA-Z]", "");
                if (keys.length()<=0&&cipher.length()>0) {
                    JOptionPane.showConfirmDialog(null,"请输入密钥","提示",0,1);
                }else if (cipher.length()<=0&&keys.length()>0) {
                    JOptionPane.showConfirmDialog(null, "请输入密文","提示",0,1);
                }else if (keys.length()<=0&&cipher.length()<=0) {
                    JOptionPane.showConfirmDialog(null, "请输入密钥和密文","提示",0,1);
                }else {
                    alphabet alpha = new alphabet();
                    int num_keys = 0, num_cipher = 0;
                    String s = "";
                    for (int i = 0; i < keys.length(); i++) {
                        num_keys = alpha.findPrim(keys.charAt(i));
                        num_cipher = alpha.findPw(num_keys, cipher.charAt(i));
                        s += alpha.decodePrim(num_cipher);
                    }
                    text_answer.setText(s);
                }
            }
        });
        btnDecode.setBounds(351, 147, 93, 23);
        contentPane.add(btnDecode);

        JLabel label_4 = new JLabel("\u7ED3  \u679C \uFF1A");
        label_4.setHorizontalAlignment(SwingConstants.CENTER);
        label_4.setBounds(10, 203, 75, 15);
        contentPane.add(label_4);

        text_answer = new JTextField();
        text_answer.setColumns(10);
        text_answer.setBounds(83, 200, 414, 21);
        contentPane.add(text_answer);
    }
}

class alphabet {
    private static String prim = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    int findPrim(char c) {
        if (c >= 'a'&&c<='z') {
            c -= 32;
        }
        return prim.indexOf(c);
    }

    int findPw(int keys, char c) {
        String s = this.change(keys);
        if (c >= 'a'&&c<='z') {
            c -= 32;
        }
        return s.indexOf(c);
    }

    String codePrim(int keys, int write) {
        String s = this.change(keys);
        String temp = "";
        temp += s.substring(write, write+1);
        return temp;
    }

    String decodePrim(int keys) {
        String s = "";
        s = prim.substring(keys,keys+1);
        return s;
    }

    private String change(int num) {
        String s = "";
        s = prim.substring(num);
        s += prim.substring(0, num);
        return s;
    }
}

