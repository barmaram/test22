package com.example.test2;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.nfc.Tag;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddClotheFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddClotheFragment extends Fragment {
    private EditText name, size, des, price;
    private Button buttonAdd,btnView ;
    private FirebaseServices fbs;
    private ImageView img;
    int SELECT_PICTURE = 200;


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
        price= getView().findViewById(R.id.etIdAddFragment);
        size = getView().findViewById(R.id.etSizeAddFragment);
        des = getView().findViewById(R.id.etDesAddFragment);
        buttonAdd = getView().findViewById(R.id.buttonAddFragment);
        fbs = FirebaseServices.getInstance();
        img=getView().findViewById(R.id.ClotheImage);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddtoFirestore();

            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGalleryAndSelectPhoto();

            }
        });

        }

    private String UploadImageToFirebase(){
        BitmapDrawable drawable = (BitmapDrawable) img.getDrawable();
        Bitmap Image = drawable.getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Image.compress (Bitmap.CompressFormat .JPEG,100,baos);
        byte [] data = baos.toByteArray();
        StorageReference ref =fbs.getStorage().getReference("Clothe image"+ UUID.randomUUID().toString());
        UploadTask uploadTask =ref.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG,"error with the pic", e); }

        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                   public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                   }

        });
        return ref.getPath();

     }



      void openGalleryAndSelectPhoto() {
        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i,"SELECT_PICTURE"),SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    img.setImageURI(selectedImageUri);
                }
            }
        }
    }

        private void AddtoFirestore() {
        String Name, Size, Des, Price;
        Name = name.getText().toString();
        Size = size.getText().toString();
        Des = des.getText().toString();
        Price=price.getText().toString();
        if (Name.trim().isEmpty() || Size.trim().isEmpty() || Des.trim().isEmpty()|| Price.trim().isEmpty()) {
            Toast.makeText(getActivity(), "SOME DATA IS MISSING!!", Toast.LENGTH_SHORT).show();
            return;
        }
        Clothe clothe = new Clothe(Name, Size,Des,Price,UploadImageToFirebase());
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

    }
}