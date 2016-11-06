package assignment2.enums;

/**
 * Created by Alex PC on 15/10/2016.
 */
public enum Role {
    REGULARUSER(0), ADMIN(1);

    private int roleNumber;

    Role(int roleNumber) {
        this.roleNumber = roleNumber;
    }

    public int getRoleNumber () {
        return this.roleNumber;
    }
}
