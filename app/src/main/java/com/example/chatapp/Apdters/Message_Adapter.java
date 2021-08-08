package com.example.chatapp.Apdters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;
import com.example.chatapp.the_class.The_messages;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class Message_Adapter extends FirestoreRecyclerAdapter <The_messages, Message_Adapter.StrikeHolder>{

    public Message_Adapter(@NonNull FirestoreRecyclerOptions<The_messages> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull Message_Adapter.StrikeHolder holder, int position
            , @NonNull The_messages model) {
            holder.text_des.setText(model.getText());
            holder.text_time.setText(model.getTime());
    }

    @NonNull
    @Override
    public StrikeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item,
                parent,false);
        return new StrikeHolder(view);
    }

    class StrikeHolder extends RecyclerView.ViewHolder{
        TextView text_time, text_des;

        public StrikeHolder(View itemView){
            super(itemView);

            text_des = itemView.findViewById(R.id.text_message_item);
            text_time = itemView.findViewById(R.id.time_message_item);


        }
    }
}
