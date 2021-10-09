INSERT INTO product (uuid, created_on, description, modified_on, name, price, quantity) VALUES ('3ffdb4d4-0fb4-4754-87b2-3466aa89ba49', '2021-10-09 23:17:49.139014', 'apple', '2021-10-09 23:17:49.139014', 'APPLE', 132.00, 12);
INSERT INTO product (uuid, created_on, description, modified_on, name, price, quantity) VALUES ('4b6e2cd1-00b8-4ef4-8ccb-3fce12a6ba1a', '2021-10-09 23:18:01.310376', 'lemon', '2021-10-09 23:18:01.310376', 'LEMON', 132.00, 12);
INSERT INTO product (uuid, created_on, description, modified_on, name, price, quantity) VALUES ('f8ad154b-8058-4dbd-93ef-773ef4762ded', '2021-10-09 23:17:55.181562', 'orange', '2021-10-09 23:17:55.181562', 'ORANGE', 132.00, 12);

INSERT INTO cart (uuid, created_on, modified_on) VALUES ('dd403b90-7305-4947-bdb7-5435598058e1', '2021-10-09 23:23:50.724348', '2021-10-09 23:37:05.310050');


INSERT INTO user (uuid, created_on, modified_on, user_type, username, cart_uuid) VALUES ('e63784e2-daff-410c-a7ec-91d598365e9d', '2021-10-09 23:18:20.012702', '2021-10-09 23:24:13.220850', 'CUSTOMER', 'user1', 'dd403b90-7305-4947-bdb7-5435598058e1');
INSERT INTO user (uuid, created_on, modified_on, user_type, username, cart_uuid) VALUES ('f35d02b6-12f3-4cac-963f-f704948b06e1', '2021-10-09 23:18:24.585998', '2021-10-09 23:18:24.585998', 'CUSTOMER', 'user3', null);
INSERT INTO user (uuid, created_on, modified_on, user_type, username, cart_uuid) VALUES ('fd51d366-9cce-448c-bf38-8e31b92ef3cb', '2021-10-09 23:18:22.632386', '2021-10-09 23:18:22.632386', 'CUSTOMER', 'user2', null);


INSERT INTO cartitem (uuid, created_on, modified_on, quantity, cart_id, productdo_uuid) VALUES ('02d4983d-2196-49f8-b788-12c93ab89df6', '2021-10-09 23:37:05.295148', '2021-10-09 23:37:10.046891', 2, 'dd403b90-7305-4947-bdb7-5435598058e1', '3ffdb4d4-0fb4-4754-87b2-3466aa89ba49');
