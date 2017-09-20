package de.itech.borad.client;

import de.itech.borad.client.chatlist.ChatRoom;
import de.itech.borad.core.KeepAliveManager;
import de.itech.borad.core.MessageController;
import de.itech.borad.core.StateManager;
import de.itech.borad.models.KeepAlive;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class Left extends BorderPane {

    MessageController controller;

    private final static String style = "-fx-background-color: #343842; -fx-text-fill: #bdbdbd; -fx-font-size: 18;";
    private final static String styleHover = "-fx-background-color: #454953; -fx-text-fill: #bdbdbd; -fx-font-size: 18;";
    private final static String styleClicked = "-fx-background-color: #232631; -fx-text-fill: #bdbdbd; -fx-font-size: 18;";
    private final static String styleSelected = "-fx-background-color: #232631; -fx-text-fill: #bdbdbd; -fx-font-size: 18;";

    private static final int LEFT_WIDTH = 260;

    private Right rightSide;

    private VBox chatList, userList;

    private Button selectedButton;

    private StateManager stateManager = StateManager.getStateManager();

    public Left(MessageController controller){
        this.controller = controller;

        HBox buttons = new HBox();
        VBox controls = new VBox();
        chatList = new VBox();
        userList = new VBox();

        this.setStyle("-fx-background-color: #444753; -fx-text-fill: white;");
        buttons.setStyle("-fx-background-color: #444753; -fx-text-fill: white;");
        controls.setStyle("-fx-background-color: #444753; -fx-text-fill: white;");
        chatList.setStyle("-fx-background-color: #444753; -fx-text-fill: white;");
        userList.setStyle("-fx-background-color: #444753; -fx-text-fill: white;");

        this.setMinWidth(LEFT_WIDTH);
        this.setMaxWidth(LEFT_WIDTH);

        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(5);

        this.setTop(buttons);
        this.setCenter(chatList);
        this.setBottom(controls);

        HBox newChatRoomControls = new HBox();
        newChatRoomControls.setPadding(new Insets(5));
        newChatRoomControls.setSpacing(5);
        newChatRoomControls.setAlignment(Pos.CENTER);
        Button newChatRoom = new Button("+");
        TextField newName = new TextField();
        newName.setPromptText("Room Name");
        TextField newRoomPassword = new TextField();
        newRoomPassword.setStyle(style);
        newRoomPassword.setPromptText("Room Password");
        newChatRoom.setOnMouseClicked(e -> {
            String newRoomName = newName.getText();
            if(newRoomName != null && !newRoomName.equals("")){
                if(!stateManager.chatRoomExists(newRoomName)){
                    if(rightSide.changeToChatPanel(newRoomName)){
                        ChatRoom room = new ChatRoom(newRoomName, newRoomPassword.getText());
                        stateManager.addChatRoom(room);
                        stateManager.setSelectedChatroom(newRoomName);
                        chatList.getChildren().add(createChatButton(newRoomName));
                    }
                }
            }
            newName.setText("");
            newRoomPassword.setText("");
        });
        newName.setStyle(style);
        newChatRoom.setStyle(style);
        newChatRoomControls.getChildren().addAll(newChatRoom, newName);

        CheckBox showOffline = new CheckBox("Incognito Mode");
        showOffline.setStyle(style);
        showOffline.setOnAction(e -> {
            stateManager.setKeepAliveActive(!((CheckBox) e.getSource()).isSelected());
        });


        controls.setPadding(new Insets(5));
        controls.setSpacing(5);
        controls.getChildren().addAll(newChatRoomControls, newRoomPassword, showOffline);

        chatList.setSpacing(5);
        chatList.setPadding(new Insets(5));

        chatList.getChildren().addAll(createChatButton("Public"), createChatButton("chatPanel 2"));

        Button selectRooms = new Button("Chatrooms");
        Button selectUsers = new Button("User");
        setUpButtons(selectRooms, buttons);
        setUpButtons(selectUsers, buttons);
        selectRooms.setOnAction(e -> {
            if(!(this.getCenter() == chatList)){
                this.setCenter(chatList);
            }
        });
        selectUsers.setOnAction(e -> {
            if(!(this.getCenter() == userList)){
                this.setCenter(userList);
            }
        });

    }

    private Button createChatButton(String text){
        Button button = new Button(text);
        button.setMinWidth(LEFT_WIDTH - 10);
        BorderPane.setMargin(button, new Insets(10, 5, 10, 5));
        button.setStyle(style);
        button.setAlignment(Pos.CENTER);
        button.setOnAction(e -> {
            rightSide.changeToChatPanel(text);
        });
        button.setOnMouseEntered(e -> {
            button.setStyle(styleHover);
        });
        button.setOnMouseExited(e -> {
            if(e.getSource() == selectedButton){
                button.setStyle(styleSelected);
            } else {
                button.setStyle(style);
            }
        });
        button.setOnMousePressed(e -> {
            button.setStyle(styleClicked);
        });
        button.setOnMouseReleased((MouseEvent e) -> {
            if(selectedButton != null){
                selectedButton.setStyle(style);
            }
            selectedButton = (Button) e.getSource();
            button.setStyle(styleHover);
        });
        return button;
    }

    private void setUpButtons(Button button, HBox box){
        //BorderPane.setMargin(button, new Insets(10, 5, 10, 5));
        button.setPadding(new Insets(10, 5, 10, 10));
        button.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(button, Priority.ALWAYS);
        button.setStyle("-fx-background-color: #333642; -fx-text-fill: #bdbdbd; -fx-font-size: 15;");
        box.getChildren().add(button);
    }

    public void setRightSide(Right rightSide){
        this.rightSide = rightSide;
    }

}
