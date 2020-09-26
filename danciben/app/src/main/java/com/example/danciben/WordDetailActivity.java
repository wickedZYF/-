package com.example.danciben;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class WordDetailActivity extends AppCompatActivity
        implements WordDetailFragment.OnFragmentInteractionListener{
    @Override
        protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }
        if (savedInstanceState == null) {
            WordDetailFragment detailFragment = new WordDetailFragment();
            detailFragment.setArguments(getIntent().getExtras());
            getFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, detailFragment)
                    .commit();
        }
}
        @Override
        public void onWordDetailClick(Uri uri) {  }
}

