package com.example.guess_for_max;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
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


public class MainActivity extends AppCompatActivity {
    private EditText gues_fld;
    private TextView info;
    private TextView tasking;
    private int previos = 0;
    private int count = 1;
    private int theNumber;// = (int) (Math.random() * 100.0D + 1.0D);
    private String info_msg = "";
    private int color = Color.BLACK;
    private int range = 100;
    private int maxTries = 10;
    //ArrayList<String> motivation;
    private String[] motiv_quotes = {"Давай же!", "У тебя получится!", "Ты в правильном направлении!",
            "Уже почти!", "Ответ близок!", "Постарайся!", "Еще чуть-чуть!", "Почти угадал!"};


    public void new_game() {
        theNumber = (int) (Math.random() * range);
        //motivation=new ArrayList<String>(Arrays.asList(motiv_quotes));
        previos = 0;
        count = 1;
        color = Color.BLACK;
        info_msg = "Введите число и нажмите кнопку!";
        info.setText(info_msg);
        info.setTextColor(color);
        gues_fld.requestFocus();
        gues_fld.setText("");
        //won = false;
        //gues_btn.setText("Мне повезет!");
        tasking.setText("Загадано число в диапазоне 0-" + range);
        SharedPreferences preferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        int gamesALL = preferences.getInt("gamesALL", 0) + 1;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("gamesALL", gamesALL);
        editor.apply();
        maxTries = (int) (Math.log(range) / Math.log(2) + 1);

    }

    public void exit_app() {
        System.exit(0);
    }

