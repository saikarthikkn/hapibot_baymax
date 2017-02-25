package data.remote;

/**
 * Created by neha_lalwani on 21-Feb-17.
 */

import java.util.List;

import data.model.Conditions;
import data.model.Diagnosis;
import data.model.SymptomFilters;
import data.model.Symptoms;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface InfermedicaService {

    @GET("symptoms")
    @Headers({"App-Id: d6847cf8 ",
            "App-Key:e9d04ea8ca30b92fa6b7927c491006f0"})
    Call<List<Symptoms>> getSymptoms();

    @GET("conditions")
    @Headers({"App-Id: d6847cf8 ",
            "App-Key:e9d04ea8ca30b92fa6b7927c491006f0"})
    Call<List<Conditions>> getConditions();

    @POST("diagnosis")
    @Headers({"App-Id: d6847cf8 ",
            "App-Key:e9d04ea8ca30b92fa6b7927c491006f0"})
    Call<Diagnosis> diagnose(@Body SymptomFilters symptomFilters);

}
