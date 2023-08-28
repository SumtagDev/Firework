package fr.cubegames.main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import fr.cubegames.main.core.Core;
import fr.cubegames.main.renderer.Renderer;

public class Main extends Canvas implements Runnable {
	private static final long serialVersionUID = -7207191290320754226L;
	
	public static int SCALE = 2;
	public static int WIDTH = 1280 * SCALE;
	public static int HEIGHT = 720 * SCALE;
	
	
	private JFrame frame;
	private Thread thread;
	
	private BufferedImage img;
	private int[] pixels;
	
	private Renderer renderer;
	private Core core;
	
	int screenWidth2 = WIDTH;
	int screenHeight2 = HEIGHT;
	BufferedImage tempScreen;
	Graphics2D g2;
	
	public synchronized void start() {
		initframe();
		
		img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		
		tempScreen = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		g2 = (Graphics2D) tempScreen.getGraphics();
		
		renderer = new Renderer(pixels);
		core = new Core(this);
		
		thread = new Thread(this, "main");
		thread.start();
	}
	
	public void run() {
		long timer = System.currentTimeMillis();
		
		double elapsed = 0;
		double nanoSeconds = 1000000000.0 / 60.0;
		long before = System.nanoTime();
		
		int ticks = 0; int frames = 0;
		
		while(true) {
			long now = System.nanoTime();
			elapsed = now - before;
			
			if(elapsed > nanoSeconds) {
				before += nanoSeconds;
				ticks++;
				drawToTempScreen();
				update();
			}else {
				frames++;
				render();
			}
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frame.setTitle("FireWorks [" + frames + " fps " + ticks + " ups]");
				frames = 0;
				ticks = 0;
			}
			
		}
		
	}
	
	private void update() {
		core.update();
		screenWidth2 = frame.getWidth();
		screenHeight2 = frame.getHeight();
	}
	
	private void clear() {
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}
	
	private void drawToTempScreen() {
		g2.drawImage(img, 0, 0, WIDTH, HEIGHT, null);
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		clear();
		
		core.render();
				
		g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
		
		g.dispose();
		bs.show();
		
	}
	
	public static void main(String[] args) {
		Main main = new Main();
		main.start();
	}
	
	private void initframe() {
		frame = new JFrame("FireWorks");
		Dimension size = new Dimension(WIDTH / SCALE, HEIGHT / SCALE);
		frame.setMinimumSize(size);
		frame.setPreferredSize(size);
		frame.setMaximumSize(size);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(true);
		frame.add(this);
		frame.setVisible(true);
	}
	
	public Renderer getRenderer() {
		return renderer;
	}
	
}