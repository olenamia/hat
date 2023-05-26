# HAT - Housing Analytics Tool

## MVP Description:
The Minimum Viable Product (MVP) for the Housing Analytics Tool focuses on essential features, which include:
- A dedicated tool that efficiently retrieves and processes data from the Zillow endpoint.
- Basic analytics that present the existing state of house values and the growth of house values over different periods of time.
- An API that responds to user requests, delivering relevant data and allowing authorized users to create, update, and delete data.
- The user interface demonstration displays current home value trends on a map and in a table view. It also shows historical trend charts and offers the ability to filter data by region type (country, state, metro, county).

## Project Description:
HAT (Housing Analytics Tool) provides an API for housing statistics and analytics, crucial for understanding market health and trends.

## Technical features and details: 
1) The project utilizes a multi-modular, 3-tier architecture for organized development and clear separation of responsibilities. 
2) The application employs an automated ETL process to provide updated data and integrates the OpenCsv library for handling CSV data. 
3) The API supports CRUD operations and performs analytics calculations. 
4) Security is based on API key authentication for modification operations. 
5) Swagger UI is used for user-friendly API documentation, interactive exploration, and Try It Out functionality.

## Project Tech Stack:
- Server-Side Framework: Java, Spring Boot, JPA/Hibernate, 
- Build Tool: Maven, 
- Libraries: OpenCsv, Swagger, 
- Database: Postgres, 
- Frontend Technologies: JavaScript, HTML/CSS, jQuery, Bootstrap, GoogleCharts, Thymeleaf.

## APIs Used:
The HAT Application connects to the Zillow endpoint to retrieve and process housing data. Additionally, HAT provides its own analytics API and showcases its usage through a demo, utilizing the built API.
