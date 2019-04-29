package ie.dcu.computing.student.lenehan4.mystoremanager.Rota;

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

public class Rota_recyclerview_config {
    private Context mcontext;
    private ShiftAdapter shiftAdapter;
    public void setConfig(RecyclerView recyclerView, Context context, List<Shifts> shifts, List<String> keys){
        mcontext = context;
        shiftAdapter = new ShiftAdapter(shifts, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(shiftAdapter);
    }



    class ShiftView extends RecyclerView.ViewHolder{
        private TextView name, date, startTime, endTime;
        private String key;



        public ShiftView(ViewGroup parent){
            super(LayoutInflater.from(mcontext).inflate(R.layout.shift_view, parent, false));

            name = itemView.findViewById(R.id.tvName);
            date = itemView.findViewById(R.id.tvDate);
            startTime = itemView.findViewById(R.id.tvStartTime);
            endTime = itemView.findViewById(R.id.tvEndTime);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mcontext, UpdateDeleteShiftActivity.class);
                    intent.putExtra("key", key);
                    intent.putExtra("Name", name.getText().toString());
                    intent.putExtra("Date", date.getText().toString());
                    intent.putExtra("Start Time", startTime.getText().toString());
                    intent.putExtra("End Time", endTime.getText().toString());

                    mcontext.startActivity(intent);
                }
            });
        }

        public void bind(Shifts shifts, String key){
            name.setText(shifts.getName());
            date.setText(shifts.getDate());
            startTime.setText(shifts.getStartTime());
            endTime.setText(shifts.getEndTime());

            this.key = key;
        }
    }

    class ShiftAdapter extends RecyclerView.Adapter<ShiftView>{
        private List<Shifts> shiftsList;
        private List<String> mkeys;

        public ShiftAdapter(List<Shifts> shiftsList, List<String> mkeys){
            this.shiftsList = shiftsList;
            this.mkeys = mkeys;

        }

        @NonNull
        @Override
        public ShiftView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ShiftView(viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull ShiftView holder, int i) {
            holder.bind(shiftsList.get(i), mkeys.get(i));

        }

        @Override
        public int getItemCount() {
            return shiftsList.size();
        }
    }

}
