package com.magnum.magnumshivam;

import com.magnum.magnumshivam.data.MainData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MainInterface {
    
    @GET("search/users?q=saransh&page=2")
    Call<MainData> STRING_CALL(
//            @Query("page") int page,
//            @Query("limit") int limit
    );

}
