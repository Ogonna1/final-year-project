package models;

import java.util.UUID;

public class UserDTO {
    private String id;
    private String fullName;
    private String loginName;
    private String phoneNumber;


    public UserDTO(String id, String fullName, String loginName, String phoneNumber) {
        this.id = id;
        this.fullName = fullName;
        this.loginName = loginName;
        this.phoneNumber = phoneNumber;
    }

    public UserDTO() {
        this.id = UUID.randomUUID().toString();
        this.fullName = "";
        this.loginName = "";
        this.phoneNumber = "";
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getLoginName() {
        return loginName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                ", loginName='" + loginName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
