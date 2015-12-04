package frolic.br.coriquiz.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ckilee on 23/11/15.
 */
public class QuizDBHelper extends SQLiteOpenHelper{
    public QuizDBHelper(Context context) {
        super(context, QuizContract.DB_NAME, null, QuizContract.DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL para criar a tabela quiz
        String CREATE_QUESTION_TABLE =
                "CREATE TABLE " + QuizContract.QUESTION_TABLE + " ( "
                        + QuizContract.Column.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + QuizContract.Column.QUESTION + " TEXT NOT NULL, "
                        + QuizContract.Column.ANSWER1 + " TEXT NOT NULL, "
                        + QuizContract.Column.ANSWER2 + " TEXT NOT NULL, "
                        + QuizContract.Column.ANSWER3 + " TEXT NOT NULL, "
                        + QuizContract.Column.ANSWER4 + " TEXT NOT NULL, "
                        + QuizContract.Column.ANSWER5 + " TEXT NOT NULL, "
                        + QuizContract.Column.RIGHT_ANSWER + " INTEGER NOT NULL)";
        db.execSQL(CREATE_QUESTION_TABLE);

        // SQL para criar a tabela quiz
        String CREATE_USER_TABLE =
                "CREATE TABLE " + QuizContract.USER_TABLE + " ( "
                        + QuizContract.Column_User.ID + " INTEGER PRIMARY KEY, "
                        + QuizContract.Column_User.Name + " TEXT NOT NULL)";
        db.execSQL(CREATE_USER_TABLE);

        // SQL para criar a tabela quiz
        String CREATE_RANKING_TABLE =
                "CREATE TABLE " + QuizContract.RANKING_TABLE + " ( "
                        + QuizContract.Column_Ranking.ID + " INTEGER PRIMARY KEY, "
                        + QuizContract.Column_Ranking.USER_ID + " INTEGER NOT NULL, "
                        + QuizContract.Column_Ranking.SCORE + " INTEGER NOT NULL)";
        db.execSQL(CREATE_RANKING_TABLE);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Ainda não precisa fazer update
    }
}
