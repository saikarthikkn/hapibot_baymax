package com.hapi.sdk.HapiBot;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.hapi.sdk.HapiBot.R;

public class ProfileConfiguration extends AppCompatActivity {

    private static final int PICK_CONTACT = 1 ;

    private SectionsPagerAdapter mSectionsPagerAdapter;


    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_configuration);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //if getying from fitbit,


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),savedInstanceState);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


    }

    public void listContacts(View v)
    {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);
    }


    public void saveProfile(View v)
    {
        SharedPreferences sharedPref =  getSharedPreferences("com.hapi.botuserpreference",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();

        EditText nameView = (EditText)findViewById(R.id.fragproconftxtfullname);
        editor.putString("userName", nameView.getText().toString());

        EditText ageView = (EditText)findViewById(R.id.fragproconftxtage);
        editor.putString("userAge", ageView.getText().toString());

        Spinner spinView = (Spinner)findViewById(R.id.fragproconfgender);
        editor.putString("userGender", spinView.getSelectedItem().toString());

        EditText htView = (EditText)findViewById(R.id.fragproconftxtheight);
        editor.putString("userHeight", htView.getText().toString());
        EditText wtView = (EditText)findViewById(R.id.fragproconftxtweight);
        editor.putString("userWeight", wtView.getText().toString());

        EditText emNameView = (EditText)findViewById(R.id.emegconftxtfullname);
        editor.putString("emerName", emNameView.getText().toString());

        EditText emphoneView = (EditText)findViewById(R.id.emegconftxtphone);
        editor.putString("emerPhone", emphoneView.getText().toString());


        editor.commit();

        Log.v("sai", "Saved name after commit is "+ sharedPref.getString("userName", "") );

        startActivity(new Intent(this, DashboardLandingActivity.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile_configuration, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_profile_configuration, container, false);
            //TextView nameView = (TextView) rootView.findViewById(R.id.cardholderName);
           // nameView.setText(getArguments().getString("userName"));
            return rootView;
        }
    }


    public static class EmergencyholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public EmergencyholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static EmergencyholderFragment newInstance(int sectionNumber) {
            EmergencyholderFragment fragment = new EmergencyholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_emergency_configuration, container, false);
//
            return rootView;
        }

//        public static void listContacts(View v)
//        {
//            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
//            startActivityForResult(intent, PICK_CONTACT);
//        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        Bundle bundleFragments;
        public SectionsPagerAdapter(FragmentManager fm, Bundle bundle) {
            super(fm);
            bundleFragments = bundle;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    PlaceholderFragment pfrag = new PlaceholderFragment().newInstance(position +1);
                    pfrag.setArguments(bundleFragments);
                    return pfrag;
                case 1:
                    EmergencyholderFragment efrag = EmergencyholderFragment.newInstance(position + 1);
                    efrag.setArguments(bundleFragments);
                    return efrag;

            }
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
