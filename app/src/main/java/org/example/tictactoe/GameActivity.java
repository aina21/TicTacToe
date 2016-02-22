package org.example.tictactoe;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class GameActivity extends AppCompatActivity {

    public static final String KEY_RESTORE = "key_restore";
    public static final String PREF_RESTORE = "pref_restore";
    private GameActivityFragment mGameFragment ;

    //restart game
    public void restartGame() {
        mGameFragment.restartGame();
    }

    //reporting winner
    public void reportWinner(final Tile.Owner winner)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.declare_winner, winner));
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        final Dialog dialog = builder.create();
        dialog.show();

        //Reset the board to the initial position
        mGameFragment.initialGame();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Restore game here...
        FragmentManager fm = getSupportFragmentManager();
        mGameFragment = (GameActivityFragment) fm.findFragmentById(R.id.fragment_game);
        boolean restore = getIntent().getBooleanExtra(KEY_RESTORE, false);
        if (restore) {
            String gameData = getPreferences(MODE_PRIVATE).getString(PREF_RESTORE, null);
            if (gameData != null) {
                mGameFragment.putState(gameData);
            }
        }

        Log.d("UT3", "restore = " + restore);
    }

    @Override
    protected void onPause() {
        super.onPause();
        String gameData = mGameFragment.setState();
        getPreferences(MODE_PRIVATE).edit().putString(PREF_RESTORE, gameData).commit();

        Log.d("UT3", "state = " + gameData);
    }



}
