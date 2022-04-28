package com.example.turboweather;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
/**
 * Класс активити cityFinder
 * <p>
 * Данный класс обрабатывает
 */
public class cityFinder extends AppCompatActivity {
    /**
     *
     * @param savedInstanceState параметр для сохранения состояния, связанного с текущим экземпляром Activity, например текущей информации о навигации или выборе, так что, если Android уничтожает и воссоздает Activity, он может вернуться, как было раньше.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_finder);
        final EditText editText=findViewById(R.id.searchCity);
        ImageView backButton=findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String newCity= editText.getText().toString();
                Intent intent=new Intent(cityFinder.this,MainActivity.class);
                intent.putExtra("City",newCity);
                startActivity(intent);



                return false;
            }
        });
    }

    /**
     * Метод вызывающий метод показывания окна правил ввода города {@link String showWindow}.
     * @param view Вид, на который был сделан клик.
     */
    public void OnClickSettings(View view) {
        showWindow();
    }

    /**
     * Метод показывающий окно правил ввода города.
     * <p>
     */
    public void showWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Правила ввода города");

        LayoutInflater inflater = LayoutInflater.from(this);
        View signin_window=inflater.inflate(R.layout.activity_settings_window,null);
        dialog.setView(signin_window);

        dialog.setPositiveButton("Понятно", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();

            }
        });
        dialog.show();
    }
}