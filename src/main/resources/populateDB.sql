create or replace function random_string(length integer) returns text as
$$
declare
  chars text[] := '{a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z}';
  result text := '';
  i integer := 0;
begin
  if length < 0 then
    raise exception 'Given length cannot be less than 0';
  end if;
  for i in 1..length loop
    result := result || chars[1+random()*(array_length(chars, 1)-1)];
  end loop;
  return result;
end;
$$ language plpgsql;
-- insert initial data
INSERT INTO a (word) SELECT
                       random_string(4)
                     FROM generate_series(1, 2000000) s(i);

INSERT INTO b (word) SELECT
                       random_string(4)
                     FROM generate_series(1, 2000000) s(i);

INSERT INTO c (id)
  SELECT id AS OID FROM a LEFT JOIN b ON a.word = b.word WHERE b.id2 ISNULL
  UNION
  SELECT id2 AS OID FROM a RIGHT JOIN b ON a.word = b.word WHERE a.id ISNULL ;
