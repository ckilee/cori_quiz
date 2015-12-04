package frolic.br.coriquiz.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Random;

import frolic.br.coriquiz.utils.Utils;

/**
 * Created by ckilee on 23/11/15.
 */
public class QuizDAO extends QuizDBHelper {

    public static final String TAG = QuizDAO.class.getSimpleName();

    public QuizDAO(Context context) {
        super(context);
    }

    private void addQuestion(Question q){
        Log.d("QuizDBHelper", "addQuestion" + q.toString());
        SQLiteDatabase db = this.getWritableDatabase();
        // Cria o ContentValues para adicionar: "column"/value
        ContentValues values = new ContentValues();
        values.put(QuizContract.Column.QUESTION, q.getQuestion());
        values.put(QuizContract.Column.ANSWER1, q.getAnswer1());
        values.put(QuizContract.Column.ANSWER2, q.getAnswer2());
        values.put(QuizContract.Column.ANSWER3, q.getAnswer3());
        values.put(QuizContract.Column.ANSWER4, q.getAnswer4());
        values.put(QuizContract.Column.ANSWER5, q.getAnswer5());
        values.put(QuizContract.Column.RIGHT_ANSWER, q.getRightAnwser());
        // faz o insert
        db.insert(QuizContract.QUESTION_TABLE, null, values);
        db.close();
    }

    public void addUserIfNotExist(){
        //Log.d("QuizDBHelper", "addUser" + user.toString());
        SQLiteDatabase db = this.getWritableDatabase();
        // Cria o ContentValues para adicionar: "column"/value
        ContentValues values = new ContentValues();
        values.put(QuizContract.Column_User.ID, User.id);
        values.put(QuizContract.Column_User.Name, User.name);
        // faz o insert
        try {
            db.insert(QuizContract.USER_TABLE, null, values);
        }catch (SQLiteConstraintException e){
            e.printStackTrace();
        }

        db.close();
    }

    public void addDefaultValues(){
        if(!isDBEmpty())
            return;
        Question q1=new Question("Quais são os nomes dos ex jogadores do Corinthians que foram embora para o Flamengo?","Emerson e Gil", "Emerson e Guerrero", "Guerrero e Danilo", "Luciano e Malcom", "Ralf e Fagner", 2);
        this.addQuestion(q1);
        Question q2=new Question("Qual foi o time que o Corinthians enfrentou e venceu por 5 a 3 no Paulistão 2015?", "Penapolense", "Palmeiras", "Santos", "Ituano", "Bragantino", 1);
        this.addQuestion(q2);
        Question q3=new Question("Jogando em casa, para que time o Corinthians foi eliminado do Paulistão 2015 empatando?","São Paulo", "Santos","Palmeiras","Ituano","Bragantino",3);
        this.addQuestion(q3);
        Question q4=new Question("De quem o Corinthians venceu de 4 a 0 na Copa libertadores com tres gols de Guerrero?","Danubio", "San Lorenzo", "Boca Juniors","River Plate","São Paulo", 1);
        this.addQuestion(q4);
        Question q5=new Question("De que time o Corinthians venceu e passou da Pré Libertadores para a Libertadores?","Tolima","Palmeiras","Boca Juniors","Once Caldas", "Internacional", 4);
        this.addQuestion(q5);
        Question q6=new Question("Qual o nome do jogador do Corinthians que foi o Artilheiro do time no Paulistão 2015?","Paolo Guerrero","Tevez","Dinei","Neto", "Malcom", 1);
        this.addQuestion(q6);
        Question q7=new Question("Para que time o Corinthians foi eliminado da Libertadores 2015?","Danubio do Uruguai","São Paulo","Emelec do Equador","Atlético Mineiro", "Guarani do Paraguai", 5);
        this.addQuestion(q7);
        Question q8=new Question("O Timão venceu o São Paulo na Libertadores por qual placar na Arena Corinthians?","2 a 0","2 a 1","5 a 0","7 a 1", "NDA", 1);
        this.addQuestion(q8);
        Question q9=new Question("Contra qual time o atacante Vagner Love marcou seu primeiro gol com a camisa do Corinthians?","Santos","Bragantino","São Paulo","Botafogo SP", "Penapolense", 2);
        this.addQuestion(q9);
        Question q10=new Question("Qual foi o placar da partida em que o São Paulo venceu o Corinthians na Libertadores 2015?","2 a 0","3 a 0","3 a 2","1 a 0", "2 a 1", 1);
        this.addQuestion(q10);
    }

    private boolean isDBEmpty(){
        boolean isDBEmpty = true;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(QuizContract.QUESTION_TABLE, new String[]{QuizContract.Column.ID}, QuizContract.Column.ID + " = ?", new String[]{"1"}, null, null, null);

        if(c.getCount()>0)
            isDBEmpty = false;
        else
            isDBEmpty = true;
        db.close();
        return isDBEmpty;
    }

    public Question getRandonQuestion(){
        String query = "select max("+QuizContract.Column.ID+") from "+QuizContract.QUESTION_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        String maxId = c.getString(0);
        int randomId = Utils.randInt(1, Integer.parseInt(maxId));

        c = db.query(QuizContract.QUESTION_TABLE,null,QuizContract.Column.ID+" = ?",new String[]{Integer.toString(randomId)},null,null,null);
        c.moveToFirst();
        Question q = new Question(c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6),Integer.parseInt(c.getString(7)));

        db.close();
        return q;
    }

}
