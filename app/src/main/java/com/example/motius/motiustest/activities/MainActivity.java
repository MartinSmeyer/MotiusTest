package com.example.motius.motiustest.activities;

/**
 * Created by Martin on 20.04.2016.
 */

        import android.os.Bundle;
        import android.support.design.widget.TabLayout;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentManager;
        import android.support.v4.app.FragmentPagerAdapter;
        import android.support.v4.view.ViewPager;
        import android.support.v7.app.AppCompatActivity;
        import android.view.WindowManager;

        import java.util.ArrayList;
        import java.util.List;

        import com.example.motius.motiustest.R;
        import com.example.motius.motiustest.fragments.FirstFragment;
        import com.example.motius.motiustest.fragments.ThirdFragment;
        import com.example.motius.motiustest.fragments.SecondFragment;


public class MainActivity extends AppCompatActivity {

    //tabs
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private int[] tabIcons = {
            R.drawable.ic_tab_people,
            R.drawable.ic_tab_person,
            R.drawable.ic_tab_star
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FirstFragment(), "ONE");
        adapter.addFragment(new SecondFragment(), "TWO");
        adapter.addFragment(new ThirdFragment(), "THREE");
        viewPager.setAdapter(adapter);
    }

    // I prefer fragments over activities in this case
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // return null to display only the icon
            return null;
        }

    }

}