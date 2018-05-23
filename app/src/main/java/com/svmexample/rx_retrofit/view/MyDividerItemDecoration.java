package com.svmexample.rx_retrofit.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

public class MyDividerItemDecoration extends RecyclerView.ItemDecoration {


    private static final  int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };

    public static final int HOR = LinearLayoutManager.HORIZONTAL;
    public static final int VER = LinearLayoutManager.VERTICAL;

    Drawable mdivider;
    int Orientation;
    Context context;
    int margin;

    public MyDividerItemDecoration(int orientation, Context context, int margin) {
        this.context = context;
        this.margin = margin;
        final TypedArray a =context.obtainStyledAttributes(ATTRS);
        mdivider=a.getDrawable(0);
        setOrientation(orientation);
    }

    private void setOrientation(int orientation) {

        if(orientation !=HOR && orientation!=VER){
            throw  new  IllegalArgumentException("invalid Orientation");
        }
        Orientation= orientation;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if(Orientation==VER){
            drawVertical(c,parent);
        }else {
            drawHorizontal(c,parent);
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        final  int top = parent.getPaddingTop();
        final int bottom= parent.getHeight()-parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for ( int i=0;i<childCount;i++){
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams layoutParams=(RecyclerView.LayoutParams)child.getLayoutParams();
            final int left= child.getRight() + layoutParams.rightMargin;
            final  int right= left+ mdivider.getIntrinsicHeight();
            mdivider.setBounds(left,top+dptoPx(margin),right,bottom-dptoPx(margin));
            mdivider.draw(c);

        }
    }

    private int dptoPx(int margin) {

        Resources r = context.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,margin,r.getDisplayMetrics()));
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + layoutParams.bottomMargin;
            final int bottom = top + mdivider.getIntrinsicHeight();
            mdivider.setBounds(left + dptoPx(margin), top, right - dptoPx(margin), bottom);
            mdivider.draw(c);
        }
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if(Orientation==VER){
            outRect.set(0,0,0,mdivider.getIntrinsicHeight());
        }else {
            outRect.set(0,0,mdivider.getIntrinsicWidth(),0);
        }
    }
}
