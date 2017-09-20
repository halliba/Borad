package de.itech.borad.client.chat;

import de.itech.borad.core.StateManager;
import de.itech.borad.models.Message;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;


public class ChatBubble extends VBox {

    private Message message;
    private final Label authorLabel = new Label();
    private final Label timeLabel = new Label();
    private final Label messageContent = new Label();

    private HBox messageContentWrapper = new HBox();
    private HBox messageTopWrapper = new HBox();
    private Polygon triangle = new Polygon();
    private Circle circle = new Circle();

    boolean isMe;

    public ChatBubble(Message message, boolean isMe) {
        this.getStyleClass().add("chat-bubble");
        this.message = message;
        authorLabel.setText(message.getUser().getName());
        messageContent.setText(message.getText());
        this.isMe = message.getUser().getName().equals(StateManager.getStateManager().getOwnName());

        initMessageTop();
        initMessageContent();

        this.setPadding(new Insets(10));
        this.getChildren().add(messageTopWrapper);
        this.getChildren().add(triangle);
        this.getChildren().add(messageContentWrapper);

        if(isMe){
            setMe();
        }
    }

    private void initMessageTop(){
        this.messageTopWrapper.getStyleClass().add("message-top");
        messageTopWrapper.setSpacing(6);

        //this.authorLabel.setText(message.getUser().toString());
        this.authorLabel.getStyleClass().add("message-author");
        this.authorLabel.getStyleClass().add("text-black");

        this.timeLabel.setText(message.getTimestamp().toLocaleString());
        this.timeLabel.getStyleClass().add("message-time");

        messageTopWrapper.setPadding(new Insets(0,0,8,0));

        circle.getStyleClass().add("circle");
        circle.setRadius(4);
        circle.setTranslateY(4);

        messageTopWrapper.getChildren().add(circle);
        messageTopWrapper.getChildren().add(this.authorLabel);
        messageTopWrapper.getChildren().add(this.timeLabel);
    }

    private void initMessageContent(){

        messageContentWrapper.getStyleClass().add("message-content-wrapper");
        messageContentWrapper.setPadding(new Insets(0,50,0,0));

        //this.messageContent.setText(message.getContent());
        this.messageContent.getStyleClass().add("message-content");
        this.messageContent.getStyleClass().add("text-white");
        this.messageContent.setWrapText(true);


        triangle.getPoints().setAll(
                10d, 0d,
                0d, 10d,
                20d, 10d
        );
        triangle.getStyleClass().add("triangle");
        triangle.setTranslateX(20);



        messageContentWrapper.getChildren().add(this.messageContent);
    }

    public void setMe(){
        this.getStyleClass().add("me");
        this.setAlignment(Pos.TOP_RIGHT);
        messageContentWrapper.setAlignment(Pos.TOP_RIGHT);
        messageContentWrapper.setPadding(new Insets(0,0,0,50));
        messageTopWrapper.setAlignment(Pos.TOP_RIGHT);
        this.authorLabel.toFront();
        this.circle.toFront();
        triangle.setTranslateX(triangle.getTranslateX()*-1);
    }
}