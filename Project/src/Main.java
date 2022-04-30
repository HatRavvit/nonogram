import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		Scanner scn = new Scanner(System.in);
		String inputMode;
		String fileName1;
		String fileName2;
		String chosen;
		String userRow;
		String userColumn;
		Nonogram nonogram;
		//Choose problem
		fileName1 = "problems\\example";
		fileName2 = "problems\\answer";
		System.out.println("Choose Problem (1~10):");
		chosen = scn.nextLine();
		fileName1 = fileName1.concat(chosen+".txt");
		fileName2 = fileName2.concat(chosen+".txt");
		//Make
		nonogram = new Nonogram(fileName1,fileName2);
		nonogram.printBoard();
		//
		while(!nonogram.checkFinish()) {
			System.out.println("Select input mode (1:бс,2:X,3:see answer):");
			inputMode = scn.nextLine();
			if(!inputMode.equals("3")) {
				System.out.println("Input row:");
				userRow = scn.nextLine();
				System.out.println("Input column:");
				userColumn = scn.nextLine();
				nonogram.inputUpdate(inputMode, userRow, userColumn);
				nonogram.printBoard();
				if(nonogram.getLife()==0) {
					System.out.println("Failed!");
					break;
				}
			}
			else {
				System.out.println("See answer");
				nonogram.printAnswer();
				break;
			}
		}
		
		if(nonogram.checkFinish()) {
			System.out.println("Success!");
		}
		scn.close();
	}

}