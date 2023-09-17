package Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_quan_l_danhsach.R;

import java.util.List;

import model.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.userViewHolDer> {
    private List<User> mUserList;

    private undatedata mUndatedata;

    public UserAdapter(undatedata mUndatedata) {
        this.mUndatedata = mUndatedata;
    }

    public interface undatedata{
        void clickupdate(User user);
        void deletedata(User user);
    }

    public void setdata(List<User> list){
        this.mUserList=list;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public userViewHolDer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,parent,false);

        return new userViewHolDer(view);
    }

    @Override
    public void onBindViewHolder(@NonNull userViewHolDer holder, int position) {

        User user=mUserList.get(position);

        if(user==null){
            return;
        }
        holder.edtname.setText(user.getName());
        holder.edtaddress.setText(user.getAddress());
        holder.edtyear.setText(user.getYear());
        holder.btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           mUndatedata.clickupdate(user);

            }
        });
        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUndatedata.deletedata(user);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(mUserList!=null){
            return mUserList.size();
        }
        return 0;
    }

    public class userViewHolDer extends RecyclerView.ViewHolder {
        private EditText edtname,edtaddress,edtyear;
        private Button btnupdate,btndelete;
        public userViewHolDer(@NonNull View itemView) {
            super(itemView);
            edtname=(EditText) itemView.findViewById(R.id.edtname);
            edtaddress=(EditText) itemView.findViewById(R.id.edtaddress);
            edtyear=(EditText) itemView.findViewById(R.id.edtyear);
            btnupdate=(Button) itemView.findViewById(R.id.btnupdate);
            btndelete=(Button) itemView.findViewById(R.id.btndelete);
        }
    }
}
