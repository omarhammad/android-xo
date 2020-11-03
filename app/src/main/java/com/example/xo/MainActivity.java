package com.example.xo;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {


    private int check_image_type;
    private int clicking_counter;
    private int[] game_state = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    private int[][] winning_pos = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {2, 4, 6}, {0, 4, 8}};
    int x_chr;
    int o_chr;
    TextView the_winner;
    Button play_again;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        x_chr = 1;
        o_chr = 2;
        check_image_type = 1;
        clicking_counter = 1;
        the_winner = findViewById(R.id.winner);
        play_again = findViewById(R.id.play_again);

        onClickImages();

    }

    public void dropImage(View view) {
        ImageView content = (ImageView) view;
        content.setTranslationY(-1500f);

        int chr_pos = Integer.parseInt(content.getTag().toString());

        if (check_image_type == x_chr) {
            content.setImageResource(R.drawable.x_chr);
            game_state[chr_pos] = x_chr;
            check_image_type = o_chr;
        } else {
            content.setImageResource(R.drawable.o_chr);
            game_state[chr_pos] = o_chr;
            check_image_type = x_chr;
        }

        Log.i("State : ", Arrays.toString(game_state));
        content.animate().translationYBy(1500f).setDuration(300);

        if (clicking_counter > 4) {
            checkWinner();
        }

    }

    public void onClickImages() {

        ArrayList<ImageView> images = new ArrayList<>();

        images.add((ImageView) findViewById(R.id.image1));
        images.add((ImageView) findViewById(R.id.image2));
        images.add((ImageView) findViewById(R.id.image3));
        images.add((ImageView) findViewById(R.id.image4));
        images.add((ImageView) findViewById(R.id.image5));
        images.add((ImageView) findViewById(R.id.image6));
        images.add((ImageView) findViewById(R.id.image7));
        images.add((ImageView) findViewById(R.id.image8));
        images.add((ImageView) findViewById(R.id.image9));

        for (final ImageView image : images) {

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.this.dropImage(v);
                    MainActivity.this.clicking_counter++;
                    image.setClickable(false);
                }
            });

        }


    }

    public void checkWinner() {

        for (int pos[] : winning_pos) {

            if (game_state[pos[0]] == game_state[pos[1]] && game_state[pos[1]] == game_state[pos[2]] && game_state[pos[2]] != 0) {
                showingWinner(game_state[pos[0]]);
            } else if (clicking_counter == 9) {
                showingWinner(0);
            }

        }

    }

    public void showingWinner(int win_chr) {

        stopImageClicking();

        if (win_chr == x_chr) {
            the_winner.setText("The winner is : X");
        } else if (win_chr == o_chr) {
            the_winner.setText("The winner is : O");
        } else {
            the_winner.setText("All has won :) ");
        }

        play_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.resetContent();
            }
        });

        the_winner.setVisibility(View.VISIBLE);
        play_again.setVisibility(View.VISIBLE);
        
    }

    public void resetContent() {

        GridLayout game = findViewById(R.id.game);

        the_winner.setVisibility(View.INVISIBLE);
        play_again.setVisibility(View.INVISIBLE);

        for (int i = 0; i < game.getChildCount(); i++) {

            ImageView image = (ImageView) game.getChildAt(i);
            image.setImageDrawable(null);
            image.setClickable(true);

            for (int j = 0; j < game_state.length; j++) {
                game_state[j] = 0;
            }

        }

        for (int j = 0; j < game_state.length; j++) {
            game_state[j] = 0;
        }

        check_image_type = 1;
        clicking_counter = 1;

    }

    public void stopImageClicking() {

        GridLayout game = findViewById(R.id.game);

        for (int i = 0; i < game.getChildCount(); i++) {
            ImageView image = (ImageView) game.getChildAt(i);
            image.setClickable(false);

        }
    }


}