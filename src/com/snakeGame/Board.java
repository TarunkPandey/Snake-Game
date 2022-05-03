package com.snakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*; // for ActionListener

                           //All basic coding/functionalities of Frame as well as game is done here

public class Board extends JPanel implements ActionListener { //JPanel acts as a container.

    private Image apple;
    private Image dot;
    private Image head;

    private final int DOT_SIZE = 10; //size of a dot of the snake.
    private final int ALL_DOTS = 900;
    private final int RANDOM_POSITION = 29; //Used to set random location of the apple b/w 10 to 30, otherwise apple location goes out of the Frame.// Apple is located b/w 0 to 300.

    private final int [] x = new int[ALL_DOTS];// (300*300)/(10*10)=900 dots on the Frame.
    private final int [] y = new int[ALL_DOTS];// x and y is used to set the location of the snake.

    private int apple_x; // for setting the location of apple.
    private int apple_y;

    private int dots;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;

    private boolean inGame= true;

    private Timer timer; // Object of Timer class. Timer class is used put a delay on snake movement;

    Board(){

        addKeyListener(new TAdapter()); // used to control snake movements. Call to TAdapter class
        setFocusable(true); //keyListener works when setFocusable is set true.
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(300,300)); /*Dimension is in awt package and setPreferredSize is in swing package.
                                                                This line sets the dimension of the frame.Acts as a setter method.*/
        loadImages(); //method call
        initGame();
    }
    public void loadImages(){ // putting icons on the Frame.
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("com/snakeGame/icons/apple.png"));
        apple = i1.getImage();

        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("com/snakeGame/icons/dot.png"));
        dot = i2.getImage();

        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("com/snakeGame/icons/head.png"));
        head = i3.getImage();
    }

    public void initGame(){ //Making basic snake and apple at the start of the game.
        dots = 3; //snake has 3 dots in the start.

        for (int i = 0; i <dots ; i++) { //3 dots of the snake are behind each other at the start of the game.
            x[i] = 50-i * DOT_SIZE; // Sets the location of the snake at the start of the game. (50-i*DOT_SIZE) sets the location of 2nd and 3rd dots behind the 1st dot.
            y[i] = 50; // Snake always starts from the same location at the start of the game.
        }
        locateApple();

        timer = new Timer(140,this); //this works with ActionListener interface.
        timer.start();
    }

    public void locateApple(){
        int r = (int) (Math.random()*RANDOM_POSITION); // Math.random generates randoms integers b/w 0 and 1. e.g: 0.6 * 20 = 12.
        apple_x = (r * DOT_SIZE); //apple_x = 12 * 10 = 120. x coordinate of apple.

        r = (int) (Math.random()*RANDOM_POSITION);
        apple_y = (r * DOT_SIZE);
    }

    public void checkApple(){ // Used to check if snake head meets apple or not.
        if ((x[0]==apple_x) && (y[0]==apple_y)){
            dots++; // Snake length increases
            locateApple();
        }
    }

    public void paintComponent(Graphics g){ //repaint() calls this method internally. This is overriding

      super.paintComponent(g);

      draw(g);
    }
    public void draw(Graphics g){
        if (inGame){
            g.drawImage(apple,apple_x,apple_y,this);

            for (int i = 0; i <dots ; i++) {
                if (i==0){
                    g.drawImage(head,x[i],y[i],this);
                }
                else {
                    g.drawImage(dot, x[i],y[i],this);
                }
            }
            Toolkit.getDefaultToolkit().sync();
        }else{
            gameOver(g);
        }
    }
    public void gameOver(Graphics g){ // for displaying "Game Over" msg.
        String msg = "Game Over";
        Font font = new Font("SAN_SERIF",Font.BOLD,14);
        FontMetrics metrices = getFontMetrics(font);

        g.setColor(Color.YELLOW);
        g.setFont(font);
        g.drawString(msg,(300- metrices.stringWidth(msg))/2,300/2); //display location of "GAME OVER"
    }

    public void checkCollision(){

        for (int i = dots; i >0 ; i--) {
            if ( (i>4) && (x[0] == x[i]) && (y[0]==y[i])){ //Snake can collide with itself only if its length is more than 4.
               inGame =false;
            }
        }

        if (y[0]>= 300){
            inGame = false;
        }
        if (x[0]>= 300){
            inGame = false;
        }
        if (x[0]< 0){
            inGame = false;
        }
        if (y[0]< 0){
            inGame = false;
        }
        if(!inGame){
            timer.stop(); // stops the game
        }
    }
    public void move(){

        for (int i = dots; i >0 ; i--) { // to move rest of body of snake with snake
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if(leftDirection){
            x[0] = x[0] -  DOT_SIZE; //to move the head of snake to left direction
        }
        if (rightDirection){
            x[0]+=DOT_SIZE;
        }
        if (upDirection){
            y[0] = y[0] -  DOT_SIZE;
        }
        if (downDirection){
            y[0]+=DOT_SIZE;
        }
    }

    public void actionPerformed(ActionEvent ae){// Overriding this method from the interface ActionListener.
         if (inGame){
             checkApple();
             checkCollision();
             move();
         }

         repaint(); // this method is called when the look of the component changes, i.e, repaint the snake when it changes its location.
    }
    private class TAdapter extends KeyAdapter{

        @Override
        public void keyPressed(KeyEvent e) { // invoked when a key is pressed. Overriding this method from KeyAdapter class.
            int key = e.getKeyCode();

            if (key== KeyEvent.VK_LEFT && (!rightDirection)){
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if (key== KeyEvent.VK_RIGHT && (!leftDirection)){
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if (key== KeyEvent.VK_UP && (!downDirection)){
                leftDirection = false;
                upDirection = true;
                rightDirection = false;
            }
            if (key== KeyEvent.VK_DOWN && (!upDirection)){
                leftDirection = false;
                rightDirection = false;
                downDirection = true;
            }
        }
    }

}
