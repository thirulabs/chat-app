package org.thirulabs.chat.server.service;

import org.thirulabs.chat.commons.Message;

import java.util.List;

public interface MessageService {
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
    List<Message> findAll();

    /**
     * Saves message
     * @param message message
     * @return saved message
     */
    Message add(Message message);
    /**
     * Returns true if update is success
     * @param id of message
     * @param message message
     * @return on success
     */
    boolean update(Long id, Message message);

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
