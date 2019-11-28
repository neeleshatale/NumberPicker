package com.neelesh.numberpicker.utility;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.neelesh.numberpicker.R;
import com.neelesh.numberpicker.interfaces.OnSingleClickListener;
/**
 * Created by Neelesh Atale on 28/11/2019.
 */

public class CustomNumberPicker extends LinearLayout {
    private View tvMinus;
    private View tvPlus;
    private TextView tvCount;
    private int minLimit = 0;
    private int maxLimit = 9;
    private Context context;
    private OnNumberChangedListener onNumberChangedListener;

    public CustomNumberPicker(final Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomNumberPicker, 0, 0);
        minLimit = typedArray.getInt(R.styleable.CustomNumberPicker_minLimit, 0);
        maxLimit = typedArray.getInt(R.styleable.CustomNumberPicker_maxLimit, 9);
        int textSize = typedArray.getInt(R.styleable.CustomNumberPicker_textSizeInSp, 16);
        int symbolSize = typedArray.getInt(R.styleable.CustomNumberPicker_symbolSizeInSp, 20);
        typedArray.recycle();
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        initViews(textSize, symbolSize);
        tvCount.setText("" + minLimit);
        tvMinus.setOnClickListener(new OnSingleClickListener(300) {
            @Override
            public void onSingleClick(View v) {
                int currentCount = Integer.parseInt(tvCount.getText().toString());
                if (currentCount > minLimit) {
                    currentCount = currentCount - 1;

                    Animation downFade = AnimationUtils.loadAnimation(context, R.anim.down_out_fade);
                    final Animation downDarken = AnimationUtils.loadAnimation(context, R.anim.down_in_darken);

                    final int finalCurrentCount = currentCount;
                    downFade.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            tvCount.setText("" + finalCurrentCount);
                            tvCount.startAnimation(downDarken);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    tvCount.startAnimation(downFade);
                    if (onNumberChangedListener != null) {
                        onNumberChangedListener.onDecrement(currentCount);
                        onNumberChangedListener.onNumberChanged(currentCount);
                    }
                }
            }
        });

        tvPlus.setOnClickListener(new OnSingleClickListener(300) {
            @Override
            public void onSingleClick(View v) {
                int currentCount = Integer.parseInt(tvCount.getText().toString());
                if (currentCount < maxLimit) {
                    currentCount = currentCount + 1;
                    Animation upFade = AnimationUtils.loadAnimation(context, R.anim.up_out_fade);
                    final Animation upDarken = AnimationUtils.loadAnimation(context, R.anim.up_in_darken);
                    final int finalCurrentCount = currentCount;
                    upFade.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            //nothing to do
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            tvCount.setText("" + finalCurrentCount);
                            tvCount.startAnimation(upDarken);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                            //nothing to do
                        }
                    });
                    tvCount.startAnimation(upFade);
                    if (onNumberChangedListener != null) {
                        onNumberChangedListener.onIncrement(currentCount);
                        onNumberChangedListener.onNumberChanged(currentCount);
                    }
                }
            }
        });

    }

    public CustomNumberPicker(Context context) {
        this(context, null);
        this.context = context;
    }

    public int getCount() {
        return Integer.parseInt(tvCount.getText().toString());
    }

    private void initViews(int textSize, int symbolSize) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.view_custom_number_picker, this, true);
        tvCount = rootView.findViewById(R.id.tv_count);
        tvMinus = rootView.findViewById(R.id.minus);
        tvPlus = rootView.findViewById(R.id.plus);
        tvCount.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
    }

    public void setOnNumberChangedListener(OnNumberChangedListener onNumberChangedListener) {
        this.onNumberChangedListener = onNumberChangedListener;
    }

    public void setNumber(int number) {
        tvCount.setText("" + number);
    }


    public interface OnNumberChangedListener {
        void onIncrement(int number);

        void onNumberChanged(int number);

        void onDecrement(int number);
    }

}
