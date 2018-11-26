package com.mk.mkfighterresultdb;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class ResultRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static int EMPTY_VIEW = -1;

    private List<Result> values;

    ResultRecyclerAdapter(List<Result> values){
        this.values = values;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        RecyclerView.ViewHolder vh;
        if (viewType == EMPTY_VIEW){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.result_empty_item, viewGroup, false);
            vh = new EmptyViewHolder(view);
        }
        else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.result_item, viewGroup, false);
            vh = new ViewHolder(view);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ViewHolder) {
            ViewHolder vh = (ViewHolder) viewHolder;
            Result currentResult = values.get(i);
            (vh).firstFighterMatchWinner.setText(String.valueOf(currentResult.getFirstFighterMatchWinner()));
            (vh).secondFighterMatchWinner.setText(String.valueOf(currentResult.getSecondFighterMatchWinner()));
            (vh).firstRoundWinner.setText(String.valueOf(currentResult.getFirstRoundWinner()));
            (vh).secondRoundWinner.setText(String.valueOf(currentResult.getSecondRoundWinner()));
            (vh).fatality.setText(String.valueOf(currentResult.getFatality()));
            (vh).brutality.setText(String.valueOf(currentResult.getBrutality()));
            (vh).withoutSpecialFinish.setText(String.valueOf(currentResult.getWithoutSpecialFinish()));
            (vh).score.setText(String.valueOf(currentResult.getScore()));
            (vh).matchCourse.setText(String.valueOf(currentResult.getMatchCourse()));
            (vh).recordDate.setText(String.valueOf(currentResult.getRecordDate()));
        }
    }

    @Override
    public int getItemCount() {
        return values.size() > 0 ? values.size() : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (values.size() <= 0) {
            return EMPTY_VIEW;
        }
        else {
            return super.getItemViewType(position);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

        private EditText firstFighterMatchWinner, secondFighterMatchWinner, firstRoundWinner, secondRoundWinner,
                fatality, brutality, withoutSpecialFinish, score, matchCourse;
        private TextView recordDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            firstFighterMatchWinner = itemView.findViewById(R.id.winMatchFirstFighterResultEd);
            secondFighterMatchWinner = itemView.findViewById(R.id.winMatchSecondFighterResultEd);
            firstRoundWinner = itemView.findViewById(R.id.winFirstRoundResultEd);
            secondRoundWinner = itemView.findViewById(R.id.winSecondRoundChgEd);
            fatality = itemView.findViewById(R.id.fatalityResultEd);
            brutality = itemView.findViewById(R.id.brutalityResultEd);
            withoutSpecialFinish = itemView.findViewById(R.id.withoutSpecFinResultEd);
            score = itemView.findViewById(R.id.scoreResultEd);
            matchCourse = itemView.findViewById(R.id.matchCourseResultEd);
            recordDate = itemView.findViewById(R.id.dateTv);

            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(this.getAdapterPosition(), 1, 1, R.string.upd_menu_item);
            menu.add(this.getAdapterPosition(), 2, 2, R.string.del_menu_item);
        }
    }

    public class EmptyViewHolder extends RecyclerView.ViewHolder {
        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public long getItemId(int position) {
        return values.get(position).getId();
    }
}
