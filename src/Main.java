import java.util.Scanner;
import gen.*;

public class Main {

	public static void main(String[] args) {
		Map m = new Map(100,200);
		
	}

	public static Cavegen setGenerator() {
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

		return new Cavegen(width, height, ratio, birth, death, iteration);
	}

}
