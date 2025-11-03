/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Chat.Entity;

/**
 *
 * @author Sahan Geesara
 */
public class Chat {
    
    private int id;
    private User user;
    private String message;

    public Chat(){
        
    }
    public Chat(int id,String message){
        this.id =id;
        this.message= message;
       
    }
    public Chat(User user,String message){
        this.user =user;
        this.message= message;
       
    }
        
    
    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
    
}
