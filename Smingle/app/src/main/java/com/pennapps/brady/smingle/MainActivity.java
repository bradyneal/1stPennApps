package com.pennapps.brady.smingle;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends FragmentActivity {

    private static final String TAG = "FragMainActivity";
    private static final String PICTURE_FILE_NAME = "picture_file";


//    private ActionBar actionBar;
    private ViewPager viewPager;
    private ImageAdapter mAdapter;
    private ProfilesFragment profileFragment;
    private TabAdapter tabAdapter;
//    private EditText etName;
//    private EditText etPhone;
//    private EditText etCaption;
//    private Camera mCamera;
//    private CameraPreview mPreview;
//    private ImageView mImage;
//    private FrameLayout preview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        profileFragment = (ProfilesFragment) getSupportFragmentManager().findFragmentById(R.id.profileFragment);
//        mAdapter = new ImageAdapter(profileFragment.getActivity());
//        profileFragment.setImageAdapter(mAdapter);
        setupPager();
    }

//    public void addContact(View view) {
//        Log.e(TAG, "mImage: " + mImage.toString());
//        mCamera = openFrontFacingCamera();
//        mPreview = new CameraPreview(this, mCamera);
//        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
//        Log.e(TAG, "preview: " + preview.toString());
//        preview.addView(mPreview);
//
//        takePictureIn5();
//    }
//
//    private void takePictureIn5() {
//        Handler handler = new Handler();
//        Toast.makeText(this, "About to take picture...", Toast.LENGTH_LONG).show();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mCamera.takePicture(null, null, mPicture);
//            }
//        }, 5000);
//    }
//
//    private Camera openFrontFacingCamera() {
//        int cameraCount = 0;
//        Camera cam = null;
//        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
//        cameraCount = Camera.getNumberOfCameras();
//        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
//            Camera.getCameraInfo(camIdx, cameraInfo);
//            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
//                Log.e(TAG, "Found front camera!");
//                try {
//                    cam = Camera.open(camIdx);
//                } catch (RuntimeException e) {
//                    Log.e(TAG, "Camera failed to open: " + e.getLocalizedMessage());
//                }
//            }
//        }
//
//        return cam;
//    }

//    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {
//
//        @Override
//        public void onPictureTaken(byte[] data, Camera camera) {
//            Log.e(TAG, "Entered picture callback");
////            Bitmap photoBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
////            mImage.setImageBitmap(photoBitmap);
////            Log.e(TAG, "preview: " + preview.toString());
////            mCamera.release();
////            Log.e(TAG, "released the camera");
//
////            preview.removeAllViews();
////            Log.e(TAG, "removed Preview SurfaceView");
//
//
//            FileOutputStream pictureOutputStream;
//
//            try {
//                pictureOutputStream = openFileOutput(PICTURE_FILE_NAME, Context.MODE_PRIVATE);
//                pictureOutputStream.write(data);
//                pictureOutputStream.close();
//            } catch (FileNotFoundException e) {
//                Log.d(TAG, "File not found: " + e.getMessage());
//            } catch (IOException e) {
//                Log.d(TAG, "Error accessing file: " + e.getMessage());
//            }
//
////            File imagePath = getFileStreamPath(PICTURE_FILE_NAME);
////            mImage.setImageDrawable(Drawable.createFromPath(imagePath.toString()));
//
//            insertContact();
//
//
//        }
//    };
//
//    private void insertContact() {
//        Handler handler = new Handler();
//        Toast.makeText(this, "Preparing to add contact...", Toast.LENGTH_LONG).show();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                String name = etName.getText().toString();
//                String phone = etPhone.getText().toString();
//                Intent contactIntent = new Intent(ContactsContract.Intents.Insert.ACTION);
//                contactIntent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
//                contactIntent.putExtra(ContactsContract.Intents.Insert.NAME, name);
//                contactIntent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
//                // insert photo into contact
//
//                startActivity(contactIntent);
//            }
//        }, 5000);
//    }

//    @Override
//    public void onAttachFragment(Fragment fragment) {
//        Log.e(TAG, "onAttachFragment");
//        if (fragment instanceof QuizFragment) {
//            Log.e(TAG, "it's a QuizFragment!");
//        } else if (fragment instanceof AddContactFragment) {
//            Log.e(TAG, "it's a AddContactFragment!");
//        } else if (fragment instanceof  ProfilesFragment) {
//            Log.e(TAG, "it's a ProfilesFragment!");
//            profileFragment = (ProfilesFragment) fragment;
//            mAdapter = new ImageAdapter(this);
//            Log.e(TAG, "mAdapter.getCount(): " + mAdapter.getCount());
//            profileFragment.setImageAdapter(mAdapter);
//        }
//    }


    private void setupPager() {
        viewPager = (ViewPager) findViewById(R.id.pager);
        tabAdapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);
        viewPager.setCurrentItem(TabAdapter.CONTACT_TAB);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
//                Log.e(TAG, "onPageScrolled at position " + i + " from " + v + " with number of pixels " + i2);
            }

            @Override
            public void onPageSelected(int i) {
                if (i == TabAdapter.QUIZ_TAB) {

                } else if (i == TabAdapter.CONTACT_TAB) {
//                    etName = (EditText) findViewById(R.id.etName);
//                    etPhone = (EditText) findViewById(R.id.etPhone);
//                    etCaption = (EditText) findViewById(R.id.etCaption);
//                    mImage = (ImageView) findViewById(R.id.ivSelfie);
                    Log.e(TAG, "Contact page selected");
                } else if (i == TabAdapter.PROFILES_TAB) {

                }
//                actionBar.setSelectedNavigationItem(i);
//                Log.e(TAG, "onPageSelection at position " + i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
//                Log.e(TAG, "onPageScrollStateChanged at position " + i);
            }
        });

//        profileFragment = (ProfilesFragment) getSupportFragmentManager().findFragmentById(R.id.profileFragment);

//        Log.e(TAG, "" + getSupportFragmentManager().getFragments());
//        Log.e(TAG, "cow");
//        Log.e(TAG, "" + getSupportFragmentManager().getFragments().size());
//
//        for (Fragment frag: getSupportFragmentManager().getFragments()) {
//            Log.e(TAG, "frag: " + frag.toString());
//            if (frag instanceof ProfilesFragment) {
//                Log.e(TAG, "found ProfilesFragment!");
//                profileFragment = (ProfilesFragment) frag;
//            }
//        }
//

//        actionBar = getActionBar();
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//
//        ActionBar.Tab quizTab = actionBar.newTab();
//        quizTab.setText("Quizzes");
//        quizTab.setTabListener(this);
//
//        ActionBar.Tab addContactTab = actionBar.newTab();
//        addContactTab.setText("Add Contact");
//        addContactTab.setTabListener(this);
//
//        ActionBar.Tab profilesTab = actionBar.newTab();
//        profilesTab.setText("Profiles");
//        profilesTab.setTabListener(this);
//
//        actionBar.addTab(quizTab);
//        actionBar.addTab(addContactTab);
//        actionBar.addTab(profilesTab);
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

//    @Override
//    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
//        viewPager.setCurrentItem(tab.getPosition());
////        Log.e(TAG, "onTabSelected at position: " + tab.getPosition() + " name: " + tab.getText());
//    }
//
//    @Override
//    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
////        Log.e(TAG, "onTabUnselected at position: " + tab.getPosition() + " name: " + tab.getText());
//    }
//
//    @Override
//    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
////        Log.e(TAG, "onTabReselected at position: " + tab.getPosition() + " name: " + tab.getText());
//    }
}