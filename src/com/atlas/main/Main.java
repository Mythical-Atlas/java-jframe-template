package com.atlas.main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.atlas.states.State;

@SuppressWarnings("serial")
public class Main extends JPanel implements KeyListener, Runnable {
	State testState;
	Thread thread;
	
	static final int FPS = 60;
	static final int SCALE = 1;
	static final int WIDTH = 640;
	static final int HEIGHT = 480;
	static final String TITLE = "JFrame Template";

	public static void main(String[] args) {
		Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		JFrame frame = new JFrame(TITLE);
		
		frame.setBounds(center.x - WIDTH * SCALE / 2, center.y - HEIGHT * SCALE / 2, WIDTH * SCALE, HEIGHT * SCALE);
		frame.setContentPane(new Main());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}

	public Main() {
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setFocusable(true);
		requestFocus();
	}

	public void addNotify() {
		super.addNotify();
		addKeyListener(this);
		thread = new Thread(this);
		thread.start();
	}

	@SuppressWarnings("static-access")
	public void run() {
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = (Graphics2D)image.getGraphics();
		
		while(true) {
			long start = System.nanoTime();
			
			testState.update();
			
			testState.draw(graphics);
			
			Graphics graphicsTemp = getGraphics();
			
			graphicsTemp.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
			graphicsTemp.dispose();
			
			long elapsed = System.nanoTime() - start;
			long wait = 1000 / FPS - elapsed / 1000000;
			
			if(wait <= 0) {wait = 0;}
			
			try {thread.sleep(wait);}
			catch(Exception e) {e.printStackTrace();}
		}
	}

	public void keyTyped(KeyEvent key) {testState.keyTyped(key.getKeyCode());}
	public void keyPressed(KeyEvent key) {testState.keyPressed(key.getKeyCode());}
	public void keyReleased(KeyEvent key) {testState.keyReleased(key.getKeyCode());}
	
	public void mouseClicked(MouseEvent mouse) {testState.mouseClicked(mouse);}
	public void mouseEntered(MouseEvent mouse) {testState.mouseEntered(mouse);}
	public void mouseExited(MouseEvent mouse) {testState.mouseExited(mouse);}
	public void mousePressed(MouseEvent mouse) {testState.mousePressed(mouse);}
	public void mouseReleased(MouseEvent mouse) {testState.mouseReleased(mouse);}
}