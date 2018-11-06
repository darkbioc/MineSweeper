package org.ieselcaminas.pmdm.minesweeper;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity
{
    private MineButton[][] board;
    GridLayout gridLayout;
    FrameLayout frameLayout;



	private boolean gameOver=false;
    private BombMatrix bombMatrix;
    TextView largeText;
    Button ib;
    int numBombs;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        largeText = findViewById(R.id.textView);
        ib = findViewById(R.id.imageButton);
        gridLayout = findViewById(R.id.gridLayout);
        gridLayout.setRowCount(Singleton.sharedInstance().getNumRows());
        gridLayout.setColumnCount(Singleton.sharedInstance().getNumCols());

	    android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
	    setSupportActionBar(toolbar);

	    start();

        ib.setOnClickListener(new View.OnClickListener()
        {
	        @Override public void onClick(View v)
	        {
		        gridLayout.removeAllViews();
				start();
	        }

        });

    }


	@Override public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_main, menu);
		return true;
	}
	@Override public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.action_settings:Intent intent = new Intent(getApplicationContext(),Settings.class);
				startActivityForResult(intent, 1234);
			default:return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent resultIntent)
	{
		if (requestCode == 1234)
		{
			if (resultCode == RESULT_OK)
			{
				start();
			}
		}
	}


	private void start()
	{
		gameOver=false;
		ib.setBackground(getDrawable(R.drawable.msface));
		numBombs = Singleton.sharedInstance().getNumBombs();
		largeText.setText(""+numBombs);
		board = new MineButton[Singleton.sharedInstance().getNumRows()][Singleton.sharedInstance().getNumCols()];
		bombMatrix = new BombMatrix();
		bombMatrix.printMatrix();
		for (int i = 0; i < Singleton.sharedInstance().getNumRows(); i++)
		{
		    for (int j = 0; j < Singleton.sharedInstance().getNumCols(); j++)
		    {
		        board[i][j] = new MineButton(getApplicationContext(), i, j, this);

		        frameLayout = new FrameLayout(this);
		        BackCell backCell = new BackCell(this);
		        frameLayout.addView(backCell);
		        if (bombMatrix.getValue(i, j) == -1) {
		            BombView bomb = new BombView(this);
		            frameLayout.addView(bomb);
		            //board[i][j].setAlpha(0.5f);
		        } else {
		            frameLayout.addView(bombMatrix.getTextView(this, i, j));
		            //board[i][j].setAlpha(0.5f);
		        }
		        frameLayout.addView(board[i][j]);
		        gridLayout.addView(frameLayout);

		        board[i][j].setOnClickListener(new View.OnClickListener()
		        {
			        @Override public void onClick(View v)
			        {
				        MineButton b=(MineButton) v;
				        if(!gameOver && b.getState() != ButtonState.FLAG)
				        {
					        int r=b.getRow();
					        int c=b.getCol();
					        if(bombMatrix.getValue(r, c) != -1)
					        {
						        openButton(r, c);
					        }
					        if(bombMatrix.getValue(r, c) == -1)
					        {
						        gameOver();
					        }
					        b.setAlpha(0f);
					        checkWin();
				        }
			        }
		        });


		    }


		}
	}

	private void openButton(int r, int c)
	{
		board[r][c].setState(ButtonState.OPEN);
		board[r][c].setVisibility(View.INVISIBLE);
		Log.d("LOL","dfghghstr");
		if(bombMatrix.getValue(r ,c) == 0 )
		{
			for(int nr=-1; nr <= 1; nr++)
			{
				for(int nc=-1; nc <= 1; nc++)
				{
					try
					{
						board[nr + r][nc + c].setAlpha(0f);
						if(board[nr + r][nc + c].getState() == ButtonState.CLOSED)
						{
							openButton(nr + r, nc + c);
						}
					}
					catch(ArrayIndexOutOfBoundsException e)
					{

					}
				}
			}
		}
	}

	public void gameOver()
	{
		gameOver=true;
		largeText.setText("GAME OVER");
		ib.setBackground(getDrawable(R.drawable.msfacelose));
		for (int i = 0; i < Singleton.sharedInstance().getNumRows(); i++)
		{
			for(int j=0; j < Singleton.sharedInstance().getNumCols(); j++)
			{
				if (bombMatrix.getValue(i,j)== -1 && board[i][j].getState() != ButtonState.FLAG)
				{
					board[i][j].setAlpha(0f);
				}
				if (board[i][j].getState() == ButtonState.FLAG && bombMatrix.getValue(i,j) != -1)
				{
					board[i][j].setState(ButtonState.WRONG);
				}
			}
		}
	}
	public void putFlag()
	{
		numBombs--;
		largeText.setText(""+numBombs);
	}
	public  void takeFlag()
	{
		numBombs++;
		largeText.setText(""+numBombs);
	}

	public void checkWin()
	{
		int openCount = 0;
			for (int i = 0; i < Singleton.sharedInstance().getNumRows(); i++)
			{
				for(int j=0; j < Singleton.sharedInstance().getNumCols(); j++)
				{
					if(board[i][j].getState() == ButtonState.OPEN)
					{
						openCount++;
					}
				}
			}
			if(openCount==(Singleton.sharedInstance().getNumRows()*Singleton.sharedInstance().getNumCols()-Singleton.sharedInstance().getNumBombs()))
			{
				win();
			}
	}

	private void win()
	{
		for (int i = 0; i < Singleton.sharedInstance().getNumRows(); i++)
		{
			for(int j=0; j < Singleton.sharedInstance().getNumCols(); j++)
			{
				if (bombMatrix.getValue(i,j)== -1 && board[i][j].getState() != ButtonState.FLAG)
				{
					board[i][j].setState(ButtonState.FLAG);
				}
			}
		}
		largeText.setText("WIN!");
		ib.setBackground(getDrawable(R.drawable.msfacewin));
		gameOver=true;
	}
	public boolean isGameOver()
	{
		return gameOver;
	}

}
