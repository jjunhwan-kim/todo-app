 create table todo (
   id varchar(255) not null,
    done bit not null,
    title varchar(255),
    user_id varchar(255),
    primary key (id)
) engine=InnoDB;

create table user_entity (
   id varchar(255) not null,
    email varchar(255) not null,
    password varchar(255) not null,
    username varchar(255) not null,
    primary key (id)
) engine=InnoDB;

alter table user_entity add constraint UK4xad1enskw4j1t2866f7sodrx unique (email);