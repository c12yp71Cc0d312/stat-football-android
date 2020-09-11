package com.example.spiderproject.teaminfo;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spiderproject.R;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class TeamInfoActivity extends AppCompatActivity implements TeamInfoSquadTabFragment.TeamInfoSquadFragmentListener {

    private SectionsPageAdapter sectionsPageAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private static String teamId;

    private ImageView teamLogo;
    private TextView team_Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_info);

        teamLogo = findViewById(R.id.imageView_teeaminfo_logo);
        team_Name = findViewById(R.id.textView_teaminfo_teamname);

        sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        viewPager = findViewById(R.id.view_pager_team_info);
        setUpViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs_team_info);
        tabLayout.setupWithViewPager(viewPager);

        teamId = getIntent().getStringExtra("teamId");

    }

    public void setUpViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(new TeamInfoSquadTabFragment(), "SQUAD");
        adapter.addFragment(new TeamInfoFixturesTabFragment(), "FIXTURES");
        viewPager.setAdapter(adapter);
    }

    public class SectionsPageAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        public SectionsPageAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

    public String getTeamId() {
        return teamId;
    }

    @Override
    public void onInputLogoAndTeamNameSent(String logo, String teamName) {
        Picasso.get()
                .load(logo)
                .placeholder(R.drawable.placeholder)
                .into(teamLogo);

        team_Name.setText(teamName);
    }
}