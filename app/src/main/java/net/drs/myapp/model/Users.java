package net.drs.myapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "app_user")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    
    @Column(name = "email",unique=true)
    private String email;
    @Column(name = "phonenumber",unique=true)
    private String phonenumber;
    @Column(name = "password")
    private String password;
    @Column(name = "name")
    private String name;

    @Column(name = "usersname")
    private String usersname;

    // email temperory password
    @Column(name = "isTempPassord")
    private boolean isTempPassord;
    
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "active")
    private int active;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
    
    
    @Transient
    private List<String> associatedRoles = new ArrayList<String>();
    

    public Users() {
    }

    public Users(Users users) {
        this.active = users.getActive();
        this.email = users.getEmail();
        this.phonenumber=users.getPhonenumber();
        this.roles = users.getRoles();
        this.name = users.getName();
        this.lastName = users.getLastName();
        this.setId(users.getId());
        this.password = users.getPassword();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return usersname;
    }

    public void setUsername(String usersname) {
        this.usersname = usersname;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public boolean isTempPassord() {
        return isTempPassord;
    }

    public void setTempPassord(boolean isTempPassord) {
        this.isTempPassord = isTempPassord;
    }

	public List<String> getAssociatedRoles() {
		return associatedRoles;
	}

	public void setAssociatedRoles(List<String> associatedRoles) {
		this.associatedRoles = associatedRoles;
	}


}
