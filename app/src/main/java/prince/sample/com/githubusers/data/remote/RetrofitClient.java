package prince.sample.com.githubusers.data.remote;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL="https://api.github.com/";
    private static  Retrofit instance=null;

    private RetrofitClient() {
    }

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
