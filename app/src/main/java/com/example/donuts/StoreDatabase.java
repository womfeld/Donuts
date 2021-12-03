package com.example.donuts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class StoreDatabase extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DonutStoreDB";


    public static final String USERS_TABLE = "USER_TABLE";
    public static final String COLUMN_USERNAME = "USERNAME";
    public static final String COLUMN_USER_FIRST_NAME = "USER_FIRST_NAME";
    public static final String COLUMN_USER_LAST_NAME = "USER_LAST_NAME";
    public static final String COLUMN_USER_PASSWORD = "PASSWORD";
    public static final String COLUMN_USER_ISBANNED = "ISBANNED";
    public static final String COLUMN_USER_REWARD = "POINTS_EARNED";
    public static final String COLUMN_USER_ROLE = "ROLE";

    private static final String SQL_CREATE_USER_TABLE =
            "CREATE TABLE " + USERS_TABLE + " (" +
                    COLUMN_USERNAME + " TEXT PRIMARY KEY," +
                    COLUMN_USER_PASSWORD + " TEXT," +
                    COLUMN_USER_FIRST_NAME + " TEXT," +
                    COLUMN_USER_LAST_NAME + " TEXT," +
                    COLUMN_USER_ISBANNED + " TEXT," +
                    COLUMN_USER_REWARD + " INTEGER," +
                    COLUMN_USER_ROLE + " TEXT not null)";



    //Has foreign key user name
    public static final String CUSTOMERS_TABLE = "CUSTOMER_TABLE";
    public static final String COLUMN_CUSTOMER_CREDIT_CARD_NUM = "CREDIT_CARD_NUM";
    public static final String COLUMN_CUSTOMER_CREDIT_CARD_CVV = "CREDIT_CARD_CVV";
    public static final String COLUMN_CUSTOMER_MAILING_ADDRESS = "MAILING_ADDRESS";

    private static final String SQL_CREATE_CUSTOMER_TABLE =
            "CREATE TABLE " + CUSTOMERS_TABLE + " (" +
                    COLUMN_USERNAME + " TEXT," +
                    COLUMN_CUSTOMER_CREDIT_CARD_NUM + " TEXT," +
                    COLUMN_CUSTOMER_CREDIT_CARD_CVV + " INTEGER," +
                    COLUMN_CUSTOMER_MAILING_ADDRESS + " TEXT," +
                    " FOREIGN KEY ("+ COLUMN_USERNAME +") REFERENCES "+USERS_TABLE+"("+ COLUMN_USERNAME +"))";



    //item_id SERIAL not null, item_name varchar(50), item_quant int, price float,
    //	primary key (item_id)
    public static final String INVENTORY_TABLE = "INVENTORY_TABLE";
    //PRIMARY KEY ITEM_ID
    public static final String COLUMN_INVENTORY_NAME = "ITEM_NAME";
    public static final String COLUMN_INVENTORY_QUANTITY = "ITEM_QUANTITY";
    public static final String COLUMN_INVENTORY_PRICE = "ITEM_PRICE";

    private static final String SQL_CREATE_INVENTORY_TABLE =
            "CREATE TABLE " + INVENTORY_TABLE + " (" +
                    COLUMN_INVENTORY_NAME + " TEXT PRIMARY KEY," +
                    COLUMN_INVENTORY_QUANTITY + " INTEGER," +
                    COLUMN_INVENTORY_PRICE + " REAL not null)";



    //e_id IS PRIMARY
    public static final String EMPLOYEES_TABLE = "EMPLOYEE_TABLE";
    public static final String COLUMN_EMPLOYEE_FIRST_NAME = "EMPLOYEE_FIRST_NAME";
    public static final String COLUMN_EMPLOYEE_LAST_NAME = "EMPLOYEE_LAST_NAME";
    public static final String COLUMN_EMPLOYEE_JOB = "EMPLOYEE_JOB";
    public static final String COLUMN_EMPLOYEE_SALARY = "EMPLOYEE_SALARY";

    private static final String SQL_CREATE_EMPLOYEE_TABLE =
            "CREATE TABLE " + EMPLOYEES_TABLE + " (" +
                    COLUMN_EMPLOYEE_FIRST_NAME + " TEXT," +
                    COLUMN_EMPLOYEE_LAST_NAME + " TEXT," +
                    COLUMN_EMPLOYEE_JOB + " TEXT," +
                    COLUMN_EMPLOYEE_SALARY + " TEXT not null)";



    //Remember, has order id autoincrement and foreign key userid
    public static final String ORDERS_TABLE = "ORDER_TABLE";
    public static final String COLUMN_ORDER_SPENT = "ORDER_TOTAL";

    private static final String SQL_CREATE_ORDER_TABLE =
            "CREATE TABLE " + ORDERS_TABLE + " (" +
                    "O_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_ORDER_SPENT + " REAL," +
                    COLUMN_USERNAME + " TEXT," +
                    " FOREIGN KEY ("+ COLUMN_USERNAME +") REFERENCES "+USERS_TABLE+"("+ COLUMN_USERNAME +"))";



    //Foreign key user id
    public static final String REVIEW_TABLE = "REVIEW_TABLE";
    //PRIMARY KEY R_ID
    public static final String COLUMN_REVIEW_FIRST_NAME = "REVIEW_FIRST_NAME";
    public static final String COLUMN_REVIEW_STARS = "STARS";
    public static final String COLUMN_REVIEW_COMMENT = "COMMENT";

    private static final String SQL_CREATE_REVIEW_TABLE =
            "CREATE TABLE " + REVIEW_TABLE + " (" +
                    "R_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_REVIEW_FIRST_NAME + " TEXT," +
                    COLUMN_REVIEW_STARS + " INTEGER," +
                    COLUMN_REVIEW_COMMENT + " TEXT not null)";




    private SQLiteDatabase database;


    public StoreDatabase(Context context){
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
        database = getWritableDatabase();
    }


    //Called first time we try to access the database
    @Override
    public void onCreate(SQLiteDatabase db){

        db.execSQL(SQL_CREATE_USER_TABLE);
        db.execSQL(SQL_CREATE_CUSTOMER_TABLE);
        db.execSQL(SQL_CREATE_INVENTORY_TABLE);
        db.execSQL(SQL_CREATE_EMPLOYEE_TABLE);
        db.execSQL(SQL_CREATE_ORDER_TABLE);
        db.execSQL(SQL_CREATE_REVIEW_TABLE);





    }




    //Whenever database version number changes
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }



    public User loadUser(String un, String pw) {

        User u;

        Cursor cursor = database.rawQuery("SELECT * FROM USER_TABLE WHERE USERNAME = ? AND PASSWORD = ?", new String[] {un, pw});

        if (cursor != null) {
            //Delete later

            cursor.moveToFirst();

            u = new User (cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME)), cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_USER_FIRST_NAME)), cursor.getString(cursor.getColumnIndex(COLUMN_USER_LAST_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_USER_ISBANNED)), cursor.getInt(cursor.getColumnIndex(COLUMN_USER_REWARD)), cursor.getString(cursor.getColumnIndex(COLUMN_USER_ROLE))
            );

            cursor.close();
        }

        else {
            u = new User("Login unsuccessful");
            //Toast.makeText(loginPage, "No user found.", Toast.LENGTH_SHORT).show();
        }


        return u;
    }


    //Onky called in manager/employee portal
    public User loadUserForManager(String un) {

        User u;

        Cursor cursor = database.rawQuery("SELECT * FROM USER_TABLE WHERE USERNAME = ?", new String[] {un});

        if (cursor != null) {
            //Delete later

            cursor.moveToFirst();

            u = new User (cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME)), cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_USER_FIRST_NAME)), cursor.getString(cursor.getColumnIndex(COLUMN_USER_LAST_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_USER_ISBANNED)), cursor.getInt(cursor.getColumnIndex(COLUMN_USER_REWARD)), cursor.getString(cursor.getColumnIndex(COLUMN_USER_ROLE))
            );

            cursor.close();
        }

        else {
            u = new User("Login unsuccessful");
            //Toast.makeText(loginPage, "No user found.", Toast.LENGTH_SHORT).show();
        }


        return u;
    }


    //Adds a user to the Database
    public void addUser(User u){

        ContentValues values = new ContentValues();
        //values.put(COLUMN_USER_ID, u.getId()); //Don't think this is needed because ID auto increments
        values.put(COLUMN_USERNAME, u.getUserName());
        values.put(COLUMN_USER_PASSWORD, u.getPassword());
        values.put(COLUMN_USER_FIRST_NAME, u.getFirst());
        values.put(COLUMN_USER_LAST_NAME, u.getLast());
        values.put(COLUMN_USER_ISBANNED, u.getIsBanned());
        values.put(COLUMN_USER_REWARD, u.getRewardPoints());
        values.put(COLUMN_USER_ROLE, u.getRole());
        database.insert(USERS_TABLE, null, values);

    }

    public void deleteUser(String userName) {
        database.delete(USERS_TABLE, COLUMN_USERNAME + " = ?", new String[]{userName});
    }



    public void addCustomer(String userName, String cardNumber, int cvv, String address) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, userName);
        values.put(COLUMN_CUSTOMER_CREDIT_CARD_NUM, cardNumber);
        values.put(COLUMN_CUSTOMER_CREDIT_CARD_CVV, cvv);
        values.put(COLUMN_CUSTOMER_MAILING_ADDRESS, address);
        database.insert(CUSTOMERS_TABLE, null, values);

    }


    //Onky called in manager/employee portal
    public int getInventoryItemQuantity(String n) {

        int q;

        Cursor cursor = database.rawQuery("SELECT ITEM_QUANTITY FROM INVENTORY_TABLE WHERE ITEM_NAME = ?", new String[] {n});

        if (cursor != null) {
            //Delete later

            cursor.moveToFirst();


            q = cursor.getInt(0);

            cursor.close();
        }

        else {
            //Change later
            q = -5;
        }


        return q;
    }




    public void addEmployee(String fn, String ln, String job, String salary) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMPLOYEE_FIRST_NAME, fn);
        values.put(COLUMN_EMPLOYEE_LAST_NAME, ln);
        values.put(COLUMN_EMPLOYEE_JOB, job);
        values.put(COLUMN_EMPLOYEE_SALARY, salary);
        database.insert(EMPLOYEES_TABLE, null, values);

    }

    public void addItemToInventory(String item, int qty, double price) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_INVENTORY_NAME, item);
        values.put(COLUMN_INVENTORY_QUANTITY, qty);
        values.put(COLUMN_INVENTORY_PRICE, price);

        database.insert(INVENTORY_TABLE, null, values);

    }



    public void addOrder(Double total, String userName) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ORDER_SPENT, total);
        values.put(COLUMN_USERNAME, userName);
        database.insert(ORDERS_TABLE, null, values);

    }

    public void addReview(String firstName, int stars, String comment) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_REVIEW_FIRST_NAME, firstName);
        values.put(COLUMN_REVIEW_STARS, stars);
        values.put(COLUMN_REVIEW_COMMENT, comment);
        database.insert(REVIEW_TABLE, null, values);
    }




    public HashMap<String, Integer> loadInventory() {

        HashMap<String, Integer> inventory = new HashMap<>();

        Cursor cursor = database.query(
                INVENTORY_TABLE,  // The table to query
                new String[]{COLUMN_INVENTORY_NAME, COLUMN_INVENTORY_QUANTITY}, // The columns to return
                null, // The columns for the WHERE clause
                null, // The values for the WHERE clause
                null, // don't group the rows
                null, // don't filter by row groups
                null); // The sort order

        if (cursor != null) {
            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {
                String name = cursor.getString(0);
                Integer quantity = Integer.parseInt(cursor.getString(1));

                inventory.put(name, quantity);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return inventory;
    }





    public void updateInventory(HashMap<String, Integer> updatedItems) {

        for (String name : updatedItems.keySet()) {

            int newValue = updatedItems.get(name);

            ContentValues cv = new ContentValues();
            cv.put(COLUMN_INVENTORY_NAME, name);
            cv.put(COLUMN_INVENTORY_QUANTITY, newValue);

            database.update(INVENTORY_TABLE, cv, "ITEM_NAME = ?", new String[]{name});


        }

    }



    public ArrayList<Review> loadReviews() {
        ArrayList<Review> reviewList = new ArrayList<>();
        Cursor cursor = database.query(
                REVIEW_TABLE,  // The table to query
                new String[]{"R_ID", COLUMN_REVIEW_FIRST_NAME, COLUMN_REVIEW_STARS, COLUMN_REVIEW_COMMENT}, // The columns to return
                null, // The columns for the WHERE clause
                null, // The values for the WHERE clause
                null, // don't group the rows
                null, // don't filter by row groups
                null); // The sort order

        if (cursor != null) {
            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {
                String name = cursor.getString(1);
                int stars = cursor.getInt(2);
                String description = cursor.getString(3);

                Review r = new Review(name, description, stars);
                reviewList.add(r);


                cursor.moveToNext();
            }
            cursor.close();
        }

        return reviewList;
    }



   public HashMap<String, Double> getOrders() {
        HashMap<String, Double> orderList = new HashMap<>();
        Cursor cursor = database.query(
                ORDERS_TABLE,  // The table to query
                new String[]{"O_ID", COLUMN_ORDER_SPENT, COLUMN_USERNAME}, // The columns to return
                null, // The columns for the WHERE clause
                null, // The values for the WHERE clause
                null, // don't group the rows
                null, // don't filter by row groups
                null); // The sort order

        if (cursor != null) {
            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {
                double moneyMade = cursor.getInt(1);
                String userN = cursor.getString(2);


                orderList.put(userN, moneyMade);


                cursor.moveToNext();
            }
            cursor.close();
        }

        return orderList;
    }


    public void updateRewardPoints(String userName, int num) {

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME, userName);
        cv.put(COLUMN_USER_REWARD, num);

        database.update(USERS_TABLE, cv, "USERNAME = ?", new String[]{userName});

    }












    public void shutDown() {

        database.close();
    }

}

