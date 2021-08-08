package com.example.chatapp.Apdters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;
import com.example.chatapp.The_Interfaces.ItemClickListener;
import com.example.chatapp.the_class.The_profile;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Acount_Adapter extends RecyclerView.Adapter<Acount_Adapter.ViewHolder> {

    private ArrayList<The_profile> theProfileArrayListe = new ArrayList<>();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    boolean status_show ;

    // data is passed into the constructor
    public Acount_Adapter(Context context, ArrayList<The_profile> theProfileArrayListe , Boolean status_show) {
        this.mInflater = LayoutInflater.from(context);
        this.theProfileArrayListe = theProfileArrayListe;
        this.status_show = status_show;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_row, parent, false);
        return new ViewHolder(view);
    }


    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String animal = theProfileArrayListe.get(position).getName();

        holder.myTextView.setText(animal);

        The_profile the_profile = theProfileArrayListe.get(position);

        if (the_profile.getImage().equals("") || the_profile.getImage() == null){
            holder.myImageView.setImageResource(R.drawable.no_image);
        }else {
            // we have a image
            String url = the_profile.getImage().toString();
            Picasso.get()
                    .load(url)
                    .centerCrop()
                    .fit()
                    .into(holder.myImageView);
        }


        if (status_show == true){
            // show status
            holder.my_status.setText(the_profile.getThe_profile_status_short().toString().trim());
            holder.dot_online.setVisibility(View.INVISIBLE);



        }else {
            // show date because is already friend of yours
            holder.my_status.setText(the_profile.getLast_connected().toString().trim());
            holder.status_massage_go_chat.setVisibility(View.INVISIBLE);
            if (theProfileArrayListe.get(position).getLast_connected().equals("online")){
                // if he online
                holder.dot_online.setImageResource(R.drawable.dot_online);
            }else {
                // if not online
                holder.dot_online.setImageResource(R.drawable.dot_offline);

            }
        }

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return theProfileArrayListe.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView , my_status;
        ImageView myImageView , dot_online;

        ImageButton status_massage_go_chat;


        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.name_account_row);
            myImageView = itemView.findViewById(R.id.image_account_row);
            my_status = itemView.findViewById(R.id.status_account_row);
            dot_online = itemView.findViewById(R.id.is_online_dot_row);
            status_massage_go_chat = itemView.findViewById(R.id.massge_icon_status);


            itemView.setOnClickListener(this);
            myImageView.setOnClickListener(this::onClick);
            status_massage_go_chat.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition() );
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return theProfileArrayListe.get(id).getName();
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events

}
