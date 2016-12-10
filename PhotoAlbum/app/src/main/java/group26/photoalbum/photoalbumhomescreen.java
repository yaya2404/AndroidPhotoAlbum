package group26.photoalbum;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;


import java.io.Serializable;

import utility.SerializeData;
import utility.PhotoAlbum;


public class photoalbumhomescreen extends AppCompatActivity {

    private ListView listView;
    private ArrayList<PhotoAlbum> albums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        try {
            FileInputStream fis = openFileInput("album.dat");
            
        }catch(IOException e){
            e.printStackTrace();
        }

        //SerializeData.initData();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        //albums = SerializeData.getData();
       // System.out.println(albums.size());
        /*
        albums.add(new PhotoAlbum("test"));

        ArrayAdapter<PhotoAlbum> adapter = new ArrayAdapter<PhotoAlbum>(this, R.layout.albums, albums);

        listView = (ListView)findViewById(R.id.album_view);
        listView.setAdapter(adapter);
        */
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
                break;
            case R.id.rename:
                break;
            case R.id.delete:
                break;


        }

        return super.onOptionsItemSelected(item);
    }
}
