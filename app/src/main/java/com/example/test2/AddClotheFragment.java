package com.example.test2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddClotheFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddClotheFragment extends Fragment {
    private EditText name, size, des, Id;
    private Button buttonAdd,btnView ;
    private FirebaseServices fbs;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddClotheFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddClotheFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddClotheFragment newInstance(String param1, String param2) {
        AddClotheFragment fragment = new AddClotheFragment();
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
        return inflater.inflate(R.layout.fragment_add_clothe, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        connectComponents();
    }

    private void connectComponents() {
        name = getView().findViewById(R.id.etNameAddFragment);
        Id = getView().findViewById(R.id.etIdAddFragment);
        size = getView().findViewById(R.id.etSizeAddFragment);
        des = getView().findViewById(R.id.etDesAddFragment);
        buttonAdd = getView().findViewById(R.id.buttonAddFragment);
        fbs = FirebaseServices.getInstance();
        btnView=getView().findViewById(R.id.btnView);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddtoFirestore();

            }
        });
    }

    private void AddtoFirestore() {
        String Name, Size, Des,id;
        Name = name.getText().toString();
        Size = size.getText().toString();
        Des = des.getText().toString();
        id=Id.getText().toString();
        if (Name.trim().isEmpty() || Size.trim().isEmpty() || Des.trim().isEmpty()) {
            Toast.makeText(getActivity(), "SOME DATA IS MISSING!!", Toast.LENGTH_SHORT).show();
            return;
        }
        Clothe clothe = new Clothe(Name, Size, Des,id,btnView);
       try {
           fbs.getFire().collection("Clothe")
                   .add(clothe)
                   .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                       @Override
                       public void onSuccess(DocumentReference documentReference) {
                           FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                           ft.replace(R.id.FlMain, new FragmentClotheRV());
                           ft.commit();
                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {

                       }
                   });
        }catch (Exception e){
           Log.e("ddd", e.getMessage());
       }

    }
}