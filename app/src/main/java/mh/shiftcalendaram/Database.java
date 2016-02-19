package mh.shiftcalendaram;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;

import java.util.ArrayList;

import mh.shiftcalendaram.templates.AlternativeShifts;
import mh.shiftcalendaram.templates.ShiftNotesTemplate;
import mh.shiftcalendaram.templates.ShiftSymbolTemplates;
import mh.shiftcalendaram.templates.ShiftTemplate;

/**
 * Created by Martin on 02.02.2016.
 */
public class Database extends SQLiteOpenHelper {


    private static final long serialVersionUID = 1L;
    static int databaseVersion = 3;
    static String databaseName = "DBCalendar";

    String createTable = "CREATE TABLE shifts ( " +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "title TEXT, "+
            "short TEXT, position INTEGER, color INTEGER )";

    String createTable2 = "CREATE TABLE alternative ( " +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "kind TEXT, "+
            "position INTEGER, month TEXT, year TEXT, custom INTEGER, color TEXT )";

    String createTable3 = "CREATE TABLE notes ( " +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, position INTEGER, month TEXT, year TEXT, note TEXT, custom INTEGER )";

    String createTable4 = "CREATE TABLE symbols ( " +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, short TEXT, color TEXT )";


    public Database(Context context) {
        super(context, databaseName, null, databaseVersion);
    }

    public void insertDefaultSymbols(SQLiteDatabase db)
    {
        db.execSQL("INSERT INTO symbols (name, short, color) VALUES ('Náhradní volno', 'NV', '#9b1c20')");
        db.execSQL("INSERT INTO symbols (name, short, color) VALUES ('Dovolená', 'D', '#1abc9c')");
        db.execSQL("INSERT INTO symbols (name, short, color) VALUES ('\"Z\" volno', 'Z', '#9b59b6')");
        db.execSQL("INSERT INTO symbols (name, short, color) VALUES ('Paragraf', 'P', '#99CC00')");
        db.execSQL("INSERT INTO symbols (name, short, color) VALUES ('Přesčas', 'PČ', '#3498db')");
        db.execSQL("INSERT INTO symbols (name, short, color) VALUES ('Odpolední+Noční', 'O+N', '#D7DF01')");
    }


    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL(createTable);
        db.execSQL(createTable2);
        db.execSQL(createTable3);
        db.execSQL(createTable4);

        insertDefaultSymbols(db);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        if (oldVersion<2) {
            db.execSQL(createTable3);
            db.execSQL(createTable4);

            insertDefaultSymbols(db);
        }
        else if (oldVersion < 3)
        {
            db.execSQL(createTable4);

            insertDefaultSymbols(db);
        }
        else
        {

            db.execSQL("DROP TABLE IF EXISTS shifts");
            db.execSQL("DROP TABLE IF EXISTS alternative");
            db.execSQL("DROP TABLE IF EXISTS notes");
            db.execSQL("DROP TABLE IF EXISTS symbols");
        }

    }

    public void insertShift(String title, String shortTitle, int position, int color)
    {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("short", shortTitle);
        values.put("position", position);
        values.put("color", color);

        db.insert("shifts",null, values);

        db.close();

    }

    public void insertAlternative(String kind, int position, String month, String year, int custom, String color)
    {


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("kind", kind);
        values.put("position", position);
        values.put("month", month);
        values.put("year", year);
        values.put("custom", custom);
        values.put("color", color);

        db.insert("alternative", null, values);

        db.close();


    }

    public void insertNote(int position, String month, String year, String note, int custom) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("position", position);
        values.put("month", month);
        values.put("year", year);
        values.put("note", note);
        values.put("custom", custom);

        db.insert("notes", null, values);

        db.close();
    }

    public ArrayList<AlternativeShifts> getSpecial()
    {

        ArrayList<AlternativeShifts> list = new ArrayList<AlternativeShifts>();
        String query = "SELECT * FROM alternative";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {

                String kind = cursor.getString(1);
                int position = cursor.getInt(2);
                String month = cursor.getString(3);
                String year = cursor.getString(4);
                int custom = cursor.getInt(5);
                String color = cursor.getString(6);



                list.add(new AlternativeShifts(kind, position, month, year, custom, color));
            } while (cursor.moveToNext());
        }

        return list;
    }


    public ArrayList<ShiftNotesTemplate> getTextNote()
    {

        ArrayList<ShiftNotesTemplate> list = new ArrayList<ShiftNotesTemplate>();

        String query = "SELECT * FROM notes";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        String text = "";
        int position = 0;
        String month = "";
        String year = "";
        int custom = -1;
        if (cursor.moveToFirst()) {
            do
            {
                position = cursor.getInt(1);
                month = cursor.getString(2);
                year = cursor.getString(3);
                text = cursor.getString(4);
                custom = cursor.getInt(5);

                if(custom != -1)
                    list.add(new ShiftNotesTemplate(position, month, year, text, custom));

            }while(cursor.moveToNext());

        }
        return list;
    }








    public void insertSymbols(String name, String shortTitle, String color)
    {

        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put("name", name); // get title
        values.put("short", shortTitle); // get author
        values.put("color", color);

        db.insert("symbols", null, values);

        db.close();


    }




    public ArrayList<ShiftTemplate> getAllShifts() {
        ArrayList<ShiftTemplate> shifts = new ArrayList<>();


        String query = "SELECT  * FROM shifts";


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        ShiftTemplate shift = null;
        if (cursor.moveToFirst()) {
            do {

                String title = cursor.getString(1);
                String shortTitle = cursor.getString(2);
                int position = cursor.getInt(3);
                int color = cursor.getInt(4);
                shift = new ShiftTemplate(title, shortTitle, color, position, "");

                shifts.add(shift);
            } while (cursor.moveToNext());
        }

        return shifts;
    }

    public ArrayList<ShiftSymbolTemplates> getSymbols()
    {

        ArrayList<ShiftSymbolTemplates> list = new ArrayList<ShiftSymbolTemplates>();
        String query = "SELECT * FROM symbols";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {

                String name = cursor.getString(1);
                String shortTitle = cursor.getString(2);
                String color = cursor.getString(3);


                list.add(new ShiftSymbolTemplates(name, shortTitle, Color.parseColor(color), ""));
            } while (cursor.moveToNext());
        }

        return list;
    }

    public void updateSymbols(String name, String shortTitle, String color, int position)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String d ="UPDATE symbols SET name='"+name+"', short='"+shortTitle+"', color='"+color+"' WHERE id in (SELECT id FROM symbols LIMIT 1 OFFSET "+position+")";

        db.execSQL(d);
        db.close();
    }

    public void updateShift(String title, String shortTitle, int position,int color, int positionOfCustom)
    {

        SQLiteDatabase db = this.getWritableDatabase();

        String d ="UPDATE shifts SET title='"+title+"', short='"+shortTitle+"', position="+position+", color="+color+" WHERE id in (SELECT id FROM Shifts LIMIT 1 OFFSET "+positionOfCustom+")";

        db.execSQL(d);

        db.close();


    }

    public void updateNote(int day, String month, String year, int custom, String note)
    {
        SQLiteDatabase db = this.getWritableDatabase();


        String update ="UPDATE notes SET note='"+note+"' WHERE position="+day+" AND month='"+month+"' AND year='"+year+"' AND custom="+custom+"";

        db.execSQL(update);
        db.close();
    }




    public void deleteAlter(int position, String month, String year, int positionOfCustom)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String d ="DELETE FROM alternative WHERE position="+position+" and month='"+month+"' and year='"+year+"' and custom='"+positionOfCustom+"'";
        db.execSQL(d);

        db.close();
    }


    public void updateAlter(int day, String month, String year, int custom, String kind, String color)
    {
        SQLiteDatabase db = this.getWritableDatabase();


        String update ="UPDATE alternative SET kind='"+kind+"' AND color='"+color+"' WHERE position="+day+" AND month='"+month+"' AND year='"+year+"' AND custom="+custom;

        db.execSQL(update);
        db.close();
    }




    public void deleteShift(int position) {


        SQLiteDatabase db = this.getWritableDatabase();

        String d = "DELETE FROM Shifts WHERE id in (SELECT id FROM Shifts LIMIT 1 OFFSET " + position + ")";
        db.execSQL(d);

        db.close();
    }


    public void deleteSymbol(int position) {


        SQLiteDatabase db = this.getWritableDatabase();

        String d ="DELETE FROM symbols WHERE id in (SELECT id FROM symbols LIMIT 1 OFFSET "+position+")";
        db.execSQL(d);

        db.close();


    }


    public void deleteNotes(int position, String month, String year, int custom)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String d ="DELETE FROM notes WHERE position="+position+" and month='"+month+"' and year='"+year+"' and custom='"+custom+"'";
        db.execSQL(d);
        db.close();
    }





}
