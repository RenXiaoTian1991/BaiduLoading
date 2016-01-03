package com.example.baiduloading;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class LoadingView extends View{

	private Paint mPaint;
	private int centerX,centerY,currentX,wayDistance = 200;
	private int desnity;
	private int position = 0;
	private int[] color = new int[]{Color.BLACK,Color.RED,Color.BLUE};
	public LoadingView(Context context) {
		super(context);
		init();
	}

	public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public LoadingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		desnity = getResources().getDisplayMetrics().densityDpi;
	}

	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		super.onFinishInflate();
		centerX= getResources().getDisplayMetrics().widthPixels/2;
		centerY = getResources().getDisplayMetrics().heightPixels/2;
		currentX = centerX;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(currentX== centerX){
			anim();
			
		}else{
			//如果两个小圆对齐则进行换色操作，将pposition加一，可以将颜色进行调换
			if(Math.abs(-currentX +centerX) <= 10){
                position++;
                mPaint.setColor(color[position % 3]);
            }else{
            	//如果没有对齐则还是用之前的颜色进行画，
                mPaint.setColor(position);
            }
			canvas.drawCircle(centerX, centerY, 20, mPaint);
			mPaint.setColor(color[(position+1)%3]);
			canvas.drawCircle(currentX, centerY, 20, mPaint);
			mPaint.setColor(color[(position+2)%3]);
			canvas.drawCircle(centerX*2 - currentX, centerY, 20, mPaint);
			//最后判断position是否超过数组长度，其实不置0也可以，可以起到颜色调换的作用，因为使用了求余数的操作
			if(position==3){
				position =0;
			}
		}
		
	}
	
	
	@SuppressLint("NewApi") private void anim(){
		ValueAnimator valueAnimator= ValueAnimator.ofInt(centerX-wayDistance,centerX);
		valueAnimator.addUpdateListener(new AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator arg0) {
				currentX = (Integer) arg0.getAnimatedValue();
				invalidate();
			}
		});
		valueAnimator.setRepeatCount(-1);
        valueAnimator.setDuration(400);
        valueAnimator.setRepeatMode(2);
        valueAnimator.start();
	}
	

}
