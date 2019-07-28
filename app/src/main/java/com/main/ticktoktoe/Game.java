package com.main.ticktoktoe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Game extends Activity implements View.OnClickListener {
    private TextView x,o;
    private String p1,p2;
    private char current_var;
    private int count;
    private boolean won;
    char[][] grid = {{' ',' ',' '},{' ',' ',' '},{' ',' ',' '}};
    Button b00,b01,b02,b10,b11,b12,b20,b21,b22;
    private int p1_won_times,p2_won_times;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        x = findViewById(R.id.x);
        o = findViewById(R.id.o);
        try {
            p1 = getIntent().getExtras().getString("x")==null?"":getIntent().getExtras().getString("x");

        }catch(NullPointerException ne){
            p1 = "X";
        }
        try {
            p2 = getIntent().getExtras().getString("o")==null?"":getIntent().getExtras().getString("o");

        }catch(NullPointerException ne){
            p2 = "O";
        }
        if(p1.equals("") && p2.equals("")){
            x.setVisibility(View.GONE);
            o.setVisibility(View.GONE);
            p1 = "X";
            p2 = "O";
        }
        else if(p1.equals("") || p2.equals("")){

            if (p1.equals("")) {

                p1 = "X";
            }
            else {

                p2 = "O";
            }
        }
        if(getIntent().hasExtra("p1_won_count") || getIntent().hasExtra("p1_won_count")) {
            x.setText(p1 + " : " + getIntent().getExtras().getString("p1_won_count"));
            o.setText(p2 + " : " + getIntent().getExtras().getString("p2_won_count"));
            p1_won_times = Integer.parseInt(getIntent().getExtras().getString("p1_won_count"));
            p2_won_times = Integer.parseInt(getIntent().getExtras().getString("p2_won_count"));
        }
        else{
            x.setText(p1 + " : " + p1_won_times);
            o.setText(p2 + " : " + p2_won_times);
        }
        current_var = 'X';
        count = 0;
        won = false;
        b00 = findViewById(R.id.btn00);
        b01 = findViewById(R.id.btn01);
        b02 = findViewById(R.id.btn02);
        b10 = findViewById(R.id.btn10);
        b11 = findViewById(R.id.btn11);
        b12 = findViewById(R.id.btn12);
        b20 = findViewById(R.id.btn20);
        b21 = findViewById(R.id.btn21);
        b22 = findViewById(R.id.btn22);
        b00.setOnClickListener(this);
        b01.setOnClickListener(this);
        b02.setOnClickListener(this);
        b10.setOnClickListener(this);
        b11.setOnClickListener(this);
        b12.setOnClickListener(this);
        b20.setOnClickListener(this);
        b21.setOnClickListener(this);
        b22.setOnClickListener(this);


    }
    public char checkRows(char[][] grid,String check_what) {
        char c = ' ';
        for (int i = 0; i < grid.length; i++) {
            int cnt = 0;
            for (int j = 0; j < grid.length; j++) {
                if (check_what.equals("row")) {
                    if (j == 0) {
                        c = grid[i][j];
                        cnt ++;
                    } else {
                        if (c == grid[i][j])
                            cnt++;

                    }
                }
                else if (check_what.equals("columns")) {

                    if (j == 0) {
                        c = grid[j][i];
                        cnt += 1;
                    } else {
                        if (c == grid[j][i])
                            cnt += 1;

                    }
                }


            }
            if (cnt == 3 && c!=' ')
                return c;
        }
        return ' ';
    }
    public char check_diagonal(char[][] arr){
        int cnt  = 0;
        char c=' ';
        for(int i=0;i<arr.length;i++) {
            for (int j = 0; j < arr.length; j++) {
                if (j == 0 && i == 0) {
                    c = arr[i][j];
                    cnt += 1;
                } else {
                    if (i == j) {
                        if (c == arr[i][j]) {
                            cnt += 1;
                        }
                    }
                }
            }
        }

        if (cnt == 3)
            return c;
        return ' ';

    }

    public char check_cross_diagonal(char[][] arr) {
        char c = ' ';
        int cnt = 0;
        for (int i = arr.length - 1; i >= 0; i--) {
            for (int j = 0; j < arr.length; j++) {
                if (j == 0 && i == arr.length - 1) {
                    c = arr[i][j];
                    cnt += 1;

                } else if ((j == 1 && i == 1) || (j == 2 && i == 0)) {
                    if (c == arr[i][j])
                        cnt += 1;
                }


            }
        }
        if (cnt == 3)
            return c;
        return ' ';
    }
    public void changeCurrentVar(){
        if (current_var == 'X')
            current_var = 'O';
        else
            current_var = 'X';
        count+=1;
    }

    public void alertBox(char winner){
        String won_player = "";
        if (winner == 'X') {
            won_player = p1;
            p1_won_times++;
        }
        else {
            won_player = p2;
            p2_won_times++;
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Won").setMessage(won_player + " is a winner").setPositiveButton("Play Again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = getIntent();
                intent.putExtra("p1_won_count",p1_won_times+"");
                intent.putExtra("p2_won_count",p2_won_times+"");
                finish();
                startActivity(intent);
            }
        }).setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();

            }
        });
        builder.create();
        builder.show();
        won = true;

    }

    public void finalCheck(){
        if (count >=4){
            char char_row = checkRows(grid, "row");
            char char_col = checkRows(grid, "columns");
            char char_diag = check_diagonal(grid);
            char char_cross_diag = check_cross_diagonal(grid);
            if (char_row == 'X' || char_row == 'O'){
                alertBox(char_row);
            }
            else{
                if (char_col == 'X' || char_col == 'O'){
                    alertBox(char_col);
                }
                else{

                    if (char_diag == 'X' || char_diag == 'O'){
                        alertBox(char_diag);
                    }else{

                        if (char_cross_diag == 'X' || char_cross_diag == 'O'){
                            alertBox(char_cross_diag);
                        }else{


                        }
                    }
                }

            }
        }
        if (count == 9 && !won){
            AlertDialog.Builder build = new AlertDialog.Builder(this);
            build.setTitle("Alert").setMessage("None has won").setPositiveButton("Replay", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = getIntent();
                    intent.putExtra("p1_won_count",p1_won_times+"");
                    intent.putExtra("p2_won_count",p2_won_times+"");
                    finish();
                    startActivity(intent);
                }
            }).setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();

                }
            });
            build.create();
            build.show();
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn00:
                if (current_var == 'X')
                    b00.setBackgroundResource(R.drawable.x);
                else
                    b00.setBackgroundResource(R.drawable.o);
                grid[0][0] = current_var;
                b00.setEnabled(false);
                changeCurrentVar();
                finalCheck();
                break;
            case R.id.btn01:
                if (current_var == 'X')
                    b01.setBackgroundResource(R.drawable.x);
                else
                    b01.setBackgroundResource(R.drawable.o);
                b01.setEnabled(false);
                grid[0][1] = current_var;
                changeCurrentVar();
                finalCheck();
                break;
            case R.id.btn02:
                if (current_var == 'X')
                    b02.setBackgroundResource(R.drawable.x);
                else
                    b02.setBackgroundResource(R.drawable.o);
                b02.setEnabled(false);
                grid[0][2] = current_var;
                changeCurrentVar();
                finalCheck();
                break;
            case R.id.btn10:

                if (current_var == 'X')
                    b10.setBackgroundResource(R.drawable.x);
                else
                    b10.setBackgroundResource(R.drawable.o);
                grid[1][0] = current_var;
                b10.setEnabled(false);
                changeCurrentVar();
                finalCheck();
                break;
            case R.id.btn11:

                if (current_var == 'X')
                    b11.setBackgroundResource(R.drawable.x);
                else
                    b11.setBackgroundResource(R.drawable.o);
                grid[1][1] = current_var;
                b11.setEnabled(false);
                changeCurrentVar();
                finalCheck();
                break;
            case R.id.btn12:
                if (current_var == 'X')
                    b12.setBackgroundResource(R.drawable.x);
                else
                    b12.setBackgroundResource(R.drawable.o);
                grid[1][2] = current_var;
                b12.setEnabled(false);
                changeCurrentVar();
                finalCheck();
                break;
            case R.id.btn20:
                if (current_var == 'X')
                    b20.setBackgroundResource(R.drawable.x);
                else
                    b20.setBackgroundResource(R.drawable.o);
                grid[2][0] = current_var;
                b20.setEnabled(false);
                changeCurrentVar();
                finalCheck();
                break;
            case R.id.btn21:
                if (current_var == 'X')
                    b21.setBackgroundResource(R.drawable.x);
                else
                    b21.setBackgroundResource(R.drawable.o);
                grid[2][1] = current_var;
                b21.setEnabled(false);
                changeCurrentVar();
                finalCheck();
                break;
            case R.id.btn22:
                if (current_var == 'X')
                    b22.setBackgroundResource(R.drawable.x);
                else
                    b22.setBackgroundResource(R.drawable.o);
                grid[2][2] = current_var;
                b22.setEnabled(false);
                changeCurrentVar();
                finalCheck();
                break;

        }
    }
}
