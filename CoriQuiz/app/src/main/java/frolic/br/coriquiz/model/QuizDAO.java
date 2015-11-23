package frolic.br.coriquiz.model;

import android.content.Context;

/**
 * Created by ckilee on 23/11/15.
 */
public class QuizDAO extends QuizDBHelper {

    public static final String TAG = QuizDAO.class.getSimpleName();

    public QuizDAO(Context context) {
        super(context);
    }

    /*
    public void addAnuncio(Anuncio anuncio) {
        Log.d(TAG, "addAnuncio " + anuncio.toString());
        SQLiteDatabase db = this.getWritableDatabase();
        // Cria o ContentValues para adicionar: "column"/value
        ContentValues values = new ContentValues();
        values.put(AnuncioContract.Column.ANUNCIANTE, anuncio.getAnunciante());
        values.put(AnuncioContract.Column.EMAIL, anuncio.getEmail());
        values.put(AnuncioContract.Column.DESCRICAO, anuncio.getDescricao());
        values.put(AnuncioContract.Column.TIPO, anuncio.getTipo());
        values.put(AnuncioContract.Column.VALOR, anuncio.getValor());
        values.put(AnuncioContract.Column.DATA_CRIACAO, anuncio.getDataCriacao().
                getTime());
        // faz o insert
        db.insert(AnuncioContract.ANUNCIO_TABLE, null, values);
        db.close();
    }*/
}
