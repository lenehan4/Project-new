package ie.dcu.computing.student.lenehan4.mystoremanager.Rota;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseRotaHelper {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private List<Shifts> shifts = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<Shifts> shifts, List<String> keys);
        void DataIsDeleted();
        void DataIsUpdated();
    }

    public FirebaseRotaHelper() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Shifts");
    }

    public void viewShifts(final DataStatus dataStatus){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                shifts.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    keys.add(dataSnapshot1.getKey());
                    Shifts shift = dataSnapshot1.getValue(Shifts.class);
                    shifts.add(shift);

                }
                dataStatus.DataIsLoaded(shifts, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void updateShifts(String key, Shifts shifts, final DataStatus dataStatus){
        databaseReference.child(key).setValue(shifts).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsUpdated();
            }
        });
    }

    public void deleteShifts(String key, final DataStatus dataStatus){
        databaseReference.child(key).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsDeleted();
            }
        });
    }
}
