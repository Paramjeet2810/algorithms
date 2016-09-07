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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.suvariyaraj.algorithms.Database.DBPARAM;
import com.suvariyaraj.algorithms.Database.ExternalDbOpenHelper;
import java.util.ArrayList;

public class SubSectionActivity extends AppCompatActivity {

    ListView listView;
    String[] array;
    private SQLiteDatabase database;
    DBPARAM dbparam = new DBPARAM ();
    ArrayList<String> titles;
    String section;
    public static final String MyPREFERENCES = "MyPrefsAlgorithmApp" ;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_section);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        section = i.getStringExtra("Section");
//        Toast.makeText(getApplicationContext(), section, Toast.LENGTH_SHORT).show();
        sharedPreferences = getSharedPreferences ("myprefAlgoApp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit ();
        editor.putString ("Section",section);
        editor.commit ();


        ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper (this, dbparam.DB_NAME);
        database = dbOpenHelper.openDataBase();

        getDistinctSubSections ();
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.single_list_view_item, titles);
        listView = (ListView) findViewById(R.id.subsection_listview);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), AlgorithmActivity.class);
                i.putExtra("Subsection", titles.get (position));
                i.putExtra ("Section", section);
                startActivity (i);
            }
        });
    }

    private void getDistinctSubSections() {
        titles = new ArrayList<String> ();
        Cursor cursor = database.query(true, dbparam.TABLE_NAME, new String[] {dbparam.COLOUMN_SUBSECTION}, dbparam.COLOUMN_SECTION+" = '"+section+"'", null, null, null, null, null);
        if(cursor!=null) {
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndex(dbparam.COLOUMN_SUBSECTION));
                    titles.add(name);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult (requestCode, resultCode, data);
        Log.i ("mycheck","I got here");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_algorithm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_developer) {
            Intent i = new Intent(getApplicationContext(),AboutUs.class);
            startActivity(i);
            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
