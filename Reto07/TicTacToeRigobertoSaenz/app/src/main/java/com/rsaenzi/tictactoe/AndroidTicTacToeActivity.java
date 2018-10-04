package com.rsaenzi.tictactoe;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AndroidTicTacToeActivity extends Activity {

    // Represents the internal state of the game
    private TicTacToeGame mGame;

    // View making up the board
    private BoardView mBoardView;

    // Various text displayed
    private TextView mInfoTextView;
    private TextView mHumanScoreTextView;
    private TextView mComputerScoreTextView;
    private TextView mTieScoreTextView;

    private boolean mGameOver = false;
    private int mHumanWins = 0;
    private int mComputerWins = 0;
    private int mTies = 0;
    private char mGoFirst;

    //static final int DIALOG_DIFFICULTY_ID = 0;
    static final int DIALOG_QUIT_ID = 1;

    MediaPlayer mHumanMediaPlayer;
    MediaPlayer mComputerMediaPlayer;

    private SharedPreferences mPrefs;

    private  boolean mSoundOn;

    @Override
    protected void onResume() {
        super.onResume();

        mHumanMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.click);
        mComputerMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.swish);
    }

    @Override
    protected void onPause() {
        super.onPause();

        mHumanMediaPlayer.release();
        mComputerMediaPlayer.release();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_tic_tac_toe);

        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        mGame = new TicTacToeGame();
        mBoardView = findViewById(R.id.board);
        mBoardView.setGame(mGame);

        // Listen for touches on the board
        mBoardView.setOnTouchListener(mTouchListener);

        // TextView
        mInfoTextView = findViewById(R.id.information);
        mHumanScoreTextView = findViewById(R.id.humanWins);
        mComputerScoreTextView = findViewById(R.id.computerWins);
        mTieScoreTextView = findViewById(R.id.ties);

        // Restore the scores
        mHumanWins = mPrefs.getInt("mHumanWins", 0);
        mComputerWins = mPrefs.getInt("mComputerWins", 0);
        mTies = mPrefs.getInt("mTies", 0);

        // Restore the scores from the persistent preference data source
        mSoundOn = mPrefs.getBoolean("sound", true);
        String difficultyLevel = mPrefs.getString("difficulty_level",
                getResources().getString(R.string.difficulty_harder));

        if (difficultyLevel.equals(getResources().getString(R.string.difficulty_easy)))
            mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Easy);

        else if (difficultyLevel.equals(getResources().getString(R.string.difficulty_harder)))
            mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Harder);

        else
            mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Expert);

        if (savedInstanceState == null) {
            startNewGame();
        }
        else {
            // Restore the game's state
            mGame.setBoardState(savedInstanceState.getCharArray("board"));
            mGameOver = savedInstanceState.getBoolean("mGameOver");
            mInfoTextView.setText(savedInstanceState.getCharSequence("info"));
            mHumanWins = savedInstanceState.getInt("mHumanWins");
            mComputerWins = savedInstanceState.getInt("mComputerWins");
            mTies = savedInstanceState.getInt("mTies");
            mGoFirst = savedInstanceState.getChar("mGoFirst");
        }
        displayScores();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putCharArray("board", mGame.getBoardState());
        outState.putBoolean("mGameOver", mGameOver);
        outState.putInt("mHumanWins", Integer.valueOf(mHumanWins));
        outState.putInt("mComputerWins", Integer.valueOf(mComputerWins));
        outState.putInt("mTies", Integer.valueOf(mTies));
        outState.putCharSequence("info", mInfoTextView.getText());
        outState.putChar("mGoFirst", mGoFirst);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mGame.setBoardState(savedInstanceState.getCharArray("board"));
        mGameOver = savedInstanceState.getBoolean("mGameOver");
        mInfoTextView.setText(savedInstanceState.getCharSequence("info"));
        mHumanWins = savedInstanceState.getInt("mHumanWins");
        mComputerWins = savedInstanceState.getInt("mComputerWins");
        mTies = savedInstanceState.getInt("mTies");
        mGoFirst = savedInstanceState.getChar("mGoFirst");
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Save the current scores
        SharedPreferences.Editor ed = mPrefs.edit();
        ed.putInt("mHumanWins", mHumanWins);
        ed.putInt("mComputerWins", mComputerWins);
        ed.putInt("mTies", mTies);
        ed.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.new_game:
                startNewGame();
                return true;

            case R.id.settings:
                startActivityForResult(new Intent(this, Settings.class), 0);
                return true;

            case R.id.reset_scores:
                reset();
                return true;

            case R.id.quit:
                showDialog(DIALOG_QUIT_ID);
                return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RESULT_CANCELED) {
            // Apply potentially new settings

            mSoundOn = mPrefs.getBoolean("sound", true);

            String difficultyLevel = mPrefs.getString("difficulty_level",
                    getResources().getString(R.string.difficulty_harder));

            if (difficultyLevel.equals(getResources().getString(R.string.difficulty_easy)))
                mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Easy);

            else if (difficultyLevel.equals(getResources().getString(R.string.difficulty_harder)))
                mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Harder);

            else
                mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Expert);
        }
    }

    // Set up the game board.
    private void startNewGame() {

        mGameOver = false;

        // Reset the view
        mGame.clearBoard();
        mBoardView.invalidate();   // Redraw the board

        // Human goes first
        mInfoTextView.setText(R.string.first_human);
    }

    private void reset() {
        mHumanWins = 0;
        mComputerWins = 0;
        mTies = 0;
        displayScores();
    }

    private void displayScores() {
        mHumanScoreTextView.setText("Human: " + Integer.toString(mHumanWins));
        mComputerScoreTextView.setText("Android: " + Integer.toString(mComputerWins));
        mTieScoreTextView.setText("Ties: " + Integer.toString(mTies));
    }

    // Listen for touches on the board
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {

        public boolean onTouch(View v, MotionEvent event) {

            if (mGameOver == true) {
                return false;
            }

            // Determine which cell was touched
            int col = (int) event.getX() / mBoardView.getBoardCellWidth();
            int row = (int) event.getY() / mBoardView.getBoardCellHeight();
            int pos = row * 3 + col;

            if (!mGameOver && setMove(TicTacToeGame.HUMAN_PLAYER, pos))	{

                // If no winner yet, let the computer make a move
                int winner = mGame.checkForWinner();
                if (winner == 0) {
                    mInfoTextView.setText(R.string.turn_computer);
                    int move = mGame.getComputerMove();
                    setMove(TicTacToeGame.COMPUTER_PLAYER, move);
                    winner = mGame.checkForWinner();
                }

                SharedPreferences.Editor ed = mPrefs.edit();

                if (winner == 0) {
                    mInfoTextView.setText(R.string.turn_human);

                } else if (winner == 1) {
                    mInfoTextView.setText(R.string.result_tie);
                    mTies += 1;
                    mGameOver = true;

                    String defaultMessage = getResources().getString(R.string.result_computer_wins);

                    SharedPreferences.Editor ed1 = mPrefs.edit();
                    ed1.putString("victory_message", defaultMessage);
                    ed1.commit();

                    mInfoTextView.setText(mPrefs.getString("victory_message", defaultMessage));

                } else if (winner == 2) {
                    mInfoTextView.setText(R.string.result_human_wins);
                    mHumanWins += 1;
                    mGameOver = true;

                    String defaultMessage = getResources().getString(R.string.result_human_wins);

                    SharedPreferences.Editor ed2 = mPrefs.edit();
                    ed2.putString("victory_message", defaultMessage);
                    ed2.commit();

                    mInfoTextView.setText(mPrefs.getString("victory_message", defaultMessage));

                } else {
                    mInfoTextView.setText(R.string.result_computer_wins);
                    mComputerWins += 1;
                    mGameOver = true;

                    String defaultMessage = getResources().getString(R.string.result_tie);

                    SharedPreferences.Editor ed3 = mPrefs.edit();
                    ed3.putString("victory_message", defaultMessage);
                    ed3.commit();

                    mInfoTextView.setText(mPrefs.getString("victory_message", defaultMessage));
                }

                displayScores();
            }

            // So we aren't notified of continued events when finger is moved
            return false;
        }

        private boolean setMove(char player, int location) {

            if(mSoundOn == true) {
                //mComputerMediaPlayer.start();
                mHumanMediaPlayer.start();    // Play the sound effect
            }

            if (mGame.setMove(player, location)) {
                mBoardView.invalidate();   // Redraw the board
                return true;
            }
            return false;
        }
    };
}