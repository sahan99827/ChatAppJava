/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Chat.Controller;

import Chat.Entity.*;
import java.io.*;
import java.util.*;
/**
 *
 * @author Sahan Geesara
 */
public class UserController {
    public List<User> list =new ArrayList<User>();
     private boolean loadingFromFile = false;
    public UserController(){
     
    }
    
    public void create(User user){
        int id =generateId();
        user.setId(id);
        
        list.add(user);
        if (!loadingFromFile) { 
            saveToFile(user);
        }
    }
     private void saveToFile(User user) {
        try (FileWriter fw = new FileWriter("User.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            out.println(
                user.getId() + " " +
                user.getName()+ " " +
                user.getPhone() + " " +
                user.getPassword()
              
            );
          
        } catch (Exception ex) {
            ex.printStackTrace();
    
        }
    }
    public void loadOldOrders() throws IOException{
          loadingFromFile = true;
            try (BufferedReader br = new BufferedReader(new FileReader("Burger.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (!line.isEmpty()) {
                        String[] parts = line.split("\\s+");
                        if (parts.length >= 8) {
                            String userphone= parts[0];
                            String name = parts[1]+ " " + parts[2];
                            String password = parts[3];
                         
                           User user = new User(userphone, name, password);
                           list.add(user);
                        }
                    }
                }
            } catch (FileNotFoundException ex) {
                System.out.println("File not found: Burger.txt");
            } catch (IOException ex) {
                ex.printStackTrace();
            }finally {
                loadingFromFile = false;
            }
        }
   public User login(String phone, String password) {
    try (BufferedReader br = new BufferedReader(new FileReader("User.txt"))) {
        String line;

        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (!line.isEmpty()) {
                String[] parts = line.split("\\s+");

                if (parts.length >= 4) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1]+" " + parts[2];
                    String userPhone = parts[3];
                    String userPassword = parts[4];

                    if (userPhone.equals(phone) && userPassword.equals(password)) {
                        // return matching user
                        return new User(userPhone, name, userPassword);
                    }
                }
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    return null; // login failed
}

   public static int generateId() {
    try {
        File file = new File("User.txt");

        if (!file.exists()) {
            file.createNewFile();
            return 1;
        }

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        String lastLine = null;

        while ((line = br.readLine()) != null) {
            lastLine = line;
        }
        br.close();

        if (lastLine == null || lastLine.isEmpty()) {
            return 1;
        }

       
        String[] parts = lastLine.split(" ");
        int lastId = Integer.parseInt(parts[0]);
        return lastId + 1;

    } catch (IOException | NumberFormatException e) {
        e.printStackTrace();
    }
    return 0;
}

}
