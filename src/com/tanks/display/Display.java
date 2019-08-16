package com.tanks.display;

import com.tanks.IO.Input;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

public abstract class Display {

    private static boolean created = false;
    private static JFrame window;

    private static BufferedImage buffer;
    private static int[] bufferData;
    private static Graphics bufferGraphics;
    private static int clearColor;

    private static BufferStrategy bufferStrategy;

    public static void create(int width, int height, String titel, int _clearColor, int numBuffers) {
        if (created) return;  //we need only one window
        created = true;

        window = new JFrame(titel);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Canvas content = new Canvas();
        content.setPreferredSize(new Dimension(width, height));
        clearColor = _clearColor;

        window.setResizable(false);
        window.getContentPane().add(content);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        bufferData = ((DataBufferInt) buffer.getRaster().getDataBuffer()).getData();
        bufferGraphics = buffer.getGraphics();

        ((Graphics2D) bufferGraphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //multibuffer
        content.createBufferStrategy(numBuffers);
        bufferStrategy = content.getBufferStrategy();
    }

    public static void swapBuffers() {

        Graphics g = bufferStrategy.getDrawGraphics();
        g.drawImage(buffer, 0, 0, null);
        bufferStrategy.show();

    }

    public static Graphics2D getGraphics() {
        return (Graphics2D) bufferGraphics;
    }

    public static void clear() {
        Arrays.fill(bufferData, clearColor);
    }

    public static void destroy() {
        if (!created) return;
        window.dispose();
    }

    public static void setTitle(String title) {
        window.setTitle(title);
    }

    public static void addInputListener(Input inputListener) {
        window.add(inputListener);
    }
}