package com.example.dino.zd4.View;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dino.zd4.R;
import com.example.dino.zd4.data.FakeDatabase;
import com.example.dino.zd4.model.Task;
import com.example.dino.zd4.model.TaskPriority;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewTask extends AppCompatActivity {

    private static final int RESULT_CODE_SUCCESS = 0;

    @BindView(R.id.edittext_newtask_title)
    EditText et_newtask_title;

    @BindView(R.id.edittext_newtask_description)
    EditText et_newtask_description;

    @BindView(R.id.spinner_newtask_priority)
    Spinner sr_newtask_priority;

    @BindView(R.id.imagebutton_newtask_savetask)
    ImageButton ib_newtast_savetask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        ButterKnife.bind(this);

        InitSpinner();
    }



    private void InitSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.priority_array,R.layout.support_simple_spinner_dropdown_item);
        sr_newtask_priority.setAdapter(adapter);
    }

    @OnClick(R.id.imagebutton_newtask_savetask)
    public void ibSaveTaskClick()
    {
        if(et_newtask_description.getText().toString().isEmpty() || et_newtask_title.getText().toString().isEmpty())
            Toast.makeText(this,"Please fill all forms",Toast.LENGTH_SHORT).show();
        else
        {
            Task newTask = null;
            switch (sr_newtask_priority.getSelectedItem().toString())
            {
                case "LOW":
                    newTask = new Task(et_newtask_title.getText().toString(),et_newtask_description.getText().toString(),TaskPriority.LOW);
                    break;
                case "MEDIUM":
                    newTask = new Task(et_newtask_title.getText().toString(),et_newtask_description.getText().toString(),TaskPriority.MEDIUM);
                    break;
                case "HIGH":
                    newTask =  newTask = new Task(et_newtask_title.getText().toString(),et_newtask_description.getText().toString(),TaskPriority.HIGH);
                    break;
            }
            Intent resultIntent = new Intent();
            resultIntent.putExtra(TasksView.NEW_TASK_RESULT_KEY,newTask);
            setResult(Activity.RESULT_OK,resultIntent);
            finish();
        }
    }
}
