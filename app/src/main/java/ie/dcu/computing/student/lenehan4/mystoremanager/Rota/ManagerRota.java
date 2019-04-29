package ie.dcu.computing.student.lenehan4.mystoremanager.Rota;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ie.dcu.computing.student.lenehan4.mystoremanager.Activities.AddUserForElections;
import ie.dcu.computing.student.lenehan4.mystoremanager.Activities.ElectionActivity;
import ie.dcu.computing.student.lenehan4.mystoremanager.R;

public class ManagerRota extends Fragment {




    RecyclerView recyclerView;
    FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.manager_rota, container, false);
        recyclerView = view.findViewById(R.id.myrv);
        fab = view.findViewById(R.id.elections_fab);
        new FirebaseRotaHelper().viewShifts(new FirebaseRotaHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Shifts> shifts, List<String> keys) {
                new Rota_recyclerview_config().setConfig(recyclerView, getContext(), shifts, keys);
            }

            @Override
            public void DataIsDeleted() {

            }

            @Override
            public void DataIsUpdated() {

            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddShiftActivity.class));
                }

            });












        return view;
    }


}
