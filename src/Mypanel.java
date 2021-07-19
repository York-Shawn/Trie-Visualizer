import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class Mypanel extends JPanel {
     public static Vector<Circle> vectorCircle = new Vector<Circle>();
    Mypanel() {
        this.setBounds(200, 30, 800, 670);
        this.setBackground(new Color(44,42,56));
    }

    protected void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.clearRect(0, 0, getWidth(), getHeight());
        g2d.setColor(new Color(44,42,56));
        g2d.fillRect(0,0,getWidth(),getHeight());
        g2d.setColor(new Color(100, 149, 237));
        g2d.fillOval(370,20,60,60);
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("cosmic",Font.BOLD,20));
        g2d.drawString("root",380,57);
        g2d.drawImage(new ImageIcon("40583669-d6189844-61c5-11e8-89e3-c52ad153da09.png").getImage(),640,412,this);
        for(int i = 0; i < vectorCircle.size(); i++) {
            if (vectorCircle.get(i).c=='1') {
                vectorCircle.get(i).delete(g);
                if (i > 0 && vectorCircle.get(i).y > vectorCircle.get(i - 1).y)
                    vectorCircle.get(i).deleteLine(g2d, vectorCircle.get(i - 1));
                if (vectorCircle.get(i).y == 100) {
                    g2d.setStroke(new BasicStroke(2));
                    g2d.setColor(new Color(44, 42, 56));
                    g2d.drawLine(vectorCircle.get(i).x + 30, vectorCircle.get(i).y, 400, 80);
                }
            }
            else {
                for (int j = 0; j < vectorCircle.size(); j++) {
                    if (vectorCircle.get(j).x == vectorCircle.get(i).x && vectorCircle.get(j).y == vectorCircle.get(i).y && vectorCircle.get(i).isWord == 1)
                        vectorCircle.get(j).isWord = 1;
                }
                if (i == vectorCircle.size() - 1)
                    vectorCircle.get(i).paint(g2d);
                else
                    vectorCircle.get(i).draw(g2d);
                if (i > 0 && vectorCircle.get(i).y > vectorCircle.get(i - 1).y)
                    vectorCircle.get(i).drawLine(g2d, vectorCircle.get(i - 1));
                if (vectorCircle.get(i).y == 100) {
                    g2d.setStroke(new BasicStroke(2));
                    g2d.setColor(new Color(204, 204, 255));
                    g2d.drawLine(vectorCircle.get(i).x + 30, vectorCircle.get(i).y, 400, 80);
                }
            }
        }
    }

    public static void deleteWord(int x, int y) {
        for (int i = 0; i < vectorCircle.size(); i++) {
            if (vectorCircle.get(i).x == x&&vectorCircle.get(i).y == y)
                vectorCircle.get(i).isWord = 0;
        }
    }
}
