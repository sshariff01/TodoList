package com.six.the.in.todolist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    ArrayAdapter<String> arrayAdapter;
    List<String> adapterList;
    ListView listView;
    Button newTaskButton;
    String listItem;

    static final int ADD_TASK = 1;  // The request code
    static final int EDIT_TASK = 2;  // The request code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapterList = new ArrayList<String>();
        listView = (ListView) findViewById(R.id.list_view);

        arrayAdapter = new ArrayAdapter<String>(
                this,
                R.layout.list_element,
                R.id.element_text,
                adapterList
        );

        listView.setAdapter(arrayAdapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                listItem = adapterList.get(pos).toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Delete Item?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                adapterList.remove(listItem);
                                arrayAdapter = new ArrayAdapter<String>(
                                        MainActivity.this,
                                        R.layout.list_element,
                                        R.id.element_text,
                                        adapterList
                                );
                                listView.setAdapter(arrayAdapter);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) { }
                        }).show();

                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, final int pos, long arg3) {
                listItem = adapterList.get(pos).toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Edit Item?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(MainActivity.this, ModifyTaskListActivity.class);
                                intent.putExtra("itemToEdit", listItem);
                                intent.putExtra("pos", pos);
                                startActivityForResult(intent, EDIT_TASK);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) { }
                        }).show();
            }
        });

//        newTaskButton = (Button) findViewById(R.id.new_task_button);
//        newTaskButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, ModifyTaskListActivity.class);
//                startActivityForResult(intent, ADD_TASK);
//            }
//        });

    }

    public void onCheckboxClicked(View view) {
        if (((CheckBox) view).isChecked()) {
            Toast.makeText(MainActivity.this, "Task Complete!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        switch(requestCode) {
            case ADD_TASK:
                // Make sure the request was successful
                if (resultCode == RESULT_OK) {
                    adapterList.add(data.getStringExtra("taskName"));
                    arrayAdapter = new ArrayAdapter<String>(
                            MainActivity.this,
                            R.layout.list_element,
                            R.id.element_text,
                            adapterList
                    );
                    listView.setAdapter(arrayAdapter);
                } else {
                    Toast.makeText(MainActivity.this, "Please input a valid task name", Toast.LENGTH_SHORT).show();
                }
                break;
            case EDIT_TASK:
                // Make sure the request was successful
                if (resultCode == RESULT_OK) {
                    if (data.getIntExtra("position", -1) >= 0) {
                        adapterList.set(data.getIntExtra("position", -1), data.getStringExtra("taskName"));
                    }
                    arrayAdapter = new ArrayAdapter<String>(
                            MainActivity.this,
                            R.layout.list_element,
                            R.id.element_text,
                            adapterList
                    );
                    listView.setAdapter(arrayAdapter);
                }
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        } else if (id == R.id.action_new_task) {
            Intent intent = new Intent(MainActivity.this, ModifyTaskListActivity.class);
            startActivityForResult(intent, ADD_TASK);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
