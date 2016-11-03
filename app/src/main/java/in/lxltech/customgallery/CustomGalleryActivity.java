package in.lxltech.customgallery;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * Created by pradeepkumar on 7/28/2016.
 */

public class CustomGalleryActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private static Button selectImages;
    private static GridView galleryImagesGridView;
    private static ArrayList<String> galleryImageUrls;
    private static GridViewAdapter imagesAdapter;
    private SparseBooleanArray mSparseBooleanArray;//Variable to store selected Images


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_gallery_activity);
        initViews();
        setListeners();
        fetchGalleryImages();
        setUpGridView();
    }

    //Init all views
    private void initViews() {
        selectImages = (Button) findViewById(R.id.selectImagesBtn);
        galleryImagesGridView = (GridView) findViewById(R.id.galleryImagesGridView);
    }

    //fetch all images from gallery
    private void fetchGalleryImages() {
        final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};//get all columns of type images
        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;//order data by date

        Cursor imagecursor = managedQuery(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
                null, orderBy + " DESC");//get all data in Cursor by sorting in DESC order

        galleryImageUrls = new ArrayList<String>();//Init array

        //Loop to cursor count
        for (int i = 0; i < imagecursor.getCount(); i++) {
            imagecursor.moveToPosition(i);
            int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);//get column index
            galleryImageUrls.add(imagecursor.getString(dataColumnIndex));//get Image from column index
            System.out.println("Array path" + galleryImageUrls.get(i));
        }
    }

    //Set Up GridView method
    private void setUpGridView() {
        mSparseBooleanArray = new SparseBooleanArray();
        imagesAdapter = new GridViewAdapter(this, galleryImageUrls, mSparseBooleanArray, true);
        galleryImagesGridView.setAdapter(imagesAdapter);
    }

    //Set Listeners method
    private void setListeners() {
        selectImages.setOnClickListener(this);
        galleryImagesGridView.setOnItemClickListener(this);
    }


    //Show hide select button if images are selected or deselected
    public void showSelectButton() {
        ArrayList<String> selectedItems = imagesAdapter.getCheckedItems();
        if (selectedItems.size() > 0) {
            selectImages.setText(selectedItems.size() + " - Images Selected");
            selectImages.setVisibility(View.VISIBLE);
        } else
            selectImages.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.selectImagesBtn:

                //When button is clicked then fill array with selected images
                ArrayList<String> selectedItems = imagesAdapter.getCheckedItems();

                //Send back result to MainActivity with selected images
                Intent intent = new Intent();
                intent.putExtra(MainActivity.CustomGalleryIntentKey, selectedItems.toString());//Convert Array into string to pass data
                setResult(RESULT_OK, intent);//Set result OK
                finish();//finish activity
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        ViewHolder viewHolder = (ViewHolder) view.getTag();

        if(mSparseBooleanArray.get(position) == false) {
            viewHolder.tickMark.setVisibility(View.VISIBLE);
            viewHolder.imageView.setAlpha(0.5f);
            mSparseBooleanArray.put(position, true);
            Log.d("log", "pos = "+position);
        } else {
            viewHolder.imageView.setAlpha(1f);
            viewHolder.tickMark.setVisibility(View.INVISIBLE);
            mSparseBooleanArray.put(position, false);
        }
        showSelectButton();
    }
}

