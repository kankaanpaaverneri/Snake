import java.awt.Color;
import java.awt.Point;
import javax.swing.JLabel;

public class SnakePart extends JLabel
{
    private Point point;
    
    public SnakePart()
    {
        this.point = new Point(0, 0);
        this.setSize(25, 25);
        this.setBackground(Color.BLACK);
        this.setOpaque(true);
    }

    public SnakePart(final Point point)
    {
        this.point = new Point(point);
        this.setSize(25, 25);
        this.setBackground(Color.BLACK);
        this.setOpaque(true);
    }

    public SnakePart(final Point point, final Color color)
    {
        this.point = new Point(point);
        this.setSize(25, 25);
        this.setBackground(color);
        this.setOpaque(true);
    }

    public final Point getPoint()
    {
        return this.point;
    }

    public final double getPointX()
    {
        return this.point.getX();
    }

    public final double getPointY()
    {
        return this.point.getY();
    }

    public void setPoint(final Point point)
    {
        this.point = point;
    }

    public void setPoint(final double x, final double y)
    {
        this.point.setLocation(x, y);
    }
}