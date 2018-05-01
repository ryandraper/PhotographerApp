package example.com.photographerapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final int SEARCH_ACTIVITY_REQUEST_CODE = 0;
    static final int CAMERA_REQUEST_CODE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    private String mCurrentPhotoPath = null;
    private int currentPhotoIndex = 0;
    private ArrayList<String> photoGallery = new ArrayList<String>();
    private Date minDate = new Date(Long.MIN_VALUE);
    private Date maxDate = new Date(Long.MAX_VALUE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnLeft = (Button)findViewById(R.id.btnLeft);
        Button btnRight = (Button)findViewById(R.id.btnRight);
        Button btnFilter = (Button)findViewById(R.id.btnFilter);
        btnLeft.setOnClickListener(this);
        btnRight.setOnClickListener(this);
        btnFilter.setOnClickListener(filterListener);


        photoGallery = populateGallery(minDate, maxDate);
        Log.d("onCreate, size", Integer.toString(photoGallery.size()));
        if (photoGallery.size() > 0) {
            mCurrentPhotoPath = photoGallery.get(currentPhotoIndex);
            Log.d("path","path: " + mCurrentPhotoPath);
            displayPhoto(mCurrentPhotoPath);
        }


    }

    private View.OnClickListener filterListener = new View.OnClickListener() {
        public void onClick(View v) {
            Intent i = new Intent(MainActivity.this, SearchActivity.class);
            startActivityForResult(i, SEARCH_ACTIVITY_REQUEST_CODE);
        }
    };



    private ArrayList<String> populateGallery(Date minDate, Date maxDate) {
        Log.d("populateGallery","start");
        File folder = new File(
                Environment.getExternalStorageDirectory()
                        .getAbsolutePath()
                , "/Android/data/example.com.photographerapp/files/Pictures");
        //Log.d("populateGallery","folder"+folder.getAbsolutePath());

        photoGallery = new ArrayList<String>();
        File[] fList = folder.listFiles();
        if (fList != null) {
            for (File f : folder.listFiles()) {
                //Log.d("loop","listFiles: " + f);
                Log.d("loop","listFiles: created " + new Date(f.lastModified()).toString());
                Log.d("loop","listFiles: minDate " + minDate.toString());
                Log.d("loop","listFiles: maxDate " + maxDate.toString());

                if( f.lastModified() >= minDate.getTime() && f.lastModified() <= maxDate.getTime()) {
                    Log.d("loop","adding file: " + f.lastModified());
                    photoGallery.add(f.toString());
                }
            }
        }
        return photoGallery;
    }
    private void displayPhoto(String path) {
        ImageView iv = (ImageView) findViewById(R.id.imageView2);
        iv.setImageURI( Uri.parse(path) );
    }

    public void onClick( View v) {

        Log.d("index","1 currentPhotoIndex before: " + currentPhotoIndex);
        Log.d("index","1 photoGallery.size(): " + photoGallery.size());

        switch (v.getId()) {
            case R.id.btnLeft:
                --currentPhotoIndex;
                break;
            case R.id.btnRight:
                ++currentPhotoIndex;
                break;
            default:
                break;
        }
        Log.d("index","2 currentPhotoIndex before: " + currentPhotoIndex);
        Log.d("index","2 photoGallery.size(): " + photoGallery.size());
        if (currentPhotoIndex <= 0) {
            Log.d("if","1");
            currentPhotoIndex = 0;
        }
        else if (currentPhotoIndex >= photoGallery.size()) {
            Log.d("if","2");
            currentPhotoIndex = photoGallery.size() - 1;
        }
        if( photoGallery.size() == 0 ) {
            Log.d("if","3");
            return;
        }

        Log.d("index","currentPhotoIndex after: " + currentPhotoIndex);
        mCurrentPhotoPath = photoGallery.get(currentPhotoIndex);
        Log.d("photoleft, size", Integer.toString(photoGallery.size()));
        Log.d("photoleft, index", Integer.toString(currentPhotoIndex));
        displayPhoto(mCurrentPhotoPath);
    }



    public void takePicture(View view){
        dispatchTakePictureIntent();
    }

    public void filterPictures(View view){
        dispatchFilterPictures();
    }

    private void dispatchFilterPictures() {
        Intent filterPicturesIntent = new Intent(this,SearchActivity.class);
        startActivity( filterPicturesIntent );
    }



    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        Log.d("foo","foo: storageDir in createImageFile" + storageDir);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );


        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.d("ERROR RYAN","ex.getMessage() - "+ex.getMessage());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        this.getPackageName()+".provider",
                        photoFile);
                Log.d("foo","foo: photoURI: "+photoURI);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SEARCH_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
//                Log.d("1 createImageFile", data.getStringExtra("STARTDATE"));
                //Log.d("1 createImageFile", data.getStringExtra("ENDDATE"));
                Long startLong = data.getLongExtra("STARTDATE", Long.MIN_VALUE);
                Long endLong = data.getLongExtra("ENDDATE", Long.MAX_VALUE);
                Log.d("startLong","startLong "+startLong);
                Log.d("endLong","endLong "+endLong);

                photoGallery = populateGallery(new Date(startLong), new Date(endLong));
                Log.d("onCreate, size", ""+photoGallery.size());
                currentPhotoIndex = 0;
                if(photoGallery.size() > 0) {
                    mCurrentPhotoPath = photoGallery.get(currentPhotoIndex);
                }
                displayPhoto(mCurrentPhotoPath);
            }
        }
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Log.d("2 createImageFile", "Picture Taken");
                photoGallery = populateGallery(minDate,maxDate);
                Log.d("2 createImageFile", "photoGallery - " + photoGallery);
                currentPhotoIndex = 0;
                if(photoGallery.size() > 0) {
                    mCurrentPhotoPath = photoGallery.get(photoGallery.size()-1);
                }
                displayPhoto(mCurrentPhotoPath);
            }
        }

    }

}