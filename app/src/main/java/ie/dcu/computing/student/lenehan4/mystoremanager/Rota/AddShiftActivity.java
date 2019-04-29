package ie.dcu.computing.student.lenehan4.mystoremanager.Rota;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ie.dcu.computing.student.lenehan4.mystoremanager.R;

public class AddShiftActivity extends AppCompatActivity {

    EditText name, date, startTime, endTime, companyName;
    Button addShift;
    DatabaseReference databaseReference;
    Shifts shifts;
    long maxid=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shift);

        name = findViewById(R.id.etName);
        date = findViewById(R.id.etDate);
        startTime = findViewById(R.id.etStartTime);
        endTime = findViewById(R.id.etEndTime);
        addShift = findViewById(R.id.btnAddShift);
        companyName = findViewById(R.id.addShiftCompanyName);
        shifts = new Shifts();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Shifts");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    maxid = (dataSnapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        addShift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shifts.setName(name.getText().toString().trim());
                shifts.setDate(date.getText().toString().trim());
                shifts.setStartTime(startTime.getText().toString().trim());
                shifts.setEndTime(endTime.getText().toString().trim());
                shifts.setCompanyName(companyName.getText().toString());

                databaseReference.child(String.valueOf(maxid+1)).setValue(shifts);


                Toast.makeText(AddShiftActivity.this, "Shift Added", Toast.LENGTH_LONG).show();

            }
        });





    }
}
