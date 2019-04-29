package ie.dcu.computing.student.lenehan4.mystoremanager.Stock;

import android.content.Intent;
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

public class AddStockActivity extends AppCompatActivity {

    private EditText UPC, Brand, Description, Quantity, Price, company;
    private Button AddItem;
    DatabaseReference databaseReference;
    Stock stock;
    long maxid=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);


        UPC = findViewById(R.id.etUpc);
        Brand = findViewById(R.id.etBrand);
        Quantity = findViewById(R.id.etQuantity);
        Price = findViewById(R.id.etPrice);
        AddItem = findViewById(R.id.btnAddItem);
        company = findViewById(R.id.add_stock_company);
        stock = new Stock();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Stock");

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

        AddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                stock.setUPC(UPC.getText().toString().trim());
                stock.setBrand(Brand.getText().toString().trim());
                stock.setQuantity(Quantity.getText().toString().trim());
                stock.setPrice(Price.getText().toString().trim());
                stock.setCompany(company.getText().toString());

                databaseReference.child(String.valueOf(maxid+1)).setValue(stock);



                Toast.makeText(AddStockActivity.this, "Stock Added", Toast.LENGTH_LONG).show();

            }
        });








    }







    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==4){
            String message = data.getStringExtra("Result");
            UPC.setText(message);
        }
    }


}
