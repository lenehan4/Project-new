package ie.dcu.computing.student.lenehan4.mystoremanager.Rota;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import ie.dcu.computing.student.lenehan4.mystoremanager.R;

public class UpdateDeleteShiftActivity extends AppCompatActivity {

    EditText mname, mdate, mstartTime, mendTime;
    Button updateShift, deleteShift;


    private String key, name, date, startTime, endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_shift);

        key = getIntent().getStringExtra("key");
        name = getIntent().getStringExtra("Name");
        date = getIntent().getStringExtra("Date");
        startTime = getIntent().getStringExtra("Start Time");
        endTime = getIntent().getStringExtra("End Time");

        mname = findViewById(R.id.etName);
        mname.setText(name);
        mdate = findViewById(R.id.etDate);
        mdate.setText(date);
        mstartTime = findViewById(R.id.etStartTime);
        mstartTime.setText(startTime);
        mendTime = findViewById(R.id.etEndTime);
        mendTime.setText(endTime);
        updateShift = findViewById(R.id.btnUpdate);
        deleteShift = findViewById(R.id.btnDelete);

        updateShift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shifts shifts = new Shifts();
                shifts.setName(mname.getText().toString());
                shifts.setDate(mdate.getText().toString());
                shifts.setStartTime(mstartTime.getText().toString());
                shifts.setEndTime(mendTime.getText().toString());

                new FirebaseRotaHelper().updateShifts(key, shifts, new FirebaseRotaHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Shifts> shifts, List<String> keys) {

                    }

                    @Override
                    public void DataIsDeleted() {

                    }

                    @Override
                    public void DataIsUpdated() {
                        Toast.makeText(UpdateDeleteShiftActivity.this, "Shifted updated", Toast.LENGTH_LONG).show();
                        finish();
                        return;
                    }
                });
            }
        });

        deleteShift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FirebaseRotaHelper().deleteShifts(key, new FirebaseRotaHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Shifts> shifts, List<String> keys) {

                    }

                    @Override
                    public void DataIsDeleted() {
                        Toast.makeText(UpdateDeleteShiftActivity.this, "Shift deleted", Toast.LENGTH_LONG).show();
                        finish();
                        return;
                    }

                    @Override
                    public void DataIsUpdated() {

                    }
                });
            }
        });
    }
}
