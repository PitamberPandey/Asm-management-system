package com.amsmanagament.system.model;


import com.fasterxml.jackson.annotation.JsonProperty;


import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String fullName;

    private String phoneNumber;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User_Role getRole() {
        return role;
    }

    public void setRole(User_Role role) {
        this.role = role;
    }

    @JsonProperty(access =JsonProperty.Access.WRITE_ONLY)
    private String password;
    private User_Role role=User_Role.ROLE_USER;

    private  String gender;

    private String dob;
    private  String profileImage;
    private  String address;
    private String acountStatus="ACTIVE";
    private String created_at;
    private String updated_at;


    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Farmer farmer;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Seller seller;

}

