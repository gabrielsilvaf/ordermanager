# Order Manager

## Instructions

- Clone the application
```sh
https://github.com/gabrielsilvaf/ordermanager.git
```

- Change the parameters of database and email on application properties
```sh
resources/application.properties
```

- Running the app:
```sh
mvn spring-boot:run
```


## Endpoints

Postman files are included in the resources/postman folder.

- Users

| Name | Method | URL |
| ------ | ------ | ------ |
| List All | GET | /users/ |
| Find | GET | /users/{id} |
| Create | POST | /users/ |
| Delete | DELETE | /users/{id} |
| Update | PUT | /users/{id} |


- Item

| Name | Method | URL |
| ------ | ------ | ------ |
| List All | GET | /items/ |
| Find | GET | /items/{id} |
| Create | POST | /items/ |
| Delete | DELETE | /items/{id} |
| Update | PUT | /items/{id} |

- Stock Movements

| Name | Method | URL |
| ------ | ------ | ------ |
| List All | GET | /stocks/ |
| Find | GET | /stocks/{id} |
| Create | POST | /stocks/ |
| Delete | DELETE | /stocks/{id} |
| Update | PUT | /stocks/{id} |
| Trace Orders | GET | /stocks/{id}/trace-orders |

- Orders

| Name | Method | URL |
| ------ | ------ | ------ |
| List All | GET | /orders/ |
| Find | GET | /orders/{id} |
| Create | POST | /orders/ |
| Delete | DELETE | /orders/{id} |
| Update | PUT | /orders/{id} |
| Trace Stocks | GET | /orders/{id}/trace-stocks |
