//*******************************************************************************************
// Author: Abdullah Najjar
// Description: John Conway's game (Game of Life) implementation, where I implementated as a
// to be read from an input file and drew the structures to be generated using the StdDraw
// library.
// Date: 3 April, 2020
// Version: 1.0
// Bugs: 0
//*******************************************************************************************
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.awt.Color;


public class Life
{

	//States
   int Free = 1;
   int Occupied = 2;
   int BeingBorn = 3;
   int Dying = 4;

	 //
   int Size = 10;
   int r,c;
   int[][] board;//2D-Array

	 //Default Constructor
   public Life()
	 {
      board = new int[Size][Size];
      clearBoard();
   }


	 //Non-Default Constructor
   public Life(int r, int c)
	 {
      board = new int[r][c];

      clearBoard();
   }


	 //To clear the board
   public void clearBoard()
	 {
      for(int i=0; i<board.length; ++i)
			{
         for(int j=0; j<board[i].length; ++j)
				 {
            board[i][j] = Free;
         }
      }
   }

	 //Accessor for being Born
   public void setBorn(int r, int c)
	 {
      board[r][c] = BeingBorn;
   }


	 //Accessor for being Occupied
   public void setOccupied(int r, int c)
	 {
      board[r][c] = Occupied;
   }


	 //Accessor for Dying
   public void setDying(int r, int c)
	 {
      board[r][c] = Dying;
   }


	 //Accessor for being Free
   public void setFree(int r, int c)
	 {
      board[r][c] = Free;
   }


	 //
   public boolean isBorn(int r, int c)
	 {
      return board[r][c] == BeingBorn;
   }


	 //
   public boolean isFree(int r, int c)
	 {
      return board[r][c] == Free;
   }



	 //
   public boolean isDying(int r, int c)
	 {
      return board[r][c] == Dying;
   }


	 //Is occupied
   public boolean isOccupied(int r, int c)
	 {
      return board[r][c] == Occupied;
   }


	 //This method is to count the neighbors
   private int countNeighbors(int r, int c)
	 {
      int count = 0;

      //Rules
      if((r-1) > 0 && (c-1) > 0)
			{
         if(board[r-1][c-1] == Occupied || board[r-1][c-1] == Dying)
            ++count;
      }

      //
      if((r-1) > 0)
			{
         if(board[r-1][c] == Occupied || board[r-1][c] == Dying)
            ++count;
      }

      //
      if((r-1) > 0 && (c+1) < board[r-1].length)
			{
         if(board[r-1][c+1] == Occupied || board[r-1][c+1] == Dying)
            ++count;
      }

      //
      if((c-1) > 0)
			{
         if(board[r][c-1] == Occupied || board[r][c-1] == Dying)
            ++count;
      }

      //
      if((c+1) < board[r].length)
			{
         if(board[r][c+1] == Occupied || board[r][c+1] == Dying)
            ++count;
      }

      //
      if((r+1) < board.length && (c-1) > 0)
			{
         if(board[r+1][c-1] == Occupied || board[r+1][c-1] == Dying)
            ++count;
      }

      //
      if((r+1) < board.length)
			{
         if(board[r+1][c] == Occupied || board[r+1][c] == Dying)
            ++count;
      }

      //
      if((r+1) < board.length && (c+1) < board[r+1].length)
			{
         if(board[r+1][c+1] == Occupied || board[r+1][c+1] == Dying)
            ++count;
      }

      return count;
   }



	 //Method: To fill the board using an input file
   public void fillBoard(Scanner inputFile)
	 {
      while(inputFile.hasNextInt())
			{
         int r = inputFile.nextInt();
         int c = inputFile.nextInt();

         board[r][c] = Occupied;
      }
   }


	 //Method: Next Generation processing
   private void nextGeneration()
	 {
	      for(int i=0; i<board.length; ++i)
				{
	         for(int j=0; j<board[i].length; ++j)
					 {
	            if(board[i][j] == BeingBorn) board[i][j] = Occupied;

	            if(board[i][j] == Dying) board[i][j] = Free;
	         }
	      }
	   }



	 //Method: To play the game
   public void playGame()throws InterruptedException
	 {

      boolean change = false;

      //
      do{
         drawBoard();
         change = false;
         int[][] next = new int[board.length][board[0].length];

         for(int i=0; i<board.length; ++i)
				 {
            for(int j=0; j<board[i].length; ++j)
						{
               next[i][j] = Free;
            }
         }

         for(int i=0; i<board.length; ++i)
				 {
            for(int j=0; j<board[i].length; ++j)
						{
               int n = countNeighbors(i, j);
               if(board[i][j] != Occupied && n == 3)
							 {
                  next[i][j] = BeingBorn;
               }
               else if(board[i][j] == Occupied && (n > 3 || n < 2))
							 {
                  next[i][j] = Dying;
               }
               else if(board[i][j] == Occupied && (n > 3 || n < 2))
							 {
                  next[i][j] = Dying;
               }
            }
         }//forLoop

         for(int i=0; i<board.length; ++i)
				 {
            for(int j=0; j<board[i].length; ++j)
						{
               if(next[i][j] == Dying || next[i][j] == BeingBorn)
							 {
                  change = true;
                  board[i][j] = next[i][j];
               }
            }
         }//forLoop

         nextGeneration();
         try
         {
            Thread.sleep(7000);
         }
				 		catch(InterruptedException e)
         {
            e.printStackTrace();
         }
      }
      while(change);
   }



	 //Method:
   private void drawBoard()
	 {

      int x,y;
      for(int i=0; i<board.length; ++i)
			{

         for(int j=0; j<board[i].length; ++j)
				 {
            if(board[i][j] == Occupied) StdDraw.setPenColor(Color.GREEN);
            else StdDraw.setPenColor(Color.BLACK);

            x = j * 10;
            y = (10 * (board.length - i));

            StdDraw.filledSquare(x,y,5);
         }
      }
   }



	 //Method: To set the Canvas for the board
   private void setCanvas()
	 {
	      StdDraw.setXscale(0, 10 * board.length);
	      StdDraw.setYscale(0, 10 * board[0].length);
	   }


	//Main
	public static void main(String[] args)throws InterruptedException
	{

	      System.out.println("Please enter input file: \n");
	      Scanner Scan = new Scanner(System.in);
	      String filename = Scan.nextLine();

	      try{
	         Scanner FinalScan = new Scanner(new File(filename));
	         int rows = FinalScan.nextInt();
	         int cols = FinalScan.nextInt();

	         Life life = new Life(rows, cols);
	         life.setCanvas();
	         life.fillBoard(FinalScan);
	         life.playGame();
	         FinalScan.close();

	      }
				catch (FileNotFoundException e)
				{
	      }//tryCatchBlock

	}//main

}//class
