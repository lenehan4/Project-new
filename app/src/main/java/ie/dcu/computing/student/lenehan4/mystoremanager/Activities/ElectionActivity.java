package ie.dcu.computing.student.lenehan4.mystoremanager.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import ie.dcu.computing.student.lenehan4.mystoremanager.AdapterClasses.MyAdapter;
import ie.dcu.computing.student.lenehan4.mystoremanager.EmployeeModelClass;
import ie.dcu.computing.student.lenehan4.mystoremanager.R;

public class ElectionActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    FloatingActionButton fab;
    MyAdapter myAdapter;
    FirebaseFirestore db;

    String TAG = ElectionActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_election);
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                //.setPersistenceEnabled(false)
                .build();
//        db.setFirestoreSettings(settings);
        initViews();
        setUpRecyclerView();
        attachListener();
       // getData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        db.collection("electionParticipants")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {


                            try {

                                List<EmployeeModelClass> employeeModelClassList = new ArrayList<>();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    EmployeeModelClass employeeModelClass = new EmployeeModelClass();
                                    employeeModelClass.setName(document.getId());
                                    employeeModelClass.setDept(document.getData().get("dept").toString());
                                    employeeModelClass.setEmployee_id(document.getData().get("id").toString());
                                    employeeModelClass.setImage(document.getData().get("imagePath").toString());
                                    employeeModelClass.setVotes(document.getData().get("votes").toString());

                                    Log.d(TAG, "onComplete: " + employeeModelClass.toString());
                                    employeeModelClassList.add(employeeModelClass);
                                }


                                myAdapter.setList(employeeModelClassList);
                            }catch (Exception e){
                                Log.d(TAG, "onComplete: "+e.toString());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });


    }

    private void attachListener() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ElectionActivity.this, AddUserForElections.class));
            }

        });
    }

    private void setUpRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));
        myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);

    }

    private void initViews() {
        recyclerView = findViewById(R.id.elections_recyclerview);
        fab = findViewById(R.id.elections_fab);
    }
}
