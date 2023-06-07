package com.example.test2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
  Context context;
   ArrayList<Clothe> clotheArrayList;
    ArrayList<String> clotheArrayListpath;
    public Adapter(Context context, ArrayList<Clothe> clotheArrayList,ArrayList<String> clotheArrayListpath) {
        this.context = context;
        this.clotheArrayList = clotheArrayList;
        this.clotheArrayListpath=clotheArrayListpath;
    }

    @NonNull
    @Override
    public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(context).inflate(R.layout.clothe,parent,false);

        return new MyViewHolder(V);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.MyViewHolder holder,int position) {
    Clothe clothe =clotheArrayList.get(position);

    holder.Name.setText(clothe.name);
    holder.Description.setText(clothe.description);
    holder.Id.setText(clothe.price);
    holder.Size.setText(clothe.size);
    holder.btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
         AppCompatActivity activity = (AppCompatActivity) context ;
         activity.getSupportFragmentManager().beginTransaction().replace(R.id.FlMain,new FragmentDetails(clotheArrayListpath.get(position))).addToBackStack(null).commit();
        }
    });
    }

    @Override
    public int getItemCount() {
        return clotheArrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
       TextView Name,Id,Size,Description;
       Button btn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Name=itemView.findViewById(R.id.tvName);
            Id=itemView.findViewById(R.id.tvId);
            Size=itemView.findViewById(R.id.tvSize);
            Description=itemView.findViewById(R.id.tvDescription);
            btn=itemView.findViewById(R.id.btnView);
        }
    }
}
