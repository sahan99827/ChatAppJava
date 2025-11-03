/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Chat.Controller;
import java.util.ArrayList;
import java.util.List;
import Chat.View.ChatView;
/**
 *
 * @author Sahan Geesara
 */
public class ChatManager {
    private static final List<ChatView> chatWindows = new ArrayList<>();

    // Register window when it's created
    public static void addWindow(ChatView window) {
        chatWindows.add(window);
    }

    // Broadcast message to every window
    public static void broadcastMessage(String senderName, String message, ChatView senderWindow) {
        for (ChatView window : chatWindows) {
            if (window == senderWindow) {
                window.setMessage("Me : " + message);
            } else {
                window.setMessage(senderName + " : " + message);
            }
        }
    }
}
