ALTER TABLE eshopdatabase.person_groups DROP FOREIGN KEY FK_PERSON_GROUPS_GROUPS;
ALTER TABLE eshopdatabase.person_groups DROP FOREIGN KEY FK_PERSON_GROUPS_PERSON;
ALTER TABLE eshopdatabase.order_detail DROP FOREIGN KEY FK_ORDER_DETAIL_ORDER;
ALTER TABLE eshopdatabase.order_detail DROP FOREIGN KEY FK_ORDER_DETAIL_PRODUCT;
ALTER TABLE eshopdatabase.customer_order DROP FOREIGN KEY FK_CUSTOMER_ORDER_CUSTOMER;
ALTER TABLE eshopdatabase.customer_order DROP FOREIGN KEY FK_CUSTOMER_ORDER_STATUS;
ALTER TABLE eshopdatabase.product DROP FOREIGN KEY FK_PRODUCT_CATEGORY_ID;
DROP TABLE eshopdatabase.person_groups;
DROP TABLE eshopdatabase.order_detail;
DROP TABLE eshopdatabase.customer_order;
DROP TABLE eshopdatabase.product;
DROP TABLE eshopdatabase.person;
DROP TABLE eshopdatabase.order_status;
DROP TABLE eshopdatabase.groups;
DROP TABLE eshopdatabase.category;



