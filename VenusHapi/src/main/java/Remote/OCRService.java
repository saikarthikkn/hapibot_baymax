package Remote;

import Models.OCRFileResponse;
import Models.OCRRecognizedTextResponse;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by sai_goturu on 2/22/2017.
 */

public interface OCRService {
    @Multipart
    @POST("upload?key=dba3d07b41fd2df5de3a4a5d40445bcb")
    Call<OCRFileResponse> upload(
            @Part("file\"; filename=\"pp.png\" ") RequestBody file
    );

    @GET("ocr")
    Call<OCRRecognizedTextResponse> recognizePIC(
            @Query("key") String apiKey,
            @Query("file_id") String fileId,
            @Query("page") String pages,
            @Query("lang") String language,
            @Query("psm") String psm
    );
}
