package com.example.test2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BasketFragmentRV#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BasketFragmentRV extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Clothe> BasketArrayList;
    Adapter ClotheAdapter;
    FirebaseServices FBS;
    User user;
    ArrayList<String> ClothePathArrayList , finalPath;
    Button btn ;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BasketFragmentRV() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BasketFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BasketFragmentRV newInstance(String param1, String param2) {
        BasketFragmentRV fragment = new BasketFragmentRV();
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
    public void onStart() {
        super.onStart();
        FBS = FirebaseServices.getInstance();
        Query query = FBS.getFire().collection("Users").whereEqualTo( "email", FBS.getAuth().getCurrentUser().getEmail());
        query.get().addOnCompleteListener (task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        for (QueryDocumentSnapshot document : querySnapshot) {
                            this.user = document.toObject(User.class);
                            continueto();
                        }
                    } else {
                        Exception e = task.getException();
                        e.printStackTrace();

                    }
                });
        recyclerView = getView().findViewById(R.id.BasketRV);
        btn=getView().findViewById(R.id.BasketBuyBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BasketTrans();
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        FBS= FirebaseServices.getInstance();
        BasketArrayList=new ArrayList<Clothe>();
        ClothePathArrayList=new ArrayList<String>();
        EventChangeListener();
    }

    private void continueto() {
        ArrayList<String> paths = user.getBasketArrayList();
        int i = 0;
        while (paths.size() > i) {
            DocumentReference ClotheRef = FBS.getFire().collection("Clothes").document(paths.get(i));
            ClotheRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.getResult().exists()) {
                        Clothe clothe = task.getResult().toObject(Clothe.class);
                        BasketArrayList.add(clothe);
                        finalPath.add(task.getResult().getId());
                        EventChangeListener();
                    }
                }
            });
            i++;
        }
    }
    private void EventChangeListener() {
        if (finalPath.size()==user.getBasketArrayList().size()){
            ClotheAdapter = new Adapter(getContext(),BasketArrayList,finalPath);
            recyclerView.setAdapter(ClotheAdapter);
        }
    }

      public void BasketTrans() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FlMain, new CardFragment());
        ft.commit();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_basket_rv, container, false);
    }

}