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
			//�������СԲ��������л�ɫ��������pposition��һ�����Խ���ɫ���е���
			if(Math.abs(-currentX +centerX) <= 10){
                position++;
                mPaint.setColor(color[position % 3]);
            }else{
            	//���û�ж���������֮ǰ����ɫ���л���
                mPaint.setColor(position);
            }
			canvas.drawCircle(centerX, centerY, 20, mPaint);
			mPaint.setColor(color[(position+1)%3]);
			canvas.drawCircle(currentX, centerY, 20, mPaint);
			mPaint.setColor(color[(position+2)%3]);
			canvas.drawCircle(centerX*2 - currentX, centerY, 20, mPaint);
			//����ж�position�Ƿ񳬹����鳤�ȣ���ʵ����0Ҳ���ԣ���������ɫ���������ã���Ϊʹ�����������Ĳ���
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
