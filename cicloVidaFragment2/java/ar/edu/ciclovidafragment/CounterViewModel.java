package ar.edu.ciclovidafragment;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CounterViewModel extends ViewModel {

    private final MutableLiveData<Integer> count = new MutableLiveData<>();

    public MutableLiveData<Integer> getCount() {

        return count;
    }

    public void incrementar(){

        Integer current = count.getValue();
        count.setValue((current == null ? 0 : current) + 1);
    }

}
