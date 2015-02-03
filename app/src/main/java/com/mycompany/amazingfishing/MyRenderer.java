package com.mycompany.amazingfishing;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyRenderer implements GLSurfaceView.Renderer {

    private Triangle mTriangle;
    private Square mSquare;
    private volatile boolean clockwise = true;
    private volatile float dx = 0.0f;

    private long previousTime;

    private float[] mRotationMatrix = new float[16];
    private float[] mSwapMatrix = new float[16];

    // listed in order of application (model -> view -> projection)
    private float[] mModelMatrix = new float[16];
    private float[] mViewMatrix = new float[16];
    private float[] mModelViewMatrix = new float[16];
    private float[] mProjectionMatrix = new float[16];

    // result
    private float[] mMVPMatrix = new float[16];

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mTriangle = new Triangle();
        mSquare = new Square();
        GLES20.glClearColor(0.0f, 0.5f, 0.5f, 1.0f);

        previousTime = SystemClock.uptimeMillis();
        Matrix.setRotateM(mRotationMatrix, 0, 0.0f, 0, 0, -1.0f);
        Matrix.setRotateM(mSwapMatrix, 0, 0.0f, 0, -1.0f, 0);
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, 3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        float ratio = (float) width / height;
        // set projection matrix
        Matrix.orthoM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 2.0f, 7);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        // determine rotation
        long currTime = SystemClock.uptimeMillis();
        float angle = 0.090f * ((int)((currTime - previousTime) % 4000L));
        previousTime = currTime;

        // rotation matrices
        Matrix.rotateM(mRotationMatrix, 0, angle, 0, 0, clockwise ? -1.0f : 1.0f);
        if (dx != 0.0f) {
            Matrix.rotateM(mSwapMatrix, 0, dx, 0, 1.0f, 0);
            dx = 0.0f;
        }

        Matrix.multiplyMM(mModelMatrix, 0, mSwapMatrix, 0, mRotationMatrix, 0);
        Matrix.multiplyMM(mModelViewMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mModelViewMatrix, 0);

        mTriangle.draw(mMVPMatrix);
    }

    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    public void toggleRotation() {
        clockwise = !clockwise;
    }

    public void toggleSwap(float dx) {
        this.dx = dx;
    }
}
