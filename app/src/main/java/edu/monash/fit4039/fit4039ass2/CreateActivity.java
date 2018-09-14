package edu.monash.fit4039.fit4039ass2;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CreateActivity extends AppCompatActivity {

    //declare variables
    private Monster monster;
    private EditText nameET;
    private EditText ageET;
    private EditText speciesET;
    private EditText attackPowerET;
    private EditText healthET;
    private TextView statusTextView;
    private TextView statusTextView2;
    private Button updateButton;
    private Button saveButton;
    private DatabaseHelper dbHelper;
    Boolean canUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        //set title of the page
        setTitle("Darwin’s Monster Encyclopaedia");
        //set a new monster
        monster = new Monster();
        //set database helper
        dbHelper = new DatabaseHelper(this);

        //set edittext, textview and button
        nameET = (EditText) findViewById(R.id.nameET);
        ageET = (EditText) findViewById(R.id.ageET);
        speciesET = (EditText) findViewById(R.id.speciesET);
        attackPowerET = (EditText) findViewById(R.id.attackPowerET);
        healthET = (EditText) findViewById(R.id.healthET);
        statusTextView = (TextView) findViewById(R.id.statusTextView);
        statusTextView2 = (TextView) findViewById(R.id.statusTextView2);
        updateButton = (Button) findViewById(R.id.updateButton);
        saveButton = (Button) findViewById(R.id.saveButton);
        canUpdate = false;

        //user click save button
        //when user click the save button, their input will be verified (by another method).
        //If there is something invalid, the system will show a error message on the screen
        //If the input is valid and the monster does not exist, the monster object will be set and the value of attributes will be stored in database
        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (setMonster() != null) {
                    if (dbHelper.select(nameET.getText().toString().trim()) != null)
                        Toast.makeText(getApplication(), "Monster exists", Toast.LENGTH_SHORT).show();
                    else {
                        monster = setMonster();
                        //set text to text view to show status
                        statusTextView.setText("Insert successfully");
                        statusTextView2.setText(monster.getSummary());
                        ContentValues values = getValues(monster);
                        //store attribute value in database (insert a row)
                        dbHelper.insert(values);
                        canUpdate = true;
                    }
                }
            }
        });

        //user click update button
        //like clicking the save button, system will also verify the user input
        //system will find the row in database by the name of last inserted monster
        //in this step, user only can modified the last inserted monster (not exist the page)
        updateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String oldName = monster.getMonsterName();
                if (setMonster() != null)
                {
                    if (!canUpdate)
                        Toast.makeText(getApplication(), "Create a monster firstly", Toast.LENGTH_SHORT).show();
                    else {
                        monster = setMonster();
                        setMonster();
                        //set text to text view to show status
                        statusTextView.setText("Update Successfully");
                        statusTextView2.setText(monster.getSummary());
                        ContentValues values = getValues(monster);
                        //update in database
                        dbHelper.update(values, Monster.COLUMN_NAME + " = ?", new String[]{oldName});
                    }
                }
            }
        });
    }

    //get content values by a given monster
    //the content values will be inserted in database
    public ContentValues getValues (Monster monster)
    {
        ContentValues values = new ContentValues();
        values.put(Monster.COLUMN_NAME, monster.getMonsterName());
        values.put(Monster.COLUMN_AGE, monster.getAge());
        values.put(Monster.COLUMN_SPECIES, monster.getSpecies());
        values.put(Monster.COLUMN_ATTACKPOWER, monster.getAttackPower());
        values.put(Monster.COLUMN_HEALTH, monster.getHealth());
        return values;
    }

    //set a new monster object which contains user input information.
    //in this method, system will check the user input
    //the validation specification is written in readme file
    private Monster setMonster()
    {
        //Check input is valid
        //check name
        if (nameET.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplication(), "Empty name", Toast.LENGTH_SHORT).show();
            return null;
        }
        else
            monster.setMonsterName(nameET.getText().toString().trim());

        //check age
        String age = ageET.getText().toString().trim();
        if (age.isEmpty()) {
            Toast.makeText(getApplication(), "Empty age", Toast.LENGTH_SHORT).show();
            return null;
        }
        else
        {
            if (!isNumeric(age)) {
                Toast.makeText(getApplication(), "Put an integer in age", Toast.LENGTH_SHORT).show();
                return null;
            }
            else
            {
                if (Integer.valueOf(age) < 0) {
                    Toast.makeText(getApplication(), "Age should be larger than 0", Toast.LENGTH_SHORT).show();
                    return null;
                }
                else
                {
                    if(Integer.valueOf(age) > 10000)
                        Toast.makeText(getApplication(), "Maximum age is 10,000", Toast.LENGTH_SHORT).show();
                    else
                        monster.setAge(new Integer(age));
                }

            }
        }

        //check species
        if (speciesET.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplication(), "Empty name", Toast.LENGTH_SHORT).show();
            return null;
        }
        else
            monster.setSpecies(speciesET.getText().toString().trim());

        //check attackPower
        String attackPower = attackPowerET.getText().toString().trim();
        if (attackPower.isEmpty()) {
            Toast.makeText(getApplication(), "Empty attack power", Toast.LENGTH_SHORT).show();
            return null;
        }
        else
        {
            if (!isNumeric(attackPower)) {
                Toast.makeText(getApplication(), "Put an integer in attack power", Toast.LENGTH_SHORT).show();
                return null;
            }
            else
            {
                if (Integer.valueOf(attackPower) < 0) {
                    Toast.makeText(getApplication(), "Attack power should be larger than 0", Toast.LENGTH_SHORT).show();
                    return null;
                }
                else
                    if(Integer.valueOf(attackPower) > 10000)
                        Toast.makeText(getApplication(), "Maximum attack power is 10,000", Toast.LENGTH_SHORT).show();
                    else
                        monster.setAttackPower(new Integer(attackPower));
            }
        }

        //check health
        String health = healthET.getText().toString().trim();
        if (health.isEmpty()) {
            Toast.makeText(getApplication(), "Empty health", Toast.LENGTH_SHORT).show();
            return null;
        }
        else
        {
            if (!isNumeric(health)) {
                Toast.makeText(getApplication(), "Put an integer in health", Toast.LENGTH_SHORT).show();
                return null;
            }
            else
            {
                if (Integer.valueOf(health) < 0) {
                    Toast.makeText(getApplication(), "Health should be larger than 0", Toast.LENGTH_SHORT).show();
                    return null;
                }
                else
                    if(Integer.valueOf(health) > 100000)
                        Toast.makeText(getApplication(), "Maximum health is 100,000", Toast.LENGTH_SHORT).show();
                    else
                        monster.setHealth(new Integer(health));
            }
        }
        return monster;
    }

    //check a string is numeric
    private boolean isNumeric(String number)
    {
        for (int i = 0; i < number.length(); i++)
        {
            if (!Character.isDigit(number.charAt(i)))
                return false;
        }
        return true;
    }
}
