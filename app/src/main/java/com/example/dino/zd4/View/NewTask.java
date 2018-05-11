package com.example.dino.zd4.View;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dino.zd4.R;
import com.example.dino.zd4.data.FakeDatabase;
import com.example.dino.zd4.model.Task;
import com.example.dino.zd4.model.TaskPriority;

import java.util.Calendar;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewTask extends AppCompatActivity  {

    private static final int RESULT_CODE_SUCCESS = 0;

    private DatePickerDialog.OnDateSetListener dateSetListener;

    private GregorianCalendar gc_dueDate;

    @BindView(R.id.edittext_newtask_title)
    EditText et_newtask_title;

    @BindView(R.id.edittext_newtask_description)
    EditText et_newtask_description;

    @BindView(R.id.spinner_newtask_priority)
    Spinner sr_newtask_priority;

    @BindView(R.id.imagebutton_newtask_savetask)
    ImageButton ib_newtast_savetask;

    @BindView(R.id.button_newtask_pickdate)
    Button btn_newtask_pickdate;

    @BindView(R.id.textview_newtask_duedate)
    TextView tv_newtask_duedate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        ButterKnife.bind(this);

        InitSpinner();
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                gc_dueDate = new GregorianCalendar(year,month,day);
                tv_newtask_duedate.setText(day + "/" +month + "/" + year);
            }
        };
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
                    newTask = new Task(et_newtask_title.getText().toString(),et_newtask_description.getText().toString(),TaskPriority.LOW,gc_dueDate);
                    break;
                case "MEDIUM":
                    newTask = new Task(et_newtask_title.getText().toString(),et_newtask_description.getText().toString(),TaskPriority.MEDIUM, gc_dueDate);
                    break;
                case "HIGH":
                    newTask =  newTask = new Task(et_newtask_title.getText().toString(),et_newtask_description.getText().toString(),TaskPriority.HIGH,gc_dueDate);
                    break;
            }
            Intent resultIntent = new Intent();
            resultIntent.putExtra(TasksView.NEW_TASK_RESULT_KEY,newTask);
            setResult(Activity.RESULT_OK,resultIntent);
            finish();
        }
    }


    @OnClick(R.id.button_newtask_pickdate)
    public void btnPickdateClick()
    {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(this,R.style.Theme_AppCompat_DayNight,dateSetListener,year,month,day);
        dialog.getDatePicker().setMinDate(Calendar.MILLISECOND);
        dialog.show();
    }




}
