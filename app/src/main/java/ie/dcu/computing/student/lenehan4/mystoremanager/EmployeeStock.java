package ie.dcu.computing.student.lenehan4.mystoremanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ie.dcu.computing.student.lenehan4.mystoremanager.Stock.AddStockActivity;

public class EmployeeStock extends Fragment {

    private Button Scanner;
    private Button EnterDetailsManually;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.employee_stock, container, false);

        Scanner = view.findViewById(R.id.btnScanner);
        EnterDetailsManually = view.findViewById(R.id.btnManually);


        Scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ScannerActivity.class));
            }
        });

        EnterDetailsManually.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddStockActivity.class));
            }
        });
        return view;

    }

}
