package prince.sample.com.githubusers.data.remote;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Singleton class to create Retrofit instance
 */
public class RetrofitClient {
    private static final String BASE_URL="https://api.github.com/";
    private static  Retrofit instance=null;

    private RetrofitClient() {
    }

    /**
     * Method to return a single retrofit instance
     * @return
     */
    public static Retrofit getInstance() {
        if(instance==null){
            synchronized (RetrofitClient.class){
                if(instance==null){
                    instance = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }

        return instance;
    }
}
