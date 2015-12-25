package frolic.br.coriquiz.model;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import frolic.br.coriquiz.LoginActivity;
import frolic.br.coriquiz.utils.Utils;
import frolic.br.corquiz.backend.myApi.MyApi;

public class EndpointsAsyncTask extends AsyncTask<Pair<Context, Integer>, Void, String> {
    private static MyApi myApiService = null;
    private LoginActivity context;

    @Override
    protected String doInBackground(Pair<Context, Integer>... params) {
        if(myApiService == null) {  // Only do this once
            /*MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });*/
            // end options for devappserver
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://corinthians-quiz-game.appspot.com/_ah/api/");

            myApiService = builder.build();
        }

        context = (LoginActivity)params[0].first;
        int dbVersion = params[0].second;

        try {
            return myApiService.getQuestions(dbVersion).execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            Gson gson = new GsonBuilder().create();
            Type listTypeQuestion = new TypeToken<QuestionJson>() {
            }.getType();
            QuestionJson questionJson = gson.fromJson(result, listTypeQuestion);
            QuizDAO quizDAO = context.getQuizDAO();
            quizDAO.clearTable(QuizContract.QUESTION_TABLE);
            quizDAO.addQuestions(questionJson.getQuestionArrayList());
            Utils.addToSharedPreferences(context, questionJson.getVersion());
            Log.i("EndpointsAsyncTask", "Question has been synced with server");
        }catch (JsonSyntaxException e){
            Log.i("EndpointsAsyncTask","Have not updated: "+result);

        }
    }
}