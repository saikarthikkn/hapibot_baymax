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
    @Headers({"App-Id: 8fd9388d",
            "App-Key:88d97aa07017c0c5af128a1e9854110a"})
    Call<List<Symptoms>> getSymptoms();

    @GET("conditions")
    @Headers({"App-Id: 8fd9388d",
            "App-Key:88d97aa07017c0c5af128a1e9854110a"})
    Call<List<Conditions>> getConditions();

    @POST("diagnosis")
    @Headers({"App-Id: 8fd9388d",
            "App-Key:88d97aa07017c0c5af128a1e9854110a"})
    Call<Diagnosis> diagnose(@Body SymptomFilters symptomFilters);

}
