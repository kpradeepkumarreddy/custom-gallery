package in.lxltech.customgallery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

/**
 * Created by pradeepkumar on 8/10/2016.
 */

public class ImagesInFullScreen extends Activity {
    private FullScreenImageAdapter adapter;
    private ViewPager viewPager;
    private ArrayList<String> imageUrls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.images_full_screen_layout);

        viewPager = (ViewPager) findViewById(R.id.pager);

        Intent i = getIntent();
        int position = i.getIntExtra("position", 0);
        imageUrls = i.getStringArrayListExtra("images");

        adapter = new FullScreenImageAdapter(this, imageUrls);

        viewPager.setAdapter(adapter);

        // displaying selected image first
        viewPager.setCurrentItem(position);
    }

}
