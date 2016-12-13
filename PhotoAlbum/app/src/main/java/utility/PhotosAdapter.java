package utility;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnLongClickListener;

import java.util.ArrayList;

import group26.photoalbum.R;
import group26.photoalbum.ShowAlbum;
import group26.photoalbum.ShowPhoto;
import group26.photoalbum.photoalbumhomescreen;

/**
 * Created by matth on 12/11/2016.
 */

public class PhotosAdapter extends ArrayAdapter<Photo>{

    private static final String PHOTO_INDEX_KEY = "photo_index";
    private static final int MOVE_INDEX = 0;
    private static final int DELETE_INDEX = 1;
    private static final int SEARCH_PAGE_KEY = 2;
    private static final int ALBUM_PAGE_KEY = 3;


    private int key;

    private PhotoAlbum album;
    private ArrayList<Photo> photos;

    public PhotosAdapter(Context context,PhotoAlbum album, int key) {
        super(context, R.layout.photo_layout, album.getPhotos());
        this.photos = album.getPhotos();
        this.album = album;
        this.key = key;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Photo item = getItem(position);
        final int index = position;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.photo_layout, parent, false);
        }
        final ImageView imgview = (ImageView) convertView.findViewById(R.id.image);
        imgview.setImageBitmap(item.getImage());

        if (this.key == ALBUM_PAGE_KEY) {
            imgview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(PHOTO_INDEX_KEY, index);
                    bundle.putString(photoalbumhomescreen.ALBUM_NAME_KEY, album.toString());
                    Intent intent = new Intent(v.getContext(), ShowPhoto.class);
                    intent.putExtras(bundle);
                    v.getContext().startActivity(intent);
                }
            });

            imgview.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    final View test = view;
                    builder.setTitle("Options")
                            .setItems(R.array.options, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case MOVE_INDEX:
                                            AlertDialog.Builder builder = new AlertDialog.Builder(test.getContext());
                                            builder.setTitle("Move Photo to a Different Album");
                                            final EditText newname = new EditText(test.getContext());
                                            newname.setHint("Album Name");
                                            newname.setInputType(InputType.TYPE_CLASS_TEXT);
                                            builder.setView(newname);

                                            builder.setNegativeButton("Move", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    String newn = newname.getText().toString();
                                                    int index = SerializeData.getAlbum(newn);
                                                    if (index == -1) {
                                                        Toast.makeText(test.getContext(), "Error: album does not exist", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        SerializeData.getData().get(index).getPhotos().add(photos.remove(photos.indexOf(item)));
                                                        refreshList();
                                                        SerializeData.writeData();
                                                    }
                                                }
                                            });

                                            builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.cancel();
                                                }
                                            });
                                            builder.show();
                                            break;
                                        case DELETE_INDEX:
                                            photos.remove(photos.indexOf(item));
                                            refreshList();
                                            SerializeData.writeData();
                                            break;

                                    }
                                }
                            });
                    builder.show();
                    return true;
                }
            });
        }
        return convertView;
    }
    private void refreshList(){
        this.notifyDataSetChanged();
    }
}
