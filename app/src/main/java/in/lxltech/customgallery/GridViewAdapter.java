package in.lxltech.customgallery;

import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
/**
 * Created by pradeepkumar on 7/28/2016.
 */

public class GridViewAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> imageUrls;
    private SparseBooleanArray mSparseBooleanArray; //Variable to store selected Images
    private boolean isCustomGalleryActivity = false;

    public GridViewAdapter(Context context, ArrayList<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
    }

    public GridViewAdapter(Context context, ArrayList<String> imageUrls, SparseBooleanArray mSparseBooleanArray, boolean isCustomGalleryActivity) {
        this.context = context;
        this.imageUrls = imageUrls;
        this.mSparseBooleanArray = mSparseBooleanArray;
        this.isCustomGalleryActivity = isCustomGalleryActivity;
    }

    //Method to return selected Images
    public ArrayList<String> getCheckedItems() {
        ArrayList<String> mTempArry = new ArrayList<String>();

        for (int i = 0; i < imageUrls.size(); i++) {
            Log.d("log", "i = " + mSparseBooleanArray.get(i));
            if (mSparseBooleanArray.get(i)) {
                mTempArry.add(imageUrls.get(i));
            }
        }
        return mTempArry;
    }

    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @Override
    public Object getItem(int i) {
        return imageUrls.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder = null;

        // no view at this position, so creating a new view
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.customgridview_item, viewGroup, false);//Inflate layout
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {  // recycling a view
            viewHolder = (ViewHolder) view.getTag();
            if (isCustomGalleryActivity) {
                if (mSparseBooleanArray.get(position)) {
                    viewHolder.tickMark.setVisibility(View.VISIBLE);
                    viewHolder.imageView.setAlpha(0.5f);
                } else {
                    viewHolder.imageView.setAlpha(1f);
                    viewHolder.tickMark.setVisibility(View.INVISIBLE);
                }
            }
        }
        Glide.with(context).load(imageUrls.get(position)).into(viewHolder.imageView);

        return view;
    }
}

