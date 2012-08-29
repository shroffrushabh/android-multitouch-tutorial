package it.jfrankie;

/**
 * This cod shows how to implement a custom View
 * that supports multitouch 
 * 
 * Refer to: http://survivingwithandroid.blogspot.com
 * 
 * */

import it.survivingwithandroid.R;
import java.util.HashMap;
import java.util.Iterator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class TouchView extends View {

	
	private HashMap<Integer, Path> fingerMap = new HashMap<Integer,Path>();
	private Paint myPaint;
	
	public TouchView(Context context) {
		super(context);
		init();
	}

	public TouchView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);	
		init();
	}

	public TouchView(Context context, AttributeSet attrs) {
		super(context, attrs);		
		init();
	}
	
	private void init() {
		myPaint = new Paint();
		myPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		myPaint.setColor(Color.CYAN);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction() & MotionEvent.ACTION_MASK;
		//Log.d("CV", "Action ["+action+"]");
		switch(action) {
			case MotionEvent.ACTION_DOWN : {
				
				int id = event.getPointerId(0);
				Log.d("CV","Pointer Down ["+id+"]");
				
				fingerMap.put(id, createCirPath(event.getX(), event.getY(), id));
				break;
			}
			case MotionEvent.ACTION_MOVE : {
			
				int touchCounter = event.getPointerCount();
				for (int t = 0; t < touchCounter; t++) {
					int id = event.getPointerId(t);
					fingerMap.remove(id);
					fingerMap.put(id, createCirPath(event.getX(t), event.getY(t), id));
				}
			}
			case MotionEvent.ACTION_POINTER_DOWN : {
				int id = event.getPointerId(getIndex(event));
				Log.d("CV", "Other point down ["+id+"]");
				fingerMap.put(id, createCirPath(event.getX(getIndex(event)), event.getY(getIndex(event)), getIndex(event)));
				break;
			}
			case MotionEvent.ACTION_POINTER_UP : {
				int id = event.getPointerId(getIndex(event));
				Log.d("CV", "Other point up ["+id+"]");
				fingerMap.remove(id);
				break;
			}
			case MotionEvent.ACTION_UP : {
				int id = event.getPointerId(0);
				Log.d("CV", "Pointer up ["+id+"]");
				fingerMap.remove(id);
				break;
			}			
		}
		
		invalidate();
		return true;
	}

	private int getIndex(MotionEvent event) {
		int idx = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
		return idx;
	}
	
	private Path createCirPath(float x, float y, int id) {
		Path p = new Path();
		
		p.addCircle(x, y, 50, Path.Direction.CW);
		return p;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Iterator<Integer> it = fingerMap.keySet().iterator();
		while (it.hasNext()) {
			Integer key = it.next();
			Path p = fingerMap.get(key);
			canvas.drawPath(p, myPaint);
		}
	}
	
	
}
