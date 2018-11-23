package org.ieselcaminas.pmdm.minesweeper;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import java.util.prefs.Preferences;
import java.util.prefs.PreferencesFactory;

public class Settings extends Activity
{
	public static class MySettings extends PreferenceFragment
	{


		@Override
		public void onCreate(final Bundle savedInstanceState)
		{

			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.activity_settings);



		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(android.R.id.content, new MySettings());
		fragmentTransaction.commit();
	}
}
