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

	@Override protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		fl = findViewById(R.id.frameLayout);
		fl.setVisibility(View.INVISIBLE);
		rGroup = findViewById(R.id.radioGroup);
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
						Singleton.sharedInstance().setNumCols(10);
						Singleton.sharedInstance().setNumRows(10);
						Singleton.sharedInstance().setNumBombs(15);
						fl.setVisibility(View.INVISIBLE);
						break;
					case R.id.radioButton2:
						Singleton.sharedInstance().setNumCols(20);
						Singleton.sharedInstance().setNumRows(20);
						Singleton.sharedInstance().setNumBombs(75);
						fl.setVisibility(View.INVISIBLE);
						break;
					case R.id.radioButton3:
						Singleton.sharedInstance().setNumCols(30);
						Singleton.sharedInstance().setNumRows(30);
						Singleton.sharedInstance().setNumBombs(150);
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
				Intent intent = new Intent(getApplicationContext(),MainActivity.class);
				setResult(1234,intent);
				finish();
			}
		});

	}
}
