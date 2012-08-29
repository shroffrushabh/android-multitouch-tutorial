package it.jfrankie;

import android.app.Activity;
import android.os.Bundle;
import it.survivingwithandroid.R;

/**
 * This cod shows how to implement a custom View
 * that supports multitouch 
 * 
 * Refer to: http://survivingwithandroid.blogspot.com
 * 
 * */

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}