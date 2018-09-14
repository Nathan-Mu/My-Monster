package edu.monash.fit4039.fit4039ass2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewActivity extends AppCompatActivity {

    //declare variables
    private Monster monster;
    TextView nameTextView;
    TextView ageTextView;
    TextView speciesTextView;
    TextView healthTextView;
    TextView attackPowerTextView;
    TextView attackValueTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        //set title
        setTitle("Darwin’s Monster Encyclopaedia");

        //set up intent
        Intent intent = getIntent();
        //get monster object from last view by using parcelable
        monster = intent.getParcelableExtra("monster");

        //set text view
        nameTextView = (TextView) findViewById(R.id.nameView);
        ageTextView = (TextView) findViewById(R.id.ageView);
        speciesTextView = (TextView) findViewById(R.id.speciesView);
        attackPowerTextView = (TextView) findViewById(R.id.attackPowerView);
        healthTextView = (TextView) findViewById(R.id.healthView);
        attackValueTextView = (TextView) findViewById(R.id.attackValueView);

        //set text to text view
        nameTextView.setText(monster.getMonsterName());
        ageTextView.setText("Age: " + monster.getAge());
        speciesTextView.setText("Species: " + monster.getSpecies());
        attackPowerTextView.setText("Attack Power: " + monster.getAttackPower());
        healthTextView.setText("Health: " + monster.getHealth());
        attackValueTextView.setText("Attack Value: " + monster.getAttackValue());
    }
}
