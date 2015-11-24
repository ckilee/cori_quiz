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
        // SQL para criar a tabela anuncio
        String CREATE_ANUNCIO_TABLE =
                "CREATE TABLE " + QuizContract.QUESTION_TABLE + " ( "
                        + QuizContract.Column.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + QuizContract.Column.QUESTION + " TEXT NOT NULL, "
                        + QuizContract.Column.ANSWER1 + " TEXT NOT NULL, "
                        + QuizContract.Column.ANSWER2 + " TEXT NOT NULL, "
                        + QuizContract.Column.ANSWER3 + " TEXT NOT NULL, "
                        + QuizContract.Column.ANSWER4 + " TEXT NOT NULL, "
                        + QuizContract.Column.ANSWER5 + " TEXT NOT NULL, "
                        + QuizContract.Column.RIGHT_ANSWER + " INTEGER NOT NULL)";
        db.execSQL(CREATE_ANUNCIO_TABLE);

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Ainda não precisa fazer update
    }
}
