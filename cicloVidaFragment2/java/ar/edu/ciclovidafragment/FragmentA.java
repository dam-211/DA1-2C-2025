package ar.edu.ciclovidafragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class FragmentA extends Fragment {

    private static final String TAG = "FragmentA";

    private TextView tvCounter;
    private Button btnIncrement;

    private CounterViewModel viewModel;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG,"Estoy en el onAttach()");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"Estoy en el onCreate()");

        viewModel = new ViewModelProvider(this)
                .get(CounterViewModel.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_a, container, false);
        Log.d(TAG,"onCreateView()");
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG,"Estoy en el onViewCreate()");

       tvCounter = view.findViewById(R.id.tvCounter);
       btnIncrement  = view.findViewById(R.id.btnIncrement);
       Button btnGoToB = view.findViewById(R.id.btnGoToB);

        viewModel.getCount().observe(getViewLifecycleOwner(), value ->
                tvCounter.setText("Contador: " + value)
        );

        btnIncrement.setOnClickListener(v -> viewModel.incrementar());

        btnGoToB.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new FragmentB(), "B")
                    .addToBackStack("B") // A queda en back stack → destruye su View, no el objeto
                    .commit();
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG,"Estoy en el onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG,"Estoy en el onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG,"Estoy en el onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG,"Estoy en el onStop()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"Estoy en el ondestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG,"Estoy en el onDetach()");
    }
}
