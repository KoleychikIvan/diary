package ru.koleychik.diary.ui.all;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.koleychik.diary.R;
import ru.koleychik.diary.ui.NumbersAdapter;

public class allFragment extends Fragment {

    private allViewModel homeViewModel;
    RecyclerView RV;
    Context context;
    NumbersAdapter numbersAdapter = new NumbersAdapter();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(allViewModel.class);
        View root = inflater.inflate(R.layout.fragment_all, container, false);

        RV = root.findViewById(R.id.rv_all);

        context = getActivity();
        numbersAdapter.getContext(context);

        LinearLayoutManager linearManager = new LinearLayoutManager(context);

        RV.setLayoutManager(linearManager);

        RV.setAdapter(numbersAdapter);

        registerForContextMenu(RV);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        numbersAdapter.notifyDataSetChanged();
    }
}
