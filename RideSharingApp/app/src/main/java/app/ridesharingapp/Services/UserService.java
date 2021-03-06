package app.ridesharingapp.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import app.ridesharingapp.Model.Requests.AddCarRequest;
import app.ridesharingapp.Model.Requests.AddRideRequest;
import app.ridesharingapp.Model.Requests.JoinRideRequest;
import app.ridesharingapp.Model.Requests.LoginRequest;
import app.ridesharingapp.Model.Requests.RegisterRequest;
import app.ridesharingapp.Model.Requests.UserUpdateRequest;
import app.ridesharingapp.Model.Responses.LoginResponse;
import app.ridesharingapp.Model.Ride;
import app.ridesharingapp.Model.User;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UserService {

    @POST("api/users/login/")
    Call<User> userLogin(@Body LoginRequest loginRequest);

    @POST("api/users/register")
    Call<User> userRegister(@Body RegisterRequest registerRequest);

    @GET("api/users/user/{userEmail}")
    Call<User> getUserDetails(@Path("userEmail") String userEmail);

    @POST("api/users/user/addCar")
    Call<User> addCar(@Body AddCarRequest addCarRequest);

    @PATCH("api/users/user/{userEmail}")
    Call<User> updateUser(@Path("userEmail") String userEmail, @Body User update);

    @Multipart
    @POST("api/users/user/upload/{userEmail}")
    Call<ResponseBody> uploadImage(@Part MultipartBody.Part image,@Part("userEmail") RequestBody userEmail);


    // RIDES
    @GET("api/rides/{rideID}")
    Call<Ride> getRide(@Path("rideID") String rideID);

    @POST("api/rides/joinRide/{rideID}")
    Call<Ride> joinRide(@Path("rideID") String rideID, @Body JoinRideRequest joinRideRequest);

    @GET("api/rides")
    Call<ArrayList<Ride>> getAllRides();


    @POST("api/rides")
    Call<Ride> createRide(@Body AddRideRequest ride);

    @DELETE("api/rides/{rideID}")
    Call<Ride> deleteRide(@Path("rideID") String rideID);


    @POST("api/rides/leave/{rideID}")
    Call<Ride> leaveRide(@Path("rideID") String rideID,@Body JoinRideRequest joinRideRequest);

//    @PATCH()
//    Call<Ride> updateRide(@Path("") String path, @Body Ride update);


}
