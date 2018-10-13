package com.example.banurarandika.project.DataBase;



import android.provider.BaseColumns;

public final class Tute {
    private Tute(){

    }
    public static class results implements BaseColumns{
        public static final String DATABASE_NAME = "mad_projectc.db";
        public static final String TABLE_NAME="Tute_table";
        public static final String COL_1 = "tute_name";
        public static final String COL_2 = "tute_no";
        public static final String COL_3 = "subject";
        public static final String COL_4= "description";
    }
}
