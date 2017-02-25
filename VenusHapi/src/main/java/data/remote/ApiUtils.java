package data.remote;

/**
 * Created by neha_lalwani on 21-Feb-17.
 */

public class ApiUtils {
    public static final String BASE_URL = "https://api.infermedica.com/v2/";

    public static InfermedicaService getService() {
        return RetrofitClient.getClient(BASE_URL).create(InfermedicaService.class);
    }

}
