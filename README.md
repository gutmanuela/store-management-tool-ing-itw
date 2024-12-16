# ing-store-management-tool

The Store Management Tool is a Spring Boot application designed to help store managers efficiently manage inventory, product, and stock movements. It provides features such as product management, stock-tracking, role-based access control.

![img.png](img.png)

Features
- Get all users
- Create, update, delete user
- Gel all roles
- Create update delete roles
- Track stock movement (ex add stock for product)
- Create, update, delete product
- Get product in a specific category
- Get all categories
- Add, delete category

Proposal for further development:
- add, get, delete customers and suppliers
- get all orders
- perform order for a customer
- get all orders for a customer
- get all suppliers for a product


Authorization is role based. Application was designed with 2 main roles: admin and product-manager.
Admin is able to access all the endpoints. Product-manager role allows user to access resources like products, categories.

For local setup & development:
- jdk 21
- MySQL Workbench
- Sql Server
- postman (for testing the endpoints)

Postman collection will be attached.
  