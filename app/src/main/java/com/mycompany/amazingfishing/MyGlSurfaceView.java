package com.mycompany.amazingfishing;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class MyGlSurfaceView extends GLSurfaceView {
    public MyGlSurfaceView(Context context) {
        super(context);
        setEGLConfigChooser(8 , 8, 8, 8, 16, 0);
        setEGLContextClientVersion(2);
        setRenderer(new MyRenderer());
    }
}
