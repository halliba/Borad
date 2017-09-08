package de.itech.borad.client.chatlist;

import javafx.beans.property.LongProperty;
import javafx.beans.property.StringProperty;


public interface ChatRoom {
    LongProperty timestampProperty();
    StringProperty nameProperty();
}
