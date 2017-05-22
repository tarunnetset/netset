package com.iqadha_app.FireBaseModel;

/**
 * Created by netset on 16/11/16.
 */

public class UserSignup {
    public String userid, Gender, Dob, Baligh_date;
    public String Email, Name, Phone_number,Language;


    public UserSignup() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public UserSignup(String userid, String Email, String Gender, String Dob, String Baligh_date, String Name, String Phone_number,String Language) {
        this.userid = userid;
        this.Email = Email;
        this.Gender = Gender;
        this.Dob = Dob;
        this.Baligh_date = Baligh_date;
        this.Name = Name;
        this.Phone_number = Phone_number;
        this.Language=Language;
        //   this.pray = pray;

    }
}
