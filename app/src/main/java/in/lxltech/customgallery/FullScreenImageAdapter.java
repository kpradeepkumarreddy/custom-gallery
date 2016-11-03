package in.lxltech.customgallery;


import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by pradeepkumar on 8/10/2016.
 */

public class FullScreenImageAdapter extends PagerAdapter {

    private Activity activity;
    private ArrayList<String> imagePaths;
    private LayoutInflater inflater;

    // constructor
    public FullScreenImageAdapter(Activity activity,
                                  ArrayList<String> imagePaths) {
        this.activity = activity;
        this.imagePaths = imagePaths;
    }

    @Override
    public int getCount() {
        return this.imagePaths.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView ivSwipe;

        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.item_layout_viewpager, container,
                false);
        TextView tvPositon = (TextView) viewLayout.findViewById(R.id.tvPosition);
        ivSwipe = (ImageView) viewLayout.findViewById(R.id.ivSwipe);

        Glide.with(activity).load(imagePaths.get(position)).into(ivSwipe);
        tvPositon.setText((position+1)+" / "+imagePaths.size());
        ((ViewPager) container).addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);
    }
}