package org.ieselcaminas.pmdm.minesweeper;

import android.content.Context;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class MineButton extends android.support.v7.widget.AppCompatImageButton
{
	public static final int WIDTH=20;
	public static final int HEIGHT=20;
	private int row, col;
	private ButtonState state;
	public MainActivity ma;


	public MineButton(Context context, int row, int col, MainActivity mac)
	{
		super(context);
		this.row=row;
		this.col=col;
		state=ButtonState.CLOSED;
		ma = mac;

		final float scale=getContext().getResources().getDisplayMetrics().density;
		int width=(int) (WIDTH * scale);
		int height=(int) (HEIGHT * scale);

		android.view.ViewGroup.LayoutParams params=new FrameLayout.LayoutParams(width, height);

		setLayoutParams(params);
		setBackgroundDrawable(getResources().getDrawable(R.drawable.boton));
		setScaleType(ScaleType.FIT_XY);
		setPadding(5, 5, 5, 5);

		setOnTouchListener(new OnTouchListener()
		{
			@Override public boolean onTouch(View v, MotionEvent event)
			{
				//Log.d("checktouch", event.toString());
				if(event.getAction() == MotionEvent.ACTION_DOWN)
				{
					setBackgroundDrawable(getResources().getDrawable(R.drawable.boton_pressed));
				}
				if(event.getAction() == MotionEvent.ACTION_UP)
				{
					setBackgroundDrawable(getResources().getDrawable(R.drawable.boton));
				}

				return false;
			}

		});
		setOnLongClickListener(new OnLongClickListener()
		{
			@Override public boolean onLongClick(View v)
			{
				MineButton button = (MineButton) v;
				if(button.getState() == ButtonState.CLOSED && !ma.isGameOver())
				{
					button.setState(ButtonState.FLAG);
					v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
					ma.putFlag();
					ma.checkWin();
				}
				else if(button.getState() == ButtonState.FLAG && !ma.isGameOver())
				{
					button.setState(ButtonState.QUESTION);
					v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
					ma.takeFlag();
					ma.checkWin();
				}
				else if(!ma.isGameOver())
				{
					button.setState(ButtonState.CLOSED);
					v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
				}
				return true;
			}
		});
	}


	public int getRow()
	{
		return row;
	}

	public int getCol()
	{
		return col;
	}

	public void setState(ButtonState state)
	{
		this.state=state;
		switch(state)
		{
			case FLAG:
				setImageDrawable(getResources().getDrawable(R.drawable.flag));
				setPadding(5, 5, 5, 5);
				setScaleType(ScaleType.FIT_XY);
				break;
			case QUESTION:
				setImageDrawable(getResources().getDrawable(R.drawable.question));
				setPadding(5, 5, 5, 5);
				setScaleType(ScaleType.FIT_XY);
				break;
			case WRONG:
				setImageDrawable(getResources().getDrawable(R.drawable.crossedbomb));
				setPadding(5, 5, 5, 5);
				setScaleType(ScaleType.FIT_XY);
				break;
			default:
				setImageBitmap(null);
		}
	}

	public ButtonState getState()
	{
		return state;
	}
}
