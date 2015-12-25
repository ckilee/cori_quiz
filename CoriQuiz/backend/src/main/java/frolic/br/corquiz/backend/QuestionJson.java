package frolic.br.corquiz.backend;

import java.util.ArrayList;

/**
 * Created by ckilee on 24/12/15.
 */
public class QuestionJson {
    private int version = 0;
    private ArrayList<Question> questionArrayList = new ArrayList<Question>();

    public ArrayList<Question> getQuestionArrayList() {
        return questionArrayList;
    }

    public void add(Question q){
        questionArrayList.add(q);
    }

    public int getVersion() {
        return version;
    }

    public QuestionJson setVersion(int version) {
        this.version = version;
        return this;
    }
}
