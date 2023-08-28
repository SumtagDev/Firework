package fr.cubegames.main.core;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.cubegames.main.Main;
import fr.cubegames.main.mathf.Vec2f;

public class Firework {
	
	public int size = 32 + new Random().nextInt(10);

	public Vec2f pos = new Vec2f(new Random().nextInt(Main.WIDTH), Main.HEIGHT + size);
	public Vec2f velocity = new Vec2f();
	
	public float mass = 0.2f;
	public float force = 3f + new Random().nextFloat(2f);
	
	public float gravity = 0.1f;
	
	public double rotation_velocity = 0;
	
	private Core core;
	
	public List<ExplodeFirework> fireworks;
	
	public int id;
	
	public int[] greenColors = new int[] {0x1CEFAA, 0x1CEF6A, 0x0FD826, 0x11FF2D};
	public int[] redColors = new int[] {0xFF6A00, 0xFF5200, 0xFF3700, 0xFFAA00};
	public int[] blueColors = new int[] {0x0FE2AA, 0x11FFD9, 0x0FE2BF, 0x11C1B3};
	
	public int color;
	
	public static final int GREEN = 0;
	public static final int BLUE = 1;
	public static final int RED = 2;
	
	private int colorData;
	
	public Firework(int colorData, Core core) {
		this.core = core;
		this.colorData = colorData;
		fireworks = new ArrayList<>();
		this.id = new SecureRandom().nextInt();
		
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
	
	boolean explosed = false;
	
	boolean xMove = new Random().nextBoolean();
	
	private void fire() {
		force -= gravity;
		float massData = force * mass;
		
		rotation_velocity += 2;
		
		velocity.addY(-massData);
		
		if(force <= -3 && !explosed) {
			explosed = true;
			explose();
		}
		
		if(xMove) {
			velocity.addX(new Random().nextFloat(0.05f, 0.1f));			
		}else {
			velocity.subX(new Random().nextFloat(0.05f, 0.1f));
		}
		
		pos.addX(velocity.getX());
		pos.addY(velocity.getY());
	}
	
	int amount = 50 + new Random().nextInt(200);
	
	private void explose() {
		for(int i = 0; i < amount; i++) {
			fireworks.add(new ExplodeFirework(new Vec2f(pos.getX(), pos.getY()), colorData, this));
		}
	}
	
	public void update() {
		fire();
		if(!fireworks.isEmpty()) {
			for(ExplodeFirework f : fireworks) {
				f.update();
			}
		}
		
		if(pos.getY() >= Main.HEIGHT + 200) {
			core.removeFirework(id);
		}
		
	}
	
	public void render() {
		if(!explosed) {
			core.getMain().getRenderer().drawRotateRect((int) pos.getX(), (int) pos.getY(), size, size, rotation_velocity, color);			
		}
		
		if(!fireworks.isEmpty()) {
			for(ExplodeFirework f : fireworks) {
				f.render();
			}			
		}
		
	}
	
	public Core getCore() {
		return core;
	}
	
}