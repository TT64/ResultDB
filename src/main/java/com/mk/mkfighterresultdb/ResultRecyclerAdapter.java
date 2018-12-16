package com.mk.mkfighterresultdb;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ResultRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static int EMPTY_VIEW = -1;

    private List<Result> values;
    private List<Result> itemsPendingRemoval;

    private RecyclerViewButtonClick recyclerViewButtonClick;

    ResultRecyclerAdapter(List<Result> values, RecyclerViewButtonClick recyclerViewButtonClick) {
        itemsPendingRemoval = new ArrayList<>();
        this.values = values;
        this.recyclerViewButtonClick = recyclerViewButtonClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        RecyclerView.ViewHolder vh;
        if (viewType == EMPTY_VIEW) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.result_empty_item, viewGroup, false);
            vh = new EmptyViewHolder(view);
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.result_item, viewGroup, false);
            vh = new ViewHolder(view);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ViewHolder) {
            ViewHolder vh = (ViewHolder) viewHolder;
            final Result currentResult = values.get(i);
            if (!itemsPendingRemoval.contains(currentResult)) {
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
                (vh).viewForeground.setVisibility(View.VISIBLE);
            } else {
                (vh).viewForeground.setVisibility(View.GONE);
                (vh).viewBackground.setVisibility(View.VISIBLE);
            }
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
        } else {
            return super.getItemViewType(position);
        }
    }

    public void pendingRemoval(int position) {
        final Result item = values.get(position);
        if (!itemsPendingRemoval.contains(item)) {
            itemsPendingRemoval.add(item);
            notifyItemChanged(position);
        }
    }

    public void pendingCancelRemoval(int position) {
        final Result item = values.get(position);
        if (itemsPendingRemoval.contains(item)) {
            itemsPendingRemoval.remove(item);
            notifyItemChanged(values.indexOf(item));
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, View.OnClickListener {

        private EditText firstFighterMatchWinner, secondFighterMatchWinner, firstRoundWinner, secondRoundWinner,
                fatality, brutality, withoutSpecialFinish, score, matchCourse;
        private TextView recordDate;
        private ImageView deleteBtn, cancelBtn, editBtn;
        private ConstraintLayout viewBackground;
        public CardView viewForeground;

        ViewHolder(View itemView) {
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
            viewForeground = itemView.findViewById(R.id.resCv);
            viewBackground = itemView.findViewById(R.id.deleteLayout);
            deleteBtn = itemView.findViewById(R.id.deleteImgBtn);
            cancelBtn = itemView.findViewById(R.id.cancelImgBtn);
            editBtn = itemView.findViewById(R.id.editImgBtn);

            itemView.setOnCreateContextMenuListener(this);
            deleteBtn.setOnClickListener(this);
            editBtn.setOnClickListener(this);
            cancelBtn.setOnClickListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(this.getAdapterPosition(), 1, 1, R.string.upd_menu_item);
            menu.add(this.getAdapterPosition(), 2, 2, R.string.del_menu_item);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.deleteImgBtn:
                    recyclerViewButtonClick.onButtonClick(getAdapterPosition(), 1);
                    break;
                case R.id.editImgBtn:
                    recyclerViewButtonClick.onButtonClick(getAdapterPosition(), 2);
                    break;
                case R.id.cancelImgBtn:
                    recyclerViewButtonClick.onButtonClick(getAdapterPosition(), 3);
                    break;
                default:
                    break;
            }
        }
    }

    public class EmptyViewHolder extends RecyclerView.ViewHolder {
        EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public long getItemId(int position) {
        return values.get(position).getId();
    }
}
