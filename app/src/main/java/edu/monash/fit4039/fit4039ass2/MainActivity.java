package edu.monash.fit4039.fit4039ass2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //declare variables
    Button createButton;
    Button searchButton;
    Button viewButton;
    public static final int ADD_MONSTER_REQUEST = 1;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set title of the page
        setTitle("Darwin’s Monster Encyclopaedia");

        //set up buttons
        createButton = (Button) findViewById(R.id.createButton);
        searchButton = (Button) findViewById(R.id.searchButton);
        viewButton = (Button) findViewById(R.id.viewButton);

        //set up database helper
        dbHelper = new DatabaseHelper(getApplicationContext());

        //method when click create button
        //jump to a new view to create monster
        createButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, CreateActivity.class);
                startActivity(intent);
            }
        });

        //method when click search button
        //jump to a new view to search monsters
        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        //method when click view button
        //jump to a new view to view monster
        viewButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Monster monster = dbHelper.getLastMonster();
                if (monster == null)
                    Toast.makeText(getApplication(), "No monster created", Toast.LENGTH_SHORT).show();
                else {
                    Intent intent = new Intent(MainActivity.this, ViewActivity.class);
                    intent.putExtra("monster", monster);
                    startActivity(intent);
                }
            }
        });
    }
}
