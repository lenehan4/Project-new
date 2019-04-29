package ie.dcu.computing.student.lenehan4.mystoremanager.AdapterClasses;

import android.app.backup.SharedPreferencesBackupHelper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ie.dcu.computing.student.lenehan4.mystoremanager.EmployeeModelClass;
import ie.dcu.computing.student.lenehan4.mystoremanager.R;
import ie.dcu.computing.student.lenehan4.mystoremanager.SharedPreferenceHelper;

public class CandidatesAdapter extends RecyclerView.Adapter<CandidatesAdapter.MyViewHolder> {




    List<EmployeeModelClass> list;
    String TAG = CandidatesAdapter.class.getSimpleName();


    public CandidatesAdapter(List<EmployeeModelClass> list) {
        this.list = list;
    }

    public CandidatesAdapter() {
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_vote_candidate, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        EmployeeModelClass employeeModelClass = list.get(i);
        myViewHolder.name.setText(employeeModelClass.getName());
        myViewHolder.dept.setText(employeeModelClass.getDept());

        Glide.with(myViewHolder.profile.getContext()).load(employeeModelClass.getImage())
                .placeholder(R.drawable.profile)
                .into(myViewHolder.profile);


    }

    public void setList(List<EmployeeModelClass> list) {

        Log.d(TAG, "setList: before"+this.list.size());
        this.list = list;
        Log.d(TAG, "setList: after"+this.list.size());
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView profile, vote;
        TextView name, dept;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            profile = itemView.findViewById(R.id.item_vote_profile_img);
            vote = itemView.findViewById(R.id.item_vote_img);
            name = itemView.findViewById(R.id.item_vote_name);
            dept = itemView.findViewById(R.id.item_vote_dept);

            vote.setOnClickListener(vote_listener);

        }




        View.OnClickListener vote_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final SharedPreferenceHelper sharedPreferenceHelper = SharedPreferenceHelper.getSharedPreferenceHelper(v.getContext());

                if (sharedPreferenceHelper.getWhenUserPutVote() == Calendar.getInstance().get(Calendar.MONTH)) {
                    showToast("You already voted");
                    return;
                }

                final EmployeeModelClass employeeModelClass = list.get(getAdapterPosition());
                Log.d(TAG, "onClick: vote given");


                FirebaseFirestore db = FirebaseFirestore.getInstance();
                final DocumentReference documentReference = db.collection("electionParticipants")
                        .document(employeeModelClass.getName());



                db.runTransaction(new Transaction.Function<Object>() {
                    @Nullable
                    @Override
                    public Object apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {


                        DocumentSnapshot snapshot = transaction.get(documentReference);

                        int votes = Integer.parseInt(snapshot.get("votes").toString());
                        votes++;


                        transaction.update(documentReference, "votes", ""+votes);
                        sharedPreferenceHelper.userSavedVote();
                        return null;
                    }
                }).addOnCompleteListener(new OnCompleteListener<Object>() {

                    @Override
                    public void onComplete(@NonNull Task<Object> task) {

                        if(task.isSuccessful()){
                            showToast("Vote given");
                        }
                    }
                });
            }
        };


        public void showToast(String text){
            Toast.makeText(itemView.getContext(),  text, Toast.LENGTH_SHORT).show();
        }
    }



}
