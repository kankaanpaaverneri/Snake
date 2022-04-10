import java.util.LinkedList;
import java.awt.*;

public class Snake
{
    private LinkedList<SnakePart> snake;
    private int snakeLength;

    public Snake()
    {
        this.snake = new LinkedList<>();
        this.setSnakeLength(10);
        this.initSnake();
    }

    public Snake(final int initialSnakeLength)
    {
        this.snake = new LinkedList<>();
        this.setSnakeLength(initialSnakeLength);
        this.initSnake();
    }

    private void initSnake()
    {
        Point point = new Point(0, 0);
        for(int i = 0; i < this.getSnakeLength(); i++)
        {
            SnakePart snakePart = new SnakePart(point);

            this.snake.add(snakePart);
            point.setLocation(snakePart.getPointX() + 25, snakePart.getPointY());
        }
    }

    public final int getSnakeLength()
    {
        return this.snakeLength;
    }

    public final SnakePart getSnakePart(final int index)
    {
        return snake.get(index);
    }

    public void setSnakePart(final SnakePart snakePart)
    {
        this.snake.add(snakePart);
    }

    public void setSnakeLength(final int snakeLength)
    {
        this.snakeLength = snakeLength;
    }
}