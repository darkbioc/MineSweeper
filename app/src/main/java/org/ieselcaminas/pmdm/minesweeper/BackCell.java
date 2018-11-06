package org.ieselcaminas.pmdm.minesweeper;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class BackCell extends AppCompatImageView {

    public BackCell(Context context) {
        super(context);
        setImageDrawable(getResources().getDrawable(R.drawable.back));
        final float scale = getContext().getResources().getDisplayMetrics().density;
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams((int) (MineButton.WIDTH * scale),
                        (int) (scale * MineButton.HEIGHT));
        setLayoutParams(layoutParams);
    }
}