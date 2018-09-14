package edu.monash.fit4039.fit4039ass2;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nathan on 7/4/17.
 */

public class Monster implements Parcelable{
    //declare static variables
    //attributes and table name
    public static final String TABLE_NAME = "monster";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_SPECIES = "species";
    public static final String COLUMN_ATTACKPOWER = "attackpower";
    public static final String COLUMN_HEALTH = "health";

    //create table statement
    public static final String CREATE_STATEMENT = "create table "
            + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + COLUMN_NAME + " TEXT NOT NULL, "
            + COLUMN_AGE  + " INTEGER NOT NULL, "
            + COLUMN_SPECIES + " TEXT NOT NULL, "
            + COLUMN_ATTACKPOWER + " INTEGER NOT NULL, "
            + COLUMN_HEALTH + " INTEGER NOT NULL)";

    //declare variables
    private long _id;
    private String monsterName;
    private int age;
    private String species;
    private int attackPower;
    private int health;

    public Monster() {

    }

    public Monster(long _id, String monsterName, int age, String species, int attackPower, int health) {
        this._id = _id;
        this.monsterName = monsterName;
        this.age = age;
        this.species = species;
        this.attackPower = attackPower;
        this.health = health;
    }

    //set up parcelable
    protected Monster(Parcel in)
    {
        _id = in.readLong();
        monsterName = in.readString();
        age = in.readInt();
        species = in.readString();
        attackPower = in.readInt();
        health = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    //set up creator
    public static final Creator<Monster> CREATOR = new Creator<Monster>() {
        @Override
        public Monster createFromParcel(Parcel in) {
            return new Monster(in);
        }

        @Override
        public Monster[] newArray(int size) {
            return new Monster[size];
        }
    };

    //set up parcelable
    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeLong(_id);
        parcel.writeString(monsterName);
        parcel.writeInt(age);
        parcel.writeString(species);
        parcel.writeInt(attackPower);
        parcel.writeInt(health);
    }

    //get id
    public long get_Id()
    {
        return _id;
    }

    //set id
    public void set_Id(long _id)
    {
        this._id = _id;
    }

    //set monster name
    public void setMonsterName(String monsterName) {
        this.monsterName = monsterName;
    }

    //set age
    public void setAge(int age) {
        this.age = age;
    }

    //set species
    public void setSpecies(String species) {
        this.species = species;
    }

    //set attack power
    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    //set health
    public void setHealth(int health) {
        this.health = health;
    }

    //get monster name
    public String getMonsterName() {
        return monsterName;
    }

    //get age
    public int getAge() {
        return age;
    }

    //get species
    public String getSpecies() {
        return species;
    }

    //get attack power
    public int getAttackPower() {
        return attackPower;
    }

    //get health
    public int getHealth() {
        return health;
    }

    //get summary
    public String getSummary()
    {
        return "Monster name: " + monsterName + ", Age: " + age + ", Species: " + species + ", Attack Power: " + attackPower + "Health: " + health;
    }

    //get attack value
    public int getAttackValue()
    {
        int randomNumber = (int)(-100 + Math.random() * 200);
        int attackValue = randomNumber + attackPower;
        if (attackValue < 1)
            return 1;
        else
            return attackValue;
    }
}
