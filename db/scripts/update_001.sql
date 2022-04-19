create table todo (
                      id serial primary key,
                      name text,
                      description text,
                      created timestamp,
                      done boolean
);