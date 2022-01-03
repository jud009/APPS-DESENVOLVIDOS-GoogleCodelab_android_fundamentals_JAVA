/*
 * Copyright (C) 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.materialme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/***
 * The adapter class for the RecyclerView, contains the sports data
 */
class SportsAdapter extends RecyclerView.Adapter<SportsAdapter.ViewHolder> {


    interface SportsAdapterClickListener {
        void onClick(View view, Sport sport);
    }

    //Member variables
    private ArrayList<Sport> mSportsData;
    private Context mContext;
    private SportsAdapterClickListener clickListener;

    /**
     * Constructor that passes in the sports data and the context
     *
     * @param sportsData ArrayList containing the sports data
     * @param context    Context of the application
     */
    SportsAdapter(Context context, ArrayList<Sport> sportsData) {
        this.mSportsData = sportsData;
        this.mContext = context;
    }

    public void setClickListener(SportsAdapterClickListener clickListener) {
        this.clickListener = clickListener;
    }

    /**
     * Required method for creating the viewholder objects.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return The newly create ViewHolder.
     */
    @Override
    public SportsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false));
    }

    /**
     * Required method that binds the data to the viewholder.
     *
     * @param holder   The viewholder into which the data should be put.
     * @param position The adapter position.
     */
    @Override
    public void onBindViewHolder(SportsAdapter.ViewHolder holder, int position) {
        //Get current sport
        Sport currentSport = mSportsData.get(position);
        //Populate the textviews with data
        holder.bindTo(currentSport);
    }


    /**
     * Required method for determining the size of the data set.
     *
     * @return Size of the data set.
     */
    @Override
    public int getItemCount() {
        return mSportsData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView mTitleText;
        private final TextView mInfoText;
        private final ImageView mSportsImage;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleText = itemView.findViewById(R.id.sportsTitle);
            mInfoText = itemView.findViewById(R.id.newsTitle);
            mSportsImage = itemView.findViewById(R.id.sports_image);
        }

        void bindTo(Sport currentSport) {

            mTitleText.setText(currentSport.getTitle());
            mInfoText.setText(currentSport.getInfo());
            Glide.with(mContext).load(currentSport.getImageResource()).into(mSportsImage);

        }

        @Override
        public void onClick(View v) {
            if (clickListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                clickListener.onClick(v,mSportsData.get(getAdapterPosition()));
            }
        }
    }
}
