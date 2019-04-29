package ie.dcu.computing.student.lenehan4.mystoremanager.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import ie.dcu.computing.student.lenehan4.mystoremanager.R;

public class AddUserForElections extends AppCompatActivity {

    private StorageReference mStorageRef;

    private static final int EXTRA_PICK_IMAGE = 121;
    ImageView add_user_img;
    EditText name, dept, employee_id, company;
    Button save_btn;
    String TAG = AddUserForElections.class.getSimpleName();


    Uri imageUri ;
    FirebaseFirestore db;

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_for_elections);
        FirebaseApp.initializeApp(this);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        db = FirebaseFirestore.getInstance();

        initViews();
        attachListener();

    }

    private void attachListener() {


        add_user_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, EXTRA_PICK_IMAGE);
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveImage();
            }
        });

    }

    private void saveImage() {


        if(imageUri == null){
            showToast("Select Image");
            return;
        }


        if(!validate()){
            return;
        }

        progressDialog.show();
        StorageReference riversRef = mStorageRef.child("images/"+name.getText().toString()+".jpg");
        Bitmap bmp = null;
        try {
            bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos);
        byte[] data = baos.toByteArray();
        riversRef//.putFile(imageUri)
                .putBytes(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Log.d(TAG, "onSuccess: ");

                        Log.d(TAG, "onSuccess: "+taskSnapshot.getMetadata().getPath());

                        mStorageRef.child(taskSnapshot.getMetadata().getPath()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                saveData(uri.toString());
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                        Log.d(TAG, "onFailure: "+exception.toString());
                        progressDialog.dismiss();
                    }
                });

    }


    public void saveData(String imagePath){
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("id", employee_id.getText().toString());
        user.put("dept", dept.getText().toString());
        user.put("imagePath", imagePath);
        user.put("votes", "0");
        user.put("company", company.getText().toString());

// Add a new document with a generated ID
        db.collection("electionParticipants")
                .document(name.getText().toString())
                .set(user)

            .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    showToast("Successfull");
                    progressDialog.dismiss();
                }else
                {
                    showToast("Unsuccessful");
                    progressDialog.dismiss();
                }
            }
        });
    }

    private boolean validate() {

        if(name.getText().toString().isEmpty()){
            name.setError("Empty");
            return false;
        }


        if (employee_id.getText().toString().isEmpty()) {

            employee_id.setError("Empty");
            return false;

        }


        if(dept.getText().toString().isEmpty()){
            dept.setError("Empty");
            return false;
        }

        return true;
    }

    private void showToast(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void initViews() {

        add_user_img = findViewById(R.id.add_employee_img);;
        name = findViewById(R.id.add_employee_name);
        dept = findViewById(R.id.add_employee_dept);
        employee_id = findViewById(R.id.add_employee_id);
        save_btn = findViewById(R.id.employee_save_btn);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading");
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please Wait...");
        company = findViewById(R.id.add_user_company);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == EXTRA_PICK_IMAGE && resultCode == RESULT_OK) {
            imageUri = data.getData();

            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(AddUserForElections.this.getContentResolver(),
                        imageUri);
                add_user_img.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

/*
           */
            Log.d(TAG, "onActivityResult: task_image");
        }
    }
}
