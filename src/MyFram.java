import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;


public class MyFram extends JFrame {
    static int mlx;
    static int mly;
    public static Mypanel panel;
    Timer timer;
    MyFram() {

        panel = new Mypanel();
        panel.setBackground(new Color(44,42,56));
        JPanel TaskBar = new JPanel();
        TaskBar.setBackground(new Color(52,52,66));
        TaskBar.setBounds(0,0,1000,30);
        TaskBar.addMouseListener(new MouseAdapter() {		//鼠标点击事件
            public void mousePressed(MouseEvent e) {
                mlx=e.getPoint().x;		//获取鼠标在组件内x坐标并赋值给mlx（组件坐标系）
                mly=e.getPoint().y;		//获取鼠标在组件内y坐标并赋值给mly（组件坐标系）
            }
        });
        TaskBar.addMouseMotionListener(new MouseMotionAdapter() {		//鼠标拖动事件
            public void mouseDragged(MouseEvent e) {
                setBounds(e.getXOnScreen()-mlx,e.getYOnScreen()-mly,getWidth(),getHeight());
            }
        });

        JButton button = new JButton();
        button.setMargin(new Insets(0,0,0,0));
        button.setText("\u2715");
        button.setForeground(Color.white);
        button.setFocusable(false);
        button.setSize(25,25);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setBounds(970,0,30,30);
        button.setBackground(new Color(52,52,66));
        button.addActionListener(e -> dispose());

        JPanel Functionbar = new JPanel();
        Functionbar.setBackground(new Color(66,66,78));
        Functionbar.setBounds(0,30,200,700);
        JLabel label = new JLabel();
        label.setBounds(5,500,200,200);
        try {
            label.setFont(loadFont("CascadiaCodePL_0.ttf", 30));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        }
        label.setForeground(new Color(240, 128, 128));
        label.setText("Visualizer");
        JLabel label1 = new JLabel();
        label1.setBounds(60,450,200,200);
        try {
            label1.setFont(loadFont("CascadiaCodePL_0.ttf",30));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        }
        label1.setForeground(new Color(240, 128, 128));
        label1.setText("Trie");
        Functionbar.add(label);
        Functionbar.add(label1);


        JTextField textField = new JTextField(20);
        textField.setBorder(BorderFactory.createEmptyBorder());
        textField.setBounds(25,35,150,20);
        try {
            textField.setFont(loadFont("CascadiaCodePL_0.ttf",20));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        }

        JButton insert = new JButton();
        insert.setText("插入");
        insert.setFont(new Font("cosmic",Font.BOLD,25));
        insert.setMargin(new Insets(0,0,0,0));
        insert.setBounds(50,75,75,40);
        insert.setBackground(new Color(66,66,78));
        insert.setForeground(new Color(212,212,212));
        insert.setFocusable(false);
        insert.setBorder(BorderFactory.createLineBorder(new Color(66,66,78)));
        insert.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        insert.addMouseListener(new MouseAdapter() {		//鼠标点击事件
            public void mousePressed(MouseEvent e) {
                Trie.insert(textField.getText().toLowerCase());
                String word = textField.getText().toLowerCase();
                ActionListener action = new ActionListener() {
                    int x = 370;
                    int i=0;
                    int isWord=0;
                    public void actionPerformed(ActionEvent e) {
                        x = x + (word.charAt(i)-'m') * 10;
                        if(i<word.length()-1) {
                            isWord = 0;
                        }
                        else {
                            isWord = 1;
                            timer.stop();
                        }
                        Circle circle =new Circle(x,100+80*i,word.charAt(i),isWord);
                        panel.vectorCircle.add(circle);
                        panel.repaint();
                        i++;
                    }
                };
                timer = new Timer(1000,action);
                timer.start();
            }
        });

        JButton search = new JButton();
        search.setText("查找");
        search.setFont(new Font("cosmic",Font.BOLD,25));
        search.setMargin(new Insets(0,0,0,0));
        search.setBounds(50,135,75,40);
        search.setBackground(new Color(66,66,78));
        search.setForeground(new Color(212,212,212));
        search.setFocusable(false);
        search.setBorder(BorderFactory.createLineBorder(new Color(66,66,78)));
        search.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        search.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String word = textField.getText().toLowerCase();
                int count = Trie.search(word);
                if(count==-1) {
                    //Graphics g = new Graphics()
                    ActionListener action = new ActionListener() {
                        int x = 370;
                        int i = 0;
                        int isWord = 0;

                        public void actionPerformed(ActionEvent e) {
                            x = x + (word.charAt(i) - 'm') * 10;
                            if (i < word.length() - 1) {
                                isWord = 0;
                            } else {
                                isWord = 1;
                                JOptionPane.showMessageDialog(null, "Word existed", "Reuslt", JOptionPane.PLAIN_MESSAGE);
                                timer.stop();
                            }
                            //Circle circle =new Circle(x,100+80*i,word.charAt(i),isWord);
                            if (panel.vectorCircle.contains(new Circle(x, 100 + 80 * i, word.charAt(i), 1)))
                                panel.vectorCircle.add(new Circle(x, 100 + 80 * i, word.charAt(i), 1));
                            else {
                                Circle circle = new Circle(x, 100 + 80 * i, word.charAt(i), isWord);
                                panel.vectorCircle.add(circle);
                            }
                            panel.repaint();
                            i++;
                        }
                    };
                    timer = new Timer(1000, action);
                    timer.start();
                }
                else if (count == 0)
                    JOptionPane.showMessageDialog(null, "Ooops, word not existed", "Reuslt", JOptionPane.PLAIN_MESSAGE);
                else {
                    ActionListener action = new ActionListener() {
                        int x = 370;
                        int i = 0;
                        int isWord = 0;

                        public void actionPerformed(ActionEvent e) {
                            x = x + (word.charAt(i) - 'm') * 10;
                            if (i < word.length() - 1) {
                                isWord = 0;
                            }
                            if (i == count-1) {
                                timer.stop();
                                JOptionPane.showMessageDialog(null, "Ooops, word not existed", "Reuslt", JOptionPane.PLAIN_MESSAGE);
                            }
                            //Circle circle =new Circle(x,100+80*i,word.charAt(i),isWord);
                            if (panel.vectorCircle.contains(new Circle(x, 100 + 80 * i, word.charAt(i), 1)))
                                panel.vectorCircle.add(new Circle(x, 100 + 80 * i, word.charAt(i), 1));
                            else {
                                Circle circle = new Circle(x, 100 + 80 * i, word.charAt(i), isWord);
                                panel.vectorCircle.add(circle);
                            }
                            panel.repaint();
                            i++;
                        }
                    };
                    timer = new Timer(1000, action);
                    timer.start();
                }
            }
        });

        JButton startsWith = new JButton();
        startsWith.setText("前缀查找");
        startsWith.setFont(new Font("cosmic",Font.BOLD,25));
        startsWith.setMargin(new Insets(0,0,0,0));
        startsWith.setBounds(20,195,150,40);
        startsWith.setBackground(new Color(66,66,78));
        startsWith.setForeground(new Color(212,212,212));
        startsWith.setFocusable(false);
        startsWith.setBorder(BorderFactory.createLineBorder(new Color(66,66,78)));
        startsWith.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        startsWith.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                String word = textField.getText().toLowerCase();
                int count = Trie.startsWith(word);
                if(count==-1) {
                    ActionListener action = new ActionListener() {
                        int x = 370;
                        int i = 0;
                        int isWord = 0;

                        public void actionPerformed(ActionEvent e) {
                            x = x + (word.charAt(i) - 'm') * 10;
                            if (i < word.length() - 1) {
                                isWord = 0;
                            } else {
                                JOptionPane.showMessageDialog(null, "Prefix existed\n"+Trie.keysWithPrefix(word), "Reuslt", JOptionPane.PLAIN_MESSAGE);
                                timer.stop();
                            }
                            //Circle circle =new Circle(x,100+80*i,word.charAt(i),isWord);
                            if (panel.vectorCircle.contains(new Circle(x, 100 + 80 * i, word.charAt(i), 1)))
                                panel.vectorCircle.add(new Circle(x, 100 + 80 * i, word.charAt(i), 1));
                            else {
                                Circle circle = new Circle(x, 100 + 80 * i, word.charAt(i), isWord);
                                panel.vectorCircle.add(circle);
                            }
                            panel.repaint();
                            i++;
                        }
                    };
                    timer = new Timer(1000, action);
                    timer.start();
                }
                else if (count == 0)
                    JOptionPane.showMessageDialog(null, "Ooops, Prefix not existed", "Reuslt", JOptionPane.PLAIN_MESSAGE);
                else {
                    ActionListener action = new ActionListener() {
                        int x = 370;
                        int i = 0;
                        int isWord = 0;

                        public void actionPerformed(ActionEvent e) {
                            x = x + (word.charAt(i) - 'm') * 10;
                            if (i < word.length() - 1) {
                                isWord = 0;
                            }
                            if (i == count-1) {
                                timer.stop();
                                JOptionPane.showMessageDialog(null, "Ooops, Prefix not existed", "Reuslt", JOptionPane.PLAIN_MESSAGE);
                            }
                            //Circle circle =new Circle(x,100+80*i,word.charAt(i),isWord);
                            if (panel.vectorCircle.contains(new Circle(x, 100 + 80 * i, word.charAt(i), 1)))
                                panel.vectorCircle.add(new Circle(x, 100 + 80 * i, word.charAt(i), 1));
                            else {
                                Circle circle = new Circle(x, 100 + 80 * i, word.charAt(i), isWord);
                                panel.vectorCircle.add(circle);
                            }
                            panel.repaint();
                            i++;
                        }
                    };
                    timer = new Timer(1000, action);
                    timer.start();
                }
            }
        });

        JButton delete = new JButton();
        delete.setText("删除");
        delete.setFont(new Font("cosmic",Font.BOLD,25));
        delete.setMargin(new Insets(0,0,0,0));
        delete.setBounds(50,255,75,40);
        delete.setBackground(new Color(66,66,78));
        delete.setForeground(new Color(212,212,212));
        delete.setFocusable(false);
        delete.setBorder(BorderFactory.createLineBorder(new Color(66,66,78)));
        delete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        delete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String word = textField.getText().toLowerCase();
                int count = Trie.delete(word);
                if(count==-1) {
                    //Graphics g = new Graphics()
                    ActionListener action = new ActionListener() {
                        int x = 370;
                        int i = 0;
                        int isWord = 0;

                        public void actionPerformed(ActionEvent e) {
                            x = x + (word.charAt(i)-'m') * 10;
                            if(i<word.length()-1) {
                                isWord = 0;
                            }
                            else {
                                panel.deleteWord(x,100+80*i);
                                isWord = 0;
                                timer.stop();
                            }
                            Circle circle =new Circle(x,100+80*i,word.charAt(i),isWord);
                            panel.vectorCircle.add(circle);
                            panel.repaint();
                            i++;
                        }
                    };
                    timer = new Timer(1000, action);
                    timer.start();
                }
                else {
                    ActionListener action = new ActionListener() {
                        int x = 370;
                        int i = 0;
                        public void actionPerformed(ActionEvent e) {
                            x = x + (word.charAt(i) - 'm') * 10;
                            if (i >= count) {
                                Circle circle = new Circle(x,100+80*i,'1',0);
                                panel.vectorCircle.add(circle);
                            }
                            else {
                                Circle circle = new Circle(x, 100+80*i, word.charAt(i),0);
                                panel.vectorCircle.add(circle);
                            }
                            if(i == word.length() - 1)
                                timer.stop();
                            panel.repaint();
                            i++;
                        }
                    };
                    timer = new Timer(1000, action);
                    timer.start();
                }
            }
        });

        JButton Match = new JButton();
        Match.setText("最长前缀匹配");
        Match.setFont(new Font("cosmic",Font.BOLD,25));
        Match.setMargin(new Insets(0,0,0,0));
        Match.setBounds(10,310,175,40);
        Match.setBackground(new Color(66,66,78));
        Match.setForeground(new Color(212,212,212));
        Match.setFocusable(false);
        Match.setBorder(BorderFactory.createLineBorder(new Color(66,66,78)));
        Match.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        Match.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                String word = textField.getText().toLowerCase();
                int count = Trie.startsWith(word);
                if(count==-1) {
                    ActionListener action = new ActionListener() {
                        int x = 370;
                        int i = 0;
                        int isWord = 0;

                        public void actionPerformed(ActionEvent e) {
                            x = x + (word.charAt(i) - 'm') * 10;
                            if (i < word.length() - 1) {
                                isWord = 0;
                            } else {
                                JOptionPane.showMessageDialog(null, "The longest prefix match with the string is "+Trie.longestPrefixof(word), "Reuslt", JOptionPane.PLAIN_MESSAGE);
                                timer.stop();
                            }
                            //Circle circle =new Circle(x,100+80*i,word.charAt(i),isWord);
                            if (panel.vectorCircle.contains(new Circle(x, 100 + 80 * i, word.charAt(i), 1)))
                                panel.vectorCircle.add(new Circle(x, 100 + 80 * i, word.charAt(i), 1));
                            else {
                                Circle circle = new Circle(x, 100 + 80 * i, word.charAt(i), isWord);
                                panel.vectorCircle.add(circle);
                            }
                            panel.repaint();
                            i++;
                        }
                    };
                    timer = new Timer(1000, action);
                    timer.start();
                }
                else if (count == 0)
                    JOptionPane.showMessageDialog(null, "Ooops, Prefix not existed", "Reuslt", JOptionPane.PLAIN_MESSAGE);
                else {
                    ActionListener action = new ActionListener() {
                        int x = 370;
                        int i = 0;
                        int isWord = 0;

                        public void actionPerformed(ActionEvent e) {
                            x = x + (word.charAt(i) - 'm') * 10;
                            if (i < word.length() - 1) {
                                isWord = 0;
                            }
                            if (i == count-1) {
                                timer.stop();
                                JOptionPane.showMessageDialog(null, "The longest prefix match with the string is "+Trie.longestPrefixof(word), "Reuslt", JOptionPane.PLAIN_MESSAGE);
                            }
                            //Circle circle =new Circle(x,100+80*i,word.charAt(i),isWord);
                            if (panel.vectorCircle.contains(new Circle(x, 100 + 80 * i, word.charAt(i), 1)))
                                panel.vectorCircle.add(new Circle(x, 100 + 80 * i, word.charAt(i), 1));
                            else {
                                Circle circle = new Circle(x, 100 + 80 * i, word.charAt(i), isWord);
                                panel.vectorCircle.add(circle);
                            }
                            panel.repaint();
                            i++;
                        }
                    };
                    timer = new Timer(1000, action);
                    timer.start();
                }
            }
        });

        Functionbar.add(textField);
        Functionbar.add(insert);
        Functionbar.add(search);
        Functionbar.add(startsWith);
        Functionbar.add(delete);
        Functionbar.add(Match);

        setUndecorated(true);
        setTitle("Trie Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000,700);
        setVisible(true);
        Toolkit kit=Toolkit.getDefaultToolkit();
        Dimension sc=kit.getScreenSize();
        setLocation(sc.width/2-500,sc.height/2-300);
        ImageIcon image = new ImageIcon("logo_64_eyes.png");
        setIconImage(image.getImage());
        getContentPane().setBackground(new Color(44,42,56));
        setLayout(null);

        add(Functionbar);
        add(panel);
        add(TaskBar);
        TaskBar.add(button);
    }

    public Font loadFont(String fontName, int size) throws IOException, FontFormatException {
        InputStream is = this.getClass().getResourceAsStream(fontName);
        Font actionJson = Font.createFont(Font.TRUETYPE_FONT,is);//返回一个指定字体类型和输入数据的font
        Font actionJsonBase = actionJson.deriveFont(Font.BOLD,size);//通过复制此 Font 对象并应用新样式和大小，创建一个新 Font 对象。
        return actionJsonBase;
    }

}
