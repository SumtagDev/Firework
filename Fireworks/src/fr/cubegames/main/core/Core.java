package fr.cubegames.main.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import fr.cubegames.main.Main;

public class Core {
	
	private Main main;
	
	private List<Firework> fireworks;
	
	private int frequence = 300;
	
	public Core(Main main) {
		this.main = main;
		
		fireworks = new ArrayList<>();
		
		initFireworks();
		
		Timer whiteChrono = new Timer();
		whiteChrono.schedule(new TimerTask() {
			public void run() {
				
				addFirework();
				
				frequence += new Random().nextInt(-300, 300);
				
			}
		}, 1000, frequence);
		
	}
	
	public void addFirework() {
		fireworks.add(new Firework(new Random().nextInt(3), this));
	}
	
	public void removeFirework(int id) {
		for(int i = 0; i < fireworks.size(); i++) {
			if(fireworks.get(i).id == id) {
				fireworks.remove(fireworks.get(i));
			}
		}
	}
	
	private void initFireworks() {
		for(int i = 0; i < 1; i++) {
			fireworks.add(new Firework(new Random().nextInt(3), this));
		}
	}
	
	public void update() {
		for(int i = 0; i < fireworks.size(); i++) {
			fireworks.get(i).update();
		}
		System.out.println(frequence);
	}
	
	public void render() {
		for(int i = 0; i < fireworks.size(); i++) {
			fireworks.get(i).render();
		}
	}
	
	public Main getMain() {
		return main;
	}
	
}