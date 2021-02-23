package khurshida.testing.aliftechtesttasklist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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

        //Assign variables
        editTextTaskName = findViewById(R.id.edit_text_task_name);
        editTextTaskDeadline = findViewById(R.id.edit_text_task_deadline);
        btnAdd = findViewById(R.id.btn_add);
        recyclerView=findViewById(R.id.recycle_view);

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_nav_view);
        AllFragment allFragment=new AllFragment();
        CompletedFragment completedFragment=new CompletedFragment();
        InProgressFragment inProgressFragment=new InProgressFragment();

        database=RoomDB.getInstance(this);
        dataList=database.mainDao().getAll();

        linearLayoutManager=new LinearLayoutManager(this);

        btnAdd.setOnClickListener(view -> {
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
                allFragment.updateListView();
            }
        });



        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id==R.id.all_frag){
                setFragment(allFragment);
                return true;
            } else if (id==R.id.completed_frag){
                setFragment(completedFragment);
                return true;
            }else if (id==R.id.in_progress_frag){
                setFragment(inProgressFragment);
                return true;
            }
            return false;
        });
        bottomNavigationView.setSelectedItemId(R.id.all_frag);
    }
    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flFragment, fragment);
        fragmentTransaction.commit();
    }

}