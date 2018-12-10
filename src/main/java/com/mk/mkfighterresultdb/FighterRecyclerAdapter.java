package com.mk.mkfighterresultdb;

import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import java.util.ArrayList;
import java.util.List;


public class FighterRecyclerAdapter extends RecyclerView.Adapter<FighterRecyclerAdapter.ViewHolder> implements FastScrollRecyclerView.SectionedAdapter, Filterable {

    Fighter[] values;
    Fighter[] filteredValues;
    private TypedArray fighterPhoto;
    private RecyclerViewClickListener listener;
    long id;

    FighterRecyclerAdapter(Fighter[] values, TypedArray fighterPhoto, long opponentId, RecyclerViewClickListener listener) {
        this.values = values;
        this.filteredValues = values;
        this.listener = listener;
        this.fighterPhoto = fighterPhoto;
        this.id = opponentId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fighter_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        //Fighter currentItem = values[i];
        Fighter currentItem = filteredValues[i];
        viewHolder.nameTv.setText(currentItem.getName());
        viewHolder.descTv.setText(currentItem.getDescription());
        viewHolder.imageView.setBackground(fighterPhoto.getDrawable((int) currentItem.getId() - 1));
    }

    @Override
    public int getItemCount() {
        //return values.length;
        return filteredValues.length;
    }

    @NonNull
    @Override
    public String getSectionName(int position) {
        return values[position].getName().substring(0, 1);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    filteredValues = values;
                } else {
                    List<Fighter> filteredList = new ArrayList<>();
                    for (Fighter value : values) {
                        if (value.getName().toLowerCase().startsWith(charString.toLowerCase())) {
                            filteredList.add(value);
                        }
                    }
                    filteredValues = filteredList.toArray(new Fighter[filteredList.size()]);
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredValues;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredValues = (Fighter[]) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView nameTv, descTv;
        private ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.nameTv);
            descTv = itemView.findViewById(R.id.descTv);
            imageView = itemView.findViewById(R.id.fighter_photo);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(getAdapterPosition());
        }
    }
}
