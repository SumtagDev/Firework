package fr.cubegames.main.core;

import java.util.Random;

import fr.cubegames.main.mathf.Vec2f;

public class ExplodeFirework {
	
	private Firework firework;
	
	public int size = 10 + new Random().nextInt(5);
	
	public Vec2f pos;
	public Vec2f velocity = new Vec2f();
	
	public float mass = 0.5f;
	public float force = 2.5f;
	
	public static final int GREEN = 0;
	public static final int BLUE = 1;
	public static final int RED = 2;
	
	public int[] greenColors = new int[] {0x1CEFAA, 0x1CEF6A, 0x0FD826, 0x11FF2D};
	public int[] redColors = new int[] {0xFF6A00, 0xFF5200, 0xFF3700, 0xFFAA00};
	public int[] blueColors = new int[] {0x0FE2AA, 0x11FFD9, 0x0FE2BF, 0x11C1B3};
	
	public int color;
	
	public double rotation_velocity = 0;
	
	public ExplodeFirework(Vec2f pos, int colorData, Firework firework) {
		this.pos = pos;
		this.firework = firework;
		
		switch(colorData) {
			case GREEN:
				this.color = greenColors[new Random().nextInt(greenColors.length)];
				break;
			case BLUE:
				this.color = blueColors[new Random().nextInt(blueColors.length)];
				break;
			case RED:
				this.color = redColors[new Random().nextInt(redColors.length)];
				break;
		}
		
	}
	
	float dir = 0;
	
	boolean direction = new Random().nextBoolean();
	boolean direction2 = new Random().nextBoolean();
	
	public void update() {
		
		float massData = force * mass;
		
		rotation_velocity += massData * new Random().nextFloat(10f);
		
		float xDir = 0;
		float yDir = 0;
		
		if(direction) {
			xDir += ((new Random().nextFloat(0.5f) / new Random().nextFloat(1f)) / 3) / 2;
		}else {
			xDir -= ((new Random().nextFloat(0.5f) / new Random().nextFloat(1f)) / 3) / 2;
		}
		
		if(direction2) {
			yDir = (-massData * new Random().nextFloat(1f)) / new Random().nextFloat(1f, 4f);
		}else {
			yDir = (massData * new Random().nextFloat(1f)) / new Random().nextFloat(1f, 4f);
		}
		
		xDir *= new Random().nextFloat(-2f, 5f);
		yDir *= new Random().nextFloat(-2f, 5f);
		
		velocity.addY(yDir);
		velocity.addX(xDir * new Random().nextFloat(2f));
		
		pos.addY(velocity.getY());
		pos.addX(velocity.getX());
	}
	
	public void render() {
		firework.getCore().getMain().getRenderer().drawRotateRect((int) pos.getX(), (int) pos.getY(), size, size, rotation_velocity, color);
	}
	
}