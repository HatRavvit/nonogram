import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Nonogram {
	int rowInfo[][];
	int colInfo[][];
	int rcount[];
	int ccount[];
	char board[][];
	char answer[][];
	int rows;
	int columns;
	int life;
	int maxRowElement;
	int maxColumnElement;
	
	public Nonogram(String fileName1, String fileName2) throws FileNotFoundException {
		this.life = 10;
		//Open file
		Scanner efs = new Scanner(new File(fileName1));
		Scanner afs = new Scanner(new File(fileName2));
		//Get row & column from file
		String[] inputSize = efs.next().split(",");
		rows = Integer.parseInt(inputSize[0]);
		columns = Integer.parseInt(inputSize[1]);
		rowInfo = new int[rows][];
		colInfo = new int[columns][];
		board = new char[rows][columns];
		//Get row information
		for(int i=0;i<rows;i++) {
			String[] rowInfos = efs.next().split(",");
			rowInfo[i] = new int[rowInfos.length];
			for(int j=0;j<rowInfos.length;j++) {
				rowInfo[i][j] = Integer.parseInt(rowInfos[j]);
			}
		}
		//Get column information
		for(int i=0;i<columns;i++) {
			String[] colInfos = efs.next().split(",");
			colInfo[i] = new int[colInfos.length];
			for(int j=0;j<colInfos.length;j++) {
				colInfo[i][j] = Integer.parseInt(colInfos[j]);
			}
		}
		//Initialize board
		for(int i=0;i<rows;i++) {
			for(int j=0;j<columns;j++) {
				board[i][j] = '*';
			}
		}
		//If some rows are zero
		for(int i=0;i<rows;i++) {
			if(rowInfo[i][0]==0) {
				for(int j=0;j<columns;j++) {
					board[i][j]='X';
				}
			}
		}
		//If some columns are zero
		for(int i=0;i<columns;i++) {
			if(colInfo[i][0]==0) {
				for(int j=0;j<rows;j++) {
					board[j][i]='X';
				}
			}
		}
		//Get answer
		answer = new char[rows][columns];
		for(int i=0;i<rows;i++) {
			char[] ans = afs.next().toCharArray();
			for(int j=0;j<columns;j++) {
				answer[i][j] = ans[2*j];
			}
		}
		//Initialize Other values
		rcount = new int[rows];
		ccount = new int[columns];
		maxRowElement = findMaxRow();
		maxColumnElement = findMaxColumn();
		//Close file
		efs.close();
		afs.close();
	}
	
	public int getLife() {
		return this.life;
	}
	
	public void printBoard(){
		//Print column coordinate
		for(int i=0;i<=maxRowElement;i++) {
			System.out.print("   ");
		}
		for(int i=0;i<columns;i++) {
			System.out.printf("%2d ",i+1);
			ccount[i] = 0;
		}
		System.out.print("\n");
		//Print column information
		for(int i=maxColumnElement;i>0;i--) {
			for(int j=0;j<=maxRowElement;j++) {
				System.out.print("   ");
			}
			for(int j=0;j<columns;j++) {
				if(colInfo[j].length>=i) {
					System.out.printf("%2d ",colInfo[j][ccount[j]++]);
				}
				else {
					System.out.print("   ");
				}
			}
			System.out.print("\n");
		}
		//Print row information
		for(int i=0;i<rows;i++) {
			rcount[i] = 0;
		}
		for(int i=0;i<rows;i++) {
			System.out.printf("%2d:", i+1);
			for(int j=maxRowElement;j>0;j--) {
				if(rowInfo[i].length>=j) {
					System.out.printf("%2d ",rowInfo[i][rcount[i]++]);
				}
				else {
					System.out.print("   ");
				}
			}
			System.out.print(" ");
			for(int j=0;j<columns;j++) {
				System.out.print(board[i][j]+"  ");
			}
			System.out.print("\n");
		}
		System.out.println("Life : "+life);
	}
	
	public void inputUpdate(String mode, String userRow, String userColumn) {
		int r = Integer.parseInt(userRow)-1;
		int c = Integer.parseInt(userColumn)-1;
		
		if(r>=rows||c>=columns) {
			System.out.println("Invalid input!");
			return;
		}
		else if(mode.equals("1")) {
			if(isCorrect('бс', r, c)) {
				board[r][c] = 'бс';
				
			}
			else {
				System.out.println("Wrong position!");
				board[r][c] = 'X';
				life--;
			}
		}
		else if(mode.equals("2")){
			if(board[r][c]!='бс') {
				board[r][c] = 'X';
			}
		}
		else {
			System.out.println("Invalid mode!");
		}
		lineFinish();
	}
	
	public void printAnswer() {
		//Print column coordinate
		for(int i=0;i<=maxRowElement;i++) {
			System.out.print("   ");
		}
		for(int i=0;i<columns;i++) {
			System.out.printf("%2d ",i+1);
			ccount[i] = 0;
		}
		System.out.print("\n");
		//Print column information
		for(int i=maxColumnElement;i>0;i--) {
			for(int j=0;j<=maxRowElement;j++) {
				System.out.print("   ");
			}
			for(int j=0;j<columns;j++) {
				if(colInfo[j].length>=i) {
					System.out.printf("%2d ",colInfo[j][ccount[j]++]);
				}
				else {
					System.out.print("   ");
				}
			}
			System.out.print("\n");
		}
		//Print row information
		for(int i=0;i<rows;i++) {
			rcount[i] = 0;
		}
		for(int i=0;i<rows;i++) {
			System.out.printf("%2d:", i+1);
			for(int j=maxRowElement;j>0;j--) {
				if(rowInfo[i].length>=j) {
					System.out.printf("%2d ",rowInfo[i][rcount[i]++]);
				}
				else {
					System.out.print("   ");
				}
			}
			System.out.print(" ");
			for(int j=0;j<columns;j++) {
				System.out.print(answer[i][j]+"  ");
			}
			System.out.print("\n");
		}
	}
	
	public boolean checkFinish() {
		for(int i=0;i<rows;i++) {
			for(int j=0;j<columns;j++) {
				if(board[i][j]!=answer[i][j]) return false;
			}
		}
		return true;
	}
	
	public boolean isCorrect(char block, int r, int c) {
		if(answer[r][c]==block) return true;
		else return false;
	}

	public void lineFinish() {
		int count, n;
		//Check rows
		int rowCount[] = new int[maxRowElement];
		for(int i=0;i<rows;i++) {
			for(int j=0;j<maxRowElement;j++) {
				rowCount[j] = 0;
			}
			count=0;
			n=0;
			//Check what user painted
			for(int j=0;j<columns;j++) {
				if(board[i][j]=='бс'&&board[i][j]==answer[i][j]) {
					count++;
				}
				else if(count!=0) {
					rowCount[n++] = count;
					count=0;
				}
			}
			if(count!=0) {
				rowCount[n++] = count;
			}
			//Compare with row information
			count=0;
			for(int j=0;j<rowInfo[i].length;j++) {
				if(rowInfo[i][j]==rowCount[j]) {
					count++;
				}
			}
			//If user painted all correctly, mark other part X
			if(count==rowInfo[i].length) {
				for(int j=0;j<columns;j++) {
					if(board[i][j]=='*') {
						board[i][j] = 'X';
					}
				}
			}
		}
		//Check columns
		int colCount[] = new int[maxColumnElement];
		for(int i=0;i<columns;i++) {
			for(int j=0;j<maxColumnElement;j++) {
				colCount[j] = 0;
			}
			count=0;
			n=0;
			//Check what user painted
			for(int j=0;j<rows;j++) {
				if(board[j][i]=='бс'&&board[j][i]==answer[j][i]) {
					count++;
				}
				else if(count!=0) {
					 colCount[n++] = count;
					 count=0;
				}
			}
			if(count!=0) {
				colCount[n++] = count;
			}
			//Compare with row information
			count=0;
			for(int j=0;j<colInfo[i].length;j++) {
				if(colInfo[i][j]==colCount[j]) {
					count++;
				}
			}
			//If user painted all correctly, mark other part X
			if(count==colInfo[i].length) {
				for(int j=0;j<columns;j++) {
					if(board[j][i]=='*') {
						board[j][i] = 'X';
					}
				}
			}
		}
	}
	
	public int findMaxRow() {
		int temp=0;
		for(int i=0;i<rows;i++) {
			if(rowInfo[i].length>temp) {
				temp = rowInfo[i].length;
			}
		}
		return temp;
	}
	
	public int findMaxColumn() {
		int temp=0;
		for(int i=0;i<columns;i++) {
			if(colInfo[i].length>temp) {
				temp = colInfo[i].length;
			}
		}
		return temp;
	}
	
}