package group26.photoalbum;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;


import java.io.Serializable;

import utility.AlbumsAdapter;
import utility.SerializeData;
import utility.PhotoAlbum;


public class photoalbumhomescreen extends AppCompatActivity {

    public static final String ALBUM_NAME_KEY = "album_name";

    private ListView listView;
    protected ArrayList<PhotoAlbum> albums;
    private AlbumsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*
        try {
            FileInputStream fis = openFileInput("album.dat");
            
        }catch(IOException e){
            e.printStackTrace();
        }
        */

        //SerializeData.initData();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        albums = new ArrayList<PhotoAlbum>();
        albums.add(new PhotoAlbum("internal test"));
        adapter = new AlbumsAdapter(this, albums);
        System.out.println(adapter.getCount());
        listView = (ListView)findViewById(R.id.album_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view,
                                    int position,
                                    long id) {
                openAlbum(position);
            }
        });
    }

    private void openAlbum(int pos){
        Bundle bundle = new Bundle();
        bundle.putString(ALBUM_NAME_KEY, albums.get(pos).toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();

        switch(id){

            case R.id.create:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Type Album Name");

                final EditText input = new EditText(this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setNegativeButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String m_Text = input.getText().toString();
                        if(getAlbum(m_Text) != -1){
                            Toast.makeText(photoalbumhomescreen.this, "Error: album name already exists", Toast.LENGTH_SHORT).show();
                        }else {
                            albums.add(new PhotoAlbum(m_Text));
                            adapter.notifyDataSetChanged();
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
            case R.id.rename:
                break;
            case R.id.delete:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                builder2.setTitle("Type Album Name");

                final EditText input2 = new EditText(this);
                input2.setInputType(InputType.TYPE_CLASS_TEXT);
                builder2.setView(input2);

                builder2.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String m_Text = input2.getText().toString();
                        int index = getAlbum(m_Text);

                        if(index == -1){
                            Toast.makeText(photoalbumhomescreen.this, "Error: could not find album", Toast.LENGTH_SHORT).show();
                        }else{
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

                builder2.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder2.show();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private int getAlbum(String name){
        for(int i = 0; i < this.albums.size(); i++){
            if(this.albums.get(i).toString().compareToIgnoreCase(name) == 0){
                return i;
            }
        }
        return -1;
    }
}
