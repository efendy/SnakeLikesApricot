package com.appikot.games.snakelikesapricot.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appikot.games.snakelikesapricot.R;

public class AchievementView extends LinearLayout
{
    private static final String TAG = "AchievementView";

    protected LinearLayout layoutBorder;
    protected LinearLayout layoutContent;
    protected TextView tvMessage;
    protected ImageView scView;
    protected TextView tvQuestion;
    protected LinearLayout actionLayout;
    protected Button btnConfirm;
    protected Button btnCancel;
    protected Context thisContext;

    public AchievementView(Context context)
    {
        super(context);
        thisContext = context;

        layoutBorder = new LinearLayout(this.getContext());
        layoutContent = new LinearLayout(this.getContext());
        tvMessage = new TextView(this.getContext());
        scView = new ImageView(this.getContext());
        tvQuestion = new TextView(this.getContext());
        actionLayout = new LinearLayout(this.getContext());
        btnConfirm = new Button(this.getContext());
        btnCancel = new Button(this.getContext());

        this.setPadding(32,32,32,32);

        layoutBorder.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        layoutBorder.setBackgroundResource(R.drawable.rounded_background_border);
        this.addView(layoutBorder);

        layoutContent.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        layoutContent.setBackgroundResource(R.drawable.rounded_background_content);
        layoutContent.setOrientation(LinearLayout.VERTICAL);
        layoutBorder.addView(layoutContent);

        tvMessage.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        tvMessage.setGravity(Gravity.CENTER);
        tvMessage.setText("Wow! Your score is pretty high-");
        tvMessage.setTextColor(Color.WHITE);
        tvMessage.setTextSize(14);
        tvMessage.setLineSpacing(12,1);
        layoutContent.addView(tvMessage);

        scView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        layoutContent.addView(scView);

        tvQuestion.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        tvQuestion.setGravity(Gravity.CENTER);
        tvQuestion.setText("Save to your Gallery?");
        tvQuestion.setTextColor(Color.YELLOW);
        tvQuestion.setTextSize(12);
        tvQuestion.setLineSpacing(12,1);
        layoutContent.addView(tvQuestion);

        actionLayout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        actionLayout.setOrientation(LinearLayout.HORIZONTAL);
        actionLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        actionLayout.setDividerPadding(16);
        layoutContent.addView(actionLayout);

        btnCancel.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        btnCancel.setText("No");
//        btnCancel.setBackgroundResource(R.drawable.button_no);
        btnCancel.setTextColor(Color.parseColor("#FF8A80"));
        btnCancel.setOnClickListener(onClickListener);
        actionLayout.addView(btnCancel);

        ViewGroup.MarginLayoutParams btnConfirmParams = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        btnConfirmParams.setMargins(8,8,8,8);
        btnConfirm.setLayoutParams(btnConfirmParams);
        btnConfirm.setText("Yes");
//        btnConfirm.setBackgroundResource(R.drawable.button_yes);
        btnConfirm.setTextColor(Color.parseColor("#69F0AE"));
        btnConfirm.setOnClickListener(onClickListener);
        actionLayout.addView(btnConfirm);

    }

    public void setImage(Bitmap bitmap)
    {
        scView.setImageBitmap(bitmap);
    }

    OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.equals(btnCancel)) {

            }
            if (view.equals(btnConfirm)) {

            }
        }
    };
}
