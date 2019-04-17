package model;

public class Post {
    int postId;
    int userId;
    String blood_group;
    String district;
    String police_station;
    String location;
    String postBody;
    int postStatus;
    String postTime;

    public Post() {
    }

    public Post(int postId, int userId, String blood_group, String district, String police_station, String location, String postBody, int postStatus, String postTime) {
        this.postId = postId;
        this.userId = userId;
        this.blood_group = blood_group;
        this.district = district;
        this.police_station = police_station;
        this.location = location;
        this.postBody = postBody;
        this.postStatus = postStatus;
        this.postTime = postTime;
    }
}
