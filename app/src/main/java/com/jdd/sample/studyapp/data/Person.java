package com.jdd.sample.studyapp.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author lc. 2018-01-18 14:17
 * @since 1.0.0
 */

public class Person implements Parcelable {

    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "[person: name=" + name + " age=" + age + "]";
    }

    private Person(Parcel in) {
        this.name = in.readString();
        this.age = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.age);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}
