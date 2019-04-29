package ie.dcu.computing.student.lenehan4.mystoremanager.AdapterClasses;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

import ie.dcu.computing.student.lenehan4.mystoremanager.EmployeeModelClass;
import ie.dcu.computing.student.lenehan4.mystoremanager.R;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{


    String TAG = MyAdapter.class.getSimpleName();

    List<EmployeeModelClass> list;

    FirebaseStorage mStorageReference;
    public MyAdapter() {
        this.list = new ArrayList<>();
        mStorageReference = FirebaseStorage.getInstance();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_eletions_layout, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        EmployeeModelClass employeeModelClass = list.get(i);
        myViewHolder.numberOfVotes.setText(employeeModelClass.getVotes());
        myViewHolder.employee_id.setText(employeeModelClass.getEmployee_id());
        myViewHolder.name.setText(employeeModelClass.getName());
        myViewHolder.dept.setText(employeeModelClass.getDept());

        Log.d(TAG, "onBindViewHolder: "+mStorageReference.getReference().getPath());

        Log.d(TAG, "onBindViewHolder: "+employeeModelClass.getImage());
        Glide.with(myViewHolder.user_img.getContext())
                .load(employeeModelClass.getImage())
                .placeholder(R.drawable.profile)
                .into(myViewHolder.user_img);


    }


    public void setList(List<EmployeeModelClass> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView user_img, delete;
        TextView name, employee_id, dept, numberOfVotes;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            user_img = itemView.findViewById(R.id.item_elections_img);
            delete = itemView.findViewById(R.id.item_elections_delete);
            name = itemView.findViewById(R.id.item_elections_name);
            dept = itemView.findViewById(R.id.item_elections_dept);
            employee_id = itemView.findViewById(R.id.item_elections_emplyee_id);
            numberOfVotes = itemView.findViewById(R.id.item_elecions_votes);
            delete.setOnClickListener(delete_listener);
        }


        View.OnClickListener delete_listener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                EmployeeModelClass employeeModelClass = list.get(getAdapterPosition());
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("electionParticipants")
                        .document(employeeModelClass.getName())
                        .delete();
                list.remove(getAdapterPosition());
                notifyDataSetChanged();
            }
        };
    }
}
