package com.iqadha_app.FireBaseModel;

/**
 * Created by netset on 17/5/17.
 */

public class AboutModel
{
        public String Title,Sub_Content;

        public AboutModel() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public AboutModel(String Title, String Sub_Content) {
            this.Title = Title;
            this.Sub_Content = Sub_Content;

            //   this.pray = pray;

        }
}
