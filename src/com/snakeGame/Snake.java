package com.snakeGame;

import javax.swing.*;
                      //CONCEPT USED : AWT & SWING & BASIC CORE JAVA

public class Snake extends JFrame { //JFrame is a window

    Snake(){                      // Frame/Game runs from here.
        super("Snake Game"); // Sets the name of the game at the title bar of Frame
        add(new Board());        //Used to add things to frame.
        pack();                  //Used to set the size of the Frame.Internally calls setPreferredSize.

        setLocationRelativeTo(null); //Sets the location of the Frame in the middle of the screen.
//      setLocation(400,200);        //Used to set Frame location anywhere we want on the screen.
        setResizable(false);          // Restricts Frame from resizing.

    }

    public static void main(String[] args) {
        new Snake().setVisible(true); //setVisible is in swing package. new Snake() is the object of Snake class
    }
}
