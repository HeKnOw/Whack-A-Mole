package com.example.heknow.whack_a_mole;

import android.app.Application;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
/**
 * Created by HeKnOw on 9/24/15.
 */
public class global_user extends Application implements Serializable{
    String name;
    String pass;
    String age;
    int level = 0;
    boolean ifSetUp = false;
    public String gender = "Male";
    long id;

    /*
    Vector<Integer> levelArray = new Vector();
    Vector<String> nameArray = new Vector();
    public Map<String,Integer> mNameLevel;

    public void setUpVectors () {
        levelArray.add(1);
        levelArray.add(2);
        levelArray.add(3);
        nameArray.add("Bill");
        nameArray.add("Jane");
        nameArray.add("Luke");
        mNameLevel.put("Bill",1);
        mNameLevel.put("Jane",2);
        mNameLevel.put("Luke",3);
    }
    public String getNameArray (int i){return this.nameArray.elementAt(i);}
    public void setNameArray(String j){this.nameArray.add(j);}

    public int getLevelArray (int i){return this.levelArray.elementAt(i);}
    public void setLevelArray(int i){this.levelArray.add(i);}
    */

    public long getID() { return this.id; }
    public void setId(long i) { this.id = i; }

    public String getGender() {return this.gender; }
    public void setGender(String gender) {
        if(gender == "M"){
            this.gender = "Male";
        } else {
            this.gender = "Female";
        }
    }

    public String getName (){
        return this.name;
    }
    public void setName(String s){
        this.name = s;
    }

    public String getPass (){
        return this.pass;
    }
    public void setPass(String s){
        this.pass = s;
    }

    public String getAge (){
        return this.age;
    }
    public void setAge(String s){
        this.age = s;
    }

    public int getLevel (){return this.level;}
    public void setLevel(int i){this.level = i;}


    public boolean getIf(){
        return this.ifSetUp;
    }

    public void setIf(boolean s){
        this.ifSetUp = s;
    }

}
