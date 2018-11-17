package com.mk.mkfighterresultdb;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.List;

public class ResultRecyclerAdapter extends RecyclerView.Adapter<ResultRecyclerAdapter.ViewHolder>{

    private List<Result> values;

    ResultRecyclerAdapter(List<Result> values){
        this.values = values;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.result_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Result currentResult = values.get(i);
        viewHolder.firstFighterMatchWinner.setText(String.valueOf(currentResult.getFirstFighterMatchWinner()));
        viewHolder.secondFighterMatchWinner.setText(String.valueOf(currentResult.getSecondFighterMatchWinner()));
        viewHolder.firstRoundWinner.setText(String.valueOf(currentResult.getFirstRoundWinner()));
        viewHolder.secondRoundWinner.setText(String.valueOf(currentResult.getSecondRoundWinner()));
        viewHolder.fatality.setText(String.valueOf(currentResult.getFatality()));
        viewHolder.brutality.setText(String.valueOf(currentResult.getBrutality()));
        viewHolder.withoutSpecialFinish.setText(String.valueOf(currentResult.getWithoutSpecialFinish()));
        viewHolder.score.setText(String.valueOf(currentResult.getScore()));
        viewHolder.matchCourse.setText(String.valueOf(currentResult.getMatchCourse()));
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private EditText firstFighterMatchWinner, secondFighterMatchWinner, firstRoundWinner, secondRoundWinner,
                fatality, brutality, withoutSpecialFinish, score, matchCourse;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            firstFighterMatchWinner = itemView.findViewById(R.id.winMatchFirstFighterResultEd);
            secondFighterMatchWinner = itemView.findViewById(R.id.winMatchSecondFighterResultEd);
            firstRoundWinner = itemView.findViewById(R.id.winFirstRoundResultEd);
            secondRoundWinner = itemView.findViewById(R.id.winSecondRoundResultEd);
            fatality = itemView.findViewById(R.id.fatalityResultEd);
            brutality = itemView.findViewById(R.id.brutalityResultEd);
            withoutSpecialFinish = itemView.findViewById(R.id.withoutSpecFinResultEd);
            score = itemView.findViewById(R.id.scoreResultEd);
            matchCourse = itemView.findViewById(R.id.matchCourseResultEd);
        }
    }
}
