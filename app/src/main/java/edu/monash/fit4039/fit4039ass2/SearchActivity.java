package edu.monash.fit4039.fit4039ass2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener{

    //declare variables
    private ListView listView;
    private MonsterAdapter adapter;
    private ArrayList<Monster> monsterList;
    private DatabaseHelper dbHelper;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setTitle("Darwin’s Monster Encyclopaedia");

        //get database handler
        dbHelper = new DatabaseHelper(getApplicationContext());
        monsterList = new ArrayList<>(dbHelper.GetAllMonsters().values());

        searchView = (SearchView) findViewById(R.id.searchView);
        listView = (ListView) findViewById(R.id.monsterListView);

        //create adapter and associate it with monsterList
        adapter = new MonsterAdapter(this, monsterList);
        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(true);

        listView.setOnItemLongClickListener(this);
        UpdateListCount();

        //user click the item on list
        //the system will send the monster object of the clicked item to next page (by intent)
        //when user click the item, it will show the details of this monster
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Monster monster = monsterList.get(position);
                Intent intent = new Intent(SearchActivity.this, ViewActivity.class);
                intent.putExtra("monster", monster);
                startActivity(intent);
            }
        });

        //user input text in search bar
        //if user input text in search bar, the table view will show the items meet the requirement
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText.trim().toLowerCase())){
                    listView.clearTextFilter();
                }else {
                    listView.setFilterText(newText.trim().toLowerCase());
                };
                return false;
            }
        });

    }

    //update the total number of items in the list
    private void UpdateListCount()
    {
        TextView numberTextView = (TextView) findViewById(R.id.numberTextView);
        int numMonster = monsterList.size();
        numberTextView.setText("All monster: " + numMonster);
    }

    //method to delete an item when longclick the item
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l)
    {
        // Build a dialog to delete item
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Remove Person?");
        builder.setMessage("Are you sure you wish to remove this person?");
        builder.setPositiveButton("Delete",
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        // Remove person from list and database
                        Monster monster = monsterList.remove(position);
                        dbHelper.delete(monster.getMonsterName());
                        // Update ListView
                        RefreshListView();
                        Toast.makeText(getBaseContext(), "Monster has been deleted", Toast.LENGTH_SHORT).show();
                    }
                });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                dialogInterface.cancel();
            }
        });
        builder.create().show();
        return false;
    }

    //refresh the table view
    private void RefreshListView()
    {
        adapter.notifyDataSetChanged();
        UpdateListCount();
    }
}
