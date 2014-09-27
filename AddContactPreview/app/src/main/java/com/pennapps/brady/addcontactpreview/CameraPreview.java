package com.pennapps.brady.addcontactpreview;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * A basic Camera preview class
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = "CameraPreview";

    private SurfaceHolder mHolder;
    private Camera mCamera;

    public CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;
        mCamera.setDisplayOrientation(90);

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
//        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // empty. Take care of releasing the Camera preview in your activity.
        Log.e(TAG, "surfaceDestroyed");
        this.getHolder().removeCallback(this);
        mCamera.stopPreview();
        mCamera.release();
        Log.e(TAG, "camera released");

    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        Log.e(TAG, "touched surfaceView");
//        mCamera.takePicture(null, rawCallback, mPicture);
//        Log.e(TAG, "called takePicture method");
//        return super.onTouchEvent(event);
//    }
//
//    private Camera.PictureCallback rawCallback = new Camera.PictureCallback() {
//        public void onPictureTaken(byte[] data, Camera camera) {
//            Log.d(TAG, "onPictureTaken - raw");
//        }
//    };
//
//    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {
//
//        @Override
//        public void onPictureTaken(byte[] data, Camera camera) {
//            Log.e(TAG, "Entered picture callback");
//            final Bitmap photoBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
//            post(new Runnable() {
//                @Override
//                public void run() {
//                    ImageView mImage = (ImageView) findViewById(R.id.ivSelfie);
//                    mImage.setMinimumHeight(100);
//                    mImage.setMinimumWidth(50);
//                    mImage.setImageBitmap(photoBitmap);
//                }
//            });
//        }
//    };

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
//        // If your preview can change or rotate, take care of those events here.
//        // Make sure to stop the preview before resizing or reformatting it.
//
//        if (mHolder.getSurface() == null) {
//            // preview surface does not exist
//            return;
//        }
//
//        // stop preview before making changes
//        try {
//            mCamera.stopPreview();
//        } catch (Exception e) {
//            Log.e(TAG, "Tried to stop nonexistent preview");
//        }
//
//        // set preview size and make any resize, rotate or
//        // reformatting changes here
//
//        // start preview with new settings
//        try {
//            mCamera.setPreviewDisplay(mHolder);
//            mCamera.startPreview();
//
//        } catch (Exception e) {
//            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
//        }
    }
}
