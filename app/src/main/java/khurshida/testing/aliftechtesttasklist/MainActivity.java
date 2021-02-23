package khurshida.testing.aliftechtesttasklist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Initialize variables
    EditText editTextTaskName, editTextTaskDeadline;
    Button btnAdd;
    RecyclerView recyclerView;

    List<MainData> dataList=new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
System.out.println("HERE");
        //Assign variables
        editTextTaskName = findViewById(R.id.edit_text_task_name);
        editTextTaskDeadline = findViewById(R.id.edit_text_task_deadline);
        btnAdd = findViewById(R.id.btn_add);
        recyclerView=findViewById(R.id.recycle_view);

        database=RoomDB.getInstance(this);
        dataList=database.mainDao().getAll();

        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mainAdapter=new MainAdapter(MainActivity.this, dataList);
        recyclerView.setAdapter(mainAdapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sTaskName = editTextTaskName.getText().toString().trim();
                String sTaskDeadline = editTextTaskDeadline.getText().toString().trim();
                if (!sTaskName.equals("")&&!sTaskDeadline.equals("")){
                    MainData data = new MainData(sTaskName,sTaskDeadline);
                    data.setTaskName(sTaskName);
                    data.setTaskDeadline(sTaskDeadline);
                    database.mainDao().insert(data);
                    editTextTaskName.setText("");
                    editTextTaskDeadline.setText("");
                    dataList.clear();
                    dataList.addAll(database.mainDao().getAll());
                    mainAdapter.notifyDataSetChanged();
                }
            }
        });


    }
}