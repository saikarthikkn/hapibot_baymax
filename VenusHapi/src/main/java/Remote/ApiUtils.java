package Remote;

/**
 * Created by sai_goturu on 2/23/2017.
 */

public class ApiUtils {

    public static final String BASE_URL = "http://api.newocr.com/v1/";

    public static OCRService getOCRService() {
        return RetrofitClient.getClient(BASE_URL).create(OCRService.class);
    }
}
