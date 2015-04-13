/**
 *
 * This is a sample application that does the following
 *  1.  Generates an ECDSA signed certificate on a p256 curve, creates a certificate signing request and
 *      sends the .csr file via a share utility (email, Bluetooth, etc)
 *
 * Supports Android JellyBean and up (API level 16 and up)
 * Tested on a Samsung Galaxy Nexus phone (Should work on a tablet, but not tested)
 *
 * Developed using Android Studio AI-140.1782451
 *
 *  Author: Ajith Kamath
 *
 */

/**
 * General description of the project
 *
 * Core of the project:
 * 1. Create a CertificateInfo object
 *      This usually involves info on your company, location and app
 * 2. Generate a Signature
 *      Implement the AlgorithmVendor interface for ECDSA,
 *      Use the ECDSAAlgorithmVendor and sign the CertificateInfo
 * 3. Create a CSR
 *      Implement the CSRVendor interface for PKCS10
 *      (The PKCS#10 standard defines a binary format for encoding CSRs for use with X.509. It is expressed in ASN.1)
 * 4. Share/send the data to the CSR endpoint by generating an Android sharing intent
 *      (Via an IntentChooser)
 *      Alternatively, we could have also used the ShareActionProvider from the ActionBar
 *
 * Note: The private key is not persisted onto a keystore as in a real life scenario.
 * This is obviously just a test project and does not include that feature
 *
 */

/**
 * The design incorporates a tabbed UI pattern, each tab loading a separate fragment
 *
 * Tab 1: Where we do all the CSR work
 * Tab 2: Loads a wikipedia page that explains about CSR
 * Tab 3: A YouTube video of a Google Talk on Encryption infrastructure
 *
 * The setting screen just has a short description of the objective of the project
 */

package com.kamathajith.mobile.android.csrrequester;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Locale;

public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    // The number of tabs to show is 3
    // Do it: Make the actual request
    // Read about it: Wikipedia page that explains the ECDSA algorithm
    // Watch it: Watch a YouTube video about CSR
    public static final int NUMBER_OF_TABS = 3;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
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
            Intent i = new Intent(this, CSRSettingActivity.class);
            startActivity(i);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
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
            // First tab: Where we do all the work. CSR Generator
            // Second tab: User can read Wikipedia on how the CSR is designed
            // Third tab: A google tech talk on YouTUBE on CSR
            switch (position) {
                case 0:
                    return CSRRequesterFragment.newInstance(position + 1);
                case 1:
                    return WikipediaFragment.newInstance(position + 1);
                case 2:
                    return YouTubeFragment.newInstance(position + 1);
                default:
                    return WikipediaFragment.newInstance(position + 1);
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return NUMBER_OF_TABS;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

}
