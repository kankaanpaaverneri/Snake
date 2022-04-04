import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Random;

public class UiFrame extends JFrame implements ActionListener, KeyListener
{
    private Snake snake;
    private Timer timer;
    private int xDirection = -25, yDirection = 0;
    private int snakeIterator = 0;
    private JLabel[] walls;
    private JLabel fruit;
    private JLabel highScoreLabel;
    private int highScore = 0;

    public UiFrame()
    {
        //Timer setup
        timer = new Timer(500, this);
        timer.start();

        //Frame setup
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.initHighScore();
        this.initWalls();
        this.initSnakeToUiFrame();
        this.initFruit();
        this.setVisible(true);
        this.addKeyListener(this);
    }

    private void initSnakeToUiFrame()
    {
        this.snake = new Snake(10);

        //Initialize point to 250, 250
        Point point = new Point(250, 250);
        for(int i = 0; i < snake.getSnakeLength(); i++)
        {
            //Setting the individual snakePart location to the center
            this.snake.getSnakePart(i).setLocation(point);

            this.add(snake.getSnakePart(i));

            //Updating the point by the snakePart point
            point.setLocation(this.snake.getSnakePart(i).getPoint());
        }
    }

    private void initHighScore()
    {
        this.highScoreLabel = new JLabel("Pisteet: " + highScore);
        this.highScoreLabel.setBounds(275, 25, 200, 25);
        this.highScoreLabel.setBackground(Color.GRAY);
        this.highScoreLabel.setOpaque(true);
        this.add(highScoreLabel);
    }

    private void initWalls()
    {
        walls = new JLabel[4];
        walls[0] = new JLabel();
        walls[0].setBounds(0, 0, 500, 25);

        walls[1] = new JLabel();
        walls[1].setBounds(0, 0, 25, 475);

        walls[2] = new JLabel();
        walls[2].setBounds(0, 450, 500, 25);

        walls[3] = new JLabel();
        walls[3].setBounds(475, 0, 25, 475);

        for(int i = 0; i < 4; i++)
        {
            walls[i].setBackground(Color.BLACK);
            walls[i].setOpaque(true);
            this.add(walls[i]);
        }
    }

    private void initFruit()
    {
        this.fruit = new JLabel();
        this.fruit.setBounds(50, 50, 25, 25);
        this.fruit.setBackground(Color.GREEN);
        this.fruit.setOpaque(true);

        this.add(fruit);
    }

    private boolean wallCollision()
    {
        if(snake.getSnakePart(0).getY() <= 10)
            return true;
        else if(snake.getSnakePart(0).getY() >= 450)
            return true;
        else if(snake.getSnakePart(0).getX() <= 10)
            return true;
        else if(snake.getSnakePart(0).getX() >= 475)
            return true;
        
        return false;
    }

    private boolean tailCollision()
    {
        for(int i = 1; i < snake.getSnakeLength(); i++)
        {
            if(snake.getSnakePart(0).getX() == snake.getSnakePart(i).getX() &&
                snake.getSnakePart(0).getY() == snake.getSnakePart(i).getY())
                    return true;
        }
        return false;
    }

    private boolean fruitCollision()
    {
        Random random = new Random();
        int xFactor = random.nextInt(17)+1;
        int yFactor = random.nextInt(17)+1;
        if(snake.getSnakePart(0).getX() == fruit.getX() && snake.getSnakePart(0).getY() == fruit.getY())
        {
            fruit.setBounds(25*xFactor, 25*yFactor, 25, 25);
            return true;
        }
        return false;
    }

    private void expandSanke()
    {
        SnakePart snakePart = new SnakePart();
        snake.setSnakeLength(snake.getSnakeLength() + 1);
        this.add(snakePart);
        snake.setSnakePart(snakePart);
    }

    private void gameOver()
    {
        timer.stop();

        String[] response = {"Kyllä"};
        JOptionPane.showOptionDialog(
                                null,
                                "Hävisit pelin!         Pisteet: " + highScore,
                                "Kuolit",
                                JOptionPane.YES_OPTION,
                                JOptionPane.INFORMATION_MESSAGE,
                                null,
                                response,
                                0);
        
        System.exit(0);
        
        
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {

        //Wall collision detection
        if(this.wallCollision() == true)
            this.gameOver();
        
        //Tail collision detection
        if(this.tailCollision() == true)
            this.gameOver();
        
        //Fruit collision
        if(this.fruitCollision() == true)
        {
            this.expandSanke();
            highScore++;
            highScoreLabel.setText("Pisteet: " + highScore);
        }


        if(snakeIterator == snake.getSnakeLength())
            snakeIterator = 0;

        //Save head location before the new position
        Point headPreviousLocation = new Point(snake.getSnakePart(0).getX(), snake.getSnakePart(0).getY());

        //Move head to the new position
        snake.getSnakePart(0).setLocation(snake.getSnakePart(0).getX() + xDirection,
            snake.getSnakePart(0).getY() + yDirection);
        
        //Make the tail follow
        snake.getSnakePart(snakeIterator).setLocation(headPreviousLocation);
        snakeIterator++;
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        switch(e.getKeyCode())
        {
            case 37:
                //Left
                if(this.getXDirection() != 25) //Prevents from moving to opposite direction
                    this.setXDirection(-25);
                
                this.setYDirection(0);
                break;
            case 38:
                //Up
                if(this.getYDirection() != 25) //Prevents from moving to opposite direction
                    this.setYDirection(-25);
                
                this.setXDirection(0);
                break;
            case 39:
                //Right
                if(this.getXDirection() != -25) //Prevents from moving to opposite direction
                    this.setXDirection(25);
                
                this.setYDirection(0);
                break;
            case 40:
                //Down
                if(this.getYDirection() != -25) //Prevents from moving to opposite direction
                    this.setYDirection(25);
                
                this.setXDirection(0);
                break;
            default:
                System.out.println("INVALID BUTTON");
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e)
    {}

    @Override
    public void keyReleased(KeyEvent e)
    {}

    //Getters and setters
    private int getXDirection()
    {
        return this.xDirection;
    }

    private void setXDirection(final int xDirection)
    {
        this.xDirection = xDirection;
    }

    private int getYDirection()
    {
        return this.yDirection;
    }

    private void setYDirection(final int yDirection)
    {
        this.yDirection = yDirection;
    }
}