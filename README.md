# VOTE_API
Used for learning purposes and my RuneScape 2012 private server (ran educationally).

What's this about? This is a voting API used for handling vote/update requests. It works with callbacks from an user-defined list.

Example procedure:
User visits https://www.runelocus.com/top-rsps-list/details-45263-aeronps/ and *attempts* to vote

Runelocus now has the task to identify if the vote is valid or not

If valid: Initialize Callback to http://api.com/callback/usr=%username%

http://api.com/callback/ reads the username and rewards the user correspondingly

Known security exploits:
* None

*Supported toplists:
RuneLocus
Rune-Server
TopG
RSPSToplist
Top100Arena
TopRSPS
RSPS-list

From what's been experienced, the username is always the first one written out as query parameter, so therefore the servlet reads out the content after = (and optionally up to &). 


## Setup (How-to):
* Import the project as Maven project
* * https://www.jetbrains.com/help/idea/13.0/importing-project-from-maven-model.pdf
* * https://books.sonatype.com/m2eclipse-book/reference/creating-sect-importing-projects.html
* * (Credits to the authors of the guides)


* Download and install PostgreSQL (https://www.postgresql.org/download/)
* Download and install pgadmin 4 (https://www.pgadmin.org/download/)
* * Write down the data you've been given for accessing the database
* * Open pgAdmin
* * Connect to database server
* * Edit => New Object => New database



### Execute following PostgreSQL-Script to create the container holding the user data:
```
create table if not exists votes(
	vote_count smallserial,
	username text,
	pending_points integer,
	last_vote bigint
);
```

### Let your server intrepretate/save following PostgreSQL-Functions to make the RESTful-API work
[PostgreSQL functions](sql/functions.sql)

Now everything should be set up regarding PostgreSQL.

Now, if you don't know how to host your Spring application, I recommend you [This service](https://www.callicoder.com/deploy-host-spring-boot-apps-on-heroku/)

TODO:
Remove the ids from the toplist and leave it only to their links
