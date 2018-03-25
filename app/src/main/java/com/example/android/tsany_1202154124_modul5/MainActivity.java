package com.example.android.tsany_1202154124_modul5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;



import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Database dtBase;
    private RecyclerView rcView;
    private Adapter adapter;
    private ArrayList<AddData> data_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("To Do List App");



        rcView = findViewById(R.id.rec_view);

        data_list = new ArrayList<>();

        dtBase = new Database(this);

        dtBase.readdata(data_list);


        SharedPreferences sharedP = this.getApplicationContext().getSharedPreferences("Preferences", 0);
        int color = sharedP.getInt("Colourground", R.color.white);


        adapter = new Adapter(this,data_list, color);

        rcView.setHasFixedSize(true);
        rcView.setLayoutManager(new LinearLayoutManager(this));

        rcView.setAdapter(adapter);


        hapusgeser();
    }


    public void hapusgeser(){

        ItemTouchHelper.SimpleCallback touchcall = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                AddData current = adapter.getData(position);

                if(direction==ItemTouchHelper.LEFT){

                    if(dtBase.removedata(current.getTodo())){

                        adapter.deleteData(position);

                        Snackbar.make(findViewById(R.id.coordinator), "List Telah Terhapus", 2000).show();
                    }
                }
            }
        };

        ItemTouchHelper touchhelp = new ItemTouchHelper(touchcall);
        touchhelp.attachToRecyclerView(rcView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id==R.id.action_settings){

            Intent intent = new Intent(MainActivity.this, Settings.class);

            startActivity(intent);

            finish();
        }
        return true;
    }


    public void addlist(View view) {

        Intent intent = new Intent(MainActivity.this, AddToDo.class);

        startActivity(intent);
    }
}