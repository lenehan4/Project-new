package ie.dcu.computing.student.lenehan4.mystoremanager.Rota;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;



import ie.dcu.computing.student.lenehan4.mystoremanager.R;

public class EmployeeRotaAdapter extends RecyclerView.Adapter<EmployeeRotaAdapter.MyViewHolder> implements Filterable {

    Context context;
    ArrayList<Shifts> shifts;
    private ArrayList<Shifts> shiftsFull;

    public EmployeeRotaAdapter(Context c)
    {
        context = c;
        shifts = new ArrayList<>();
        shiftsFull = new ArrayList<>(shifts);
    }

    public void setShifts(ArrayList<Shifts> shifts) {
        this.shifts = shifts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.shift_view,viewGroup,false));
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




    @Override
    public Filter getFilter() {
        return shiftFilter;
    }

    private Filter shiftFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Shifts> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0){
                filteredList.addAll(shiftsFull);
            }
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Shifts item : shiftsFull){
                    if (item.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            shifts.clear();
            shifts.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };




    class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView name, date,startTime, endTime;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvName);
            date = itemView.findViewById(R.id.tvDate);
            startTime = itemView.findViewById(R.id.tvStartTime);
            endTime = itemView.findViewById(R.id.tvEndTime);
        }
    }
}
