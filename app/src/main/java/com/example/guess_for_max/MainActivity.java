package com.example.guess_for_max;

import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private EditText gues_fld;
    private Button gues_btn;
    private TextView info;
    private int gues;
    private int previos=0;
    private int count=1;
    private int theNumber;// = (int) (Math.random() * 100.0D + 1.0D);
    boolean won;
    String info_msg="";
    int color= Color.BLACK;
    ArrayList<String> motivation;
    String []motiv_quotes={"Давай же!","У тебя получится!","Ты в правильном направлении!",
            "Уже почти!", "Ответ близок!", "Постарайся!", "Еще чуть-чуть!", "Почти угадал!"};


    public void new_game(){
        theNumber=(int) (Math.random() * 100.0D + 1.0D);
        motivation=new ArrayList<String>(Arrays.asList(motiv_quotes));
    }
    public void exit_app() {
        System.exit(0);
    }

    public void check_guess(){
        color=Color.BLACK;

        try {
            gues = Integer.parseInt(gues_fld.getText().toString());
            if ((gues>=0&gues<=100)&previos != gues) {
                if (theNumber == gues) {
                    //info.setText("Вы угадали! Попыток: " + count);
                    info_msg="Вы угадали! Попыток: " + count;
                    info.setText(info_msg);
                    //info.setIcon(new ImageIcon("congrats.png"));
                    gues_btn.setText("Выход");
                    won = true;
                } else if (gues < theNumber) {
                    //info.setText("Загаданное число больше! Попыток: " + count);
                    info_msg="Загаданное число больше! Попыток: " + count;
                    info.setText(info_msg);
                    int motiv_quot_indx=(int)(Math.random() * 7);
                    Toast.makeText(MainActivity.this, motivation.get(motiv_quot_indx), Toast.LENGTH_LONG).show();
                    //info.setIcon(new ImageIcon("up.png"));
                } else {
                    //info.setText("Загаданное число меньше! Попыток: " + count);
                    info_msg="Загаданное число меньше! Попыток: " + count;
                    info.setText(info_msg);
                    int motiv_quot_indx=(int)(Math.random() * 7);
                    Toast.makeText(MainActivity.this, motivation.get(motiv_quot_indx), Toast.LENGTH_LONG).show();
                    //info.setIcon(new ImageIcon("down.png"));
                }
                ++count;
                Toast.makeText(MainActivity.this, "МОЛОДЕЦ!!!", Toast.LENGTH_LONG).show();
            } else if (0>gues|gues>100) {
                //info.setText("Ответ должен быть числом из диапазона 0-100");
                info_msg="Ответ должен быть числом из диапазона 0-100";
                color=Color.RED;
                info.setText(info_msg);
                // info.setIcon(new ImageIcon("range.png"));
                //info.setForeground(Color.RED);
                //info.setTextColor(#3F51B5);
            }
            else {
                //info.setText("Повторный ответ!");
                info_msg="Повторный ответ!";
                color=Color.RED;
                info.setText(info_msg);
                // info.setIcon(new ImageIcon("wrong.png"));
                // info.setForeground(Color.RED);
            }
            previos = gues;
        } catch (NumberFormatException ex) {
            //info.setText("Ответ должен быть числом из диапазона 0-100");
            info_msg="Ответ должен быть числом из диапазона 0-100";
            color=Color.RED;
            info.setText(info_msg);
            // info.setIcon(new ImageIcon("no_let.png"));
            // info.setForeground(Color.RED);
        }
        gues_fld.requestFocus();
        gues_fld.selectAll();
        //info.setText(info_msg);
        info.setTextColor(color);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gues_fld = (EditText) findViewById(R.id.gues_fld);
        gues_btn = (Button) findViewById(R.id.gues_btn);
        info = (TextView) findViewById(R.id.info);
        new_game();
        gues_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!won) {
                    check_guess();
                } else {
                    exit_app();
                }
            }
        });
        gues_fld.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (!won) {
                    check_guess();
                    return true;
                } else {
                    exit_app();
                    return false;
                }
            }

        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}