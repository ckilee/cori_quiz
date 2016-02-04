package frolic.br.coriquiz.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.List;
import java.util.Random;

import frolic.br.coriquiz.utils.Utils;

/**
 * Created by ckilee on 23/11/15.
 */
public class QuizDAO extends QuizDBHelper {

    private static String notInString = "";

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

    public void clearTable(String tableName){
        SQLiteDatabase db = this.getWritableDatabase();
        // faz o insert
        db.delete(tableName,null,null);
        db.close();
    }

    public void addDefaultValues(){

        if(!isDBEmpty())
            return;
        Log.i("QuizDAO","Adding default values");
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
        Question q11 = new Question("Quais são as cores contidas no símbolo do Corinthians?", "Preto e branco", "Preto e vermelho", "Preto, branco e vermelho", "Preto e Roxo", "Branco, roxo e vermelho",3);
        this.addQuestion(q11);
        Question q12 = new Question("Qual o nome do jogador chinês que integrou a equipe do corinthians em 2012 e 2013", "Quon", "Zizao", "Li", "Chun", "Chang",2);
        this.addQuestion(q12);
        Question q13 = new Question("Qual é o ano de fundação do Corinthians?", "1915", "1925", "1910", "1829", "1930",3);
        this.addQuestion(q13);
        Question q14 = new Question("Qual desses jogadores nunca jogou pelo Corinthians?", "Luis Fabiano", "Alexandre Pato", "Roberto Rivelino", "Vampeta", "Emerson Sheik",1);
        this.addQuestion(q14);
        Question q15 = new Question("Marcelinho Carioca recebeu uma placa de pelé fazendo um belo gol em cima de qual goleiro?", "Rogério Ceni no São Paulo", "Marcos no Palmeira", "Edinho no Santos", "Zetti no São Paulo", "Bruno no Flamengo",3);
        this.addQuestion(q15);
        Question q16 = new Question("O Corinthians conquistou a primeira copa libetadores jogando contra qual time na final?", "Cruz Azul", "Santos", "São Paulo", "RiverPlate", "Boca Juniors",5);
        this.addQuestion(q16);
        Question q17 = new Question("Em 2012 o Corinthians conquistou o mundial de clubes jogando contra qual time na final?", "Liverpool", "Real Madri", "Vasco da Gama", "Chelsea", "Barcelona",4);
        this.addQuestion(q17);
        Question q18 = new Question("Em 2000 o Corinthians conquistou o mundial de clubes da Fifa jogando contra qual time na final?", "Vasco da Gama", "Real Madri", "São Paulo", "Flamengo", "Fluminense",1);
        this.addQuestion(q18);
        Question q19 = new Question("Como ficou conhecido o período em que os jogadores corinthianos tinham papel ativo nas decisões do clube, onde tudo era resolvido pelo voto?", "Democracia Corinthiana", "República Corinthiana", "Anarquia Corinthiana", "Socialismo Corinthiano", "Capitalismo Corinthiano",1);
        this.addQuestion(q19);
        Question q20 = new Question("Quem é o maior artilheir do corinthians com 305 gols?", "Ronaldo", "Marcelinho Carioca", "Sócrates", "Rivelino", "Cláudio",5);
        this.addQuestion(q20);
        Question q21 = new Question("Qual desses jogadores estrangeiros não jogou no corinthians?", "Gamarra", "Calitos Tevez", "Riquelme", "Guerrero", "Herrera",3);
        this.addQuestion(q21);
        Question q22 = new Question("Qual é o mascote do Corinthians?", "Gambá", "Mosqueteiro", "Cachorro", "São Jorge", "Gavião",2);
        this.addQuestion(q22);
        Question q23 = new Question("Como é apelidado o atual estádio do Corinthians?", "Monumental", "Bombonera", "Caldeirão", "Itaquerão", "Mosquetão",4);
        this.addQuestion(q23);
        Question q24 = new Question("Qual desses goleiros nunca jogou pelo Corinthians?", "Dida", "Julio Cesar", "Ronaldo", "Cássio", "Felipe",2);
        this.addQuestion(q24);
        Question q25 = new Question("Qual foi o período em que o Timão viveu o maior jejum de sua história?", "De 1945 até 1966", "De 1986 até 2009", "De 1977 até 2000", "De 1955 até 1976", "De 1945 até 1979",4);
        this.addQuestion(q25);
    }

    public void addQuestions(List<Question> questionList){
        for (Question curQuestion:questionList) {
            this.addQuestion(curQuestion);
        }
    }

    private boolean isDBEmpty(){
        boolean isDBEmpty = true;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(QuizContract.QUESTION_TABLE, new String[]{QuizContract.Column.ID}, null, null, null, null, null,"2");

        if(c.getCount()>0)
            isDBEmpty = false;
        else
            isDBEmpty = true;
        db.close();
        return isDBEmpty;
    }

    public Question getRandonQuestion(){
        /*
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
        return q;*/
        Log.i("QuizDAO","notInString:"+notInString);

        String query = "select * from "+QuizContract.QUESTION_TABLE+" where "+QuizContract.Column.ID+" not in ("+notInString+") order by random() limit 11";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        Question q = new Question(c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6),Integer.parseInt(c.getString(7)));
        if(notInString.isEmpty()){
            notInString = "'"+c.getString(0)+"'";
        }else{
            notInString = notInString+",'"+c.getString(0)+"'";
        }

        db.close();
        return q;
    }

    public static void resetNotInString() {
        QuizDAO.notInString = "";
    }
}
