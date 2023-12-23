# About
Project created in the course API REST with Spring Boot.

## Requirements
* JDK 17
* Maven 3.8.x
* MySQL 8
* Postman

## Steps to Setup
1. Create the database
```
CREATE DATABASE taskbook CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci;

USE taskbook;

CREATE TABLE tasks (
  id INTEGER NOT NULL AUTO_INCREMENT,
  description VARCHAR(100) NOT NULL,
  initial_date DATETIME NOT NULL,
  final_date DATETIME NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE utf8mb3_general_ci;
```

2. Clone the application
```
git clone https://github.com/erosvitor/taskbook.git
```

3. Build the project
```
mvn package
```

4. Run the project
```
java -jar target/taskbook-0.0.1-SNAPSHOT.jar
```

## Swagger
```
http://localhost:8080/swagger-ui
```

## Using the project via Postman
1. Create task
```
POST http://localhost:8080/tasks

{
  "description": "Finalizar curso API REST com Spring Boot",
  "initialDate": "2023-09-22T08:00:00",
  "finalDate": "2023-09-22T18:00:00"
}
```

2. Read all tasks
```
GET http://localhost:8080/tasks
```

3. Update task data
```
PUT http://localhost:8080/tasks

{
  "id": 1,
  "description": "Finalizar curso API REST com Spring Boot",
  "initialDate": "2023-09-22T08:00:00",
  "finalDate": "2023-09-25T18:00:00"
}
```

4. Delete task
```
DELETE http://localhost:8080/tasks/1
```

## Using the project via curl
1. Create task
```
curl --location 'http://localhost:8080/tasks' \
     --header 'Content-Type: application/json' \
     --data ' {
         "description": "Finalizar curso API REST com Spring Boot",
         "initialDate": "2023-09-22T08:00:00",
         "finalDate": "2023-09-22T18:00:00"
       }'
```

2. Read all tasks
```
curl --location 'http://localhost:8080/tasks'
```

3. Update task data
```
curl --location --request PUT 'http://localhost:8080/tasks' \
     --header 'Content-Type: application/json' \
     --data ' {
         "id": 2,
         "description": "Finalizar curso API REST com Spring Boot",
         "initialDate": "2023-09-22T08:00:00",
         "finalDate": "2023-09-25T18:00:00"
      }'
```

4. Delete task
```
curl --location --request DELETE 'http://localhost:8080/tasks/1'
```

## License
This project is under license from MIT. For more details, see the LICENSE file.

