# VOTE_API
Used for learning purposes and my RuneScape 2012 private server (ran educationally).

What's this about? This is a voting API used for handling vote/update requests. It works with callbacks from an user-defined list.

Example procedure:
User visits https://www.runelocus.com/top-rsps-list/details-45263-aeronps/ and *attempts* to vote

Runelocus now has the task to identify if the vote is valid or not

If valid: Initialize Callback to http://api.com/callback/usr=%username%


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

### PostgreSQL-Functions essential to make the RESTful-API work
```
CREATE OR REPLACE FUNCTION get_user(playerName text, create_account boolean default false)
  RETURNS json AS
$$
declare result votes;
BEGIN
  IF NOT present(playerName) --if not present, means player never voted
  THEN
    if create_account
    THEN
      PERFORM create_user(0 :: smallint, playerName, 0, 0::bigint);
    end if;
    return json_build_object('vote_count', 0, 'username', (CASE WHEN create_account THEN playerName ELSE '' END) :: text, 'pending_points', 0, 'last_vote',0);
  end if;
  SELECT *
  FROM votes
  WHERE username = playerName
  into STRICT result; -- only one result else throw error
  return row_to_json(result, true); --if there is a result, return result, if not just return default as defined
END;
$$
LANGUAGE plpgsql;
```

```
CREATE OR REPLACE FUNCTION update(row0 smallint, row1 text, row2 integer, row3 bigint) RETURNS void as $$ --user update on callback here

BEGIN
  IF NOT present(row1) --just incase in some way the user didnt get created
    THEN
      PERFORM get_user(row1, true);
  END IF;
  UPDATE votes SET vote_count = row0, pending_points = row2, last_vote = row3 WHERE username = row1; --usage of rowN is because duplicates
end;
$$
LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION present(playerName text)
  RETURNS boolean AS --better than repeating the same statement (select etc)
$$
BEGIN
  RETURN EXISTS(SELECT username
                FROM votes
                WHERE username = playerName);
END;
$$
LANGUAGE plpgsql;
```

```
CREATE OR REPLACE FUNCTION create_user(row0 smallint, row1 text, row2 integer, row3 bigint) --really only just for get_user
  RETURNS void AS
$$
BEGIN
  IF NOT present(row1)
  THEN
    INSERT into votes VALUES (row0, row1, row2, row3);
  END IF;
END;
$$
LANGUAGE plpgsql;
```
