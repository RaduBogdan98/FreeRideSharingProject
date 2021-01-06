package app.ridesharingapp.Model.Requests;

public class JoinRideRequest {
    private String userEmail;

    public JoinRideRequest() {
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
