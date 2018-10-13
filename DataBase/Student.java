package com.example.banurarandika.project.DataBase;

import android.provider.BaseColumns;

public final class Student {
    private Student(){

    }
    public static class students implements BaseColumns{

        public static final String DATABASE_NAME = "mad_projectc.db";
        public static final String TABLE_NAME="Student_table";
        public static final String COL_1 = "first_name";
        public static final String COL_2 = "last_name";
        public static final String COL_3 = "email_address";
        public static final String COL_4 = "mobile_number";
        public static final String COL_5 = "password";

    }
}
