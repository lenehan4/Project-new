package ie.dcu.computing.student.lenehan4.mystoremanager.Stock;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ie.dcu.computing.student.lenehan4.mystoremanager.R;
import ie.dcu.computing.student.lenehan4.mystoremanager.Rota.Rota_recyclerview_config;
import ie.dcu.computing.student.lenehan4.mystoremanager.Rota.Shifts;
import ie.dcu.computing.student.lenehan4.mystoremanager.Rota.UpdateDeleteShiftActivity;

public class Stock_recycler_config {

    private Context mcontext;
    private StockAdapter stockAdapter;


    public void setConfig(RecyclerView recyclerView, Context context, List<Stock> stocks, List<String> keys){
        mcontext = context;
        stockAdapter = new StockAdapter(stocks, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(stockAdapter);
    }



    class StockView extends RecyclerView.ViewHolder{
        private TextView brand, upc, quantity, price;
        private String key;



        public StockView(ViewGroup parent){
            super(LayoutInflater.from(mcontext).inflate(R.layout.item, parent, false));

            brand = itemView.findViewById(R.id.tvBrand);
            upc = itemView.findViewById(R.id.tvUpc);
            quantity = itemView.findViewById(R.id.tvQuantity);
            price = itemView.findViewById(R.id.tvPrice);

            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mcontext, UpdateDeleteShiftActivity.class);
                    intent.putExtra("key", key);
                    intent.putExtra("Brand", brand.getText().toString());
                    intent.putExtra("UPC", upc.getText().toString());
                    intent.putExtra("Quantity", quantity.getText().toString());
                    intent.putExtra("Price", price.getText().toString());

                    mcontext.startActivity(intent);
                }
            });*/
        }

        public void bind(Stock stock, String key){
            brand.setText(stock.getBrand());
            upc.setText(stock.getUPC());
            quantity.setText(stock.getQuantity());
            price.setText(stock.getPrice());

            this.key = key;
        }
    }

    class StockAdapter extends RecyclerView.Adapter<Stock_recycler_config.StockView>{
        private List<Stock> stockList;
        private List<String> mkeys;

        public StockAdapter(List<Stock> stockList, List<String> mkeys){
            this.stockList = stockList;
            this.mkeys = mkeys;

        }

        @NonNull
        @Override
        public Stock_recycler_config.StockView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new Stock_recycler_config.StockView(viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull Stock_recycler_config.StockView holder, int i) {
            holder.bind(stockList.get(i), mkeys.get(i));

        }

        @Override
        public int getItemCount() {
            return stockList.size();
        }
    }
}
