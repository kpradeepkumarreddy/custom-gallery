package in.lxltech.customgallery;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private static Button openCustomGallery;
    private static GridView selectedImageGridView;

    private static final int CustomGallerySelectId = 1;//Set Intent Id
    public static final String CustomGalleryIntentKey = "ImageArray";//Set Intent Key Value
    private ArrayList<String> selectedImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setListeners();
        getSharedImages();
    }

    //Init all views
    private void initViews() {
        openCustomGallery = (Button) findViewById(R.id.openCustomGallery);
        selectedImageGridView = (GridView) findViewById(R.id.selectedImagesGridView);
    }

    //set Listeners
    private void setListeners() {
        openCustomGallery.setOnClickListener(this);
        selectedImageGridView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.openCustomGallery:
                //Start Custom Gallery Activity by passing intent id
                startActivityForResult(new Intent(MainActivity.this, CustomGalleryActivity.class), CustomGallerySelectId);
                break;
        }
    }

    protected void onActivityResult(int requestcode, int resultcode,
                                    Intent imagereturnintent) {
        super.onActivityResult(requestcode, resultcode, imagereturnintent);
        switch (requestcode) {
            case CustomGallerySelectId:
                if (resultcode == RESULT_OK) {
                    String imagesArray = imagereturnintent.getStringExtra(CustomGalleryIntentKey);//get Intent data
                    //Convert string array into List by splitting by ',' and substring after '[' and before ']'
                    Log.d("log", "imagesArray = " + imagesArray);
                    selectedImages = new ArrayList<String>(Arrays.asList(imagesArray.substring(1, imagesArray.length() - 1).split(", ")));
                    Log.d("log", "selectedImages = " + selectedImages);
                    loadGridView(selectedImages);//call load gridview method by passing converted list into arrayList
                }
                break;
        }
    }

    //Load GridView
    private void loadGridView(ArrayList<String> imagesArray) {
        GridViewAdapter adapter = new GridViewAdapter(MainActivity.this, imagesArray);
        selectedImageGridView.setAdapter(adapter);
    }

    //Read Shared Images
    private void getSharedImages() {

        //If Intent Action equals then proceed
        if (Intent.ACTION_SEND_MULTIPLE.equals(getIntent().getAction())
                && getIntent().hasExtra(Intent.EXTRA_STREAM)) {
            ArrayList<Parcelable> list =
                    getIntent().getParcelableArrayListExtra(Intent.EXTRA_STREAM);//get Parcelabe list
            ArrayList<String> selectedImages = new ArrayList<>();
            Log.d("log", "getSharedImages ");
            //Loop to all parcelable list
            for (Parcelable parcel : list) {
                Uri uri = (Uri) parcel; //get URI
                String sourcepath = getPath(uri); //Get Path of URI
                selectedImages.add(sourcepath); //add images to arraylist
            }
            loadGridView(selectedImages); //call load gridview
        }
    }


    //get actual path of uri
    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        startManagingCursor(cursor);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ImagesInFullScreen.class);
        intent.putExtra("images", selectedImages);
        intent.putExtra("position", position);
        startActivity(intent);
    }
}
