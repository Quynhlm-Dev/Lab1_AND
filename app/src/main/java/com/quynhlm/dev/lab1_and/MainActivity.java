package com.quynhlm.dev.lab1_and;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.quynhlm.dev.lab1_and.Adapter.Todo_Adapter;
import com.quynhlm.dev.lab1_and.Dao.TodoDao;
import com.quynhlm.dev.lab1_and.Model.Todo_Model;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Toolbar toolbar;
    FloatingActionButton fab;

    Todo_Adapter todoAdapter;

    ArrayList<Todo_Model> list = new ArrayList<>();
    TodoDao todoDao;

    EditText edttitle, edtcontact, edtdate, edttype, edtid;
    Button btnSubmit;

    private void minmap() {
        recyclerView = findViewById(R.id.RecyclerView);
        toolbar = findViewById(R.id.toolbar);
        fab = findViewById(R.id.fab_add);
        todoDao = new TodoDao(this);
        list = todoDao.selectAll();
        todoAdapter = new Todo_Adapter(this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(todoAdapter);
    }

    private void SupporToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("TODOLIST");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.popur_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idex = item.getItemId();
        if (idex == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        minmap();
        SupporToolbar();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_add_data();
            }
        });
    }

    private void dialog_add_data() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.add_data, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        edtid = view.findViewById(R.id.edtid);
        edttitle = view.findViewById(R.id.edttitle);
        edtcontact = view.findViewById(R.id.edtcontact);
        edtdate = view.findViewById(R.id.edtdate);
        edttype = view.findViewById(R.id.edttype);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(edtid.getText().toString());
                String title = edttitle.getText().toString();
                String contact = edtcontact.getText().toString();
                String date = edtdate.getText().toString();
                String type = edttype.getText().toString();
                if (title.trim().isEmpty() || contact.trim().isEmpty() || date.trim().isEmpty() || type.trim().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    Todo_Model todo_model = new Todo_Model(id, title, contact, date, type);
                    if (todoDao.insert_data(todo_model)) {
                        Toast.makeText(MainActivity.this, "Add successfully", Toast.LENGTH_SHORT).show();
                        list.add(todo_model);
                        todoAdapter.notifyDataSetChanged();
                        alertDialog.dismiss();
                    } else {
                        Toast.makeText(MainActivity.this, "Add not successfully", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    @Override
    protected void onStart() {
        Log.e("OnStart", "onStart: " );
        super.onStart();
    }
}