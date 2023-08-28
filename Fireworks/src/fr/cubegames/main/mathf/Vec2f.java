package fr.cubegames.main.mathf;

public class Vec2f {
	
private float x, y;
	
	public Vec2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vec2f() {
		this.x = 0;
		this.y = 0;
	}
	
	public float getX() {
		return x;
	}
	
	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}
	
	public float getY() {
		return y;
	}
	
	public void setX(float data) {
		x = data;
	}
	
	public void setY(float data) {
		y = data;
	}
	
	public void addX(float data) {
		x += data;
	}
	
	public void addY(float data) {
		y += data;
	}
	
	public void subX(float data) {
		x -= data;
	}
	
	public void subY(float data) {
		y -= data;
	}
	
}