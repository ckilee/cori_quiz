/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package frolic.br.corquiz.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.repackaged.com.google.gson.Gson;
import com.google.appengine.repackaged.com.google.gson.GsonBuilder;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.corquiz.br.frolic",
                ownerName = "backend.corquiz.br.frolic",
                packagePath = ""
        )
)
public class MyEndpoint {
    private int serverDbVersion = 3;
    QuestionJson questionJson = new QuestionJson().setVersion(serverDbVersion);

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "getQuestions")
    public MyBean sayHi(@Named("dbVersion") int dbVersion) {
        MyBean response = new MyBean();
        if(dbVersion<serverDbVersion) {
            Gson gson = new GsonBuilder().create();
            addDefaultValues();
            String retVal = gson.toJson(questionJson);
            response.setData(retVal);
        }
        else {
            response.setData("Local Db version is higher or equal server version");
        }

        return response;
    }

    public void addDefaultValues(){

        Question q1=new Question("1Quais são os nomes dos ex jogadores do Corinthians que foram embora para o Flamengo?","Emerson e Gil", "Emerson e Guerrero", "Guerrero e Danilo", "Luciano e Malcom", "Ralf e Fagner", 2);
        questionJson.add(q1);
        Question q2=new Question("1Qual foi o time que o Corinthians enfrentou e venceu por 5 a 3 no Paulistão 2015?", "Penapolense", "Palmeiras", "Santos", "Ituano", "Bragantino", 1);
        questionJson.add(q2);
        Question q3=new Question("1Jogando em casa, para que time o Corinthians foi eliminado do Paulistão 2015 empatando?","São Paulo", "Santos","Palmeiras","Ituano","Bragantino",3);
        questionJson.add(q3);
        Question q4=new Question("1De quem o Corinthians venceu de 4 a 0 na Copa libertadores com tres gols de Guerrero?","Danubio", "San Lorenzo", "Boca Juniors","River Plate","São Paulo", 1);
        questionJson.add(q4);
        Question q5=new Question("1De que time o Corinthians venceu e passou da Pré Libertadores para a Libertadores?","Tolima","Palmeiras","Boca Juniors","Once Caldas", "Internacional", 4);
        questionJson.add(q5);
        Question q6=new Question("1Qual o nome do jogador do Corinthians que foi o Artilheiro do time no Paulistão 2015?","Paolo Guerrero","Tevez","Dinei","Neto", "Malcom", 1);
        questionJson.add(q6);
        Question q7=new Question("1Para que time o Corinthians foi eliminado da Libertadores 2015?","Danubio do Uruguai","São Paulo","Emelec do Equador","Atlético Mineiro", "Guarani do Paraguai", 5);
        questionJson.add(q7);
        Question q8=new Question("1O Timão venceu o São Paulo na Libertadores por qual placar na Arena Corinthians?","2 a 0","2 a 1","5 a 0","7 a 1", "NDA", 1);
        questionJson.add(q8);
        Question q9=new Question("1Contra qual time o atacante Vagner Love marcou seu primeiro gol com a camisa do Corinthians?","Santos","Bragantino","São Paulo","Botafogo SP", "Penapolense", 2);
        questionJson.add(q9);
        Question q10=new Question("1Qual foi o placar da partida em que o São Paulo venceu o Corinthians na Libertadores 2015?","2 a 0","3 a 0","3 a 2","1 a 0", "2 a 1", 1);
        questionJson.add(q10);
    }

}
