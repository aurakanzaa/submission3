package com.example.aura.submission3_kamus.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aura.submission3_kamus.DetailActivity;
import com.example.aura.submission3_kamus.R;
import com.example.aura.submission3_kamus.helper.Config;
import com.example.aura.submission3_kamus.model.KamusModel;

import java.util.ArrayList;

public class KamusAdapter extends RecyclerView.Adapter<KamusAdapter.MyViewHolder> implements Filterable {

    private ArrayList<KamusModel> kamusModels;
    private ArrayList<KamusModel> searchResult;
    private Context context;

    public KamusAdapter(ArrayList<KamusModel> kamusModels, Context context) {
        this.kamusModels = kamusModels;
        this.context = context;
    }

    public void replaceAll(ArrayList<KamusModel> items) {
        kamusModels = items;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView kata_dasar;
        TextView arti;
        LinearLayout detail;

        MyViewHolder(View itemView) {
            super(itemView);
            detail = itemView.findViewById(R.id.detail);
            kata_dasar = itemView.findViewById(R.id.kata_dasar);
            arti = itemView.findViewById(R.id.arti);


        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.kata_dasar.setText(kamusModels.get(position).getWord());
        holder.arti.setText(kamusModels.get(position).getTranslate());
        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(Config.BUNDLE_WORD, holder.kata_dasar.getText().toString().trim());
                intent.putExtra(Config.BUNDLE_TRANSLATE, holder.arti.getText().toString().trim());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return kamusModels.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<KamusModel> resultsItems = new ArrayList<>();

                if (searchResult == null)
                    searchResult = kamusModels;
                if (constraint != null) {
                    if (kamusModels != null & searchResult.size() > 0) {
                        for (final KamusModel g : searchResult) {
                            if (g.getWord().toLowerCase().contains(constraint.toString()))
                                resultsItems.add(g);
                        }
                    }
                    oReturn.values = resultsItems;
                }

                return oReturn;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                kamusModels = (ArrayList<KamusModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }



}
