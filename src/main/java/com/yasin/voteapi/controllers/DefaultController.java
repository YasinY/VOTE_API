package com.yasin.voteapi.controllers;

import com.yasin.voteapi.database.Database;
import com.yasin.voteapi.datasets.toplist.ToplistHandler;
import com.yasin.voteapi.datasets.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Yasin
 */
@RestController
public class DefaultController {

    private boolean validHost(String host) {
        if (host.contains("localhost")) {
            return true;
        }
        return ToplistHandler.getInstance().getToplists().stream().anyMatch(toplist -> toplist.getName().contains(host));
    }

    private String getUsername(String link) {
        int equalOperator = link.indexOf("=");
        int andOperator = link.indexOf('&');
        if (andOperator != -1) {
            return link.substring(equalOperator + 1, andOperator);
        }
        return link.substring(equalOperator + 1, link.length());
    }

    @RequestMapping(value = "/callback/{link}", method = RequestMethod.GET) //regex is diff in java..
    public void callback(@RequestHeader String host, @PathVariable("link") String link) {
        if (validHost(host)) {
            int reward = 0;
            if (host.contains("runelocus")) {
                reward = 10;
            }
            update(getUsername(link), reward);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/vote/{user}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getVote(@PathVariable("user") String user) {
        return Database.getInstance().getUser(user);
    }


    private void update(String username, int points) {
        ResponseEntity request = getVote(username);
        if (request.hasBody()) { //if user exists, even after creation, reward
            User voter = (User) request.getBody();
            if (voter.getVotesToday() < ToplistHandler.getInstance().getToplists().size()) {
                Database.getInstance().updateUser(voter.getVoteCount() + 1, voter.getName(),
                        voter.getPendingPoints() + points, voter.getLastVote());
            }
        }
    }
}
