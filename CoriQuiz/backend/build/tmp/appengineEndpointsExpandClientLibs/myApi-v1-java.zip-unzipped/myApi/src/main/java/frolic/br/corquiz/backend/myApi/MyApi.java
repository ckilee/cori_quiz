/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://github.com/google/apis-client-generator/
 * (build: 2015-11-16 19:10:01 UTC)
 * on 2015-12-25 at 17:38:12 UTC 
 * Modify at your own risk.
 */

package frolic.br.corquiz.backend.myApi;

/**
 * Service definition for MyApi (v1).
 *
 * <p>
 * This is an API
 * </p>
 *
 * <p>
 * For more information about this service, see the
 * <a href="" target="_blank">API Documentation</a>
 * </p>
 *
 * <p>
 * This service uses {@link MyApiRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class MyApi extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.21.0 of the myApi library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
  }

  /**
   * The default encoded root URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_ROOT_URL = "https://corinthians-quiz-game.appspot.com/_ah/api/";

  /**
   * The default encoded service path of the service. This is determined when the library is
   * generated and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_SERVICE_PATH = "myApi/v1/";

  /**
   * The default encoded base URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   */
  public static final String DEFAULT_BASE_URL = DEFAULT_ROOT_URL + DEFAULT_SERVICE_PATH;

  /**
   * Constructor.
   *
   * <p>
   * Use {@link Builder} if you need to specify any of the optional parameters.
   * </p>
   *
   * @param transport HTTP transport, which should normally be:
   *        <ul>
   *        <li>Google App Engine:
   *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
   *        <li>Android: {@code newCompatibleTransport} from
   *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
   *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
   *        </li>
   *        </ul>
   * @param jsonFactory JSON factory, which may be:
   *        <ul>
   *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
   *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
   *        <li>Android Honeycomb or higher:
   *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
   *        </ul>
   * @param httpRequestInitializer HTTP request initializer or {@code null} for none
   * @since 1.7
   */
  public MyApi(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  MyApi(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * An accessor for creating requests from the MyEndpoint collection.
   *
   * <p>The typical use is:</p>
   * <pre>
   *   {@code MyApi myApi = new MyApi(...);}
   *   {@code MyApi.MyEndpoint.List request = myApi.myEndpoint().list(parameters ...)}
   * </pre>
   *
   * @return the resource collection
   */
  public MyEndpoint myEndpoint() {
    return new MyEndpoint();
  }

  /**
   * The "myEndpoint" collection of methods.
   */
  public class MyEndpoint {

    /**
     * Create a request for the method "myEndpoint.addDefaultValues".
     *
     * This request holds the parameters needed by the myApi server.  After setting any optional
     * parameters, call the {@link AddDefaultValues#execute()} method to invoke the remote operation.
     *
     * @return the request
     */
    public AddDefaultValues addDefaultValues() throws java.io.IOException {
      AddDefaultValues result = new AddDefaultValues();
      initialize(result);
      return result;
    }

    public class AddDefaultValues extends MyApiRequest<Void> {

      private static final String REST_PATH = "addDefaultValues";

      /**
       * Create a request for the method "myEndpoint.addDefaultValues".
       *
       * This request holds the parameters needed by the the myApi server.  After setting any optional
       * parameters, call the {@link AddDefaultValues#execute()} method to invoke the remote operation.
       * <p> {@link AddDefaultValues#initialize(com.google.api.client.googleapis.services.AbstractGoogle
       * ClientRequest)} must be called to initialize this instance immediately after invoking the
       * constructor. </p>
       *
       * @since 1.13
       */
      protected AddDefaultValues() {
        super(MyApi.this, "POST", REST_PATH, null, Void.class);
      }

      @Override
      public AddDefaultValues setAlt(java.lang.String alt) {
        return (AddDefaultValues) super.setAlt(alt);
      }

      @Override
      public AddDefaultValues setFields(java.lang.String fields) {
        return (AddDefaultValues) super.setFields(fields);
      }

      @Override
      public AddDefaultValues setKey(java.lang.String key) {
        return (AddDefaultValues) super.setKey(key);
      }

      @Override
      public AddDefaultValues setOauthToken(java.lang.String oauthToken) {
        return (AddDefaultValues) super.setOauthToken(oauthToken);
      }

      @Override
      public AddDefaultValues setPrettyPrint(java.lang.Boolean prettyPrint) {
        return (AddDefaultValues) super.setPrettyPrint(prettyPrint);
      }

      @Override
      public AddDefaultValues setQuotaUser(java.lang.String quotaUser) {
        return (AddDefaultValues) super.setQuotaUser(quotaUser);
      }

      @Override
      public AddDefaultValues setUserIp(java.lang.String userIp) {
        return (AddDefaultValues) super.setUserIp(userIp);
      }

      @Override
      public AddDefaultValues set(String parameterName, Object value) {
        return (AddDefaultValues) super.set(parameterName, value);
      }
    }

  }

  /**
   * Create a request for the method "getQuestions".
   *
   * This request holds the parameters needed by the myApi server.  After setting any optional
   * parameters, call the {@link GetQuestions#execute()} method to invoke the remote operation.
   *
   * @param dbVersion
   * @return the request
   */
  public GetQuestions getQuestions(java.lang.Integer dbVersion) throws java.io.IOException {
    GetQuestions result = new GetQuestions(dbVersion);
    initialize(result);
    return result;
  }

  public class GetQuestions extends MyApiRequest<frolic.br.corquiz.backend.myApi.model.MyBean> {

    private static final String REST_PATH = "sayHi/{dbVersion}";

    /**
     * Create a request for the method "getQuestions".
     *
     * This request holds the parameters needed by the the myApi server.  After setting any optional
     * parameters, call the {@link GetQuestions#execute()} method to invoke the remote operation. <p>
     * {@link
     * GetQuestions#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param dbVersion
     * @since 1.13
     */
    protected GetQuestions(java.lang.Integer dbVersion) {
      super(MyApi.this, "POST", REST_PATH, null, frolic.br.corquiz.backend.myApi.model.MyBean.class);
      this.dbVersion = com.google.api.client.util.Preconditions.checkNotNull(dbVersion, "Required parameter dbVersion must be specified.");
    }

    @Override
    public GetQuestions setAlt(java.lang.String alt) {
      return (GetQuestions) super.setAlt(alt);
    }

    @Override
    public GetQuestions setFields(java.lang.String fields) {
      return (GetQuestions) super.setFields(fields);
    }

    @Override
    public GetQuestions setKey(java.lang.String key) {
      return (GetQuestions) super.setKey(key);
    }

    @Override
    public GetQuestions setOauthToken(java.lang.String oauthToken) {
      return (GetQuestions) super.setOauthToken(oauthToken);
    }

    @Override
    public GetQuestions setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetQuestions) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetQuestions setQuotaUser(java.lang.String quotaUser) {
      return (GetQuestions) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetQuestions setUserIp(java.lang.String userIp) {
      return (GetQuestions) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Integer dbVersion;

    /**

     */
    public java.lang.Integer getDbVersion() {
      return dbVersion;
    }

    public GetQuestions setDbVersion(java.lang.Integer dbVersion) {
      this.dbVersion = dbVersion;
      return this;
    }

    @Override
    public GetQuestions set(String parameterName, Object value) {
      return (GetQuestions) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link MyApi}.
   *
   * <p>
   * Implementation is not thread-safe.
   * </p>
   *
   * @since 1.3.0
   */
  public static final class Builder extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder {

    /**
     * Returns an instance of a new builder.
     *
     * @param transport HTTP transport, which should normally be:
     *        <ul>
     *        <li>Google App Engine:
     *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
     *        <li>Android: {@code newCompatibleTransport} from
     *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
     *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
     *        </li>
     *        </ul>
     * @param jsonFactory JSON factory, which may be:
     *        <ul>
     *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
     *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
     *        <li>Android Honeycomb or higher:
     *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
     *        </ul>
     * @param httpRequestInitializer HTTP request initializer or {@code null} for none
     * @since 1.7
     */
    public Builder(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
        com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      super(
          transport,
          jsonFactory,
          DEFAULT_ROOT_URL,
          DEFAULT_SERVICE_PATH,
          httpRequestInitializer,
          false);
    }

    /** Builds a new instance of {@link MyApi}. */
    @Override
    public MyApi build() {
      return new MyApi(this);
    }

    @Override
    public Builder setRootUrl(String rootUrl) {
      return (Builder) super.setRootUrl(rootUrl);
    }

    @Override
    public Builder setServicePath(String servicePath) {
      return (Builder) super.setServicePath(servicePath);
    }

    @Override
    public Builder setHttpRequestInitializer(com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
    }

    @Override
    public Builder setApplicationName(String applicationName) {
      return (Builder) super.setApplicationName(applicationName);
    }

    @Override
    public Builder setSuppressPatternChecks(boolean suppressPatternChecks) {
      return (Builder) super.setSuppressPatternChecks(suppressPatternChecks);
    }

    @Override
    public Builder setSuppressRequiredParameterChecks(boolean suppressRequiredParameterChecks) {
      return (Builder) super.setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
    }

    @Override
    public Builder setSuppressAllChecks(boolean suppressAllChecks) {
      return (Builder) super.setSuppressAllChecks(suppressAllChecks);
    }

    /**
     * Set the {@link MyApiRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setMyApiRequestInitializer(
        MyApiRequestInitializer myapiRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(myapiRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}