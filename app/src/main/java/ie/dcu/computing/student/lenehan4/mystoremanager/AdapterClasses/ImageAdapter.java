package ie.dcu.computing.student.lenehan4.mystoremanager.AdapterClasses;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ie.dcu.computing.student.lenehan4.mystoremanager.FirebaseClasses.Upload;
import ie.dcu.computing.student.lenehan4.mystoremanager.R;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Context mcontext;
    private List<Upload> mupload;

    public ImageAdapter(Context context, List<Upload> uploads){
        mcontext = context;
        mupload = uploads;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.winners, viewGroup, false);
        return new ImageViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder imageViewHolder, int i) {
        Upload uploadCurrent = mupload.get(i);
        imageViewHolder.month.setText(uploadCurrent.getMonth());
        Picasso.get().load(uploadCurrent.getImageUri())
                .placeholder(R.mipmap.ic_launcher)
                .fit().centerCrop().into(imageViewHolder.imageView);

    }

    @Override
    public int getItemCount() {
        return mupload.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public TextView month;
        public ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            month = itemView.findViewById(R.id.tvMonth);
            imageView = itemView.findViewById(R.id.ivWinner);
        }
    }
}
