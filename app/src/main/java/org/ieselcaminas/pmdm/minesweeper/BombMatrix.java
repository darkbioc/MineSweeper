package org.ieselcaminas.pmdm.minesweeper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

class BombMatrix
{
	private int[][] bombMatrix = new int[Singleton.sharedInstance().getNumRows()][Singleton.sharedInstance().getNumCols()];
	private int numBombs = Singleton.sharedInstance().getNumBombs();
	TextView tv;
	// 0=safe -1=bomb
	public BombMatrix()
	{
		while(numBombs>0)
		{
			int row = getRandomNumberInRange(0,Singleton.sharedInstance().getNumRows()-1);
			int col = getRandomNumberInRange(0,Singleton.sharedInstance().getNumCols()-1);
			if(bombMatrix[row][col] != -1)
			{
				bombMatrix[row][col] = -1;
				numBombs--;
			}
		}
		for(int i=0; i < Singleton.sharedInstance().getNumRows(); i++)
		{
			for(int j=0; j < Singleton.sharedInstance().getNumCols(); j++)
			{
				if(bombMatrix[i][j] != -1)
				{
					for(int r=-1; r <= 1; r++)
					{
						for(int c=-1; c <= 1; c++)
						{
							try
							{
								if(bombMatrix[i + r][j + c] == -1 && !(c == 0 && r == 0))
								{
									bombMatrix[i][j]++;
								}
							}
							catch(ArrayIndexOutOfBoundsException e)
							{

							}
						}
					}
				}
			}
		}
	}
	public void printMatrix()
	{
		for(int i=0; i < Singleton.sharedInstance().getNumRows(); i++)
		{
			for(int j=0; j < Singleton.sharedInstance().getNumCols(); j++)
			{
				System.out.print(bombMatrix[i][j]+" ");
			}
			System.out.println();
		}
	}
	private static int getRandomNumberInRange(int min, int max)
	{
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	public int getValue(int i, int j)
	{
		return bombMatrix[i][j];
	}

	public View getTextView(Context context, int i, int j)
	{
		tv = new TextView(context);
		tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
		tv.setTypeface(Typeface.DEFAULT_BOLD);
		float scale=context.getResources().getDisplayMetrics().density;
		tv.setPadding((int)(scale*6),0,0,0);
		tv.setText(""+bombMatrix[i][j]);

		switch(tv.getText().toString())
		{
			case "1":
				tv.setTextColor(Color.BLUE);
				break;
			case "2":
				tv.setTextColor(Color.GREEN);
				break;
			case "3":
				tv.setTextColor(Color.RED);
				break;
			case "4":
				tv.setTextColor(Color.CYAN);
				break;
			case "5":
				tv.setTextColor(Color.YELLOW);
				break;
			case "6":
				tv.setTextColor(Color.MAGENTA);
				break;
			case "7":
				tv.setTextColor(Color.GRAY);
				break;
			case "8":
				tv.setTextColor(Color.DKGRAY);
				break;
			case "0":
				tv.setText("");

		}
		return tv;
	}
}