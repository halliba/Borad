package de.itech.borad.client;

import de.itech.borad.client.chatlist.ChatRoom;
import de.itech.borad.core.MessageController;
import de.itech.borad.core.StateManager;
import de.itech.borad.models.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;

import java.util.HashMap;

public class Left extends BorderPane {

    MessageController controller;

    private final static String style = "-fx-background-color: #343842; -fx-text-fill: #bdbdbd; -fx-font-size: 18;";
    private final static String styleHover = "-fx-background-color: #454953; -fx-text-fill: #bdbdbd; -fx-font-size: 18;";
    private final static String styleClicked = "-fx-background-color: #232631; -fx-text-fill: #bdbdbd; -fx-font-size: 18;";
    private final static String styleSelected = "-fx-background-color: #232631; -fx-text-fill: #bdbdbd; -fx-font-size: 18;";

    private static final int LEFT_WIDTH = 260;

    private static final int USER_LIST_ELEMENT_HEIGHT = 35;
    private static final int BOX_SPACING = 5;

    private Right rightSide;

    private VBox chatList, userList;

    private Button selectedButton;

    private StateManager stateManager = StateManager.getStateManager();

    private HashMap<User, Circle> userStatus;

    public Left(MessageController controller){

        this.controller = controller;

        userStatus = new HashMap<>();

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
        buttons.setSpacing(BOX_SPACING);

        this.setTop(buttons);
        this.setCenter(chatList);
        this.setBottom(controls);

        HBox newChatRoomControls = new HBox();
        newChatRoomControls.setSpacing(BOX_SPACING);
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


        controls.setPadding(new Insets(BOX_SPACING));
        controls.setSpacing(BOX_SPACING);
        controls.getChildren().addAll(newChatRoomControls, newRoomPassword, showOffline);

        chatList.setSpacing(BOX_SPACING);
        chatList.setPadding(new Insets(BOX_SPACING));
        chatList.getChildren().add(createChatButton("Public"));

        userList.setSpacing(BOX_SPACING);
        userList.setPadding(new Insets(BOX_SPACING));
        userList.setAlignment(Pos.TOP_CENTER);

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

    private void createUserPanel(User user){
        if(!userStatus.containsKey(user)){
            HBox userBox = new HBox();
            Pane circlePane = new Pane();
            Circle onlineCircle = new Circle();
            onlineCircle.setFill(Color.web("#86BB71"));
            onlineCircle.setRadius(4);
            onlineCircle.setTranslateY(USER_LIST_ELEMENT_HEIGHT * 0.5);
            onlineCircle.setTranslateX(USER_LIST_ELEMENT_HEIGHT * 0.5);
            Label label = new Label(user.getName());
            label.setMaxHeight(Double.MAX_VALUE);
            label.setStyle(style);
            circlePane.setMaxHeight(Double.MAX_VALUE);
            circlePane.setMinHeight(USER_LIST_ELEMENT_HEIGHT);
            circlePane.setMinWidth(USER_LIST_ELEMENT_HEIGHT);
            circlePane.setMaxWidth(USER_LIST_ELEMENT_HEIGHT);
            userBox.setStyle(style);
            userBox.setBorder(new Border(new BorderStroke(null, null, new CornerRadii(3), null)));
            userBox.setMinHeight(USER_LIST_ELEMENT_HEIGHT);
            userBox.setMaxWidth(Double.MAX_VALUE);
            VBox.getVgrow(userBox);
            label.setTextAlignment(TextAlignment.CENTER);
            circlePane.getChildren().add(onlineCircle);
            userBox.getChildren().addAll(circlePane, label);
            userList.getChildren().add(userBox);
            userStatus.put(user, onlineCircle);
        }
    }

    public void setIsUserOnline(User user, boolean isOnline){
        Circle onlineCircle = userStatus.get(user);
        if(onlineCircle !=  null ){
            if(isOnline){
                onlineCircle.setFill(Color.web("#86BB71"));
            } else {
                onlineCircle.setFill(Color.web("#d8721e"));
            }
        }else {
            createUserPanel(user);
        }
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
        button.setPadding(new Insets(BOX_SPACING));
        button.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(button, Priority.ALWAYS);
        button.setStyle("-fx-background-color: #333642; -fx-text-fill: #bdbdbd; -fx-font-size: 15;");
        box.getChildren().add(button);
    }

    public void setRightSide(Right rightSide){
        this.rightSide = rightSide;
    }

}
