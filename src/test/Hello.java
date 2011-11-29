/**.
 * 
 */
package test;

import com.opensymphony.xwork2.ActionSupport;

/**.
 * @author Dino
 * @28 juin 2011
 */
public class Hello {
    private String firstName;
    private String lastName;

    public String execute() {
        return ActionSupport.SUCCESS;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
