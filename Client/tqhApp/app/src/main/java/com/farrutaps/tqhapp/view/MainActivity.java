package com.farrutaps.tqhapp.view;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.ImageView;
import android.widget.ListView;

import com.farrutaps.tqhapp.Adapters.StatusAdapter;
import com.farrutaps.tqhapp.R;
import com.farrutaps.tqhapp.model.Options;
import com.farrutaps.tqhapp.model.User;


public class MainActivity extends AppCompatActivity {

    private static final int NUM_TABS = 2;
    private static User userWZ, userFK;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        // Set icons to the tabs.
        int[] icons = {R.drawable.ic_people, R.drawable.ic_person};
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setIcon(icons[i]);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        userWZ = new User("sonia");
        userFK = new User("sebastian");
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

            int section = getArguments().getInt(ARG_SECTION_NUMBER);
            View rootView = null;

            switch(section)
            {
                case 1:
                    rootView = inflater.inflate(R.layout.fragment_main, container, false);

                    // List View with status
                    ListView lvStatus = (ListView) rootView.findViewById(R.id.lv_status);
                    StatusAdapter lvAdapter = new StatusAdapter(rootView.getContext(), Options.values(), userWZ, userFK);
                    lvStatus.setAdapter(lvAdapter);

                    // Home back LEDs
                    ImageView ledWZ1 = (ImageView) rootView.findViewById(R.id.led_wz_1);
                    ImageView ledWZ2 = (ImageView) rootView.findViewById(R.id.led_wz_2);
                    ImageView ledWZ4 = (ImageView) rootView.findViewById(R.id.led_wz_4);
                    ImageView ledWZ8 = (ImageView) rootView.findViewById(R.id.led_wz_8);
                    ImageView[] ledsWZ = {ledWZ8, ledWZ4, ledWZ2, ledWZ1};
                    userWZ.setHour(1);
                    this.setBackHomeLeds(userWZ, ledsWZ, rootView);

                    ImageView ledFK1 = (ImageView) rootView.findViewById(R.id.led_fk_1);
                    ImageView ledFK2 = (ImageView) rootView.findViewById(R.id.led_fk_2);
                    ImageView ledFK4 = (ImageView) rootView.findViewById(R.id.led_fk_4);
                    ImageView ledFK8 = (ImageView) rootView.findViewById(R.id.led_fk_8);
                    ImageView[] ledsFK = {ledFK8, ledFK4, ledFK2, ledFK1};
                    userFK.setHour(12);
                    this.setBackHomeLeds(userFK, ledsFK, rootView);

                    // testing change leds
                    userWZ.setStatus(Options.EMPITUFE,true);
                    userWZ.setStatus(Options.CACA,true);
                    userFK.setStatus(Options.MASATGE,true);
                    lvAdapter.notifyDataSetChanged();

                    break;

                case 2:
                    rootView = inflater.inflate(R.layout.fragment_user, container, false);

                    break;
            }

            return rootView;
        }

        private void setBackHomeLeds(User user, ImageView[] leds, View view)
        {
            String binStr = Integer.toBinaryString(user.getHour());
            while(binStr.length() < 4)
            {
                binStr = "0" + binStr;
            }
            for(int i = 0; i < binStr.length(); i++) {
                if (binStr.charAt(i) == '1')
                    leds[i].setBackground(view.getResources().getDrawable(R.drawable.led_on));
                else
                    leds[i].setBackground(view.getResources().getDrawable(R.drawable.led_off));
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
            // Show total pages.
            return NUM_TABS;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            /*switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
            }*/
            return null;
        }
    }
}
