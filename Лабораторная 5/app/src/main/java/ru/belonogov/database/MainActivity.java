package ru.belonogov.database;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TableLayout table;
    Button deleteBtn;
    Button addBtn;

    String[] firstName = { "Саша" , "Андрей" , "Степан" , "Игорь" , "Никита"
        , "Рома" , "Данил" , "Артём", "Феофан" , "Афанасий", "Иван"
        , "Стас" , "Перкосрак" , "Даздраперм" , "Иосиф" , "Сид"};
    String[] secondName = { "Белоногов" , "Степанов" , "Кручинин" , "Лёвкин"
            , "Панин" , "Кудрявцев" , "Жданов" , "Михайлишин" , "Меньщиков" , "Круглов"
            , "Скамов" , "Заскамов" , "Мамонтов" , "Джугашвили" , "Копатыч" , "Гервас" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        table = findViewById(R.id.TableLayout);
        deleteBtn = findViewById(R.id.delButton);
        addBtn = findViewById(R.id.addButton);
    }


    ///Добавление
    public void AddIntoDB() {
        SQLiteDatabase db = openOrCreateDatabase("studentsDatabase.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS mytable (name TEXT, weight INTEGER, growth INTEGER, age INTEGER);");
            Random random = new Random();
            int weight = random.nextInt(50) + 50;
            int height = random.nextInt(45) + 150;
            int age = random.nextInt(5) + 18;
            String fullName = firstName[random.nextInt(16)] + " " + secondName[random.nextInt(16)];
            db.execSQL("INSERT INTO mytable VALUES " +
                    "('" + fullName + "', '" + weight + "' , '" + height + "' , '" + age + "');");
        db.close();
    }


    ///Чистка
    public void clearDB() {
        SQLiteDatabase db = openOrCreateDatabase("studentsDatabase.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS mytable (name TEXT, weight INTEGER, height INTEGER, age INTEGER);");
        db.execSQL("DROP TABLE mytable");
        table.removeAllViews();
    }


    ///Нажатие на "Добавить"
    public void onClickAdd(View view) {
        AddIntoDB();
        printDB();
    }


    ///Нажатие на "Очистить"
    public void onClickDelete(View view) {
        clearDB();
    }

    ///Вывод
    public void printDB() {
        table.removeAllViews();
        SQLiteDatabase db = openOrCreateDatabase("studentsDatabase.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS mytable (name TEXT, weight INTEGER, height INTEGER, age INTEGER);");
        Cursor c = db.rawQuery("SELECT * FROM mytable ORDER BY name;", null);

        while(c.moveToNext()) {
            TextView nameView = new TextView(this);
            TextView weightView = new TextView(this);
            TextView heightView = new TextView(this);
            TextView ageView = new TextView(this);

            nameView.setText(c.getString(0));
            weightView.setText(c.getString(1));
            heightView.setText(c.getString(2));
            ageView.setText(c.getString(3));

            nameView.setGravity(Gravity.CENTER);
            weightView.setGravity(Gravity.CENTER);
            heightView.setGravity(Gravity.CENTER);
            ageView.setGravity(Gravity.CENTER);

            nameView.setLayoutParams(new
                    TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1f
            ));
            weightView.setLayoutParams(new
                    TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1f
            ));
            heightView.setLayoutParams(new
                    TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1f
            ));
            ageView.setLayoutParams(new
                    TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1f
            ));

            TableRow row = new TableRow(this);
            row.addView(nameView);
            row.addView(weightView);
            row.addView(heightView);
            row.addView(ageView);

            table.addView(row);

        }
        c.close();
        db.close();
    }
}