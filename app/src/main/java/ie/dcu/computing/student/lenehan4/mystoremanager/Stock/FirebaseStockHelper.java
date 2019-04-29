package ie.dcu.computing.student.lenehan4.mystoremanager.Stock;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ie.dcu.computing.student.lenehan4.mystoremanager.Rota.FirebaseRotaHelper;
import ie.dcu.computing.student.lenehan4.mystoremanager.Rota.Shifts;

public class FirebaseStockHelper {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private List<Stock> stocks = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<Stock> stocks, List<String> keys);
        void DataIsDeleted();
    }

    public FirebaseStockHelper() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Stock");
    }

    public void viewStock(final FirebaseStockHelper.DataStatus dataStatus){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                stocks.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    keys.add(dataSnapshot1.getKey());
                    Stock s = dataSnapshot1.getValue(Stock.class);
                    stocks.add(s);

                }
                dataStatus.DataIsLoaded(stocks, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
