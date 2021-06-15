package com.example.dude.projectworkz1;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SlideScreen extends AppCompatActivity {
    private ViewPager mSLideViewPager;
    private LinearLayout mDotLayout;
    private Slideshow sliderAdapter;
    private TextView[] mDots;
    private Button mNextBtn;
    private Button mBackBtn;
    private int mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_screen);
        mSLideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.dots);

        mNextBtn = (Button) findViewById(R.id.nextBtn);
        mBackBtn = (Button) findViewById(R.id.prevBtn);

        sliderAdapter = new Slideshow(this);
        mSLideViewPager.setAdapter(sliderAdapter);
        addDotsIndicator(0);
        mSLideViewPager.addOnPageChangeListener(viewListener);

        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSLideViewPager.setCurrentItem(mCurrentPage + 1);
            }
        });

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSLideViewPager.setCurrentItem(mCurrentPage - 1);
            }
        });
    }

    public void addDotsIndicator(int position) {
        mDots = new TextView[4];
        for (int i = 0; i < mDots.length; i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226"));
            mDotLayout.addView(mDots[i]);
        }
        if (mDots.length > 0) {
            mDots[position].setTextColor(getResources().getColor(R.color.colorWhite));
        }

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        boolean last_page_changed = false;

        @Override
        public void onPageScrolled(int position, float v, int i1) {
            int last_index = sliderAdapter.getCount() - 1;
            if (last_page_changed && position == last_index) {
            }
        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);
            mCurrentPage = i;
            if (i == 0) {
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(false);
                mBackBtn.setVisibility(View.INVISIBLE);
                mNextBtn.setText("Next");
                mBackBtn.setText("");

            } else if (i == mDots.length - 1) {
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(true);
                mBackBtn.setVisibility(View.VISIBLE);
                mNextBtn.setText("Finish");
                mBackBtn.setText("Back");

            } else {
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(true);
                mBackBtn.setVisibility(View.VISIBLE);
                mNextBtn.setText("Next");
                mBackBtn.setText("Back");
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {
            int last_index = sliderAdapter.getCount() - 1;
            int curr_item = mSLideViewPager.getCurrentItem();
            if (curr_item == last_index && i == 1) {
                last_page_changed = true;
                finish();
            } else {
                last_page_changed = false;
            }
        }
    };
}

class Slideshow extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public Slideshow(Context context) {
        this.context = context;
    }

    public int[] slide_image = {
            R.drawable.slide4,
            R.drawable.sc,
            R.drawable.scr,
            R.drawable.spi
    };
    public String[] slide_headings = {
            "Welcome to Break Bill!", "Add expenses", "Settle up", "Lets get started"
    };
    public String[] slide_desc = {
            "Break Bill keeps track of balances between",
            " you can spilt expenses with groups or with",
            "Pay your friends back any time", "Slide to begin"
    };
    public String[] slide_desc1 = {"friends.", "individuals.", " ", " "};

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.slide_image);
        TextView slideHeading = (TextView) view.findViewById(R.id.slide_headings);
        TextView slideDescription = (TextView) view.findViewById(R.id.slide_desc);
        TextView slideDescription1 = (TextView) view.findViewById(R.id.slide_desc1);
        slideImageView.setImageResource(slide_image[position]);
        slideHeading.setTextColor(Color.parseColor("#0000FF"));
        slideHeading.setText(slide_headings[position]);
        slideDescription.setTextColor(Color.parseColor("#000000"));
        slideDescription.setText(slide_desc[position]);
        slideDescription1.setTextColor(Color.parseColor("#000000"));
        slideDescription1.setText(slide_desc1[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}