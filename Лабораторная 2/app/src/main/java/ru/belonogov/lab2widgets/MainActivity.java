package ru.belonogov.lab2widgets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            SectionPagerAdapter pagerAdapter = new SectionPagerAdapter(getSupportFragmentManager(), new Lifecycle() {
                @Override
                public void addObserver(@NonNull LifecycleObserver observer) {

                }

                @Override
                public void removeObserver(@NonNull LifecycleObserver observer) {

                }

                @NonNull
                @Override
                public State getCurrentState() {
                    return null;
                }
            });

            ViewPager2 pager = findViewById(R.id.pager);
            pager.setAdapter(pagerAdapter);

            TabLayout tabLayout = findViewById(R.id.tMain);
            TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, pager,
                    ((tab, position) -> tab.setText(String.valueOf((position + 228)))));
            tabLayoutMediator.attach();
        }

    private static class SectionPagerAdapter extends FragmentStateAdapter {
        public SectionPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @Override
        public int getItemCount() {
            return 3;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new tFirst();
                case 1:
                    return new tSecond();
                case 2:
                    return new tThird();
            }
            return new Fragment();
        }
    }
}