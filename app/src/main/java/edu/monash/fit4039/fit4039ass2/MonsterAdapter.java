package edu.monash.fit4039.fit4039ass2;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nathan on 8/4/17.
 */

public class MonsterAdapter extends BaseAdapter implements Filterable
{
    private Context currentContext;
    MyFilter mFilter;
    private ArrayList<Monster> monsterList;
    private List<Monster> filterList;

    public MonsterAdapter(Context con, ArrayList<Monster> monsters)
    {
        currentContext = con;
        filterList = monsters;
        monsterList = monsters;
    }

    @Override
    public int getCount()
    {
        return filterList.size();
    }

    @Override
    public Object getItem(int i)
    {
        return filterList.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        // check if view already exists. if not inflate it
        if (view == null)
        {
            LayoutInflater inflater = (LayoutInflater) currentContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // create a list item based off layout definition
            view = inflater.inflate(R.layout.list_monster_item, null);
        }
        //assign values to the textview using monster object
        TextView nameView = (TextView) view.findViewById(R.id.nameView);
        TextView ageView = (TextView) view.findViewById(R.id.ageView);
        TextView speciesView = (TextView) view.findViewById(R.id.speciesView);
        TextView attackPowerView = (TextView) view.findViewById(R.id.attackPowerView);
        TextView healthView = (TextView) view.findViewById(R.id.healthView);

        nameView.setText(filterList.get(i).getMonsterName());
        ageView.setText("Age: " + filterList.get(i).getAge());
        speciesView.setText("Species: " + filterList.get(i).getSpecies());
        attackPowerView.setText("AP: " + filterList.get(i).getAttackPower());
        healthView.setText("HP: " + filterList.get(i).getHealth());

        return view;
    }

    //get the filter object
    @Override
    public Filter getFilter() {
        if (mFilter ==null){
            mFilter = new MyFilter();
        }
        return mFilter;
    }

    // create a new filter to select based on baseadapter
    class MyFilter extends Filter{

        //Identify a filter
        //Make filter rule in performFiltering(CharSequence charSequence)
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults result = new FilterResults();
            List<Monster> list ;
            if (TextUtils.isEmpty(charSequence)){
                // if search text is null, return all data
                list  = monsterList;
            }else {
                // or return the data meet the requirement
                list = new ArrayList<>();
                for (Monster m :monsterList){
                    if (m.getMonsterName().toLowerCase().contains(charSequence)){
                        list.add(m);
                    }

                }
            }
            result.values = list;
            result.count = list.size();

            return result;
        }

        //inform change to the system
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            filterList = (List<Monster>)filterResults.values;
            if (filterResults.count > 0){
                //inform searching successfully
                notifyDataSetChanged();
            }else {
                // inform searching unsuccessfully
                notifyDataSetInvalidated();
            }
        }
    }
}
