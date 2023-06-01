create sequence orders_seq start with 1 increment by 50;
create sequence restaurants_seq start with 1 increment by 50;

create table orders (
    id bigint not null,
    address varchar(255),
    price integer,
    status varchar(255),
    restaurant_id bigint,
    primary key (id)
);

create table restaurants (
    id bigint not null,
    address varchar(255),
    is_vegan boolean,
    name varchar(255),
    phone_number varchar(255),
    primary key (id)
);

create table restaurants_orders (restaurant_entity_id bigint not null, orders_id bigint not null);

alter table if exists restaurants_orders add constraint UK_oyo9wn5wj3oc3dhxmbasq9qp0 unique (orders_id);
alter table if exists orders add constraint FK2m9qulf12xm537bku3jnrrbup foreign key (restaurant_id) references restaurants;
alter table if exists restaurants_orders add constraint FKit81jhijbk4bbrsrxxyg3jpyh foreign key (orders_id) references orders;
alter table if exists restaurants_orders add constraint FK26n7fxridlavsx99ub46dbvh1 foreign key (restaurant_entity_id) references restaurants;
