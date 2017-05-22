package com.iqadha_app.FireBaseModel;

/**
 * Created by netset on 17/5/17.
 */

public class FaqModel
{
    public String Question,Answer;

     public FaqModel() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public  FaqModel(String Question,String Answer){
        this.Question = Question;
        this.Answer = Answer;

        //   this.pray = pray;

    }
}

