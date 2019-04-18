package com.vivekvishwanath.tipsease;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

    public class TippingListAdapter extends RecyclerView.Adapter<TippingListAdapter.ViewHolder> {

        ArrayList<TipObject> tips;
        Context context;

        public TippingListAdapter(ArrayList<TipObject> tips) {
            this.tips = tips;
        }

        @NonNull
        @Override
        public TippingListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            context = parent.getContext();
            View itemView = LayoutInflater.from(context).inflate(R.layout.recent_tip_layout, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull TippingListAdapter.ViewHolder holder, int position) {
            TipObject tip = tips.get(position);
            holder.tipSender.setText(tip.getSenderName());
            holder.dateReceived.setText(tip.getDateReceived());
            holder.receivedTipAmount.setText(Double.toString(tip.getTipAmount()));

            setEnterAnimation(holder.tipCardView);
        }

        private void setEnterAnimation(View viewToAnimate) {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
        }

        @Override
        public int getItemCount() {
            return tips.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {

            CardView tipCardView;
            TextView receivedTipAmount;
            TextView dateReceived;
            TextView tipSender;

            public ViewHolder(View itemView) {
                super(itemView);

                tipCardView = itemView.findViewById(R.id.tip_card_view);
                receivedTipAmount = itemView.findViewById(R.id.received_tip_amount);
                dateReceived = itemView.findViewById(R.id.date_received);
                tipSender = itemView.findViewById(R.id.tip_sender);

            }
        }
    }


