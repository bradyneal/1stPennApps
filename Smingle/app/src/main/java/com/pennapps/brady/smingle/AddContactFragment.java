package com.pennapps.brady.smingle;


import android.app.Fragment;
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
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.Intents.Insert;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class AddContactFragment extends android.support.v4.app.Fragment {

    private static final String TAG = "AddContactFragment";
    public static final String PICTURE_FILE_NAME = "picture_file";

    private EditText etName;
    private EditText etPhone;
    private EditText etCaption;
    private ImageButton bSubmit;
    private ImageView mImage;
    private FrameLayout preview;
    private Camera mCamera;
    private CameraPreview mPreview;
    private FragmentActivity mActivity;
    private ArrayList<String[]> profiles;
    private android.support.v4.app.Fragment profileFragment;
    private String[] profile;

    public AddContactFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_contact, container, false);
        mImage = (ImageView) v.findViewById(R.id.ivSelfie);
        etName = (EditText) v.findViewById(R.id.etName);
        etPhone = (EditText) v.findViewById(R.id.etPhone);
        etCaption = (EditText) v.findViewById(R.id.etCaption);
        mImage = (ImageView) v.findViewById(R.id.ivSelfie);
        preview = (FrameLayout) v.findViewById(R.id.camera_preview);

        bSubmit = (ImageButton) v.findViewById(R.id.ibSubmit);
        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addContact(view);
            }
        });

        profileFragment = getFragmentManager().findFragmentById(R.id.profileFragment);

        mActivity = getActivity();

        Log.e(TAG, "found the imageView in fragment: " + mImage.toString());
        return v;
    }

    public void addContact(View view) {
        Log.e(TAG, "mImage: " + mImage.toString());

        String name = etName.getText().toString();
        String phone = etPhone.getText().toString();
        profile = new String[] {name.replaceAll(" ", "_"), name, phone};

//        ImageAdapter.profiles.add(profile);

        mCamera = openFrontFacingCamera();
        mPreview = new CameraPreview(getActivity(), mCamera);
//        FrameLayout preview = (FrameLayout) v.findViewById(R.id.camera_preview);
        Log.e(TAG, "preview: " + preview.toString());
        preview.addView(mPreview);

        takePictureIn5();
    }

    private void takePictureIn5() {
        Handler handler = new Handler();
        Toast.makeText(getActivity(), "Selfie!", Toast.LENGTH_SHORT).show();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mCamera.takePicture(null, null, mPicture);
            }
        }, 100);
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

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Log.e(TAG, "Entered picture callback");


//            mImage.setImageBitmap(photoBitmap);
//            Log.e(TAG, "preview: " + preview.toString());
//            mCamera.release();
//            Log.e(TAG, "released the camera");

            preview.removeAllViews();
            Log.e(TAG, "removed Preview SurfaceView");

            FileOutputStream pictureOutputStream;

            try {
//                String filename = profiles.get(profiles.size() - 1)[0] + "_" + PICTURE_FILE_NAME;
                pictureOutputStream = mActivity.openFileOutput(PICTURE_FILE_NAME, Context.MODE_PRIVATE);
                Bitmap photoBitmap = rotateBitmap(BitmapFactory.decodeByteArray(data, 0, data.length), 270);
                pictureOutputStream.write(bitmapToByteArray(photoBitmap));
                pictureOutputStream.close();
            } catch (FileNotFoundException e) {
                Log.d(TAG, "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d(TAG, "Error accessing file: " + e.getMessage());
            }

//            File imagePath = mActivity.getFileStreamPath(PICTURE_FILE_NAME);
//            mImage.setImageDrawable(Drawable.createFromPath(imagePath.toString()));

//            Bitmap photoBitmap = rotateBitmap(BitmapFactory.decodeByteArray(data, 0, data.length), 270);
//            Bitmap photoBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

//            insertContact(bitmapToByteArray(photoBitmap));

//            insertContact(data);

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
        Log.e(TAG, "insertContact()");
//        Handler handler = new Handler();
//        Toast.makeText(getActivity(), "Preparing to add contact...", Toast.LENGTH_LONG).show();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                ArrayList<ContentValues> data = new ArrayList<ContentValues>();
//
//                File imagePath = mActivity.getFileStreamPath(PICTURE_FILE_NAME);
//                byte[] photoData = drawableToByteArray(Drawable.createFromPath(imagePath.toString()));
//
//                ContentValues row = new ContentValues();
//                row.put(Data.MIMETYPE, ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE);
//                row.put(ContactsContract.CommonDataKinds.Photo.PHOTO, photoData);
//                data.add(row);
//
//                Intent contactIntent = new Intent(Intent.ACTION_INSERT, Contacts.CONTENT_URI);
//                contactIntent.putParcelableArrayListExtra(Insert.DATA, data);
//                String name = etName.getText().toString();
//                String phone = etPhone.getText().toString();
//
////                contactIntent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
//                contactIntent.putExtra(ContactsContract.Intents.Insert.NAME, name);
//                contactIntent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
//
//                startActivity(contactIntent);
//            }
//        }, 5000);

        ArrayList<ContentValues> data = new ArrayList<ContentValues>();

        File imagePath = mActivity.getFileStreamPath(PICTURE_FILE_NAME);
        byte[] photoData = drawableToByteArray(Drawable.createFromPath(imagePath.toString()));

        ContentValues row = new ContentValues();
        row.put(Data.MIMETYPE, ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE);
        row.put(ContactsContract.CommonDataKinds.Photo.PHOTO, photoData);
        data.add(row);

        Intent contactIntent = new Intent(Intent.ACTION_INSERT, Contacts.CONTENT_URI);
        contactIntent.putParcelableArrayListExtra(Insert.DATA, data);
        String name = etName.getText().toString();
        String phone = etPhone.getText().toString();

//        contactIntent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
        contactIntent.putExtra(ContactsContract.Intents.Insert.NAME, name);
        contactIntent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);

        startActivity(contactIntent);
    }


}
