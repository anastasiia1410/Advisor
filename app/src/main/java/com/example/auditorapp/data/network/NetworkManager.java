package com.example.auditorapp.data.network;

import android.content.Context;

import com.example.auditorapp.core.App;
import com.example.auditorapp.core.AppPreference;
import com.example.auditorapp.data.network.entity.DetailsRequest;
import com.example.auditorapp.data.network.entity.DraftsRequest;
import com.example.auditorapp.data.network.entity.ReviewRequest;
import com.example.auditorapp.data.network.entity.SignUpResponse;
import com.example.auditorapp.entity.review.Review;
import com.example.auditorapp.entity.user.User;
import com.google.gson.Gson;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkManager {
    private final Gson gson;
    private final AppPreference appPreference;
    private final Api api;

    public NetworkManager(Context context) {
        appPreference = App.getInstance(context).getAppPreference();
        gson = new Gson();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://parseapi.back4app.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        api = retrofit.create(Api.class);
    }

    public Single<String> logging(String username, String password) {
        return api.logging(username, password)
                .map(SignUpResponse::getToken)
                .doOnSuccess(appPreference::saveToken);

    }

    public Single<String> registration(User user) {
        return api.registration(user)
                .map(SignUpResponse::getToken)
                .doOnSuccess(appPreference::saveToken);
    }

    public Single<User> getCurrentUser() {
        String token = appPreference.getToken();
        return api.currentUser(token);
    }

    public Completable sendDrafts(String author, String title, String text) {
        DraftsRequest draftsRequest = new DraftsRequest(author, title, text);
        String token = appPreference.getToken();
        return api.sendDrafts(token, draftsRequest);
    }

    public Single<List<Review>> getReviews() {
        String token = appPreference.getToken();
        return api.getReviews(token)
                .map(ReviewRequest::getResults);
    }

    public Single<Review> getReviewsDetail(String objectId) {
        String token = appPreference.getToken();
        DetailsRequest request = new DetailsRequest();

        request.setObjectId(objectId);
        String json = gson.toJson(request);
        return api.getReviewById(token, json)
                .map(reviewDetailsRequest -> reviewDetailsRequest.getResults().get(0));
    }

    public Completable deleteReview(String objectId) {
        String token = appPreference.getToken();
        return api.deleteReview(token, objectId);
    }
}
