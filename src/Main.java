import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Terraingen tg = new Terraingen(100,100,0.6f, 5, 2, 6);
		//Terraingen tg = setGenerator();
		tg.show(6);
	}
	
	public static Terraingen setGenerator(){
		Scanner scan = new Scanner(System.in);
		int width = 0, height = 0, birth = 0, death = 0, iteration = 0;
		float ratio = 0;

		System.out.println("Width ?");
		width = scan.nextInt();
		
		System.out.println("Height ?");
		height = scan.nextInt();
		
		System.out.println("Spawn rate ?");
		ratio = scan.nextFloat();
		
		System.out.println("Birth Limit ? (Lives if more than x neighbours)");
		birth = scan.nextInt();
		
		System.out.println("Death Limit ? (Dies if less than x neighbours)");
		death = scan.nextInt();
		
		System.out.println("Number of iterations ?");
		iteration = scan.nextInt();
		
		return new Terraingen(width,height,ratio,birth,death,iteration);
	}
	
}
