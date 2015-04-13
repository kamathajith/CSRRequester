package com.kamathajith.mobile.android.csrrequester;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.kamathajith.mobile.android.csrrequester.R;

/**
 * Created by kamathab on 3/16/15.
 */
public class CSRSettingActivity extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }
}
