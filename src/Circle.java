import java.awt.*;

public class Circle {
    int x;
    int y;
    char c;
    int isWord;
    public Circle(int x, int y, char c, int isWord) {
        this.x = x;
        this.y = y;
        this.c = c;
        this.isWord = isWord;
    }

    public void draw(Graphics g) {
        g.setColor(new Color(100, 149, 237));
        g.fillOval(x,y,60,60);
        g.setColor(Color.BLACK);
        g.setFont(new Font("cosmic",Font.BOLD,20));
        g.drawString(c+"|"+isWord,x+17,y+35);
    }
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ALPHA_INTERPOLATION,RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHints(rh);
        g2d.setColor(Color.yellow);
        g2d.fillOval(x,y,60,60);
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("cosmic",Font.BOLD,20));
        g2d.drawString(c+"|"+isWord,x+17,y+35);
    }

    public void drawLine(Graphics g,Circle circle) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(new Color(204, 204, 255));
        g2d.drawLine(this.x+30,this.y, circle.x+30, circle.y+60);
    }

    public void delete(Graphics g) {
        g.setColor(new Color(44,42,56));
        g.fillOval(x,y,60,60);
    }
    public void deleteLine(Graphics g, Circle circle) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(new Color(44,42,56));
        g2d.drawLine(this.x+30,this.y, circle.x+30, circle.y+60);
    }
}
