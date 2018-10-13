package com.example.banurarandika.project.DataBase;

import android.provider.BaseColumns;

public final class Result {
    private Result(){

    }
    public static class results implements BaseColumns{
        public static final String DATABASE_NAME = "mad_projectc.db";
        public static final String TABLE_NAME="Result_table";
        public static final String COL_1 = "student_name";
        public static final String COL_2 = "enrollment_key";
        public static final String COL_3 = "marks";
        public static final String COL_4 = "gpa";
        public static final String COL_5 = "class";
    }
}
