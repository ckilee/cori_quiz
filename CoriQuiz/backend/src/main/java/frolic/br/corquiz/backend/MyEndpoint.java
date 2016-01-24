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
    private int serverDbVersion = 4;
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

        Question q1=new Question("Quais são os nomes dos ex jogadores do Corinthians que foram embora para o Flamengo?","Emerson e Gil", "Emerson e Guerrero", "Guerrero e Danilo", "Luciano e Malcom", "Ralf e Fagner", 2);
        questionJson.add(q1);
        Question q2=new Question("Qual foi o time que o Corinthians enfrentou e venceu por 5 a 3 no Paulistão 2015?", "Penapolense", "Palmeiras", "Santos", "Ituano", "Bragantino", 1);
        questionJson.add(q2);
        Question q3=new Question("Jogando em casa, para que time o Corinthians foi eliminado do Paulistão 2015 empatando?","São Paulo", "Santos","Palmeiras","Ituano","Bragantino",3);
        questionJson.add(q3);
        Question q4=new Question("De quem o Corinthians venceu de 4 a 0 na Copa libertadores com tres gols de Guerrero?","Danubio", "San Lorenzo", "Boca Juniors","River Plate","São Paulo", 1);
        questionJson.add(q4);
        Question q5=new Question("De que time o Corinthians venceu e passou da Pré Libertadores para a Libertadores?","Tolima","Palmeiras","Boca Juniors","Once Caldas", "Internacional", 4);
        questionJson.add(q5);
        Question q6=new Question("Qual o nome do jogador do Corinthians que foi o Artilheiro do time no Paulistão 2015?","Paolo Guerrero","Tevez","Dinei","Neto", "Malcom", 1);
        questionJson.add(q6);
        Question q7=new Question("Para que time o Corinthians foi eliminado da Libertadores 2015?","Danubio do Uruguai","São Paulo","Emelec do Equador","Atlético Mineiro", "Guarani do Paraguai", 5);
        questionJson.add(q7);
        Question q8=new Question("O Timão venceu o São Paulo na Libertadores por qual placar na Arena Corinthians?","2 a 0","2 a 1","5 a 0","7 a 1", "NDA", 1);
        questionJson.add(q8);
        Question q9=new Question("Contra qual time o atacante Vagner Love marcou seu primeiro gol com a camisa do Corinthians?","Santos","Bragantino","São Paulo","Botafogo SP", "Penapolense", 2);
        questionJson.add(q9);
        Question q10 = new Question("Qual foi o placar da partida em que o São Paulo venceu o Corinthians na Libertadores 2015?","2 a 0","3 a 0","3 a 2","1 a 0", "2 a 1", 1);
        questionJson.add(q10);
        Question q11 = new Question("Quais são as cores contidas no símbolo do Corinthians?", "Preto e branco", "Preto e vermelho", "Preto, branco e vermelho", "Preto e Roxo", "Branco, roxo e vermelho",3);
        questionJson.add(q11);
        Question q12 = new Question("Qual o nome do jogador chinês que integrou a equipe do corinthians em 2012 e 2013", "Quon", "Zizao", "Li", "Chun", "Chang",2);
        questionJson.add(q12);
        Question q13 = new Question("Qual é o ano de fundação do Corinthians?", "1915", "1925", "1910", "1829", "1930",3);
        questionJson.add(q13);
        Question q14 = new Question("Qual desses jogadores nunca jogou pelo Corinthians?", "Luis Fabiano", "Alexandre Pato", "Roberto Rivelino", "Vampeta", "Emerson Sheik",1);
        questionJson.add(q14);
        Question q15 = new Question("Marcelinho Carioca recebeu uma placa de pelé fazendo um belo gol em cima de qual goleiro?", "Rogério Ceni no São Paulo", "Marcos no Palmeira", "Edinho no Santos", "Zetti no São Paulo", "Bruno no Flamengo",3);
        questionJson.add(q15);
        Question q16 = new Question("O Corinthians conquistou a primeira copa libetadores jogando contra qual time na final?", "Cruz Azul", "Santos", "São Paulo", "RiverPlate", "Boca Juniors",5);
        questionJson.add(q16);
        Question q17 = new Question("Em 2012 o Corinthians conquistou o mundial de clubes jogando contra qual time na final?", "Liverpool", "Real Madri", "Vasco da Gama", "Chelsea", "Barcelona",4);
        questionJson.add(q17);
        Question q18 = new Question("Em 2000 o Corinthians conquistou o mundial de clubes da Fifa jogando contra qual time na final?", "Vasco da Gama", "Real Madri", "São Paulo", "Flamengo", "Fluminense",1);
        questionJson.add(q18);
        Question q19 = new Question("Como ficou conhecido o período em que os jogadores corinthianos tinham papel ativo nas decisões do clube, onde tudo era resolvido pelo voto?", "Democracia Corinthiana", "República Corinthiana", "Anarquia Corinthiana", "Socialismo Corinthiano", "Capitalismo Corinthiano",1);
        questionJson.add(q19);
        Question q20 = new Question("Quem é o maior artilheir do corinthians com 305 gols?", "Ronaldo", "Marcelinho Carioca", "Sócrates", "Rivelino", "Cláudio",5);
        questionJson.add(q20);
        Question q21 = new Question("Qual desses jogadores estrangeiros não jogou no corinthians?", "Gamarra", "Calitos Tevez", "Mascherano", "Guerrero", "Herrera",3);
        questionJson.add(q21);
        Question q22 = new Question("Qual é o mascote do Corinthians?", "Gambá", "Mosqueteiro", "Cachorro", "São Jorge", "Gavião",2);
        questionJson.add(q22);
        Question q23 = new Question("Como é apelidado o atual estádio do Corinthians?", "Monumental", "Bombonera", "Caldeirão", "Itaquerão", "Mosquetão",4);
        questionJson.add(q23);
        Question q24 = new Question("Qual desses goleiros nunca jogou pelo Corinthians?", "Dida", "Julio Cesar", "Ronaldo", "Cássio", "Felipe",2);
        questionJson.add(q24);
        Question q25 = new Question("Qual foi o período em que o Timão viveu o maior jejum de sua história?", "De 1945 até 1966", "De 1986 até 2009", "De 1977 até 2000", "De 1955 até 1976", "De 1945 até 1979",4);
        questionJson.add(q25);

    }

}
