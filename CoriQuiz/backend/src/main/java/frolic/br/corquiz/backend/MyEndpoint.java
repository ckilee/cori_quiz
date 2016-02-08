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
    private int serverDbVersion = 0;
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
        Question q21 = new Question("Qual desses jogadores estrangeiros não jogou no corinthians?", "Gamarra", "Calitos Tevez", "Riquelme", "Guerrero", "Herrera",3);
        questionJson.add(q21);
        Question q22 = new Question("Qual é o mascote do Corinthians?", "Gambá", "Mosqueteiro", "Cachorro", "São Jorge", "Gavião",2);
        questionJson.add(q22);
        Question q23 = new Question("Como é apelidado o atual estádio do Corinthians?", "Monumental", "Bombonera", "Caldeirão", "Itaquerão", "Mosquetão",4);
        questionJson.add(q23);
        Question q24 = new Question("Qual desses goleiros nunca jogou pelo Corinthians?", "Dida", "Julio Cesar", "Ronaldo", "Cássio", "Felipe",2);
        questionJson.add(q24);
        Question q25 = new Question("Qual foi o período em que o Timão viveu o maior jejum de sua história?", "De 1945 até 1966", "De 1986 até 2009", "De 1977 até 2000", "De 1955 até 1976", "De 1945 até 1979",4);
        questionJson.add(q25);
        Question q26 = new Question("Qual desses times nunca conquistou o campeonato mundial de clubes?", "Corinthians", "São Paulo", "Santos", "Internacional", "Palmeiras",5);
        questionJson.add(q26);
        Question q27 = new Question("Qual jogador corinthianos tinha o apelido de Talismã?", "Tupãnzinho", "Cleber", "Neto", "Sócrates", "Rivelino",1);
        questionJson.add(q27);
        Question q28 = new Question("Corinthians foi coroado campeão brasileiro de 2015 ganhando de 6x1 contra qual time?", "Vasco da Gama", "Atlético Mineiro", "Palmeiras", "São Paulo", "Santos",4);
        questionJson.add(q28);
        Question q29 = new Question("Como é conhecido popularmente os confrontos entre Corinthians e Palmeiras?", "Majestoso", "Derby Paulista", "Clássico Alvinegro", "FlaFlu", "Choque Rei",2);
        questionJson.add(q29);
        Question q30 = new Question("Quais desses gritos não é usado pela torcida do corinthians?", "Loucos por ti Corinthians!", "Não pára não pára não pára!", "Vai Corinthians!", "Ooohh Todo poderoso Timão", "Vamos Corinthias, vamos ser campeão!",5);
        questionJson.add(q30);
        Question q32 = new Question("Quais jogadores foram vendidos para times Chineses em 2016?", "Jadson e Ralf", "Cleber e Cássio", "Wagner Love e Gil", "Elias e Fagner", "Uendel e Bruno Henrique",1);
        questionJson.add(q32);
        Question q33 = new Question("Quais jogadores foram vendidos para times Chineses em 2016?", "Jadson e Ralf", "Cleber e Cássio", "Wagner Love e Gil", "Elias e Fagner", "Uendel e Bruno Henrique",1);
        questionJson.add(q33);
        Question q34 = new Question("Qual time Rivelino também atuou depois de sair do Corinthians?", "Palmeiras", "São Paulo", "Botafogo", "Fluminense", "Vasco",4);
        questionJson.add(q34);
        Question q35 = new Question("Qual o nome do jogador corinthiano que recebeu o apelido de 'xodó da fiel'? ", "Marcelinho Carioca ", "Tevez", "Dinei", "Neto", "Rivelino", 4);
        questionJson.add(q35);
        Question q36 = new Question("Qual o nome do jogador corinthiano que imitou um porco no jogo contra o Palmeiras?", "Marcelinho Carioca ", "Edilson", "Neto", "Viola", "Vampeta", 4);
        questionJson.add(q36);
        Question q37 = new Question("Qual o nome do jogador corinthiano que participou de três conquistas do Campeonatos Brasileiros - 1990, 1998, 1999 ?", "Marcelinho Carioca ", "Tevez", "Luisinho", "Neto", "Dinei", 5);
        questionJson.add(q37);
        Question q38 = new Question("Qual o nome do jogador corinthiano que marcou o gol decisivo na final do Campeonato Paulista de 1993 e imitou um porco?", "Marcelinho Carioca ", "Tevez", "Dinei", "Neto", "Viola", 5);
        questionJson.add(q38);
        Question q39 = new Question("Após quanto tempo depois de seu nascimento o Corinthians deixou de ser um time de Varzea e passou para o futebol oficial ?", "2 anos", "1 ano", "4 anos", "7 anos", "8 anos", 1);
        questionJson.add(q39);
        Question q40 = new Question("Quem inventou o apelido de 'Bambi' para os São Paulinos ?", "Marcelinho Carioca", "Vampeta", "Sócrates", "Dinei", "Idário", 2);
        questionJson.add(q40);
        Question q41 = new Question("Qual é o estádio do Corinthians ?", "Pacaembu", "Arena Corinthians", "Morumbi", "Maracanã", "Mineirão", 2);
        questionJson.add(q41);
        Question q42 = new Question("A torcida do Corinthians é a maior de(o) :", "Mundo", "Universo", "Brasil", "São Paulo", "Europa", 4);
        questionJson.add(q42);
        Question q43 = new Question("Quem era conhecido como Calcanhar de Ouro?", "Sócrates", "Marcelinho Carioca", "Rivelino", "Biro-Biro", "Rafael", 1);
        questionJson.add(q43);
        Question q44 = new Question("Qual a data que o Corinthians foi criado?", "1 de fevereiro de 1909", "2 de outubro de 1920", "1 de setembro de 1910", "1 de setembro de 1810", "27 de abril de 1929", 3);
        questionJson.add(q44);
        Question q45 = new Question("Quem fez o gol de cabeça contra o palmeiras, que marcou sua volta ao futebol, ficando assim para a história?", "Chicão", "Ronaldo", "Defederico", "Edu", "Bill", 2);
        questionJson.add(q45);
        Question q46 = new Question("Em qual estádio o Corinthians marcou seu 1000º gol na história do Campeonato Brasileiro?", "Pacaembu", "Maracanã", "Morumbi", "Mineirão", "Arena da Baixada", 4);
        questionJson.add(q46);
        Question q47 = new Question("Contra qual time Carlitos Tevez fez seu último gol pelo Corinthians?", "Palmeiras", "Flamengo", "Internacional", "Fluminense", "Paysandu", 4);
        questionJson.add(q47);
        Question q48 = new Question("Em qual ano ocorreu a famosa Invasão Corintiana ao Maracanã?", "1950", "1954", "1976", "1977", "1983", 3);
        questionJson.add(q48);
        Question q49 = new Question("Qual ex-jogador do Corinthians recebeu uma placa de Pelé por ter feito um golaço na Vila Belmiro contra o Santos?", "Marcelinho Carioca", "Neto", "Casagrande", "Dinei", "Ricardinho", 1);
        questionJson.add(q49);
        Question q50 = new Question("Qual o critério usado para colocar o Corinthians no Mundial da FIFA de 2000?", "Título do Brasileirão-1999", "Título da Taça Libertadores da América-1999", "Título da Copa Mercosul-1999", "Nenhum (ele foi convidado)", "Título do Brasileirão-1998", 5);
        questionJson.add(q50);
        Question q51 = new Question("Qual a data de aniversário do Corinthians?", "26 de agosto", "1º de setembro", "12 de outubro", "1º de novembro", "16 de dezembro", 2);
        questionJson.add(q51);
        Question q52 = new Question("Em 2002, o Corinthians lançou um uniforme em comemoração dos 25 anos da quebra do tabu de 23 anos sem títulos paulistas. Qual era a cor desse uniforme?", "Roxo", "Dourado", "Grená", "Laranja", "Prateado", 5);
        questionJson.add(q52);
        Question q53 = new Question("Qual o famoso apelido do Estádio Alfredo Scüring, localizado no Pq. São Jorge e que é o estádio particular do Corinthians?", "Ninho dos Gaviões", "Fazendinha", "Fielzão", "Alfredão", "Jorjão", 2);
        questionJson.add(q53);
        Question q54 = new Question("Qual ex-jogador do Timão colocou o apelido 'bambi' nos são paulinos?", "Vampeta", "Ronaldo", "Jorge Henrique", "Dentinho", "Ronaldo", 1);
        questionJson.add(q54);
        Question q55 = new Question("Em que ano o Corinthians completou cem anos?", "2010", "2011", "2015", "2011", "2014", 1);
        questionJson.add(q55);
        Question q56 = new Question("Qual jogador foi considerado o Talismã corintiano nos anos 90?", "Marcelinho", "Neto", "Wilson Mano", "Tupãzinho", "Dinei", 4);
        questionJson.add(q56);
        Question q57 = new Question("Quais os dois jogadores que perderam penalti na decisão da vaga para a semi-final da taça libertadores contra o Palmeiras em 1999?", "Dinei e Marcelinho", "Marcelinho e Vampeta", "Vampeta e Dinei", "Ricardinho e Marcelinho", "Nenhuma das alternativas", 3);
        questionJson.add(q57);
        Question q58 = new Question("Contra qual time foi o último jogo de Teves pelo Timão?", "Palmeiras", "Flamengo", "Botafogo", "Parana Clube", "Atlético Pr", 3);
        questionJson.add(q58);
        Question q59 = new Question("Contra quais clubes o Corinthians decidiu o brasileirão na década de 90?", "São Paulo, Palmeiras e Cruzeiro.", "Internacional, Atlético Mineiro e Santos.", "São Paulo, Palmeiras, Cruzeiro e Atlético Mineiro", "São Paulo, Santos e Inter", "Palmeiras, Atlético e Cruzeiro", 3);
        questionJson.add(q59);
        Question q60 = new Question("Qual clube eliminou o Corinthians na libertadores de 1991 em pleno Morumbi?", "Grêmio", "River Plate", "Boca Juniors", "Palmeiras", "Flamengo", 3);
        questionJson.add(q60);
        Question q61 = new Question("Em que ano e contra qual clube o Corinthians foi campeão da Copa do Brasil pela 1ª vez?", "1994 - Grêmio", "1995 - Grêmio", "1995 - Vitória", "1998 - Cruzeiro", "1996 - Vasco", 2);
        questionJson.add(q61);
        Question q62 = new Question("Em 2011, um rival estrangeiro do Corinthians foi rebaixado em seu campeonato nacional. Você se lembra qual é este time que, causou uma tremenda revolta em seus torcedores?", "Estudiantes.", "River Plate.", "Newels Old' Boys.", "Shaktar Donetks.", "Rosario Central", 2);
        questionJson.add(q62);
        Question q63 = new Question("Em que data o Corinthians foi fundado?", "25 de setembro de 1910 ", "1 de outubro de 1910 ", "1 de setembro de 1910", "25 de outrubro de 1910 ", "26 de outrubro de 1910 ", 3);
        questionJson.add(q63);
        Question q64 = new Question("O Corinthians foi fundado por um grupo de que?", "fazendeiros", "dentistas", "operários", "jogadores amadores", "jogadores profissíonais", 3);
        questionJson.add(q64);
        Question q65 = new Question("Qual jogador do Timão recebeu o apelido de Sheik por jogar um grande tempo na Arábia Saudita?", "Emerson", "Jorge Henrique", "Liedson", "Danilo", "Bruno Cesar", 1);
        questionJson.add(q65);
        Question q66 = new Question("A decisão do mundial que o Corinthians ganhou foi em qual lugar?", "São Paulo ", "Rio de Janeiro", "Tóquio", "Yokohama", "Santos", 2);
        questionJson.add(q66);
        Question q67 = new Question("Qual foi a primeira participação do Corinthians na Libertadores?", "1977", "1991", "1987", "1988", "1990", 1);
        questionJson.add(q67);
        Question q68 = new Question("O jejum corinthiano acabou em 1977. Quantos anos o Corinthians ficou sem vencer?", "30 anos", "23 anos", "15 anos", "12 anos", "23 meses", 2);
        questionJson.add(q68);
        Question q69 = new Question("Qual a nacionalidade do zagueiro Gamarra, que passou pelo Corinthians?", "Argentino", "Paraguaio", "Uruguaio", "Boliviano", "Italiano", 2);
        questionJson.add(q69);
        Question q70 = new Question("O Corinthians foi campeão em 1977 encerrando o jejum. De quem o Corinthians ganhou na final do campeonato?", "Ponte Preta", "Vasco", "Flamengo", "Santos", "Portuguesa", 1);
        questionJson.add(q70);
        Question q71 = new Question("Qual o nome do jogador corinthiano conhecido como 'pé de anjo'?", "Rivelino", "Sócrates", "Marcelinho Carioca", "Neto ", "Ronaldo", 3);
        questionJson.add(q71);
        Question q72 = new Question("Qual o nome jogador corinthiano conhecido como 'Doutor'?", "Sócrates", "Rivelino", "Luisinho", "Neto ", "Casa Grande", 1);
        questionJson.add(q72);
        Question q73 = new Question("Que lesão o Adriano (Imperador) teve em abril de 2011 deixando-o fora dos gramados por mais 6 meses?", "Fraturou o Fêmur", "Luxou o Pé", "Fraturou o braço", "Fraturou o Tendão de Aquiles", "Deslocou a perna", 4);
        questionJson.add(q73);
        Question q74 = new Question("no ano de 1976 milhares de corintianos viajaram de São Paulo até o Rio de Janeiro para assistir ao jogo entre Fluminense e Corinthians no estádio do Maracanã pela semi-final do Campeonato Brasileiro. Este movimento ficou conhecido como:", "Invasão Corinthiana", "Migração Corinthiana", "Geração Corinthiana", "Furacão Corinthiano", "NDA", 1);
        questionJson.add(q74);
        Question q75 = new Question("Em qual ano o Corinthians foi campeão da Copa do Mundo de Clubes da Fifa?", "Em 1990", "Em 2005", "Em 2000", "Em 2010", "Em 2015", 3);
        questionJson.add(q75);
        Question q76 = new Question("Qual jogador mais jogou com a camisa do Timão?", "Ronaldo", "Biro-biro", "Wladimir", "Rivellino", "Tevez", 3);
        questionJson.add(q76);
        Question q77 = new Question("O Corinthians conquistou o seu primeiro título brasileiro em cima do:", "Palmeiras", "Cruzeiro", "São Paulo", "Internacional", "Santos", 3);
        questionJson.add(q77);
        Question q78 = new Question("Quando falamos em 'Derby Paulista', estamos nos referindo ao clássico entre:", "Corinthians e Palmeiras", "Corinthians e São Paulo", "Corinthians e Santos", "Corinthians e Ponte Preta", "Corinthians e Portuguesa", 1);
        questionJson.add(q78);
        Question q79 = new Question("Quem fundou o Corinthians?", "Um grupo de operários do bairro do Bom Retiro, na cidade de São Paulo", "Um grupo de operários recém-chegados da Itália", "Um grupo de jogadores profissionais vindos de outros clubes brasileiros", "Um grupo de operários de Itaquera, em São Paulo", "Um grupo de operários recém-chegados da Alemanha", 1);
        questionJson.add(q79);
        Question q80 = new Question("Qual é o nome oficial do Corinthians?", "Corinthians Paulista Esporte Clube", "Esporte Clube Corinthians", "Sport Club Corinthians Paulista", "Sport Club Paulista do Corinthians", "Corinthians Paulista", 3);
        questionJson.add(q80);
        Question q81 = new Question("Qual é o rival mais antigo do Corinthians?", "Palmeiras", "Santos", "São Paulo", "Portuguesa", "Ponte Preta", 5);
        questionJson.add(q81);
        Question q82 = new Question("Qual o nome do jogador corinthiano que recebeu o apelido de 'xodó da fiel'? ", "Marcelinho Carioca ", "Tevez", "Rivelino", "Neto", "Ronaldo", 4);
        questionJson.add(q82);
        Question q83 = new Question("A decisão do mundial que o Corinthians ganhou foi em qual lugar?", "Yokohama", "São Paulo ", "Rio de Janeiro", "Tóquio", "Santos", 3);
        questionJson.add(q83);
        Question q84 = new Question("Qual a nacionalidade do zagueiro Gamarra, que passou pelo Corinthians?", "Argentino", "Paraguaio", "Uruguaio", "Boliviano", "Colombiano", 2);
        questionJson.add(q84);
        Question q85 = new Question("Qual o nome do jogador corinthiano que imitou um porco no jogo contra o Palmeiras? ", "Marcelinho Carioca ", "Edilson", "Viola", "Vampeta", "Neto", 3);
        questionJson.add(q85);
        Question q86 = new Question("Qual o nome jogador corinthiano conhecido como 'Doutor'?", "Sócrates", "Rivelino", "Luisinho", "Neto", "Casa Grande", 1);
        questionJson.add(q86);
        Question q87 = new Question("Jogando contra qual destes times o Corinthians foi campeão da Série B em 2008?", "Ceará", "Paraná", "Criciúma", "Palmeiras", "Santos", 3);
        questionJson.add(q87);
        Question q88 = new Question("Em qual ano o Corinthians conquistou seu primeiro título? ", "1911", "1914", "1915", "1916", "1917", 2);
        questionJson.add(q88);
        Question q89 = new Question("Qual foi o maior tempo que o Corinthians ficou sem perder para seu arqui-rival Palmeiras? ", "7 anos", "5 anos", "3 anos", "4 anos", "8 anos", 1);
        questionJson.add(q89);
        Question q90 = new Question("O período sem títulos da equipe do Corinthians durou quantos anos? ", "21 anos", "23 anos", "22 anos", "24 anos", "26 anos", 3);
        questionJson.add(q90);
        Question q91 = new Question("Qual jogador estava na seleção brasileira tricampeã mundial, em 1970, e jogava pelo Corinthians na mesma época?", "Rivelino", "Clodoaldo", "Leão", "", "", 1);
        questionJson.add(q91);
        Question q92 = new Question("Contra quem o Corinthians jogou na partida que ficou conhecida como 'invasão corintiana'?", "Fluminense", "Vasco", "Flamengo", "Palmeiras", "Santos", 1);
        questionJson.add(q92);
        Question q93 = new Question("Em 1977, o Corinthians quebrou o jejum de 22 anos sem conquistas vencendo o Campeonato Paulista. Quem fez o gol do título?", "Wladimir", "Zé Maria", "Basílio", "Ronaldo", "Tevez", 3);
        questionJson.add(q93);
        Question q94 = new Question("Quem era o camisa 8 do time do Corinthians na época da Democracia Corintiana?", "Casagrande", "Sócrates", "Biro-Biro", "Ronaldo", "Tevez", 2);
        questionJson.add(q94);

    }

}
