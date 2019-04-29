package ie.dcu.computing.student.lenehan4.mystoremanager.Rota;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
//import android.widget.SearchView;
import android.widget.Toast;
import android.support.v7.widget.SearchView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ie.dcu.computing.student.lenehan4.mystoremanager.R;
import ie.dcu.computing.student.lenehan4.mystoremanager.Rota.Shifts;
import ie.dcu.computing.student.lenehan4.mystoremanager.SharedPreferenceHelper;

public class EmployeeRota extends Fragment {
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    ArrayList<Shifts> shifts;

    EmployeeRotaAdapter employeeRotaAdapter;
    String TAG = EmployeeRota.class.getSimpleName();



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.employee_rota, container, false);
        setHasOptionsMenu(true);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Shifts");

        recyclerView = view.findViewById(R.id.rvRota);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        shifts = new ArrayList<Shifts>();

        employeeRotaAdapter = new EmployeeRotaAdapter(getActivity());

        recyclerView.setAdapter(employeeRotaAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d(TAG, "onDataChange: "+dataSnapshot.toString());
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Shifts s = dataSnapshot1.getValue(Shifts.class);

                    if(s.getCompanyName().equalsIgnoreCase(SharedPreferenceHelper
                            .getSharedPreferenceHelper(container.getContext()).getCompany()))
                          shifts.add(s);
                }
                employeeRotaAdapter.setShifts(shifts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Ooops something went wrong", Toast.LENGTH_LONG).show();
            }
        });




        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                employeeRotaAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}
