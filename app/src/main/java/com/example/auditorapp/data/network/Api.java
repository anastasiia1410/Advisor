package com.example.auditorapp.data.network;

import com.example.auditorapp.data.network.entity.DraftsRequest;
import com.example.auditorapp.data.network.entity.ReviewRequest;
import com.example.auditorapp.data.network.entity.SignUpResponse;
import com.example.auditorapp.entity.user.User;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
public interface Api {
    @GET("login")
    @Headers({
            "X-Parse-Application-Id: ZBgDdyJtVZePeiKre8aec0qIvyPAw15G8q9mlbNx",
            "X-Parse-REST-API-Key: UPOLlVKvxLhTilIz5zM87BbCpoqkyNcmtf4zH9Xc",
            "X-Parse-Revocable-Session: 1"
    })
    Single<SignUpResponse> logging(@Query("username") String username,
                                   @Query("password") String password);

    @POST("users")
    @Headers({
            "X-Parse-Application-Id: ZBgDdyJtVZePeiKre8aec0qIvyPAw15G8q9mlbNx",
            "X-Parse-REST-API-Key: UPOLlVKvxLhTilIz5zM87BbCpoqkyNcmtf4zH9Xc",
            "X-Parse-Revocable-Session: 1",
            "Content-Type: application/json"
    })
    Single<SignUpResponse> registration(@Body User user);


    @GET("users/me")
    @Headers({
            "X-Parse-Application-Id: ZBgDdyJtVZePeiKre8aec0qIvyPAw15G8q9mlbNx",
            "X-Parse-REST-API-Key: UPOLlVKvxLhTilIz5zM87BbCpoqkyNcmtf4zH9Xc"
    })
    Single<User> currentUser(@Header("X-Parse-Session-Token") String sessionToken);


    @POST("classes/Review")
    @Headers({
            "X-Parse-Application-Id: ZBgDdyJtVZePeiKre8aec0qIvyPAw15G8q9mlbNx",
            "X-Parse-REST-API-Key: UPOLlVKvxLhTilIz5zM87BbCpoqkyNcmtf4zH9Xc",
            "Content-Type: application/json",
    })
    Completable sendDrafts(@Header("X-Parse-Session-Token") String sessionToken,
                           @Body DraftsRequest body);

    @GET("classes/Review")
    @Headers({
            "X-Parse-Application-Id: ZBgDdyJtVZePeiKre8aec0qIvyPAw15G8q9mlbNx",
            "X-Parse-REST-API-Key: UPOLlVKvxLhTilIz5zM87BbCpoqkyNcmtf4zH9Xc"
    })
    Single<ReviewRequest> getReviews(@Header("X-Parse-Session-Token") String sessionToken);


    @GET("classes/Review")
    @Headers({
            "X-Parse-Application-Id: ZBgDdyJtVZePeiKre8aec0qIvyPAw15G8q9mlbNx",
            "X-Parse-REST-API-Key: UPOLlVKvxLhTilIz5zM87BbCpoqkyNcmtf4zH9Xc"
    })
    Single<ReviewRequest> getReviewById(@Header("X-Parse-Session-Token") String sessionToken,
                                               @Query("where") String json);

    @DELETE("classes/Review/{objectId}")
    @Headers({
            "X-Parse-Application-Id: ZBgDdyJtVZePeiKre8aec0qIvyPAw15G8q9mlbNx",
            "X-Parse-REST-API-Key: UPOLlVKvxLhTilIz5zM87BbCpoqkyNcmtf4zH9Xc"
    })
    Completable deleteReview(@Header("X-Parse-Session-Token") String sessionToken,
                             @Path("objectId") String objectId);
}
