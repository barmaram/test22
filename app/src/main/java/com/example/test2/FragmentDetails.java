package com.example.test2;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.StorageReference;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDetails extends Fragment {
   private String path;
   private FirebaseServices fbs;
    private TextView Name, Size, Des, Price;
    private Button ButtonAdd;
    ImageView ClotheImg ,BackButton;
   Clothe clothe;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentDetails() {
        // Required empty public constructor
    }
    public FragmentDetails(String path)
    {
        this.path=path;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentDetails newInstance(String param1, String param2) {
        FragmentDetails fragment = new FragmentDetails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Connectcomponets();
    }

    private void Connectcomponets() {
        Name = getView().findViewById(R.id.tvNameDetails);
        Price = getView().findViewById(R.id.tvPriceDetails);
        Size = getView().findViewById(R.id.tvSizeDetails);
        Des = getView().findViewById(R.id.tvDecDetails);
        BackButton=getView().findViewById(R.id.BackButton);
        ButtonAdd = getView().findViewById(R.id.btnAddDetails);
        ClotheImg=getView().findViewById(R.id.imageClotheDetails);
        fbs=FirebaseServices.getInstance();
        ButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BasketFragmentRVTrans();
            }
        });
        Eventonchange();

    }

    private void BasketFragmentRVTrans() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FlMain, new BasketFragmentRV());
        ft.commit();
    }

    private void Eventonchange()
    {
            DocumentReference userRef = fbs.getFire().collection("Clothe").document(path);
            userRef.get()
                    .addOnSuccessListener((DocumentSnapshot documentSnapshot) -> {
                        if (documentSnapshot.exists()) {
                            clothe = documentSnapshot.toObject(Clothe.class);
                            hearme();
                        } else {
                            System.out.println("User document doesn't exist.");
                        }
                    }).addOnFailureListener(e -> {
                        System.out.println("Error retrieving user:" + e.getMessage());
                    });
    }

    private void hearme() {
        Name.setText("Name: "+clothe.getName());
        Size.setText("Size: "+clothe.getSize());
        Price.setText("Price: "+clothe.getPrice());
        Des.setText("Description: "+clothe.getDescription());
        StorageReference storageRef= fbs.getStorage().getReference().child(clothe.getImage());
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getContext())
                        .load(uri)
                        .into(ClotheImg);
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
   BackButton.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           getFragmentManager().popBackStack();
       }
   });
    }

}










