package com.relay.protection.pojo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author liw
 * @version 1.0
 * @date 2022/7/13 16:30
 */

public interface BaseTree<T> {

    Long getId();

    Long getParentId();

    String getName();

    List<T> getChild();

}
