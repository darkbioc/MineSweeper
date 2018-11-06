package org.ieselcaminas.pmdm.minesweeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Dialog extends AppCompatActivity
{
	Button bYes;
	Button bNo;
	@Override protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dialog);

		final Intent intent = getIntent();

		bNo = findViewById(R.id.bNo);
		bYes = findViewById(R.id.bYes);

		bYes.setOnClickListener(new View.OnClickListener()
		{
			@Override public void onClick(View v)
			{
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		bNo.setOnClickListener(new View.OnClickListener()
		{
			@Override public void onClick(View v)
			{
				finish();
			}
		});
	}
}
