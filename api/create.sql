create sequence game_sequence start with 1 increment by 1;
create sequence gamer_sequence start with 1 increment by 1;
create sequence gamerrole_sequence start with 1 increment by 1;
create sequence gamesession_sequence start with 1 increment by 1;
create sequence genre_sequence start with 1 increment by 1;
create sequence platform_sequence start with 1 increment by 1;

    create table game (
       id bigint not null,
        name varchar(255) not null,
        primary key (id)
    );

    create table gamer (
       id bigint not null,
        avatar_url varchar(255),
        bio varchar(255),
        birthdate date not null,
        created_at date not null,
        email varchar(255) not null,
        login varchar(20),
        password varchar(255),
        playing_time_end time not null,
        playing_time_start time not null,
        version integer,
        primary key (id)
    );

    create table gamer_favourite_game (
       gamer_id bigint not null,
        game_id bigint not null
    );

    create table gamer_favourite_genre (
       gamer_id bigint not null,
        genre_id integer not null
    );

    create table gamer_gamerrole (
       gamer_id bigint not null,
        gamerrole_id bigint not null
    );

    create table gamer_platform (
       gamer_id bigint not null,
        platform_id bigint not null
    );

    create table gamerrole (
       id bigint not null,
        name varchar(255) not null,
        primary key (id)
    );

    create table gamesession (
       id bigint not null,
        access_type varchar(255) not null,
        availability_times varchar(255),
        created_at date not null,
        date date not null,
        description varchar(255),
        is_competitive boolean not null,
        max_members integer not null,
        min_age integer not null,
        modified_at date not null,
        name varchar(255) not null,
        number_of_members integer not null,
        visibility_type varchar(255) not null,
        gamer_id bigint not null,
        game_id bigint not null,
        primary key (id)
    );

    create table gamesession_member (
       gamesession_id bigint not null,
        gamer_id bigint not null
    );

    create table gamesession_platform (
       gamesession_id bigint not null,
        platform_id bigint not null
    );

    create table genre (
       id integer not null,
        name varchar(255) not null,
        primary key (id)
    );

    create table pending_member (
       gamesession_id bigint not null,
        gamer_id bigint not null,
        message varchar(255),
        requested_at date not null,
        primary key (gamesession_id, gamer_id)
    );

    create table platform (
       id bigint not null,
        name varchar(255) not null,
        primary key (id)
    );

    alter table if exists game 
       add constraint UK_jare70vqqti665ds3b2eh7rk8 unique (name);

    alter table if exists gamer 
       add constraint UK_r7rjyl9vp4kxqp2h84socdxjr unique (email);

    alter table if exists gamer 
       add constraint UK_mh0yryxbnt9773cpj0w7302xa unique (login);

    alter table if exists gamerrole 
       add constraint UK_ir5p1q6jfkej1wq8t4qkw1ru9 unique (name);

    alter table if exists platform 
       add constraint UK_hp36t3hx9su23msu2p5qvukyh unique (name);

    alter table if exists gamer_favourite_game 
       add constraint FKfo2b7leajph3sxnmk5ip33rps 
       foreign key (game_id) 
       references game;

    alter table if exists gamer_favourite_game 
       add constraint FKn8kibf2b9hfs2uiwd8pyabobd 
       foreign key (gamer_id) 
       references gamer;

    alter table if exists gamer_favourite_genre 
       add constraint FKsmmaa3gj5nm80ybr9san9vk5g 
       foreign key (genre_id) 
       references genre;

    alter table if exists gamer_favourite_genre 
       add constraint FKoprgk3ccbli7d2ru9x7fgea3v 
       foreign key (gamer_id) 
       references gamer;

    alter table if exists gamer_gamerrole 
       add constraint FKiwuy7a7ov70r6mahgtqejyg1e 
       foreign key (gamerrole_id) 
       references gamerrole;

    alter table if exists gamer_gamerrole 
       add constraint FK2crx6hwt5w1hmg94svf8br7tj 
       foreign key (gamer_id) 
       references gamer;

    alter table if exists gamer_platform 
       add constraint FKkpj2nyon3kssb0yd2k3h7vrct 
       foreign key (platform_id) 
       references platform;

    alter table if exists gamer_platform 
       add constraint FKd89m70jler0jdwhjxcqfo4qtc 
       foreign key (gamer_id) 
       references gamer;

    alter table if exists gamesession 
       add constraint FKb395cf3retquv8j9b37v3t2iu 
       foreign key (gamer_id) 
       references gamer;

    alter table if exists gamesession 
       add constraint FKbq54c1nwbxa0fnpq47dqe3b31 
       foreign key (game_id) 
       references game;

    alter table if exists gamesession_member 
       add constraint FK5x90q29iqg90blht3fkdhd67i 
       foreign key (gamer_id) 
       references gamer;

    alter table if exists gamesession_member 
       add constraint FKgqqg5haq712w6klr6vc76q641 
       foreign key (gamesession_id) 
       references gamesession;

    alter table if exists gamesession_platform 
       add constraint FK9wjt32jlrcd45kx2tjdn2b9pr 
       foreign key (platform_id) 
       references platform;

    alter table if exists gamesession_platform 
       add constraint FK915iabxec1d1oh609bvxo1lus 
       foreign key (gamesession_id) 
       references gamesession;

    alter table if exists pending_member 
       add constraint FKqyvx5iabqy7dldgh7t0ji82g6 
       foreign key (gamesession_id) 
       references gamesession;

    alter table if exists pending_member 
       add constraint FKpb2a6x16c6n626vbbk3uwwko2 
       foreign key (gamer_id) 
       references gamer;
