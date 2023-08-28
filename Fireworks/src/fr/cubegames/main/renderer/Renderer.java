package fr.cubegames.main.renderer;

import fr.cubegames.main.Main;

public class Renderer {
	
	private int[] pixels;
	
	public Renderer(int[] pixels) {
		this.pixels = pixels;
	}
	
	public void drawPixel(int x, int y, int color) {
		if(x < 0 || y < 0 || x >= Main.WIDTH || y >= Main.HEIGHT) return;
		pixels[x + y * Main.WIDTH] = color;
	}
	
	public void drawRect(int xp, int yp, int w, int h, int color) {
		
		for(int x = 0; x < w; x++) {
			for(int y = 0; y < h; y++) {
				int xx = x + xp;
				int yy = y + yp;
				
				drawPixel(xx, yy, color);
				
			}
		}
		
	}
	
	public void drawRotateRect(double x, double y, double w, double h, double angle, int color) {
		
		double c = Math.cos(Math.toRadians(angle));
		double s = Math.sin(Math.toRadians(angle));
		
		for(double xx = 0; xx < w; xx += 0.3) {
			for(double yy = 0; yy < h; yy += 0.3) {
				double xp = xx - (w / 2);
				double yp = yy - (h / 2);
				
				int xa = (int)x + (int) ((double)((double)xp * c) - ((double)yp * s));
				int ya = (int)y + (int) ((double)((double)xp * s) + ((double)yp * c));
				
				drawPixel(xa, ya, color);
				
			}
		}
		
	}
	
}