package in.lxltech.customgallery;


import android.view.View;
import android.widget.ImageView;

/**
 * Created by pradeepkumar on 8/1/2016.
 */
public class ViewHolder {
    ImageView imageView, tickMark;

    ViewHolder(View view){
        imageView = (ImageView) view.findViewById(R.id.galleryImageView);
        tickMark = (ImageView) view.findViewById(R.id.tickMark);
    }
}
