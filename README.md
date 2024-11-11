# SpringEntryTest - Book Management CRUD Application

SpringEntryTest is a simple book management application with CRUD functionality, developed using Java Spring Boot and PostgreSQL as the database.

## What's Inside

This project uses the following components:

- **Spring Boot**: For building and deploying the web application.
- **Spring Data JPA**: To interact with the PostgreSQL database.
- **PostgreSQL**: The database used for storing application data.
- **Spring Validation**: To validate input data.
- **Lombok**: For generating getters, setters, and constructors automatically.
- **Spring Boot DevTools**: Development tools for a streamlined workflow.

## Installation

1. **Import the Project into the IDE**:

- Use an IDE such as IntelliJ IDEA or Eclipse to import the project.

2. **Configure the Database:**:

- Create a PostgreSQL database named `BookStore`.
+ Set up a `book` table in the BookStore database with the following fields:

   | Field              | Type       | Description                         |
   | ------------------ | ---------- | ----------------------------------- |
   | **id**             | String     | Primary key, auto-generated UUID    |
   | **title**          | String     | Book title                          |
   | **author**         | String     | Author's name                       |
   | **published_date** | Date       | Publication date                    |
   | **isbn**           | String     | ISBN code (unique)                  |
   | **price**          | BigDecimal | Book price                          |
    
- Update the database configuration in `src/main/resources/application.properties`:
  ```properties
  spring.datasource.url=jdbc:postgresql://localhost:5432/BookStore
  spring.datasource.username=your_username
  spring.datasource.password=your_password
  spring.jpa.hibernate.ddl-auto=update
  ```

3. **Run the Application**:
    - Open the terminal and navigate to the project's root directory.
    - Start the application with the command:
      ```bash
      mvn spring-boot:run
      ```

The application will launch and can be accessed at [http://localhost:8080](http://localhost:8080).

## Data Validation and Error Handling

This section describes the data validation and error-handling mechanisms employed by the application to ensure data integrity and provide meaningful feedback to users.

### Data Validation

The application performs validation on input book data to ensure accuracy and consistency, applying constraints on each field as follows:

- **Title**:

    - **Cannot be blank** (`@NotBlank`): The title field must have a value and cannot be empty.
    - **Maximum length limit** (`@Size(max = 100)`): The title's maximum length is 100 characters.

- **Author**:

    - **Cannot be blank** (`@NotBlank`): The author field must have a value and cannot be empty.
    - **Contains only letters and spaces** (`@Pattern`): The author’s name can only include letters, spaces, and Vietnamese accent characters (e.g., à, á, â,...).
    - **Maximum length limit** (`@Size(max = 50)`): The author’s name can be up to 50 characters long.

- **Published Date**:

    - **Must be current or past date** (`@PastOrPresent`): The book's published date must be either today or in the past.

- **ISBN**:

    - **Valid format** (`@Pattern`): The ISBN must adhere to the standard ISBN-13 or ISBN-10 format.
    - **Unique**: The application ensures that the ISBN does not duplicate any existing books in the database.

- **Price**:
    - **Cannot be null** (`@NotNull`): The book’s price must be specified and cannot be empty.
    - **Positive value** (`@DecimalMin(value = "0.0", inclusive = false)`): The price must be greater than zero.

## Error Handling

The application implements error handling to ensure users receive clear and meaningful feedback in case of issues. Some key error-handling scenarios include:

- **Book not found for a given ID**: If a user requests a book by an ID that does not exist in the database, the application will throw a `BookNotFoundException` and return the error message `"Book not found with id: {id}"`.

- **Invalid or duplicate ISBN**: If the ISBN format is invalid or already exists in the database, the application will throw an `IllegalArgumentException` with the message `"A book with the same ISBN already exists"`.

- **Invalid price**: If the book's price does not meet the positive value requirement, the system will return the validation error message `"Price must be greater than 0"`.

- **Invalid API requests**: If the request is invalid or missing required information, relevant validation error messages will be returned based on the constraints (`@NotBlank`, `@Pattern`, `@Size`, `@PastOrPresent`, `@DecimalMin`).

Through these validation and error-handling mechanisms, the application ensures data integrity and validity within the book management system.

## API Endpoints

### Create Book

- **POST /books**
    - Creates a new book in the database.
    - **Request Body**:
      ```json
      {
        "title": "Book Title",
        "author": "Author Name",
        "publishedDate": "2024-01-01",
        "isbn": "978-3-16-148410-0",
        "price": 199.99
      }
      ```
    - **Response**:
        - **Status**: 201 Created
        - **Body**:
          ```json
          {
            "status": "success",
            "message": "Book created successfully",
            "data": {
              "id": "UUID",
              "title": "Book Title",
              "author": "Author Name",
              "publishedDate": "2024-01-01",
              "isbn": "978-3-16-148410-0",
              "price": 199.99
            }
          }
          ```

### Retrieve All Books with Pagination

- **GET /books**
    - Retrieves all books with pagination.
    - **Query Parameters**:
        - `page`: Page number (default: 0)
        - `size`: Number of items per page (default: 10)
    - **Response**:
        - **Status**: 200 OK
        - **Body**:
          ```json
          {
            "status": "success",
            "message": "Books fetched successfully",
            "data": [
              {
                "id": "UUID",
                "title": "Book Title",
                "author": "Author Name",
                "publishedDate": "2024-01-01",
                "isbn": "978-3-16-148410-0",
                "price": 199.99
              },
              ...
            ]
          }
          ```
        - If no books are found:
          ```json
          {
            "status": "success",
            "message": "No books found",
            "data": []
          }
          ```

### Retrieve a Book by ID

- **GET /books/{id}**
    - Retrieves the details of a book by its ID.
    - **Path Parameter**:
        - `id`: The ID of the book to retrieve.
    - **Response**:
        - **Status**: 200 OK
        - **Body**:
          ```json
          {
            "status": "success",
            "message": "Found book with id = {id}",
            "data": {
              "id": "UUID",
              "title": "Book Title",
              "author": "Author Name",
              "publishedDate": "2024-01-01",
              "isbn": "978-3-16-148410-0",
              "price": 199.99
            }
          }
          ```

### Update Book

- **PUT /books/{id}**
    - Updates the details of an existing book by its ID.
    - **Path Parameter**:
        - `id`: The ID of the book to update.
    - **Request Body**:
      ```json
      {
        "title": "Updated Book Title",
        "author": "Updated Author Name",
        "publishedDate": "2024-02-01",
        "isbn": "978-3-16-148410-1",
        "price": 249.99
      }
      ```
    - **Response**:
        - **Status**: 200 OK
        - **Body**:
          ```json
          {
            "status": "success",
            "message": "Book updated successfully",
            "data": {
              "id": "UUID",
              "title": "Updated Book Title",
              "author": "Updated Author Name",
              "publishedDate": "2024-02-01",
              "isbn": "978-3-16-148410-1",
              "price": 249.99
            }
          }
          ```

### Delete Book

- **DELETE /books/{id}**
    - Deletes a book by its ID.
    - **Path Parameter**:
        - `id`: The ID of the book to delete.
    - **Response**:
        - **Status**: 204 No Content
        - **Body**:
          ```json
          {
            "status": "success",
            "message": "Book deleted successfully",
            "data": null
          }
          ```
