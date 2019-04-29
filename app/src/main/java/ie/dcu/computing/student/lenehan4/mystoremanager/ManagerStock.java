package ie.dcu.computing.student.lenehan4.mystoremanager;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import ie.dcu.computing.student.lenehan4.mystoremanager.Stock.FirebaseStockHelper;
import ie.dcu.computing.student.lenehan4.mystoremanager.Stock.Stock;
import ie.dcu.computing.student.lenehan4.mystoremanager.Stock.Stock_recycler_config;

public class ManagerStock extends Fragment {
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.manager_stock, container, false);

        recyclerView = view.findViewById(R.id.rv);
        new FirebaseStockHelper().viewStock(new FirebaseStockHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Stock> stocks, List<String> keys) {
                new Stock_recycler_config().setConfig(recyclerView, getContext(), stocks, keys);
            }

            @Override
            public void DataIsDeleted() {

            }
        });





        return view;
    }
}
