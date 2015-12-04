package frolic.br.coriquiz.model;

import android.provider.BaseColumns;

/**
 * Created by ckilee on 23/11/15.
 */
public class QuizContract {
    // Constantes do banco
    public static final String DB_NAME = "quiz.db";
    public static final int DB_VERSION = 1;
    public static final String QUESTION_TABLE = "QUESTION";
    public static final String USER_TABLE = "USER";
    public static final String RANKING_TABLE = "RANKING";
    public static final String DEFAULT_SORT = Column.QUESTION + " DESC";
    public static final String[] COLUMN_NAMES = {
            Column.ID, Column.QUESTION, Column.ANSWER1, Column.ANSWER2,
            Column.ANSWER3, Column.ANSWER4, Column.ANSWER5, Column.RIGHT_ANSWER };
    public class Column {
        public static final String ID = BaseColumns._ID;
        public static final String QUESTION = "QUESTION";
        public static final String ANSWER1 = "ANSWER1";
        public static final String ANSWER2 = "ANSWER2";
        public static final String ANSWER3 = "ANSWER3";
        public static final String ANSWER4 = "ANSWER4";
        public static final String ANSWER5 = "ANSWER5";
        public static final String RIGHT_ANSWER = "RIGHT_ANSWER";
    }

    public class Column_User {
        public static final String ID = BaseColumns._ID;
        public static final String Name = "NAME";
    }

    public class Column_Ranking {
        public static final String ID = BaseColumns._ID;
        public static final String USER_ID = "USER_ID";
        public static final String SCORE = "SCORE";
    }
}
