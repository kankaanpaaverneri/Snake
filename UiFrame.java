import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Random;

public class UiFrame extends JFrame implements ActionListener, KeyListener
{
    private Snake snake;
    private Timer timer;
    private int timerCounter = 0;
    private int bigFruitLifetimeCounter = 0;
    private int xDirection = -25, yDirection = 0;
    private int snakeIterator = 1;
    private JLabel[] walls;
    private JLabel fruit;
    private JLabel bigFruit;
    private JLabel highScoreLabel;
    private int highScore = 0;
    Audio slurpSound, bigFruitCatchSound;

    public UiFrame()
    {
        //Audio setup
        try
        {
            this.slurpSound = new Audio("slurp_sfx.wav");
            this.bigFruitCatchSound = new Audio("ai_etta_mita_luksusta_sfx.wav");
        }

        catch(Exception e)
        {
            e.printStackTrace();
        }

        //Timer setup
        this.timer = new Timer(500, this);
        this.timer.start();

        //Frame setup
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.initHighScore();
        this.initWalls();
        this.initSnakeToUiFrame();
        this.initFruit();
        this.initBigFruit();
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
        this.highScoreLabel.setForeground(Color.WHITE);
        this.highScoreLabel.setBounds(275, 0, 200, 25);
        this.highScoreLabel.setBackground(Color.BLACK);
        this.highScoreLabel.setOpaque(true);
        this.add(highScoreLabel);
    }

    private void initWalls()
    {
        this.walls = new JLabel[4];
        this.walls[0] = new JLabel();
        this.walls[0].setBounds(0, 0, 500, 25);

        this.walls[1] = new JLabel();
        this.walls[1].setBounds(0, 0, 25, 475);

        this.walls[2] = new JLabel();
        this.walls[2].setBounds(0, 450, 500, 25);

        this.walls[3] = new JLabel();
        this.walls[3].setBounds(475, 0, 25, 475);

        for(int i = 0; i < 4; i++)
        {
            this.walls[i].setBackground(Color.BLACK);
            this.walls[i].setOpaque(true);
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

    private void initBigFruit()
    {
        this.bigFruit = new JLabel();
        //this.bigFruit.setBounds(200, 200, 25, 25);
        this.bigFruit.setBackground(Color.CYAN);
        this.bigFruit.setOpaque(true);

        this.add(bigFruit);
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
        if(snake.getSnakePart(0).getX() == this.fruit.getX() && snake.getSnakePart(0).getY() == this.fruit.getY())
        {
            this.fruit.setBounds(25*xFactor, 25*yFactor, 25, 25);
            return true;
        }
        return false;
    }

    private boolean bigFruitCollision()
    {
        if(snake.getSnakePart(0).getX() == this.bigFruit.getX() &&
            snake.getSnakePart(0).getY() == this.bigFruit.getY())
        {
            return true;
        }
        return false;
    }

    private void expandSanke()
    {
        SnakePart snakePart = new SnakePart();
        this.add(snakePart);
        snake.setSnakeLength(snake.getSnakeLength() + 1);
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
            this.slurpSound.getClip().setMicrosecondPosition(0);
            this.slurpSound.getClip().start();
            this.expandSanke();
            this.highScore++;
            this.highScoreLabel.setText("Pisteet: " + highScore);
            this.timerCounter++;
        }

        //Big fruit collision
        if(this.bigFruitCollision() == true)
        {
            this.slurpSound.getClip().setMicrosecondPosition(0);
            this.slurpSound.getClip().start();
            this.bigFruitCatchSound.getClip().setMicrosecondPosition(0);
            this.bigFruitCatchSound.getClip().start();
            this.expandSanke();
            this.highScore += 10;
            this.highScoreLabel.setText("Pisteet: " + highScore);
            this.bigFruit.setBounds(-500, -500, 25, 25);
        }

        //BigFruit emerges
        if(this.timerCounter == 5)
        {
            this.timerCounter = 0;
            Random random = new Random();
            this.bigFruit.setBounds((random.nextInt(17)+1)*25, (random.nextInt(17)+1)*25, 25, 25);
            this.bigFruit.setOpaque(true);
        }

        //Start the bigFruitLifetimeCounter
        if(this.bigFruit.isOpaque())
        {
            this.bigFruitLifetimeCounter++;
        }

        //If bigFruitLifetimeCounter reaches 20. Then Big Fruit disappears
        if(this.bigFruitLifetimeCounter == 20)
        {
            this.bigFruit.setBounds(-500, -500, 25, 25);
            this.bigFruit.setOpaque(false);
            this.bigFruitLifetimeCounter = 0;
        }

        if(this.snakeIterator == snake.getSnakeLength()-1)
        {
            this.snakeIterator = 1;
        }

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