    public void check_guess() {
        color = Color.BLACK;
        try {
            int gues = Integer.parseInt(gues_fld.getText().toString());
            if ((gues >= 0 & gues <= range) & previos != gues) {
                if (theNumber == gues) {
                    info_msg = "Вы угадали! Попыток: " + count;
                    info.setText(info_msg);
                    //info.setIcon(new ImageIcon("congrats.png"));
                    Toast.makeText(MainActivity.this, "МОЛОДЕЦ!!! оставалось " + maxTries + " попыток", Toast.LENGTH_LONG).show();
                    //gues_btn.setText("Выход");
                    //won = true;
                    SharedPreferences preferences =
                            PreferenceManager.getDefaultSharedPreferences(this);
                    int gamesWon = preferences.getInt("gamesWon", 0) + 1;
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("gamesWon", gamesWon);
                    editor.apply();
                    AlertDialog newgameDialog = new AlertDialog.Builder(MainActivity.this).create();
                    newgameDialog.setTitle("Вы победили! Поздравляем!");
                    newgameDialog.setMessage("Сыграть еще раз?");
                    newgameDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ДА",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    new_game();
                                    dialog.dismiss();
                                }
                            });
                    newgameDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "НЕТ",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    exit_app();
                                }
                            });
                    newgameDialog.show();
                } else if (gues < theNumber) {
                    info_msg = "Загаданное число больше! Попыток: " + count;
                    info.setText(info_msg);
                    //int motiv_quot_indx = (int) (Math.random() * motiv_quotes.length);
                    //Toast.makeText(MainActivity.this, motiv_quotes[motiv_quot_indx], Toast.LENGTH_LONG).show();
                    Toast.makeText(MainActivity.this, "Осталось " + maxTries + " попыток", Toast.LENGTH_LONG).show();
                    //info.setIcon(new ImageIcon("up.png"));
                } else {
                    info_msg = "Загаданное число меньше! Попыток: " + count;
                    info.setText(info_msg);
                    int motiv_quot_indx = (int) (Math.random() * motiv_quotes.length);
                    //Toast.makeText(MainActivity.this, motiv_quotes[motiv_quot_indx], Toast.LENGTH_LONG).show();
                    Toast.makeText(MainActivity.this, "Осталось " + maxTries + " попыток", Toast.LENGTH_LONG).show();
                    //info.setIcon(new ImageIcon("down.png"));
                }
                ++count;
                maxTries--;
                if(maxTries==-1){
                    AlertDialog newgameDialog = new AlertDialog.Builder(MainActivity.this).create();
                    newgameDialog.setTitle("К сожалению, вы проиграли!");
                    newgameDialog.setMessage("Сыграть еще раз?");
                    newgameDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ДА",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    new_game();
                                    dialog.dismiss();
                                }
                            });
                    newgameDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "НЕТ",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    exit_app();
                                }
                            });
                    newgameDialog.show();
                }
                //Toast.makeText(MainActivity.this, "Осталось "+maxTries+" попыток", Toast.LENGTH_LONG).show();
            } else if (0 > gues | gues > range) {
                info_msg = "Ответ должен быть числом из диапазона 0-" + range;
                color = Color.RED;
                info.setText(info_msg);
                // info.setIcon(new ImageIcon("range.png"));
            } else {
                info_msg = "Повторный ответ!";
                color = Color.RED;
                info.setText(info_msg);
                // info.setIcon(new ImageIcon("wrong.png"));
            }
            previos = gues;
        } catch (NumberFormatException ex) {
            info_msg = "Ответ должен быть числом из диапазона 0-" + range;
            color = Color.RED;
            info.setText(info_msg);
            // info.setIcon(new ImageIcon("no_let.png"));
        }
        gues_fld.requestFocus();
        gues_fld.selectAll();
        info.setTextColor(color);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gues_fld = findViewById(R.id.gues_fld);
        Button gues_btn = findViewById(R.id.gues_btn);
        info = findViewById(R.id.info);
        tasking = findViewById(R.id.tasking);
        SharedPreferences preferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        range = preferences.getInt("range", 100);
        new_game();
        gues_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (!won) {
                    check_guess();
                //} else {
                  //  exit_app();
              //  }
            }
        });
        gues_fld.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //if (!won) {
                    check_guess();
                  //  return true;
              //  } else {
                  //  exit_app();
                  //  return false;
               // }
            return true;
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
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        // }
        switch (item.getItemId()) {
            case R.id.action_settings:
                final CharSequence[] items = {"от 0 до 10", "от 0 до 100", "от 0 до 1000"};
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Выберете сложность:");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                range = 10;
                                storeRange(10);
                                new_game();
                                break;
                            case 1:
                                range = 100;
                                storeRange(100);
                                new_game();
                                break;
                            case 2:
                                range = 1000;
                                storeRange(1000);
                                new_game();
                                break;
                        }
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                return true;
            case R.id.action_newgame: {
                new_game();
                return true;
            }
            case R.id.action_gamestats:
                double stat;
                SharedPreferences preferences =
                        PreferenceManager.getDefaultSharedPreferences(this);
                int gamesWon = preferences.getInt("gamesWon", 0);
                int gamesALL = preferences.getInt("gamesALL", 0);
                AlertDialog statDialog = new AlertDialog.Builder(MainActivity.this).create();
                statDialog.setTitle("Статистика игр");
                if(gamesALL==0)
                    statDialog.setMessage("Вы выйграли " + gamesWon + " игр.\nВсего сыграно " + gamesALL + " игр.\nПроцент побед NA/");
                else {stat = (double)100*gamesWon / gamesALL;
                    String s=String.format("%.2f",stat);
                    statDialog.setMessage("Вы выйграли " + gamesWon + " игр.\nВсего сыграно " + gamesALL + " игр.\nПроцент побед " + s+"%");
                }
                statDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                statDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Обнулить",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences preferences =
                                        PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                                int gamesALL = 0;
                                int gamesWon = 0;
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putInt("gamesALL", gamesALL);
                                editor.putInt("gamesWon", gamesWon);
                                editor.apply();
                            }
                        });
                statDialog.show();
                return true;
            case R.id.action_about:
                AlertDialog aboutDialog = new AlertDialog.Builder(MainActivity.this).create();
                aboutDialog.setTitle("О программе");
                aboutDialog.setMessage("(c)2020 Вадимко development");
                aboutDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                aboutDialog.show();
                return true;

            case R.id.action_exit: {
                exit_app();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void storeRange(int newRange) {
        SharedPreferences preferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("range", newRange);
        editor.apply();
    }
}