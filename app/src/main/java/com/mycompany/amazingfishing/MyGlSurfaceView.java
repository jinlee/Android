package com.mycompany.amazingfishing;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class MyGlSurfaceView extends GLSurfaceView {

    private MyRenderer mRenderer = new MyRenderer();

    public MyGlSurfaceView(Context context) {
        super(context);
        setEGLConfigChooser(8 , 8, 8, 8, 16, 0);
        setEGLContextClientVersion(2);
        setRenderer(mRenderer);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        switch (e.getAction()) {
            case MotionEvent.ACTION_UP:
                mRenderer.toggleRotation();
        }

        return true;
    }
}
