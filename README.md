# VOTE_API
Used for learning purposes and my RuneScape 2012 private server (ran educationally).

What's this about? This is a voting API used for handling vote/update requests. It works with callbacks from an user-defined list.

Example procedure:
User visits https://www.runelocus.com/top-rsps-list/details-45263-aeronps/ and votes
Runelocus now has the task to identify if the vote is valid or not

If valid: send GET request to http://api.com/callback/usr=%username%&



##Setup:
SQL-Script to create the container holding the user data:
`create table if not exists votes
(
	vote_count smallserial,
	username text,
	pending_points integer,
	last_vote bigint
);`


