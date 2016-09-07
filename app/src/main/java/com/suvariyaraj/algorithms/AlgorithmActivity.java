package com.suvariyaraj.algorithms;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.suvariyaraj.algorithms.Database.DBPARAM;
import com.suvariyaraj.algorithms.Database.ExternalDbOpenHelper;
import com.suvariyaraj.algorithms.Helper.HtmlFormatter;

import java.util.ArrayList;

public class AlgorithmActivity extends AppCompatActivity {

    /**
     * The {@link PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private static SQLiteDatabase database;
    static DBPARAM dbparam = new DBPARAM();
    ArrayList<String> titles;
    static ArrayList<String> array;
    static String section;
    static String subsection;
    static int id;
    static boolean isBookmarked;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    String title;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algorithm);


        Intent i = getIntent();
        subsection = i.getStringExtra("Subsection");
        section = i.getStringExtra("Section");
        Log.d("Algo",subsection+section);
        ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this, dbparam.DB_NAME);
        database = dbOpenHelper.openDataBase();

        final FloatingActionButton fab_copy = (FloatingActionButton) findViewById(R.id.fab_copy);
        FloatingActionButton fab_share = (FloatingActionButton) findViewById(R.id.fab_share);
        FloatingActionButton fab_doubt = (FloatingActionButton) findViewById(R.id.fab_doubt);
        final FloatingActionsMenu fab_menu = (FloatingActionsMenu) findViewById(R.id.fab_menu);

        if (fab_copy != null) {
            fab_copy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = "C Code : \n" + array.get(1).replace("~~", "") + "\n\nJava Code : \n" + array.get(2).replace("~~", "");
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("Shared Content", s);
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(getApplicationContext(), "Content Copied", Toast.LENGTH_SHORT).show();
                    if (fab_menu != null) {
                        fab_menu.collapse();
                    }
                }
            });
        }

        if (fab_share != null) {
            fab_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = "Algorithm Name : " + subsection + "\n\nC Code : \n" + array.get(1).replace("~~", "") + "\n\nJava Code : \n" + array.get(2).replace("~~", "");
                    if (fab_menu != null) {
                        fab_menu.collapse();
                    }
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, s);
                    sendIntent.setType("text/plain");
                    startActivity(Intent.createChooser(sendIntent, "Share using..."));
                }
            });
        }

        if (fab_doubt != null) {
            fab_doubt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), DoubtActivity.class);
                    i.putExtra("Title", subsection);
                    if (fab_menu != null) {
                        fab_menu.collapse();
                    }
                    startActivity(i);
                }
            });
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String title="";
        if(subsection.length()>20){
            title=subsection.substring(0, 20)+"...";
        }
        else{
            title=subsection;
        }
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setHomeButtonEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        if (mViewPager != null) {
            mViewPager.setAdapter(mSectionsPagerAdapter);
        }

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        if (tabLayout != null) {
            tabLayout.setupWithViewPager(mViewPager);
            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    mViewPager.setCurrentItem(tab.getPosition());
                    Log.d("mycheck", "tab : " + tab.getPosition());
                    if (tab.getPosition() == 0) {
                        fab_menu.setVisibility(View.GONE);
                    }
                    else {
                        fab_menu.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Algorithm Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.suvariyaraj.algorithms/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Algorithm Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.suvariyaraj.algorithms/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_algorithm, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            array = new ArrayList<String>();
            array.add("Description Not Available!");
            array.add("C Code Not Available!");
            array.add("Java Code Not Available!");
            //Log.d("AlgoActt",array.get(0));
            getInformation();
            //Log.d("AlgoActt",array.get(0));

            NestedScrollView scrollView = (NestedScrollView) rootView.findViewById(R.id.abcscrollView);
            HorizontalScrollView scrollView1 = new HorizontalScrollView(rootView.getContext());
            LinearLayout linearLayout = new LinearLayout(rootView.getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            linearLayout.setLayoutParams(params);
            params.bottomMargin = 10;
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            if (getArguments().getInt(ARG_SECTION_NUMBER) - 1 == 0) {

                String temparray[] = array.get(0).split("~~");
                //Log.d("AlgoActt1",temparray[0]);
                //array.set (0, "");
                for (int i = 0; i < temparray.length; i++) {
                    if (temparray[i].startsWith("Image:")) {
                        ImageView iv = new ImageView(rootView.getContext());
                        iv.setId(i + 1);
                        final int itd = this.getResources().getIdentifier(temparray[i].substring(6).toLowerCase(), "drawable", getActivity().getPackageName());
                        iv.setImageResource(itd);
                        iv.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        iv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(rootView.getContext(), ImageFullScreenActivity.class);
                                i.putExtra("image", itd);
                                startActivity(i);
                            }
                        });
                        linearLayout.addView(iv);

                    } else {
                        TextView tv = new TextView(rootView.getContext());
                        tv.setText(temparray[i]);

                        tv.setTextSize(18);
                        tv.setId(i + 1);
                        tv.setTextColor(Color.parseColor("#000000"));
                        linearLayout.addView(tv);
                    }
                }
            }

            if (getArguments().getInt(ARG_SECTION_NUMBER) - 1 == 1) {
                String temparray[] = array.get(1).toString().split("~~");

                for (int i = 0; i < temparray.length; i++) {
                    Log.d("AlgoAct",temparray[i]);

                    if (temparray[i].startsWith("Output:")||temparray[i].startsWith("Input/Output:")) {
                        TextView tv = new TextView(rootView.getContext());
                        tv.setText(temparray[i]);
                        tv.setId(i + 100);
                        tv.setTextColor(Color.parseColor("#000000"));
                        tv.setBackgroundColor(Color.parseColor("#e0e0e0"));
                        linearLayout.addView(tv, params);
                    } else if (temparray[i].startsWith("Input:")) {
                        TextView tv = new TextView(rootView.getContext());
                        tv.setText(temparray[i]);
                        tv.setId(i + 100);
                        tv.setTextColor(Color.parseColor("#000000"));
                        tv.setBackgroundColor(Color.parseColor("#e0e0e0"));
                        linearLayout.addView(tv, params);
                    } else {
                        if (temparray[i].trim().equals("")) {
                            continue;
                        }
//                        Toast.makeText(rootView.getContext(), "adsad", Toast.LENGTH_SHORT).show();
                        TextView tv = new TextView(rootView.getContext());
                        Log.d("mycheck1",temparray[i].trim());
                        Log.d("mycheck1WithoutTrim",temparray[i]);

                        tv.setText(Html.fromHtml(HtmlFormatter.htmlFormatter(temparray[i].trim ())));
                        //Log.d("mycheck1", String.valueOf(Html.fromHtml(HtmlFormatter.htmlFormatter(temparray[i].trim ()))));
                        //tv.setText(temparray[i].trim());
                        tv.setHorizontallyScrolling(true);
                        tv.setId(i + 100);
                        tv.setTextColor(Color.parseColor("#000000"));
                        linearLayout.addView(tv, params);
                    }
                }
            }

            if (getArguments().getInt(ARG_SECTION_NUMBER) - 1 == 2) {
                String temparray[] = array.get(2).toString().split("~~");
                for (int i = 0; i < temparray.length; i++) {
                    if (temparray[i].startsWith("Output:")||temparray[i].startsWith("Input/Output:")) {
                        TextView tv = new TextView(rootView.getContext());
                        tv.setText(temparray[i]);
                        tv.setId(i + 100);
                        tv.setTextColor(Color.parseColor("#000000"));
                        tv.setBackgroundColor(Color.parseColor("#e0e0e0"));
                        linearLayout.addView(tv, params);
                    } else if (temparray[i].startsWith("Input:")) {
                        TextView tv = new TextView(rootView.getContext());
                        tv.setText(temparray[i]);
                        tv.setId(i + 100);
                        tv.setBackgroundColor(Color.parseColor("#e0e0e0"));
                        tv.setTextColor(Color.parseColor("#000000"));
                        linearLayout.addView(tv, params);
                    } else {
                        if (temparray[i].trim().equals("")) {
                            continue;
                        }
                        TextView tv = new TextView(rootView.getContext());
                        tv.setText(temparray[i].trim());
                        tv.setTextColor(Color.parseColor("#000000"));
                        tv.setId(i + 100);
                        linearLayout.addView(tv, params);
                    }
                }
            }

            NestedScrollView.LayoutParams params2 = new NestedScrollView.LayoutParams(NestedScrollView.LayoutParams.MATCH_PARENT, NestedScrollView.LayoutParams.MATCH_PARENT);
            if (getArguments().getInt(ARG_SECTION_NUMBER) - 1 > 0) {
                scrollView1.addView(linearLayout);
                scrollView.addView(scrollView1);
            } else {
                scrollView.addView(linearLayout, params2);
            }
            return rootView;
        }

        private void getInformation() {
            Cursor cursor = database.query(true, dbparam.TABLE_NAME, new String[]{dbparam.COLOUMN_DESCRIPTION, dbparam.COLOUMN_CODEC, dbparam.COLOUMN_CODEJAVA}, dbparam.COLOUMN_SECTION + "='" + section + "' AND " + dbparam.COLOUMN_SUBSECTION + "='" + subsection + "'", null, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                if (!cursor.isAfterLast()) {
                    do {
                        String discription = cursor.getString(cursor.getColumnIndex(dbparam.COLOUMN_DESCRIPTION));
                        array.set(0, discription);
                        String codeC = cursor.getString(cursor.getColumnIndex(dbparam.COLOUMN_CODEC));
                        array.set(1, codeC);
                        String codeJAVA = cursor.getString(cursor.getColumnIndex(dbparam.COLOUMN_CODEJAVA));
                        array.set(2, codeJAVA);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Description";
                case 1:
                    return "C";
                case 2:
                    return "Java";
            }
            return null;
        }


    }
}
