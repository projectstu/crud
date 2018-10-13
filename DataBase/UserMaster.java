package com.example.banurarandika.project.DataBase;

import android.provider.BaseColumns;

public final class UserMaster {
    private UserMaster(){}

    public static class User implements BaseColumns{
        public static final String TABLE_NAME="user";
        public static final String COLUMN_NAME_USERNAME="username";
        public static final String COLUMN_NAME_EMAIL="email";
        public static final String COLUMN_NAME_PHOTO="photo";
    }
}
