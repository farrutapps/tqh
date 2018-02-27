package com.farrutaps.tqhapp.view;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.farrutaps.tqhapp.Adapters.MyStatusAdapter;
import com.farrutaps.tqhapp.Adapters.StatusAdapter;
import com.farrutaps.tqhapp.R;
import com.farrutaps.tqhapp.controller.Controller;
import com.farrutaps.tqhapp.controller.Parameters;



public class MainActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener {

    private static final int NUM_TABS = 2;

    /* Activity Resources*/
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private int[] tabLayoutIcons = {R.drawable.ic_people, R.drawable.ic_person};
    private FloatingActionButton fabBackHome;
    private TextView username;

    /* Main Fragment Resources */
    private static ListView lvStatus;
    private static StatusAdapter lvStatusAdapter;
    private static ImageView ledu01, ledu02, ledu04, ledu08, ledu11, ledu12, ledu14, ledu18;
    private static ImageButton ibRefresh;

    /* User Fragment Resources */
    private static ListView lvMyStatus;
    private static MyStatusAdapter lvMyStatusAdapter;
    private static ImageButton ibPublish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Controller(this);

        Intent intent = getIntent();
        int id = intent.getIntExtra(Parameters.USER_ID.name(), 0);

        try {
            Controller.setMaster(Controller.getUsers().get(id));
            Controller.sendGet();
        } catch (Exception e) {}

        this.initResources();
        this.setResources();

    }

    private void initResources() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mViewPager = (ViewPager) findViewById(R.id.container);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        fabBackHome = (FloatingActionButton) findViewById(R.id.fab_back_home);
        username = (TextView) findViewById(R.id.action_username);
    }

    private void setResources () {
        /* Toolbar */
        setSupportActionBar(toolbar);

        /* ViewPager */
        // Create the adapter that will return a fragment for each of the
        // primary sections of the activity and set up the ViewPager with the sections adapter.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);

        /* TabLayout */
        tabLayout.setupWithViewPager(mViewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setIcon(tabLayoutIcons[i]);
        }

        /* FloatingActionButton */
        fabBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNumberPicker(view);
            }
        });

        /* TextView */
        try {
            username.setText(Controller.getMaster().getUsername());
        } catch (Exception e) {}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
        String msg;
        if(numberPicker.getValue() == 0)
            msg = getString(R.string.back_home_dk);
        else
            msg = getString(R.string.back_home, numberPicker.getValue());

        // Save the time value and send it to the server
        try {
            Controller.setBackHome(numberPicker.getValue());
            Controller.sendPost(false);
            refreshMasterBackHomeLeds();
            Controller.showToast(this, msg);
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Controller.showToast(this, getString(R.string.any_error));
        }
    }

    public void showNumberPicker(View view){
        NumberPickerDialog dialog = new NumberPickerDialog();
        dialog.setValueChangeListener(this);
        dialog.show(getSupportFragmentManager(), Parameters.TIME_PICKER.name());
    }

    public static void refreshMasterBackHomeLeds() {
        try {
            Controller.refreshUserBackHomeLeds(Controller.getMaster());
        } catch (Exception e) {}
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private static ImageView[] ledsUser0, ledsUser1;
        private static View rootView;

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {}

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
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            int section = getArguments().getInt(ARG_SECTION_NUMBER);

            switch(section)
            {
                case 1:
                    rootView = inflater.inflate(R.layout.fragment_main, container, false);
                    this.setMainFragment();
                    break;

                case 2:
                    rootView = inflater.inflate(R.layout.fragment_user, container, false);
                    this.setUserFragment();
                    break;
            }

            return rootView;
        }

        /*** MAIN FRAGMENT ***/
        private void setMainFragment() {
            initMainResources();
            setMainResources();
        }

        private void initMainResources() {
            // ListView with Status Options for both users
            lvStatus = (ListView) rootView.findViewById(R.id.lv_status);

            // Back Home LEDs
            ledu01 = (ImageView) rootView.findViewById(R.id.led_u0_1);
            ledu02 = (ImageView) rootView.findViewById(R.id.led_u0_2);
            ledu04 = (ImageView) rootView.findViewById(R.id.led_u0_4);
            ledu08 = (ImageView) rootView.findViewById(R.id.led_u0_8);
            ledsUser0 = new ImageView[] {ledu08, ledu04, ledu02, ledu01};

            ledu11 = (ImageView) rootView.findViewById(R.id.led_u1_1);
            ledu12 = (ImageView) rootView.findViewById(R.id.led_u1_2);
            ledu14 = (ImageView) rootView.findViewById(R.id.led_u1_4);
            ledu18 = (ImageView) rootView.findViewById(R.id.led_u1_8);
            ledsUser1 = new ImageView[] {ledu18, ledu14, ledu12, ledu11};

            // Refresh ImageButton
            ibRefresh = (ImageButton) rootView.findViewById(R.id.ib_refresh);
        }

        private void setMainResources() {
            /* ListView */
            try {
                lvStatusAdapter = new StatusAdapter(rootView.getContext(), Controller.getOptions(), Controller.getUsers());
                lvStatus.setAdapter(lvStatusAdapter);
                Controller.setMainAdapter(lvStatusAdapter);
            } catch (Exception e) {}

            /* LEDs */
            refreshMasterBackHomeLeds();

            /* ImageButton */
            ibRefresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Controller.sendGet();
                    } catch (Exception e) {}
                }
            });
        }

        public static ImageView[] getLedsUser(int id) {
            switch(id)
            {
                case 0:
                    return ledsUser0;

                case 1:
                    return ledsUser1;

                default:
                    return null;
            }
        }

        public static View getRootView() {
            return rootView;
        }

        /*** USER FRAGMENT ***/
        private void setUserFragment() {
            initUserResources();
            setUserResources();
        }

        private void initUserResources() {
            lvMyStatus = (ListView) rootView.findViewById(R.id.lv_my_status);
            ibPublish = (ImageButton) rootView.findViewById(R.id.ib_publish);
        }

        private void setUserResources() {
            /* ListView */
            try {
                lvMyStatusAdapter = new MyStatusAdapter(rootView.getContext(), Controller.getOptions(), Controller.getMaster());
                lvMyStatus.setAdapter(lvMyStatusAdapter);
                Controller.setUserAdapter(lvMyStatusAdapter);
            } catch (Exception e) {}

            /* ImageButton */
            ibPublish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String msg;
                    try {
                        Controller.sendPost(false);
                        msg = getString(R.string.sent_status);
                        lvStatusAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        msg = getString(R.string.any_error);
                    }
                    Controller.showToast(view.getContext(), msg);
                }
            });
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
            // Show total pages.
            return NUM_TABS;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }
}
