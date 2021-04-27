package com.example.postit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.service.autofill.AutofillService;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.RecViewHolder> {

    private ArrayList<String> displayNamesArray, messagesArray, datesArray;
    private Context context;
    TextView txt;

    public RecAdapter(Context ctx, ArrayList<String> displayNamesArray, ArrayList<String> messagesArray, ArrayList<String> datesArray){
        this.displayNamesArray = displayNamesArray;
        this.messagesArray = messagesArray;
        this.datesArray = datesArray;
        this.context = ctx;
    }

    @NonNull
    @Override
    public RecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_recycler, parent, false);

        return new RecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecViewHolder holder, int position) {
        String displayName = displayNamesArray.get(position);
        if(displayName.equals("Anonymous")){
            System.out.println("DAAAAAAAAAAAAAAAAA");
            holder.displayName.setText(displayNamesArray.get(position));
            holder.displayName.setTextColor(Color.parseColor("#EEC627"));
        }
        else{
            System.out.println("nuuuuuuuuuuuuuuuuuu");
            holder.displayName.setText(displayNamesArray.get(position));
            holder.displayName.setTextColor(Color.parseColor("#EE8A27"));
        }
        holder.message.setText(messagesArray.get(position));
        holder.date.setText(datesArray.get(position));
    }

    @Override
    public int getItemCount() {
        return displayNamesArray.size();
    }

    public class RecViewHolder extends RecyclerView.ViewHolder{
        TextView displayName;
        TextView message;
        TextView date;

        public RecViewHolder(@NonNull View itemView){
            super(itemView);
            displayName = itemView.findViewById(R.id.displayNameBox);
            message = itemView.findViewById(R.id.messageBox);
            date = itemView.findViewById(R.id.dataBox);
        }
    }
}
