package ar.edu.ciclovidafragment;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null ) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new FragmentA(), "A")
                    .commit();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Activity", "ResumeActiviy");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Activity", "Stop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Activity", "OnPauseActiviy");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Activity", "OnDestroyActiviy");
    }
}