package com.farrutaps.tqhapp.view;

import android.app.ActionBar;
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
import com.farrutaps.tqhapp.client.Connection;
import com.farrutaps.tqhapp.controller.Controller;
import com.farrutaps.tqhapp.model.Options;
import com.farrutaps.tqhapp.model.User;

import java.util.Random;


public class MainActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener {

    private static final int NUM_TABS = 2;

    /* Activity Resources*/
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private int[] tabLayoutIcons = {R.drawable.ic_people, R.drawable.ic_person};
    private FloatingActionButton fab;
    private TextView username;

    /* Main Fragment Resources */
    private static ListView lvStatus;
    private static StatusAdapter lvStatusAdapter;
    private static ImageView ledWZ1, ledWZ2, ledWZ4, ledWZ8, ledFK1, ledFK2, ledFK4, ledFK8;

    /* User Fragment Resources */
    private static ListView lvMyStatus;
    private static MyStatusAdapter lvMyStatusAdapter;
    private static ImageButton ibPublish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Controller();
        // TODO delete test
        try {
            Controller.setMaster(Controller.getUsers().get(0));
        } catch(Exception e) {}

        this.initResources();
        this.setResources();

    }

    private void initResources() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mViewPager = (ViewPager) findViewById(R.id.container);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        fab = (FloatingActionButton) findViewById(R.id.fab);
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
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNumberPicker(view);

                // TODO change
                /*String result = "CACA";
                try {
                    result = Connection.exampleGET();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(view.getContext(),result,Toast.LENGTH_LONG).show();*/
            }
        });

        /* TextView */
        try {
            username.setText(Controller.getMaster().getUsername());
        } catch (Exception e) {}
    }

    private void test(View view) {
        Random r = new Random();
        Controller.getUsers().get(0).getStatus().setOnToOption(Options.HAMBRE.getRandom(), r.nextBoolean());
        Controller.getUsers().get(1).getStatus().setOnToOption(Options.HAMBRE.getRandom(), r.nextBoolean());
        lvStatusAdapter.notifyDataSetChanged();

        Controller.getUsers().get(0).setBackHome(r.nextInt(12));
        Controller.getUsers().get(1).setBackHome(r.nextInt(12));
        PlaceholderFragment.setBackHomeLeds(view);
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
                    this.setMainFragment(rootView);
                    break;

                case 2:
                    rootView = inflater.inflate(R.layout.fragment_user, container, false);
                    this.setUserFragment(rootView);
                    break;
            }

            return rootView;
        }

        /*** MAIN FRAGMENT ***/
        private void setMainFragment(View rootView) {
            initMainResources(rootView);
            setMainResources(rootView);
        }

        private void initMainResources(View rootView) {
            // ListView with Status Options for both users
            lvStatus = (ListView) rootView.findViewById(R.id.lv_status);

            // Back Home LEDs
            ledWZ1 = (ImageView) rootView.findViewById(R.id.led_wz_1);
            ledWZ2 = (ImageView) rootView.findViewById(R.id.led_wz_2);
            ledWZ4 = (ImageView) rootView.findViewById(R.id.led_wz_4);
            ledWZ8 = (ImageView) rootView.findViewById(R.id.led_wz_8);
            ledFK1 = (ImageView) rootView.findViewById(R.id.led_fk_1);
            ledFK2 = (ImageView) rootView.findViewById(R.id.led_fk_2);
            ledFK4 = (ImageView) rootView.findViewById(R.id.led_fk_4);
            ledFK8 = (ImageView) rootView.findViewById(R.id.led_fk_8);
        }

        private void setMainResources(View rootView) {
            /* ListView */
            try {
                lvStatusAdapter = new StatusAdapter(rootView.getContext(), Options.values(), Controller.getUsers());
                lvStatus.setAdapter(lvStatusAdapter);
            } catch (Exception e) {}

            /* LEDs */
            setBackHomeLeds(rootView);
        }

        public static void setBackHomeLeds(View rootView){
            ImageView[] ledsWZ = {ledWZ8, ledWZ4, ledWZ2, ledWZ1};
            try {
                setUserBackHomeLeds(Controller.getUsers().get(0), ledsWZ, rootView);
            } catch (Exception e) {}

            ImageView[] ledsFK = {ledFK8, ledFK4, ledFK2, ledFK1};
            try {
                setUserBackHomeLeds(Controller.getUsers().get(1), ledsFK, rootView);
            } catch (Exception e) {}
        }

        private static void setUserBackHomeLeds(User user, ImageView[] leds, View rootView)
        {
            // Get the decimal hour in binary base with 4 digits
            String binStr = Integer.toBinaryString(user.getBackHome());
            while (binStr.length() < 4) {
                binStr = "0" + binStr;
            }

            // Turn the LEDs on or off depending on the binary result
            for(int i = 0; i < binStr.length(); i++) {
                if (binStr.charAt(i) == '1')
                    leds[i].setBackground(rootView.getResources().getDrawable(R.drawable.led_on));
                else
                    leds[i].setBackground(rootView.getResources().getDrawable(R.drawable.led_off));
            }
        }

        /*** USER FRAGMENT ***/
        private void setUserFragment(View rootView) {
            initUserResources(rootView);
            setUserResources(rootView);
        }

        private void initUserResources(View rootView) {
            lvMyStatus = (ListView) rootView.findViewById(R.id.lv_my_status);
            ibPublish = (ImageButton) rootView.findViewById(R.id.ib_publish);
        }

        private void setUserResources(View rootView) {
            /* ListView */
            try {
                lvMyStatusAdapter = new MyStatusAdapter(rootView.getContext(), Options.values(), Controller.getMaster());
                lvMyStatus.setAdapter(lvMyStatusAdapter);
            } catch (Exception e) {}

            ibPublish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    lvStatusAdapter.notifyDataSetChanged();

                    // TODO sent info to Esteful
                    String msg = getResources().getString(R.string.save_my_status);
                    Toast.makeText(view.getContext(), msg, Toast.LENGTH_SHORT).show();
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

    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
        String msg;
        if(numberPicker.getValue() == 0)
            msg = getResources().getString(R.string.back_home_dk);
        else
            msg = getResources().getString(R.string.back_home, numberPicker.getValue());

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void showNumberPicker(View view){
        NumberPickerDialog newFragment = new NumberPickerDialog();
        newFragment.setValueChangeListener(this);
        newFragment.show(getSupportFragmentManager(), "time picker");
    }
}
