CREATE TABLE orphanages (
    id uuid,
    name varchar(50),
    latitude float,
    longitude float,
    about text,
    instructions text,
    opening_hours varchar(50),
    opening_on_weekends boolean default false,
    primary key(id)
)