package ie.dcu.computing.student.lenehan4.mystoremanager.Activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import ie.dcu.computing.student.lenehan4.mystoremanager.AdapterClasses.CandidatesAdapter;
import ie.dcu.computing.student.lenehan4.mystoremanager.EmployeeModelClass;
import ie.dcu.computing.student.lenehan4.mystoremanager.R;
import ie.dcu.computing.student.lenehan4.mystoremanager.SharedPreferenceHelper;

public class VoteActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CandidatesAdapter candidatesAdapter;
    FirebaseFirestore db;
    String TAG = VoteActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);


        db = FirebaseFirestore.getInstance();
        initViews();


    }

    private void initViews() {
        recyclerView = findViewById(R.id.vote_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));


        candidatesAdapter = new CandidatesAdapter();

        recyclerView.setAdapter(candidatesAdapter);





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

                                    if(SharedPreferenceHelper.getSharedPreferenceHelper(VoteActivity.this).getCompany()
                                    .equalsIgnoreCase(document.getData().get("company").toString())){

                                        EmployeeModelClass employeeModelClass = new EmployeeModelClass();
                                        employeeModelClass.setName(document.getId());
                                        employeeModelClass.setDept(document.getData().get("dept").toString());
                                        employeeModelClass.setEmployee_id(document.getData().get("id").toString());
                                        employeeModelClass.setImage(document.getData().get("imagePath").toString());
                                        employeeModelClass.setVotes(document.getData().get("votes").toString());

                                        Log.d(TAG, "onComplete: " + employeeModelClass.toString());
                                        employeeModelClassList.add(employeeModelClass);
                                    }
                                }


                                //candidatesAdapter = new CandidatesAdapter(employeeModelClassList);
                                //recyclerView.setAdapter(candidatesAdapter);
                                candidatesAdapter.setList(employeeModelClassList);
                                Log.d(TAG, "onComplete: ");
                            }catch (Exception e){
                                Log.d(TAG, "onComplete: "+e.toString());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });


    }
}
