package utility;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import android.content.Context;
import android.widget.TextView;

import group26.photoalbum.R;

/**
 * Created by matth on 12/10/2016.
 */

public class AlbumsAdapter extends ArrayAdapter<PhotoAlbum> {

    public AlbumsAdapter(Context context, ArrayList<PhotoAlbum> albums) {
        super(context, R.layout.albums, albums);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        PhotoAlbum item = getItem(position);


        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.albums,parent,false);
        }

        TextView albumName = (TextView) convertView.findViewById(R.id.albumName);
        albumName.setText(item.toString());
        return convertView;
    }


}
