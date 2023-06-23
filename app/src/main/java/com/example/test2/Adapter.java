package com.example.test2;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.StorageReference;

import java.time.Instant;
import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    Context context;
    boolean y;
    FirebaseServices fbs;
    ArrayList<Clothe> clotheArrayList;
    ArrayList<String> clotheArrayListpath;
    BasketFragmentRV basketFragmentRV;

    public Adapter(Context context,  ArrayList<Clothe> clotheArrayList, ArrayList<String> clotheArrayListpath,BasketFragmentRV basketFragmentRV) {
        this.context = context;
        this.clotheArrayList = clotheArrayList;
        this.clotheArrayListpath = clotheArrayListpath;
        this.basketFragmentRV=basketFragmentRV;
    }

    public Adapter(Context context, ArrayList<Clothe> clotheArrayList, ArrayList<String> clotheArrayListpath) {
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    Clothe clothe =clotheArrayList.get(position);
    fbs=FirebaseServices.getInstance();
    holder.Name.setText(clothe.getName());
    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AppCompatActivity activity = (AppCompatActivity) context ;
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.FlMain,new FragmentDetails(clotheArrayListpath.get(position))).addToBackStack(null).commit();
        }
    });

        StorageReference storageRef= fbs.getStorage().getReference().child(clothe.getImage());
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context)
                        .load(uri)
                .into(holder.ClotheImage);
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
       if (basketFragmentRV==null){
            ViewGroup ParentView=(ViewGroup) holder.erase.getParent();
            ParentView.removeView(holder.erase);
            holder.erase.setVisibility(View.GONE);
        }
        else holder.erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                basketFragmentRV.removebasket(clotheArrayListpath.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return clotheArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
       TextView Name;
       ImageView ClotheImage,erase;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Name=itemView.findViewById(R.id.tvName);

            ClotheImage=itemView.findViewById(R.id.Clotheimgiteam);
            erase=itemView.findViewById(R.id.erase);
        }
    }


}
