package khurshida.testing.aliftechtesttasklist;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<MainData> dataList;
    private Activity context;
    private RoomDB database;

    public MainAdapter(Activity context, List<MainData> dataList){
        this.context=context;
        this.dataList=dataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate((R.layout.list_row_main), parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        MainData data = dataList.get(position);
        database=RoomDB.getInstance(context);
        holder.textViewTaskDeadline.setText(data.getTaskDeadline());
        holder.textViewTaskName.setText(data.getTaskName());
        holder.btnEditTask.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                MainData d = dataList.get(holder.getAdapterPosition());
                int sID=d.getID();
                String sTaskName=d.getTaskName();
                String sTaskDeadline=d.getTaskDeadline();
                Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.dialog_update);

                int width= WindowManager.LayoutParams.MATCH_PARENT;
                int height=WindowManager.LayoutParams.WRAP_CONTENT;

                dialog.getWindow().setLayout(width,height);
                dialog.show();

                EditText editTextTaskName=dialog.findViewById(R.id.edit_text_task_name);
                EditText editTextTaskDeadline=dialog.findViewById(R.id.edit_text_task_deadline);
                Button btnUpdate = dialog.findViewById(R.id.btnUpdate);

                editTextTaskDeadline.setText(sTaskDeadline);
                editTextTaskName.setText(sTaskName);

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        String uTextTaskName=editTextTaskName.getText().toString().trim();
                        String uTextTaskDeadline=editTextTaskDeadline.getText().toString().trim();
                        database.mainDao().update(sID,uTextTaskName,uTextTaskDeadline);
                        dataList.clear();
                        dataList.addAll(database.mainDao().getAll());
                        notifyDataSetChanged();
                    }
                });

            }
        });
        holder.btnDeleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainData d=dataList.get(holder.getAdapterPosition());
                database.mainDao().delete(d);
                int position = holder.getAdapterPosition();
                dataList.remove(position);
                notifyItemRangeChanged(position,dataList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTaskName, textViewTaskDeadline;
        ImageView btnEditTask, btnDeleteTask;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTaskName=itemView.findViewById(R.id.text_view_task_name);
            textViewTaskDeadline=itemView.findViewById(R.id.text_view_task_deadline);
            btnDeleteTask=itemView.findViewById(R.id.btn_delete_task);
            btnEditTask=itemView.findViewById(R.id.btn_edit_task);
        }
    }
}
