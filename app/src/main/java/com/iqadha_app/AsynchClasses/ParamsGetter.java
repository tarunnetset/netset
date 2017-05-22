package com.iqadha_app.AsynchClasses;

import java.io.File;

/**
 * Created by netset on 8/6/16.
 */
public class ParamsGetter {
    private String key, values;
    private File file = null;

    public ParamsGetter() {
    }

    public ParamsGetter(String key, String values) {
        setKey(key);
        setValues(values);
    }

    public ParamsGetter(String key, File values) {
        setKey(key);
        setFile(values);
    }


    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

}
