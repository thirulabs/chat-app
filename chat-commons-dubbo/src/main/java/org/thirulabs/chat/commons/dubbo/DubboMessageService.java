package org.thirulabs.chat.commons.dubbo;

import java.util.List;

public interface DubboMessageService {
    DubboMessage findById(Long id);

    List<DubboMessage> findAll();

    DubboMessage add(DubboMessage dubboMessage);

    boolean update(Long id, DubboMessage dubboMessage);

    boolean remove(Long id);

    void removeAll();
}
