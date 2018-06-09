package com.yasin.voteapi.tasks;

import com.yasin.voteapi.tasks.impl.VoteReset;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Yasin
 */
public class TaskHandler extends Thread {

    private ScheduledExecutorService scheduledExecutorService;

    public TaskHandler() {
        scheduledExecutorService = Executors.newScheduledThreadPool(1);
    }
    public void init() {
        scheduledExecutorService.schedule(new VoteReset(), 5, TimeUnit.MINUTES); //checks every 5 minutes if players can vote
    }
}
