package com.example.dino.zd4.View;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.dino.zd4.R;
import com.example.dino.zd4.data.FakeDatabase;
import com.example.dino.zd4.model.Task;
import com.example.dino.zd4.model.TaskGenerator;
import com.example.dino.zd4.model.TaskPriority;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import static java.util.Calendar.YEAR;

public class TasksView extends AppCompatActivity {

    public static final int NEW_TASK_REQUEST_CODE = 1;
    public static final String NEW_TASK_RESULT_KEY = "result";


    @BindView(R.id.rv_tasks_view)
    RecyclerView recyclerView;

    @BindView(R.id.fab_tasks_view)
    FloatingActionButton fab;

    @BindView(R.id.togglebutton_tasksview_prikaz)
    ToggleButton tb_tasksview_prikaz;

    @BindView(R.id.spinner_tasksview_sortby)
    Spinner sr_tasksview_sortby;

    private FakeDatabase fakeDatabase;
    private FakeDatabase fakeDatabaseCopy;
    private FakeDatabase fakeDatabaseChanged;

    private LayoutInflater layoutInflater;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasksview);
        ButterKnife.bind(this);

        TaskGenerator taskGenerator = new TaskGenerator();
        fakeDatabase = new FakeDatabase();
        fakeDatabaseCopy = new FakeDatabase();
        fakeDatabaseChanged = new FakeDatabase();
        fakeDatabase.save(taskGenerator.generate(10));

        layoutInflater= getLayoutInflater();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CustomAdapter();
        recyclerView.setAdapter(adapter);
        
        InitOrderingSpinner();


    }

    private void InitOrderingSpinner() {
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.orderby_array, R.layout.support_simple_spinner_dropdown_item);
        sr_tasksview_sortby.setAdapter(adapter);
        sr_tasksview_sortby.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long ID) {
                if(position == 1)
                {
                    int count = 0;
                    CopyDatabase(fakeDatabase,fakeDatabaseCopy);
                    fakeDatabaseChanged.deleteAll();


                    count= fakeDatabaseCopy.count();
                    for(int i = 0; i < count; i++)
                    {
                        if(fakeDatabaseCopy.get(i).getPriority() == R.color.taskpriority_high)
                        {
                            fakeDatabaseChanged.save(fakeDatabaseCopy.get(i));
                            fakeDatabaseCopy.delete(fakeDatabaseCopy.get(i));
                            i--;
                            count= fakeDatabaseCopy.count();
                        }
                    }
                    count= fakeDatabaseCopy.count();
                    for(int i = 0; i < count; i++)
                    {
                        if(fakeDatabaseCopy.get(i).getPriority() == R.color.taskpriority_medium)
                        {
                            fakeDatabaseChanged.save(fakeDatabaseCopy.get(i));
                            fakeDatabaseCopy.delete(fakeDatabaseCopy.get(i));
                            i--;
                            count= fakeDatabaseCopy.count();
                        }
                    }
                    for(int i = 0; i < fakeDatabaseCopy.count(); i++)
                    {
                        fakeDatabaseChanged.save(fakeDatabaseCopy.get(i));
                        fakeDatabaseCopy.delete(fakeDatabaseCopy.get(i));
                    }

                    CopyDatabase(fakeDatabaseChanged,fakeDatabase);
                    adapter.notifyDataSetChanged();

                }
                else if(position == 2)
                {
                    int count = 0;
                    CopyDatabase(fakeDatabase,fakeDatabaseCopy);
                    fakeDatabaseChanged.deleteAll();


                    count= fakeDatabaseCopy.count();
                    for(int i = 0; i < count; i++)
                    {
                        if(fakeDatabaseCopy.get(i).getPriority() == R.color.taskpriority_low)
                        {
                            fakeDatabaseChanged.save(fakeDatabaseCopy.get(i));
                            fakeDatabaseCopy.delete(fakeDatabaseCopy.get(i));
                            i--;
                            count= fakeDatabaseCopy.count();
                        }
                    }
                    count= fakeDatabaseCopy.count();
                    for(int i = 0; i < count; i++)
                    {
                        if(fakeDatabaseCopy.get(i).getPriority() == R.color.taskpriority_medium)
                        {
                            fakeDatabaseChanged.save(fakeDatabaseCopy.get(i));
                            fakeDatabaseCopy.delete(fakeDatabaseCopy.get(i));
                            i--;
                            count= fakeDatabaseCopy.count();
                        }
                    }
                    for(int i = 0; i < fakeDatabaseCopy.count(); i++)
                    {
                        fakeDatabaseChanged.save(fakeDatabaseCopy.get(i));
                        fakeDatabaseCopy.delete(fakeDatabaseCopy.get(i));
                    }

                    CopyDatabase(fakeDatabaseChanged,fakeDatabase);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @OnCheckedChanged(R.id.togglebutton_tasksview_prikaz)
    public void tbPrikazStateChanged()
    {
        CopyDatabase(fakeDatabase,fakeDatabaseCopy);
        fakeDatabaseChanged.deleteAll();

        if(tb_tasksview_prikaz.isChecked())
        {
            for(int i = 0; i < fakeDatabase.count(); i++)
            {
                if(!fakeDatabase.get(i).isCompleted())
                    fakeDatabaseChanged.save(fakeDatabase.get(i));
            }
            CopyDatabase(fakeDatabaseChanged,fakeDatabase);
            adapter.notifyDataSetChanged();
        }
        else
        {
            CopyDatabase(fakeDatabaseCopy,fakeDatabase);
            adapter.notifyDataSetChanged();
        }

    }


    private void CopyDatabase(FakeDatabase copyFrom, FakeDatabase copyTo)
    {
        copyTo.deleteAll();
        for(int i=0; i< copyFrom.count(); i++)
            copyTo.save(copyFrom.get(i));
    }

    @OnClick(R.id.fab_tasks_view)
    public void onFabClick()
    {
        Intent intent = new Intent(this,NewTask.class);
        startActivityForResult(intent,NEW_TASK_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                Task newTask =(Task) data.getExtras().getSerializable(NEW_TASK_RESULT_KEY);
                fakeDatabase.save(newTask);
                adapter.notifyDataSetChanged();
            }
            else {
                    Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
            }
        }
    }
    private class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder>
    {
        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = layoutInflater.inflate(R.layout.item_task,parent,false);
            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CustomViewHolder holder, int position) {

            Task currentTask = fakeDatabase.get(position);

            holder.taskTitle.setText(currentTask.getTitle());
            holder.taskDescription.setText(currentTask.getDescription());
            holder.taskPriority.setBackgroundResource(currentTask.getPriority());

            GregorianCalendar gc = currentTask.getDueDate();
            holder.taskDueDate.setText("Due date: " + gc.get(Calendar.DAY_OF_MONTH) + "/" + gc.get(Calendar.MONTH) + "/" +gc.get(YEAR) );

            if(currentTask.isCompleted())
                holder.taskStatus.setChecked(true);
            else
                holder.taskStatus.setChecked(false);
        }

        @Override
        public int getItemCount() {
            return fakeDatabase.count();
        }

        class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        {
            private ImageView taskPriority;
            private TextView taskTitle;
            private TextView taskDescription;
            private TextView taskDueDate;
            private ToggleButton taskStatus;

            public CustomViewHolder(View itemView) {
                super(itemView);

                this.taskPriority = itemView.findViewById(R.id.imageview_task_priority);
                this.taskTitle = itemView.findViewById(R.id.textview_task_title);
                this.taskDescription = itemView.findViewById(R.id.textview_task_description);
                this.taskDueDate = itemView.findViewById(R.id.textview_task_duedate);
                this.taskStatus = itemView.findViewById(R.id.togglebutton_task_state);

                taskPriority.setOnClickListener(this);
                taskStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(b)
                            fakeDatabase.get(getAdapterPosition()).setCompleted(true);
                        else
                            fakeDatabase.get(getAdapterPosition()).setCompleted(false);
                    }
                });

            }


            @Override
            public void onClick(View view) {
                if(view.getId() == R.id.imageview_task_priority)
                {
                    switch(fakeDatabase.get(getAdapterPosition()).getPriority())
                    {
                        case R.color.taskpriority_low:
                            fakeDatabase.get(getAdapterPosition()).setPriority(TaskPriority.MEDIUM);
                            break;
                        case R.color.taskpriority_medium:
                            fakeDatabase.get(getAdapterPosition()).setPriority(TaskPriority.HIGH);
                            break;
                        case R.color.taskpriority_high:
                            fakeDatabase.get(getAdapterPosition()).setPriority(TaskPriority.LOW);
                            break;
                    }
                    notifyDataSetChanged();
                }
            }
        }
    }
}
