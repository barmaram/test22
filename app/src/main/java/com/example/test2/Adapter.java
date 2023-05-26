package com.example.test2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
  Context context;
  ArrayList<Clothe> clotheArrayList;

    public Adapter(Context context, ArrayList<Clothe> clotheArrayList) {
        this.context = context;
        this.clotheArrayList = clotheArrayList;
    }

    @NonNull
    @Override
    public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(context).inflate(R.layout.clothe,parent,false);

        return new MyViewHolder(V);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.MyViewHolder holder, int position) {
    Clothe clothe =clotheArrayList.get(position);

    holder.Name.setText(clothe.name);
    holder.Description.setText(clothe.description);
    holder.Id.setText(clothe.id);
    holder.Size.setText(clothe.size);

    }

    @Override
    public int getItemCount() {
        return clotheArrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
       TextView Name,Id,Size,Description;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Name=itemView.findViewById(R.id.tvName);
            Id=itemView.findViewById(R.id.tvId);
            Size=itemView.findViewById(R.id.tvSize);
            Description=itemView.findViewById(R.id.tvDescription);
        }
    }
}
