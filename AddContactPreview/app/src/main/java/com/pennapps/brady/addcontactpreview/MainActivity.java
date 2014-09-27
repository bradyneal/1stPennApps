package com.pennapps.brady.addcontactpreview;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends Activity {

    private static final String TAG = "AddContactFragment";
    private static final String PICTURE_FILE_NAME = "picture_file";

    private EditText etName;
    private EditText etPhone;
    private EditText etCaption;
    private ImageButton bSubmit;
    private FrameLayout preview;
    private Camera mCamera;
    private CameraPreview mPreview;
    private boolean pictureTaken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

    }
    @Override
    protected void onStop() {
        super.onStop();
        clearEditTexts();
        pictureTaken = false;
    }

    private void clearEditTexts() {
        etName.setText("");
        etPhone.setText("");
        etCaption.setText("");
    }

    private void initViews() {
        etName = (EditText) findViewById(R.id.etName);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etCaption = (EditText) findViewById(R.id.etCaption);

        etCaption.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    hideSoftKeyboard();
                    if (pictureTaken) {
                        insertContact();
                    } else {
                        snapAndAddContact(null);
                    }
                    return true;
                }
                return false;
            }
        });

        preview = (FrameLayout) findViewById(R.id.camera_preview);

        bSubmit = (ImageButton) findViewById(R.id.ibSubmit);
        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pictureTaken) {
                    insertContact();
                } else {
                    snapAndAddContact(null);
                }
            }
        });
    }

    private void hideSoftKeyboard() {
        if(getCurrentFocus()!= null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void snapPicture() {
        pictureTaken = true;
        takePicture(mSnap);
    }

    private void snapAndAddContact(View view) {
        takePicture(mSnapAndContact);
    }

    private void takePicture(final Camera.PictureCallback callback) {
        pictureTaken = true;
        mCamera = openFrontFacingCamera();
        mPreview = new CameraPreview(this, mCamera);
        preview.addView(mPreview);
        Toast.makeText(this, "Preparing to take picture...", Toast.LENGTH_LONG).show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mCamera.takePicture(null, null, callback);
            }
        }, 3000);

    }

    private Camera openFrontFacingCamera() {
        int cameraCount = 0;
        Camera cam = null;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();
        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                Log.e(TAG, "Found front camera!");
                try {
                    cam = Camera.open(camIdx);
                } catch (RuntimeException e) {
                    Log.e(TAG, "Camera failed to open: " + e.getLocalizedMessage());
                }
            }
        }

        return cam;
    }

    private Camera.PictureCallback mSnap = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Log.e(TAG, "Entered picture callback");

            preview.removeAllViews();
            Log.e(TAG, "removed Preview SurfaceView");

            FileOutputStream pictureOutputStream;

            try {
                pictureOutputStream = openFileOutput(PICTURE_FILE_NAME, Context.MODE_PRIVATE);
                Bitmap photoBitmap = rotateBitmap(BitmapFactory.decodeByteArray(data, 0, data.length), 270);
                pictureOutputStream.write(bitmapToByteArray(photoBitmap));
                pictureOutputStream.close();
            } catch (FileNotFoundException e) {
                Log.d(TAG, "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d(TAG, "Error accessing file: " + e.getMessage());
            }
        }
    };

    private Camera.PictureCallback mSnapAndContact = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Log.e(TAG, "Entered picture callback");

            preview.removeAllViews();
            Log.e(TAG, "removed Preview SurfaceView");

            FileOutputStream pictureOutputStream;

            try {
                pictureOutputStream = openFileOutput(PICTURE_FILE_NAME, Context.MODE_PRIVATE);
                Bitmap photoBitmap = rotateBitmap(BitmapFactory.decodeByteArray(data, 0, data.length), 270);
                pictureOutputStream.write(bitmapToByteArray(photoBitmap));
                pictureOutputStream.close();
            } catch (FileNotFoundException e) {
                Log.d(TAG, "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d(TAG, "Error accessing file: " + e.getMessage());
            }

            insertContact();
        }
    };

    private Bitmap rotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    private byte[] bitmapToByteArray(Bitmap bmp) {
        Log.e(TAG, "starting byte array");
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 85, stream);
        byte[] byteArray = stream.toByteArray();
        Log.e(TAG, "Finished byte array");
        return  byteArray;
    }

    private byte[] drawableToByteArray(Drawable d) {
        Bitmap bmp = ((BitmapDrawable)d).getBitmap();
        return bitmapToByteArray(bmp);
    }

    private void insertContact() {
        pictureTaken = false;
        Log.e(TAG, "insertContact()");

        ArrayList<ContentValues> data = new ArrayList<ContentValues>();

        File imagePath = getFileStreamPath(PICTURE_FILE_NAME);
        byte[] photoData = drawableToByteArray(Drawable.createFromPath(imagePath.toString()));

        ContentValues row = new ContentValues();
        row.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE);
        row.put(ContactsContract.CommonDataKinds.Photo.PHOTO, photoData);
        data.add(row);

        Intent contactIntent = new Intent(Intent.ACTION_INSERT, ContactsContract.Contacts.CONTENT_URI);
        contactIntent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, data);
        String name = etName.getText().toString();
        String phone = etPhone.getText().toString();
        String notes = etCaption.getText().toString();

//        contactIntent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
        contactIntent.putExtra(ContactsContract.Intents.Insert.NAME, name);
        contactIntent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
        contactIntent.putExtra(ContactsContract.Intents.Insert.NOTES, notes);

        startActivity(contactIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
