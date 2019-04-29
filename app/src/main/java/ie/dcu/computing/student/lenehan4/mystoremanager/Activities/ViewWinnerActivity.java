package ie.dcu.computing.student.lenehan4.mystoremanager.Activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ie.dcu.computing.student.lenehan4.mystoremanager.AdapterClasses.ImageAdapter;
import ie.dcu.computing.student.lenehan4.mystoremanager.FirebaseClasses.Upload;
import ie.dcu.computing.student.lenehan4.mystoremanager.R;

public class ViewWinnerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageAdapter adapter;

    private ProgressBar progressCircle;

    private DatabaseReference databaseReference;
    private List<Upload> uploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_winner);

        recyclerView = findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        uploads = new ArrayList<>();

        progressCircle = findViewById(R.id.progress_circle);

        databaseReference = FirebaseDatabase.getInstance().getReference("Uploads!");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Upload upload = postSnapshot.getValue(Upload.class);
                    uploads.add(upload);
                }
                adapter = new ImageAdapter(ViewWinnerActivity.this, uploads);
                recyclerView.setAdapter(adapter);
                progressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewWinnerActivity.this, databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
