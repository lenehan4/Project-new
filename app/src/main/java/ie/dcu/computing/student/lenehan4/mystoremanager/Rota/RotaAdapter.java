package ie.dcu.computing.student.lenehan4.mystoremanager.Rota;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ie.dcu.computing.student.lenehan4.mystoremanager.R;

public class RotaAdapter extends RecyclerView.Adapter<RotaAdapter.MyViewHolder> {

    Context context;
    ArrayList<Shifts> shifts;

    public RotaAdapter(Context c, ArrayList<Shifts> s)
    {
        shifts = s;
        context = c;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.shift_view, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.name.setText(shifts.get(i).getName());
        myViewHolder.date.setText(shifts.get(i).getDate());
        myViewHolder.startTime.setText(shifts.get(i).getStartTime());
        myViewHolder.endTime.setText(shifts.get(i).getEndTime());

    }

    @Override
    public int getItemCount() {
        return shifts.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView name, date, startTime, endTime;
        public MyViewHolder(View itemView) {

            super(itemView);
            name = itemView.findViewById(R.id.tvName);
            date = itemView.findViewById(R.id.tvDate);
            startTime = itemView.findViewById(R.id.tvStartTime);
            endTime = itemView.findViewById(R.id.tvEndTime);

        }
    }
}
