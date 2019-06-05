package cse.hansung.kr.smartdiaper;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;

public class SettingPreferenceFragment extends PreferenceFragment {
    SharedPreferences prefs;

    ListPreference cycleTimeListPreference;

    SwitchPreference cycleTimeSwitchPreference;

    //EditTextPreference ipAddressEditPreference;

    PreferenceManager preferenceManager;

    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        preferenceManager = getPreferenceManager();

        preferenceManager.setSharedPreferencesName("settingsPreference");
        preferenceManager.setSharedPreferencesMode(Context.MODE_PRIVATE);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        addPreferencesFromResource(R.xml.preference);

        cycleTimeSwitchPreference = (SwitchPreference)findPreference("cycletime");
        cycleTimeListPreference = (ListPreference)findPreference("cycletimelist");
        //ipAddressEditPreference = (EditTextPreference)findPreference("ipaddress");



        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(prefListener);

        cycleTimeListPreference.setValue(cycleTimeListPreference.getValue());
        cycleTimeListPreference.setSummary(cycleTimeListPreference.getEntry().toString());
        if(prefs.getBoolean("cycletime",true)){
            cycleTimeSwitchPreference.setSummary("사용");
        }
        //Log.d("ggggg",ipAddressEditPreference.getText());
        //ipAddressEditPreference.setSummary(ipAddressEditPreference.getText());
        //ip 설정
       // CycleJsonActivity.setIpAddress(ipAddressEditPreference.getText());

        //prefs.registerOnSharedPreferenceChangeListener(prefListener);
    }

    SharedPreferences.OnSharedPreferenceChangeListener prefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

            preferenceManager = getPreferenceManager();

            if(preferenceManager.getSharedPreferences().getBoolean("cycletime", true)){
                //알림 기능 켜기
                cycleTimeSwitchPreference.setSummary("사용");
            }
            else{
                //알림 기능 끄기
                cycleTimeSwitchPreference.setSummary("사용 안함");
            }

//            ipAddressEditPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
//                @Override
//                public boolean onPreferenceChange(Preference preference, Object newValue) {
//                    ipAddressEditPreference.setText(newValue.toString());
//                    ipAddressEditPreference.setSummary(newValue.toString());
//                    CycleJsonActivity.setIpAddress(ipAddressEditPreference.getText());
//                    Log.d("ggggg",ipAddressEditPreference.getText());
//                    return false;
//                }
//            });

            cycleTimeListPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    cycleTimeListPreference.setValue(newValue.toString());
                    cycleTimeListPreference.setSummary(cycleTimeListPreference.getEntry().toString());
                    if (newValue.equals("10"))
                        CycleTimer.setPeriod(10);
                    if (newValue.equals("30"))
                        CycleTimer.setPeriod(30);
                    if (newValue.equals("60"))
                        CycleTimer.setPeriod(60);
                    if (newValue.equals("180"))
                        CycleTimer.setPeriod(180);

                    return true;
                }
            });
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        prefs=PreferenceManager.getDefaultSharedPreferences(getActivity());
        prefs.registerOnSharedPreferenceChangeListener(prefListener);
    }

    public void onResume(){
        super.onResume();
        prefs.registerOnSharedPreferenceChangeListener(prefListener);


    }

    public void onPause(){
        super.onPause();
        prefs.registerOnSharedPreferenceChangeListener(prefListener);

    }
}
