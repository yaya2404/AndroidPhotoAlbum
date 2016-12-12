package utility;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import group26.photoalbum.R;

/**
 * Created by matth on 12/11/2016.
 */

public class PhotosAdapter extends ArrayAdapter<Photo>{
    public PhotosAdapter(Context context, ArrayList<Photo> photos) {
        super(context, R.layout.photo_layout, photos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Photo item = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.photo_layout,parent,false);
        }

        ImageView imgview = (ImageView) convertView.findViewById(R.id.image);
        imgview.setImageBitmap(item.getImage());


        return convertView;
    }

}
