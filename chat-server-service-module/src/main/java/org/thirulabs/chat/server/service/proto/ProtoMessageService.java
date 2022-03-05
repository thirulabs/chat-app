package org.thirulabs.chat.server.service.proto;


import org.thirulabs.chat.commons.proto.Message;
import org.thirulabs.chat.commons.proto.MessageArray;

public interface ProtoMessageService {
    /**
     * Finds message by id
     * @param id of message
     * @return Message if success, or empty when not found
     * Note: Could have used Optional here
     */
    Message findById(Long id);

    /**
     * Finds all messages
     * @return list of messages
     */
    MessageArray findAll();

    /**
     * Saves message
     * @param message message
     * @return saved message
     */
    Message add(Message message);
    /**
     * Returns true if update is success
     * @param message message
     * @return on success
     */
    boolean update(Message message);

    /**
     * Returns true if removal is success
     * @param id of message
     * @return true on success
     */
    boolean remove(Long id);

    /**
     * Removes all messages
     */
    void removeAll();
}
