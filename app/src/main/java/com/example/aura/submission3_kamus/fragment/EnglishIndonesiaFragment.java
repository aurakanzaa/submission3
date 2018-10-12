package com.example.aura.submission3_kamus.fragment;

import android.content.Context;
import android.database.SQLException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aura.submission3_kamus.R;
import com.example.aura.submission3_kamus.adapter.KamusAdapter;
import com.example.aura.submission3_kamus.database.KamusDataHelper;
import com.example.aura.submission3_kamus.model.KamusModel;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;


public class EnglishIndonesiaFragment extends Fragment {
    private MaterialSearchBar search;
    private RecyclerView recyler;

    private KamusDataHelper kamusDataHelper;
    private KamusAdapter kamusDataAdapter;

    private ArrayList<KamusModel> kamusDataModels;

    private boolean English = true;

    public EnglishIndonesiaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_english_indonesia, container, false);

        search = view.findViewById(R.id.search);
        recyler =  view.findViewById(R.id.rv);

        kamusDataModels = new ArrayList<>();
        kamusDataHelper = new KamusDataHelper(getActivity());
        kamusDataAdapter = new KamusAdapter(kamusDataModels, getActivity());
        recyler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyler.setAdapter(kamusDataAdapter);

        search.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                kamusDataAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        loadData("");

        return view;
    }


    private void loadData(String search) {
        try {
            kamusDataHelper.open();
            kamusDataModels = kamusDataHelper.getDataALl(English);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            kamusDataHelper.close();
        }
        kamusDataAdapter.replaceAll(kamusDataModels);
    }
}
