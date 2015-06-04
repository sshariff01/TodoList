package com.six.the.in.todolist;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class ModifyTaskListActivity extends ActionBarActivity {
    Button addButton;
    EditText taskToModify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_task_list);

        addButton = (Button) findViewById(R.id.add_button);
        taskToModify = (EditText) findViewById(R.id.new_task);
        if (
                getIntent().getStringExtra("itemToEdit") != null
                && !getIntent().getStringExtra("itemToEdit").isEmpty()
                ) {
            taskToModify.setText(getIntent().getStringExtra("itemToEdit").toString());
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String taskName = taskToModify.getText().toString();

                if (taskName == null || taskName.equals("")) finish();

                getIntent().putExtra("taskName", taskName);
                getIntent().putExtra("position", getIntent().getIntExtra("pos", -1));

                setResult(RESULT_OK, getIntent());
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}
