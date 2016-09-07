package com.suvariyaraj.algorithms;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class MyIntro extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Add your slide's fragments here.
        // AppIntro will automatically generate the dots indicator and buttons.
   //    addSlide(first_fragment);
  //      addSlide(second_fragment);
 //       addSlide(third_fragment);
//        addSlide(fourth_fragment);

        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. AppIntro will do the rest.
        addSlide(AppIntroFragment.newInstance("Welcome coders!!", "Learn all the codes at just one spot.", R.drawable.copy_icon, Color.parseColor("#3F51B5")));
        addSlide(AppIntroFragment.newInstance("", "Codes available in java.", R.drawable.copy_icon, Color.parseColor("#3F51B5")));
        addSlide(AppIntroFragment.newInstance("", "Also available in C", R.drawable.copy_icon, Color.parseColor("#3F51B5")));

//        addSlide (AppIntroFragment.newInstance(R.layout.intro1));
        // OPTIONAL METHODS
        // Override bar/separator color.
        setBarColor(Color.parseColor("#3F51B5"));
        setSeparatorColor(Color.parseColor("#2196F3"));
//
//        // Hide Skip/Done button.
        //showSkipButton(true);//shows skip
        setProgressButtonEnabled(true);//Shows skip,done and '->' arrow

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permisssion in Manifest.
        setVibrate(true);//on touch of skip or done vibration is felt
        setVibrateIntensity(30);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);

        // Do something when users tap on Skip button.
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        loadMainActivity();
        // Do something when users tap on Done button.
    }
    private void loadMainActivity(){
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }
    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
         super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }

    @Override
    protected void onPause() {
        super.onPause ();
        finish ();
    }
}