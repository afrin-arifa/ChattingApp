package com.example.creativeitfirebase.Model;

public class User   {

   String UserName,UserPhone,UserEmail,userId,UserPass,UserCoverPic,UserProfilePic;



    public User() {
    }

    public User(String userName, String userPhone, String userEmail, String userId, String userPass, String userCoverPic, String userProfilePic) {
        UserName = userName;
        UserPhone = userPhone;
        UserEmail = userEmail;
        this.userId = userId;
        UserPass = userPass;
        UserCoverPic = userCoverPic;
        UserProfilePic = userProfilePic;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserPhone() {
        return UserPhone;
    }

    public void setUserPhone(String userPhone) {
        UserPhone = userPhone;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPass() {
        return UserPass;
    }

    public void setUserPass(String userPass) {
        UserPass = userPass;
    }

    public String getUserCoverPic() {
        return UserCoverPic;
    }

    public void setUserCoverPic(String userCoverPic) {
        UserCoverPic = userCoverPic;
    }

    public String getUserProfilePic() {
        return UserProfilePic;
    }

    public void setUserProfilePic(String userProfilePic) {
        UserProfilePic = userProfilePic;
    }
}
