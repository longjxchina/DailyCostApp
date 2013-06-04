package com.app.controls;

import com.app.dailycostapp.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

public class ToDoListItemView extends TextView {
	private Paint marginPaint;
	private Paint linePaint;
	private int paperColor;
	private float margin;
	
	public ToDoListItemView(Context context) {
		super(context);
		init();
	}
	
	public ToDoListItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ToDoListItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	private void init(){
		Resources curRes = getResources();
		
		marginPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		marginPaint.setColor(curRes.getColor(R.color.notepad_margin));
		linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		linePaint.setColor(curRes.getColor(R.color.notepad_lines));
		
		paperColor = curRes.getColor(R.color.notepad_paper);
		margin = curRes.getDimension(R.dimen.margin);
	}
	
	@Override
	public void onDraw(Canvas canvas){
		canvas.drawColor(paperColor);
		canvas.drawLine(0, 0, 0, getMeasuredHeight(), linePaint);
		canvas.drawLine(0, getMeasuredHeight(), getMeasuredWidth(), getMeasuredHeight(), linePaint);
		canvas.drawLine(margin, 0, margin, getMeasuredHeight(), marginPaint);
		canvas.save();
		canvas.translate(margin, 0);
		
		super.onDraw(canvas);
		canvas.restore();
	}
}
