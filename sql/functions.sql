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