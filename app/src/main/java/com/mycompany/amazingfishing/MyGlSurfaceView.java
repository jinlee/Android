package com.mycompany.amazingfishing;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class MyGlSurfaceView extends GLSurfaceView {

    private MyRenderer mRenderer = new MyRenderer();

    private float previousX;
    private boolean pointerMoved;

    public MyGlSurfaceView(Context context) {
        super(context);
        setEGLConfigChooser(8 , 8, 8, 8, 16, 0);
        setEGLContextClientVersion(2);
        setRenderer(mRenderer);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                pointerMoved = false;
                previousX = e.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                pointerMoved = true;
                float x = e.getX();
                float dx = x - previousX;
                previousX = x;
                mRenderer.toggleSwap(dx);
                break;
            case MotionEvent.ACTION_UP:
                if (!pointerMoved)
                    mRenderer.toggleRotation();
                break;
        }

        return true;
    }
}
