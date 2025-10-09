package ar.edu.ciclovidafragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentB extends Fragment {


    private static final String TAG = "FragmentB";

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "OnAtach()");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "crete view()");
        return inflater.inflate(R.layout.fragment_b, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG, "onViewCreated()");

        Button btnBack = view.findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v->{
            requireActivity().getSupportFragmentManager().popBackStack();
        });
    }

    @Override public void onStart()  { super.onStart();  Log.d(TAG, "onStart"); }
    @Override public void onResume() { super.onResume(); Log.d(TAG, "onResume"); }
    @Override public void onPause()  { Log.d(TAG, "onPause"); super.onPause(); }
    @Override public void onStop()   { Log.d(TAG, "onStop"); super.onStop(); }
    @Override public void onDestroyView() { Log.d(TAG, "onDestroyView"); super.onDestroyView(); }
    @Override public void onDestroy() { Log.d(TAG, "onDestroy"); super.onDestroy(); }
    @Override public void onDetach() { Log.d(TAG, "onDetach"); super.onDetach(); }
}
