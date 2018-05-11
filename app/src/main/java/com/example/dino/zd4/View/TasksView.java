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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dino.zd4.R;
import com.example.dino.zd4.data.FakeDatabase;
import com.example.dino.zd4.model.Task;
import com.example.dino.zd4.model.TaskGenerator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TasksView extends AppCompatActivity {

    public static final int NEW_TASK_REQUEST_CODE = 1;
    public static final String NEW_TASK_RESULT_KEY = "result";


    @BindView(R.id.rv_tasks_view)
    RecyclerView recyclerView;

    @BindView(R.id.fab_tasks_view)
    FloatingActionButton fab;

    private FakeDatabase fakeDatabase;

    private LayoutInflater layoutInflater;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasksview);
        ButterKnife.bind(this);

        TaskGenerator taskGenerator = new TaskGenerator();
        fakeDatabase = new FakeDatabase();
        fakeDatabase.save(taskGenerator.generate(10));

        layoutInflater= getLayoutInflater();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CustomAdapter();
        recyclerView.setAdapter(adapter);
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
            private ViewGroup container;

            public CustomViewHolder(View itemView) {
                super(itemView);

                this.taskPriority = itemView.findViewById(R.id.imageview_task_priority);
                this.taskTitle = itemView.findViewById(R.id.textview_task_title);
                this.taskDescription = itemView.findViewById(R.id.textview_task_description);
                this.container = itemView.findViewById(R.id.root_rl_task);

                container.setOnClickListener(this);

            }


            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"hi",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
