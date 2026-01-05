package com.example.sllifeline.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "sllifeline.db";
    private static final int DATABASE_VERSION = 1;

    // ================= USERS =================
    public static final String TABLE_USERS = "users";
    public static final String COL_USER_ID = "id";
    public static final String COL_EMAIL = "email";
    public static final String COL_PASSWORD = "password";
    public static final String COL_ROLE = "role";
    public static final String COL_EMAIL_VERIFIED = "email_verified";

    // ================= DONOR =================
    public static final String TABLE_DONOR = "donor";
    public static final String COL_DONOR_ID = "id";
    public static final String COL_DONOR_USER_ID = "user_id";
    public static final String COL_NAME = "name";
    public static final String COL_NIC = "nic";
    public static final String COL_AGE = "age";
    public static final String COL_BLOOD_GROUP = "blood_group";
    public static final String COL_PHONE = "phone";
    public static final String COL_ADDRESS = "address";
    public static final String COL_CITY = "city";
    public static final String COL_DISTRICT = "district";

    // ================= HOSPITAL =================
    public static final String TABLE_HOSPITAL = "hospital";
    public static final String COL_HOSPITAL_ID = "id";
    public static final String COL_HOSPITAL_USER_ID = "user_id";
    public static final String COL_HOSPITAL_NAME = "name";
    public static final String COL_REG_NO = "registration_no";
    public static final String COL_HOSPITAL_PHONE = "phone";
    public static final String COL_HOSPITAL_ADDRESS = "address";

    // ================= BLOOD REQUEST =================
    public static final String TABLE_BLOOD_REQUEST = "blood_request";
    public static final String COL_REQUEST_ID = "id";
    public static final String COL_REQUEST_HOSPITAL_ID = "hospital_id";
    public static final String COL_REQUEST_BLOOD_GROUP = "blood_group";
    public static final String COL_RADIUS = "radius_km";
    public static final String COL_LATITUDE = "latitude";
    public static final String COL_LONGITUDE = "longitude";
    public static final String COL_CREATED_AT = "created_at";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "CREATE TABLE " + TABLE_USERS + " (" +
                        COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_EMAIL + " TEXT UNIQUE, " +
                        COL_PASSWORD + " TEXT, " +
                        COL_ROLE + " TEXT, " +
                        COL_EMAIL_VERIFIED + " INTEGER DEFAULT 0)"
        );

        db.execSQL(
                "CREATE TABLE " + TABLE_DONOR + " (" +
                        COL_DONOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_DONOR_USER_ID + " INTEGER, " +
                        COL_NAME + " TEXT, " +
                        COL_NIC + " TEXT, " +
                        COL_AGE + " INTEGER, " +
                        COL_BLOOD_GROUP + " TEXT, " +
                        COL_PHONE + " TEXT, " +
                        COL_ADDRESS + " TEXT, " +
                        COL_CITY + " TEXT, " +
                        COL_DISTRICT + " TEXT)"
        );

        db.execSQL(
                "CREATE TABLE " + TABLE_HOSPITAL + " (" +
                        COL_HOSPITAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_HOSPITAL_USER_ID + " INTEGER, " +
                        COL_HOSPITAL_NAME + " TEXT, " +
                        COL_REG_NO + " TEXT, " +
                        COL_HOSPITAL_PHONE + " TEXT, " +
                        COL_HOSPITAL_ADDRESS + " TEXT, " +
                        COL_CITY + " TEXT, " +
                        COL_DISTRICT + " TEXT)"
        );

        db.execSQL(
                "CREATE TABLE " + TABLE_BLOOD_REQUEST + " (" +
                        COL_REQUEST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_REQUEST_HOSPITAL_ID + " INTEGER, " +
                        COL_REQUEST_BLOOD_GROUP + " TEXT, " +
                        COL_RADIUS + " INTEGER, " +
                        COL_LATITUDE + " REAL, " +
                        COL_LONGITUDE + " REAL, " +
                        COL_CREATED_AT + " TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BLOOD_REQUEST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOSPITAL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DONOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // ========== BASIC USER METHODS ==========
    public boolean insertUser(String email, String password, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_EMAIL, email);
        cv.put(COL_PASSWORD, password);
        cv.put(COL_ROLE, role);
        cv.put(COL_EMAIL_VERIFIED, 0);
        return db.insert(TABLE_USERS, null, cv) != -1;
    }

    public Cursor loginUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(
                "SELECT * FROM " + TABLE_USERS + " WHERE email=? AND password=?",
                new String[]{email, password}
        );
    }
}
