package utility;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

import group26.photoalbum.R;
import group26.photoalbum.ShowPhoto;

/**
 * Created by matth on 12/11/2016.
 */

public class PhotosAdapter extends ArrayAdapter<Photo>{

    private static final String PHOTO_INDEX_KEY = "photo_index";

    public PhotosAdapter(Context context, ArrayList<Photo> photos) {
        super(context, R.layout.photo_layout, photos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Photo item = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.photo_layout,parent,false);
        }

        ImageView imgview = (ImageView) convertView.findViewById(R.id.image);
        imgview.setImageBitmap(item.getImage());
        /*
        imgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(PHOTO_INDEX_KEY, item);
                Intent intent = new Intent(v.getContext(), ShowPhoto.class);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
            }
        });
        */
        return convertView;
    }
}
