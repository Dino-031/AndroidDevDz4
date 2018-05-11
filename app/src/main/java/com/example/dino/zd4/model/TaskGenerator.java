package com.example.dino.zd4.model;

import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

/**
 * Created by Dino on 11/05/2018.
 */

public class TaskGenerator {

    private static final Random generator = new Random();

    private static String[] titles = {
            "Osc", "Potato", "Homework", "Shopping", "Gaming", "Reading",
            "Android", "Studying", "Question", "Ideas", "Party", "Nothing",
    };

    private static String[] descriptions = {
            "Do it.", "Play it", "Drink it", "Answer it", "Shut it down",
            "Try it.", "Don't do it.", "Ignore it.",
    };

    public static List<Task> generate(int taskCount){
        List<Task> tasks = new ArrayList<>();
        for(int i=0; i < taskCount; i++)
        {
            String title = titles[generator.nextInt(titles.length)];
            String description = descriptions[generator.nextInt(descriptions.length)];

            int prioritySelector = generator.nextInt(TaskPriority.values().length);
            TaskPriority priority = TaskPriority.values()[prioritySelector];

            GregorianCalendar gc = new GregorianCalendar();
            int year = randBetween(Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.YEAR) + 1);
            gc.set(gc.YEAR,year);
            int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
            gc.set(gc.DAY_OF_YEAR, dayOfYear);

            tasks.add(new Task(title, description, priority, gc));
        }

        return tasks;
    }
    public static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }
}
