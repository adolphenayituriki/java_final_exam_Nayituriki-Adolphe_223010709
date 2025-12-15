package com.form;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    //constructor to take the image location or path
    public BackgroundPanel(String imagePath) {
        try {
            backgroundImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
        }catch (Exception e){
            System.out.println("Error Loading Image: "+ e.getMessage());
        }
        setLayout(new BorderLayout());
    }
@Override
public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (backgroundImage != null) {
        g.drawImage(backgroundImage, 0, 0, getWidth(), getWidth(), this);
    }
}
}
