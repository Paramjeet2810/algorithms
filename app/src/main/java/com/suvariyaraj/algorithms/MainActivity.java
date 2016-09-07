package com.suvariyaraj.algorithms;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.suvariyaraj.algorithms.Database.DBPARAM;
import com.suvariyaraj.algorithms.Database.ExternalDbOpenHelper;
import com.suvariyaraj.algorithms.Database.MyDBHelper;
import com.suvariyaraj.algorithms.GridView.GridViewAdapter;
import com.suvariyaraj.algorithms.GridView.GridViewItem;
import com.suvariyaraj.algorithms.GridView.ViewHolder;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    GridViewAdapter gridViewAdapter;
    private SQLiteDatabase database;
    ArrayList<Integer> image_ids; ArrayList<String> titles, image;
    DBPARAM dbparam = new DBPARAM ();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       SharedPreferences sharedPreferences = getSharedPreferences("MyPrefAlgoApp", Context.MODE_PRIVATE);
        boolean isNewUser = sharedPreferences.getBoolean("newuser",false);
        if(!isNewUser){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("newuser", true);
            editor.commit();
            Intent i = new Intent(this, MyIntro.class);
            startActivity(i);
            finish();
        }

//        ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper (this, dbparam.DB_NAME);
        ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this, dbparam.DB_NAME);
        database = dbOpenHelper.openDataBase();
        image_ids = new ArrayList<Integer> ();
        getDistinctSections ();
        for(int i=0;i<image.size ();i++) {
            int itd = this.getResources ().getIdentifier (image.get (i), "drawable", getPackageName ());
            image_ids.add (itd);
        }
        gridView = (GridView) findViewById(R.id.gridView);
        gridViewAdapter = new GridViewAdapter(this, image_ids, titles);
        gridView.setAdapter(gridViewAdapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GridViewItem item = (GridViewItem)  gridViewAdapter.getItem(position);
                Intent i = new Intent(getApplicationContext(), SubSectionActivity.class);
                i.putExtra("Section",item.getTitle());
                startActivityForResult (i, 1);
            }
        });
    }

    private void getDistinctSections() {
        image = new ArrayList<String> ();
        titles = new ArrayList<String> ();
        Cursor cursor = database.query(true, dbparam.TABLE_NAME, new String[] {dbparam.COLOUMN_SECTION}, null, null, null, null, null, null);
        if(cursor!=null) {
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndex(dbparam.COLOUMN_SECTION));
                    titles.add(name);
                    image.add (name.toLowerCase().replace (" ", "_"));
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_algorithm, menu);
           // Toast.makeText(MainActivity.this,"HEllo", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
     //  Toast.makeText(MainActivity.this,"HEllo"+id, Toast.LENGTH_SHORT).show();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_developer) {
            Intent i = new Intent(getApplicationContext(),AboutUs.class);
            startActivity(i);
            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            //Toast.makeText(MainActivity.this,"HEllo"+id, Toast.LENGTH_SHORT).show();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
