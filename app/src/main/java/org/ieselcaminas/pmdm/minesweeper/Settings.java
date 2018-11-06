package org.ieselcaminas.pmdm.minesweeper;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

public class Settings extends AppCompatActivity
{
	RadioGroup rGroup;
	EditText etRows;
	EditText etCols;
	EditText etBombs;
	FloatingActionButton fab;
	FrameLayout fl;
	int numCols = 20;
	int numRows = 20;
	int numBombs = 75;

	@Override protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		fl = findViewById(R.id.frameLayout);
		fl.setVisibility(View.INVISIBLE);
		rGroup = findViewById(R.id.radioGroup);
		rGroup.check(R.id.radioButton2);
		etCols = findViewById(R.id.etCols);
		etRows = findViewById(R.id.etRows);
		etBombs = findViewById(R.id.etBombs);
		fab = findViewById(R.id.floatingActionButton2);
		int checked = rGroup.getCheckedRadioButtonId();
		if (checked == R.id.radioButton4)
		{
			fl.setVisibility(View.VISIBLE);
			etCols.setText(Singleton.sharedInstance().getNumCols());
			etRows.setText(Singleton.sharedInstance().getNumRows());
			etBombs.setText(Singleton.sharedInstance().getNumBombs());
		}
		rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
		{
			@Override public void onCheckedChanged(RadioGroup group, int checkedId)
			{
				switch(checkedId)
				{
					case R.id.radioButton:
						numCols = 10;
						numRows = 10;
						numBombs = 15;
						fl.setVisibility(View.INVISIBLE);
						break;
					case R.id.radioButton2:
						numCols = 20;
						numRows = 20;
						numBombs = 75;
						fl.setVisibility(View.INVISIBLE);
						break;
					case R.id.radioButton3:
						numCols = 30;
						numRows = 30;
						numBombs = 150;
						fl.setVisibility(View.INVISIBLE);
						break;
					case R.id.radioButton4:
						fl.setVisibility(View.VISIBLE);
						break;

				}
			}
		});
		fab.setOnClickListener(new View.OnClickListener()
		{
			@Override public void onClick(View v)
			{
				if(rGroup.getCheckedRadioButtonId() == R.id.radioButton4)
				{
					Singleton.sharedInstance().setNumCols((Integer.parseInt(etCols.getText().toString())));
					Singleton.sharedInstance().setNumRows((Integer.parseInt(etRows.getText().toString())));
					Singleton.sharedInstance().setNumBombs((Integer.parseInt(etBombs.getText().toString())));
				}
				else
				{
					Singleton.sharedInstance().setNumCols(numCols);
					Singleton.sharedInstance().setNumRows(numRows);
					Singleton.sharedInstance().setNumBombs(numBombs);
				}
				Intent intent = getIntent();
				setResult(RESULT_OK,intent);
				finish();
			}
		});

	}
}
