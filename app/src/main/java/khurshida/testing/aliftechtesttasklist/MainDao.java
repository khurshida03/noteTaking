package khurshida.testing.aliftechtesttasklist;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Delete;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MainDao {

    //Insert query
    @Insert(onConflict = REPLACE)
    void insert(MainData mainData);

    //Delete query
    @Delete
    void delete(MainData mainData);

    //Update query
    @Query("UPDATE table_name SET task_name= :sTaskName, task_deadline=:sTaskDeadline WHERE ID=:sID")
    void update(int sID, String sTaskName, String sTaskDeadline);

    //Get all data query
    @Query("SELECT * FROM table_name")
    List<MainData> getAll();

}
