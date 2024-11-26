package main;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLOutput;

public class GamePanel extends JPanel implements  Runnable {
    final int originalTieSize = 16;
    final int scale = 3;

    final int tileSize = originalTieSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    int FPS = 60;

    KeyHanlder keyH = new KeyHanlder();
    Thread gameThread;

    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel () {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
//    public void run() {
//        double drawInterval = 1000000000/FPS;
//        double v = System.nanoTime() + drawInterval;
//        long lastTime = System.nanoTime();
//        long timer = 0;
//        long drawCount = 0;
//        while(gameThread != null) {
//            timer += (long) (v - lastTime);
//
//            update();
//            repaint();
//            drawCount++;
//
//            if(timer >= 1000000000) {
//                System.out.println("FPS:" + drawCount);
//                drawCount = 0;
//                timer = 0;
//            }
//
//
//            try {
//                double remainingTime = v - System.nanoTime();
//                remainingTime = remainingTime/1000000;
//                if(remainingTime < 0) {
//                    remainingTime = 0;
//                }
//                Thread.sleep((long)remainingTime);
//
//                v += drawInterval;
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }

    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1)  {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if(timer > 1000000000) {
                System.out.println("FPS: " + FPS);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        if(keyH.upPressed) {
            playerY -= playerSpeed;
        } else if(keyH.downPressed) {
            playerY += playerSpeed;
        } else if(keyH.leftPressed) {
            playerX -= playerSpeed;
        } else if(keyH.rightPressed) {
            playerX += playerSpeed;
        }

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.white);

        g2.fillRect(playerX, playerY, tileSize, tileSize);

        g2.dispose();
    }
}
