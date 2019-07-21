package physics;

public class Vector {

	private float x, y;
	public static Vector g = new Vector(0f,5f);

	public Vector(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}

	public Vector add(Vector other) {
		return new Vector(this.x + other.x,this.y + other.y);
	}
	
	public Vector multiply(float f){
		return new Vector(this.x * f, this.y * f);
	}
	
	public Vector normalize() {
		return this.multiply(1/this.length());
	}
	
	public float length() {
		return (float) Math.sqrt(this.x * this.x + this.y * this.y);
	}

	public float getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
