/*
 * Copyright (C) 2023 The Evolution X Project
 *               2018 Havoc-OS
 * SPDX-License-Identifier: Apache-2.0
 */

package org.evolution.oplus.OPlusExtras.kcal;

import android.app.Fragment;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceFragment;
import androidx.preference.PreferenceManager;
import androidx.viewpager.widget.ViewPager;

import com.android.settingslib.collapsingtoolbar.CollapsingToolbarBaseActivity;

public class KcalActivity extends CollapsingToolbarBaseActivity {

    private static final String TAG = "Kcal";

    private ViewPager viewPager;
    private LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;

    private Kcal mKcalFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.android.settingslib.collapsingtoolbar.R.layout.kcal);

        setupImageSlider();

        Fragment fragment = getFragmentManager().findFragmentById(com.android.settingslib.collapsingtoolbar.R.id.kcal);
        if (fragment == null) {
            mKcalFragment = new Kcal();
            getFragmentManager().beginTransaction()
                .add(com.android.settingslib.collapsingtoolbar.R.id.kcal, mKcalFragment)
                .commit();
        } else {
            mKcalFragment = (Kcal) fragment;
        }
    }

    private void setupImageSlider() {
        viewPager = (ViewPager) findViewById(com.android.settingslib.collapsingtoolbar.R.id.preview);
        sliderDotspanel = (LinearLayout) findViewById(com.android.settingslib.collapsingtoolbar.R.id.SliderDots);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getApplicationContext());
        viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for(int i = 0; i < dotscount; i++) {
            dots[i] = new ImageView(getApplicationContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), com.android.settingslib.collapsingtoolbar.R.drawable.inactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            sliderDotspanel.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), com.android.settingslib.collapsingtoolbar.R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), com.android.settingslib.collapsingtoolbar.R.drawable.inactive_dot));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), com.android.settingslib.collapsingtoolbar.R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
}
