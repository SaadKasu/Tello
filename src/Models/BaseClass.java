package Models;

import java.util.Date;
import java.util.Deque;
import java.util.LinkedList;
import java.util.UUID;
import java.util.concurrent.DelayQueue;

public class BaseClass {
    protected String id, lastModifiedBy, createdBy;
    protected Date lastModifiedAt, createdAt;

    BaseClass(){
        this.id = UUID.randomUUID().toString();
        this.lastModifiedAt = this.createdAt =new Date();
        Deque<Integer> deque = new LinkedList<>();
        deque.re
    }
}
