package com.example.test2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Forgotpass#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Forgotpass extends Fragment {

       private FirebaseServices fbs ;
       private EditText etEmail;
       private Button buttonReset;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Forgotpass() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Forjet_pass.
     */
    // TODO: Rename and change types and number of parameters
    public static Forgotpass newInstance(String param1, String param2) {
        Forgotpass fragment = new Forgotpass();
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
        return inflater.inflate(R.layout.fragment_forgotpass, container, false);
    }
    @Override
    public void onStart()
    {
        super.onStart();
      fbs =FirebaseServices.getInstance();
      etEmail =getView().findViewById(R.id.etEmail);
       buttonReset =getView().findViewById(R.id.buttonReset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etEmail.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "There is no email", Toast.LENGTH_SHORT).show();
                    return;
                }
                fbs.getAuth().sendPasswordResetEmail(etEmail.getText().toString())

                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getActivity(), "Check your Email", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(getActivity(), "Failed , Ckeck the email Address you entered", Toast.LENGTH_SHORT).show();
                                }
                            }

                            });
            }
        });
    }


}