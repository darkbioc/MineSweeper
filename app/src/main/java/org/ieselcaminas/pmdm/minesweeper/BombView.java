package org.ieselcaminas.pmdm.minesweeper;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.FrameLayout;

public class BombView extends AppCompatImageView
{
	public BombView(Context c)
	{
		super(c);
		final float scale=getContext().getResources().getDisplayMetrics().density;
		int width=(int) (MineButton.WIDTH * scale);
		int height=(int) (MineButton.HEIGHT * scale);

		android.view.ViewGroup.LayoutParams params=new FrameLayout.LayoutParams(width, height);

		setLayoutParams(params);
		setImageDrawable(getResources().getDrawable(R.drawable.bomb));
		setPadding(5, 5, 5, 5);
		setScaleType(ScaleType.FIT_XY);
	}
}
