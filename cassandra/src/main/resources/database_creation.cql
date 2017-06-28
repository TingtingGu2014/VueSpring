CREATE KEYSPACE IF NOT EXISTS xprotocol WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'}  AND durable_writes = true;

CREATE TABLE IF NOT EXISTS xprotocol.user_details (

    user_details_id timeuuid,

    user_id bigint,

    address text,

    state varchar,

    city varchar,

    zipcode varchar,

    PRIMARY KEY (user_details_id)

) ;
