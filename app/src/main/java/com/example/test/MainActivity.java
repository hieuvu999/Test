package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;

public class MainActivity extends AppCompatActivity {
    private TextView tvResult;
    private ScrollView scrollView;
    private LinearLayout layout;

    private ImageButton btnUp;
    private View viewBtn;

    private Handler handler = new Handler();



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.tvResult);
        layout = findViewById(R.id.layout);
        scrollView = findViewById(R.id.scrollView);
        viewBtn = findViewById(R.id.viewBtn);
        btnUp = findViewById(R.id.btnUp);


        scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                scrollView.fullScroll(View.FOCUS_DOWN);

                scrollView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                    private int previousScrollY = 0;
                    @Override
                    public void onScrollChanged() {
                        isScrolling = true;
                        int scrollY = scrollView.getScrollY();
                        int scrollViewHeight = scrollView.getHeight();
                        if (scrollY >= scrollView.getChildAt(0).getHeight() - scrollViewHeight) {
                            viewBtn.setVisibility(View.GONE);
                        }else {
                            viewBtn.setVisibility(View.VISIBLE);
                        }
                        if (isView && scrollY != previousScrollY) {
                            tvResult.setVisibility(View.VISIBLE);
                            previousScrollY = scrollY;
                        } else {
                            if (tvResult.getVisibility() != View.GONE) {
                                handler.postDelayed(() -> tvResult.setVisibility(View.GONE), 1000);
                            }
                        }
                        if (scrollY >= scrollView.getChildAt(0).getHeight() - scrollViewHeight) {
                            isView = true;
                        }



                    }
                });

            }
        });

        scrollView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            private int previousScrollY = 0;

            @Override
            public boolean onPreDraw() {
                int currentScrollY = scrollView.getScrollY();
                int currentScrollX = scrollView.getScrollX();
                if (currentScrollY != previousScrollY) {
                    int childCount = layout.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View childView = layout.getChildAt(i);
                        int viewTop = childView.getTop();
                        int viewBottom = childView.getBottom();
                        int scrollY = scrollView.getScrollY();


                        if (currentScrollY >= viewTop) {
                            switch (i) {
                                case 0: {
                                    tvResult.setText("Nhóm1");
                                    break;
                                }
                                case 3: {
                                    tvResult.setText("Nhóm2");
                                    break;
                                }
                                case 7: {
                                    tvResult.setText("Nhóm3");
                                    break;
                                }
                                default:{
                                    break;
                                }
                            }


                        }
                    }

                    previousScrollY = currentScrollY;
                }

                return true;
            }
        });

        scrollView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                // Khi cuộn kết thúc (trước khi vẽ lại)
                if (isScrolling) {
                    isScrolling = false;
                    if (tvResult.getVisibility() != View.GONE) {
                        handler.postDelayed(() -> tvResult.setVisibility(View.GONE), 1000);
                    }
                }
                return true;
            }
        });

        btnUp.setOnClickListener(v -> scrollView.fullScroll(ScrollView.FOCUS_DOWN));


    }
    private Boolean isView = false;

    private boolean isScrolling = false;

}