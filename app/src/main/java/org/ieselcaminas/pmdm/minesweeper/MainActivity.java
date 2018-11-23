package org.ieselcaminas.pmdm.minesweeper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity
{
    private MineButton[][] board;
    GridLayout gridLayout;
    FrameLayout frameLayout;

	SharedPreferences pref;


	private boolean gameOver=false;
    private BombMatrix bombMatrix;
    TextView largeText;
    Button ib;
    int numBombs;
    int themeApplied;
	int themeID;

	//settings variables
	boolean darkTheme;
	boolean showCounter;
	int dif;
	int ratio;
	int rows;
	int cols;
	int bombs;
	int[] compareStart = new int[5];
	int[] compareEnd = new int[5];

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	    pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	    boolean theme = pref.getBoolean("theme",false);
	    if(theme)
	    {
	    	setTheme(R.style.DarkTheme);
	    }
	    else
	    {
	        setTheme(R.style.AppTheme);
	    }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null)
        {
        	themeApplied = savedInstanceState.getInt("theme");
        	compareStart = savedInstanceState.getIntArray("init");
        }
        else
        {
	        themeApplied = 0;
	        compareStart = new int[5];
        }
        largeText = findViewById(R.id.textView);
        ib = findViewById(R.id.imageButton);
        gridLayout = findViewById(R.id.gridLayout);



	    android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
	    setSupportActionBar(toolbar);

	    start();

        ib.setOnClickListener(new View.OnClickListener()
        {
	        @Override public void onClick(View v)
	        {
		        Intent intentR = new Intent(getApplicationContext(),Dialog.class);
		        startActivityForResult(intentR, 4321);
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
			case R.id.action_settings:
				dif=Integer.parseInt(pref.getString("dif", "1"));
				ratio=Integer.parseInt(pref.getString("ratio", "50"));
				//Customs
				rows=Integer.parseInt(pref.getString("rows", "20"));
				cols=Integer.parseInt(pref.getString("cols", "20"));
				bombs=Integer.parseInt(pref.getString("bombs", "50"));
				compareStart[0] = dif;
				compareStart[1] = ratio;
				compareStart[2] = rows;
				compareStart[3] = cols;
				compareStart[4] = bombs;
				Intent intent = new Intent(getApplicationContext(),Settings.class);
				startActivity(intent);
			default:return super.onOptionsItemSelected(item);


		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent resultIntent)
	{
		if (requestCode == 4321)
		{
			if (resultCode == RESULT_OK)
			{
				start();
			}
		}
	}


	private void start()
	{
		gridLayout.removeAllViews();
		gridLayout.setRowCount(Singleton.sharedInstance().getNumRows());
		gridLayout.setColumnCount(Singleton.sharedInstance().getNumCols());
		gameOver=false;
		ib.setBackground(getDrawable(R.drawable.msface));
		numBombs = Singleton.sharedInstance().getNumBombs();
		largeText.setText(getString(R.string.tvBombsCounter)+numBombs);
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
		largeText.setText(R.string.tGameOver);
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
		largeText.setText(getString(R.string.tvBombsCounter)+numBombs);
	}
	public  void takeFlag()
	{
		numBombs++;
		largeText.setText(getString(R.string.tvBombsCounter)+numBombs);
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
		largeText.setText(R.string.tWin);
		ib.setBackground(getDrawable(R.drawable.msfacewin));
		gameOver=true;
	}
	public boolean isGameOver()
	{
		return gameOver;
	}

	@Override protected void onResume()
	{
		super.onResume();
		darkTheme=pref.getBoolean("theme", false);

		//Opciones
		showCounter=pref.getBoolean("counter", true);
		dif=Integer.parseInt(pref.getString("dif", "1"));
		ratio=Integer.parseInt(pref.getString("ratio", "50"));
		//Customs
		rows=Integer.parseInt(pref.getString("rows", "20"));
		cols=Integer.parseInt(pref.getString("cols", "20"));
		bombs=Integer.parseInt(pref.getString("bombs", "50"));

		compareEnd[0] = dif;
		compareEnd[1] = ratio;
		compareEnd[2] = rows;
		compareEnd[3] = cols;
		compareEnd[4] = bombs;

		if(dif != 3)
		{
			switch(dif)
			{
				case 0:
					Singleton.sharedInstance().setNumRows(10);
					Singleton.sharedInstance().setNumCols(10);
					break;
				case 1:
					Singleton.sharedInstance().setNumRows(25);
					Singleton.sharedInstance().setNumCols(25);
					break;
				case 2:
					Singleton.sharedInstance().setNumRows(35);
					Singleton.sharedInstance().setNumCols(35);
					break;
			}
		}
		else
		{
			Singleton.sharedInstance().setNumRows(rows);
			Singleton.sharedInstance().setNumCols(cols);
		}
		if(ratio != 0)
		{
			int rat;
			int cells=Singleton.sharedInstance().getNumRows() * Singleton.sharedInstance().getNumCols();
			switch(ratio)
			{
				case 25:
					rat=(cells / 100) * 25;
					Singleton.sharedInstance().setNumBombs(rat);
					break;
				case 50:
					rat=(cells / 100) * 50;
					Singleton.sharedInstance().setNumBombs(rat);
					break;
				case 75:
					rat=(cells / 100) * 75;
					Singleton.sharedInstance().setNumBombs(rat);
					break;
			}
		}
		else
		{
			int cells=Singleton.sharedInstance().getNumRows() * Singleton.sharedInstance().getNumRows();
			int rat;
			if(bombs > 0 && bombs < 100)
			{
				rat=(cells / 100) * bombs;
				Singleton.sharedInstance().setNumBombs(rat);
			}
			else
			{
				Toast.makeText(getApplicationContext(), R.string.toastWarning, Toast.LENGTH_SHORT).show();
				rat=(cells / 100) * 50;
				Singleton.sharedInstance().setNumBombs(rat);
			}
		}

		if(!Arrays.equals(compareStart,compareEnd))
		{
			start();
		}

		if(showCounter)
		{
			largeText.setVisibility(View.VISIBLE);
		}
		else
		{
			largeText.setVisibility(View.INVISIBLE);
		}


		if(darkTheme)
		{
			themeID = R.style.DarkTheme;
		}
		else
		{
			themeID = R.style.AppTheme;
		}
		if(themeApplied != themeID)
		{
			themeApplied = themeID;
			recreate();
		}


	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState)
	{
		savedInstanceState.putInt("theme", themeID);
		savedInstanceState.putIntArray("init", compareStart);
		// Always call the superclass so it can save the view hierarchy state
		super.onSaveInstanceState(savedInstanceState);

	}

}
