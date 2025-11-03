package Chat.Controller;

import Chat.Entity.Chat;
import Chat.Entity.User;
import java.io.*;
import java.util.*;

public class ChatController {
    public List<Chat> chatlist = new ArrayList<>();
    private boolean loadingFromFile = false;

    public ChatController() {}

    public void sendMessage(Chat chat) {
        int id = generateId();
        chat.setId(id);
        chatlist.add(chat);

        if (!loadingFromFile) {
            saveToFile(chat);
        }
    }

    private void saveToFile(Chat chat) {
        try (FileWriter fw = new FileWriter("Message.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            out.println(chat.getId() + "," +
                        chat.getUser().getId() + "," +
                        chat.getMessage());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

       public void loadOldMessages() throws IOException {
            loadingFromFile = true;
            chatlist.clear();

            List<User> userList = new ArrayList<>();
            File userFile = new File("User.txt");
            if (userFile.exists()) {
                try (BufferedReader userReader = new BufferedReader(new FileReader(userFile))) {
                    String userLine;
                    while ((userLine = userReader.readLine()) != null) {
                        String[] userParts = userLine.split("\\|");
                        if (userParts.length >= 2) {
                            int uid = Integer.parseInt(userParts[0]);
                            String uname = userParts[1];

                            User user = new User();
                            user.setId(uid);
                            user.setName(uname);
                            userList.add(user);
                        }
                    }
                }
            }

            File file = new File("Message.txt");
            if (!file.exists()) {
                loadingFromFile = false;
                return;
            }

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    if (parts.length >= 3) {
                        int messageId = Integer.parseInt(parts[0]);
                        int userId = Integer.parseInt(parts[1]);
                        String message = parts[2];

                        // Find user by ID
                        User user = null;
                        for (User u : userList) {
                            if (u.getId() == userId) {
                                user = u;
                                break;
                            }
                        }
                        if (user == null) { 
                            user = new User();
                            user.setId(userId);
                            user.setName("Unknown User");
                        }

                        Chat chat = new Chat(user, message);
                        chat.setId(messageId);
                        chatlist.add(chat);
                    }
                }
            } finally {
                loadingFromFile = false;
            }
        }


    public static int generateId() {
        try {
            File file = new File("Message.txt");
            if (!file.exists()) {
                file.createNewFile();
                return 1;
            }

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line, lastLine = null;
            while ((line = br.readLine()) != null) {
                lastLine = line;
            }
            br.close();

            if (lastLine == null || lastLine.isEmpty()) {
                return 1;
            }

            String[] parts = lastLine.split(",");
            int lastId = Integer.parseInt(parts[0]);
            return lastId + 1;

        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
