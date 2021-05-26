package co.com.security.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Class User.
 * 
 * @author AVARGAS
 */
@Entity
@Table(name = "USERS")
public class User implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 2922102959087108437L;

    /** The id. */
    @Id
    @Column
    private String id;

    @Column(name = "user_name")
    private String userName;

    /** The pass. */
    @Column
    private String pass;

    /** The name. */
    @Column
    private String name;

    /**
     * Instantiates a new user.
     */
    public User() {
        super();
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the pass.
     *
     * @return the pass
     */
    public String getPass() {
        return pass;
    }

    /**
     * Sets the pass.
     *
     * @param pass the new pass
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
