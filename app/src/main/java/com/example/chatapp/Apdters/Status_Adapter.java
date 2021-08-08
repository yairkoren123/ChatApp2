package com.example.chatapp.Apdters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;
import com.example.chatapp.the_class.The_profile;
import com.example.chatapp.the_class.The_status;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Status_Adapter extends RecyclerView.Adapter<Status_Adapter.ViewHolder>{

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener_Status;

    ArrayList<The_status> status_one_friend = new ArrayList<>();

    private ArrayList<The_profile> the_profile_friend_have_status =  new ArrayList<>();


    public Status_Adapter(Context context , ArrayList<The_profile> the_profile_friend_have_status) {
        this.mInflater = LayoutInflater.from(context);
        this.the_profile_friend_have_status = the_profile_friend_have_status;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_row_status, parent, false);
        return new ViewHolder(view);
    }


    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        The_profile the_profile = the_profile_friend_have_status.get(position);

        status_one_friend = the_profile.getStatuses_of_the_profile();

        holder.Textqueue.setText("Status in queue : " + status_one_friend.size());
        holder.myTextView.setText(the_profile.getName());
        if (status_one_friend.size() >= 1) {
            String url = status_one_friend.get(status_one_friend.size()-1).getImage_status().toString();

//            GradientDrawable shape = new GradientDrawable();
//            shape.setShape(GradientDrawable.OVAL);
//            shape.setStroke(2,Color.BLACK,600,200);
//            shape.setSize(0,2);
//            //holder.myImageView.setBackground(shape);


//            Drawable drawable = new DrawableBuilder()
//                    .rectangle()
//                    .solidColor(0xffe67e22)
//                    .bottomLeftRadius(20) // in pixels
//                    .bottomRightRadius(20) // in pixels
////        .cornerRadii(0, 0, 20, 20) // the same as the two lines above
//                    .build();
//            GradientDrawable shape = new GradientDrawable();
//            shape.setShape(GradientDrawable.RECTANGLE);
//            shape.setCornerRadii(new float[] { 8, 8, 8, 8, 0, 0, 0, 0 });
//            shape.setColor(backgroundColor);
//            shape.setStroke(3, borderColor);
//            v.setBackground(shape);

            Log.d("status_url", "onBindViewHolder: " + url);
            if (url != null && !url.equals("")) {
                Picasso.get()
                        .load(url)
                        .fit()
                        .centerCrop()
                        .into(holder.myImageView);
            } else {
                holder.myImageView.setImageResource(R.drawable.no_image);
            }
    }else {
            holder.myImageView.setImageResource(R.drawable.no_image);

        }
//         set the last image in the holder. image
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return the_profile_friend_have_status.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView, Textqueue;
        ImageView myImageView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.name_account_row_status);
            myImageView = itemView.findViewById(R.id.image_account_row_status);
            Textqueue = itemView.findViewById(R.id.queue_row_status);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener_Status != null) mClickListener_Status.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return the_profile_friend_have_status.get(id).getName();
    }

    // allows clicks events to be caught
    public void setClickListener_Status(ItemClickListener itemClickListener) {
        this.mClickListener_Status = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}


