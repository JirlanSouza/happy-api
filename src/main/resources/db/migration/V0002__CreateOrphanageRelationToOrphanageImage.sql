create table orphanage_images (
    id uuid,
    orphanage_id uuid,
    name varchar(50),
    size integer,
    mime_type varchar(5),
    url varchar,
    primary key (id),
    foreign key (orphanage_id) references orphanages
);