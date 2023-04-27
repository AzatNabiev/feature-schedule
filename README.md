1) The project has users with different roles - Manager, Developer, Tester.
2) The project has several Features, each of which is broken down into subtasks (Tasks).
3) Each entity has its own status - Open, In Progress, Resolved, Completed.
4) The project development process looks as follows:
- The Manager creates Features and Tasks, assigns them to the Developer.
- The Developer completes the Task and transitions it to the Tester with a status of Resolved.
- The Tester checks the Task, and if it's completed, closes it. If it's not completed, creates a Bug and transitions the Task to In Progress. To transition the Task to In Progress, it's mandatory to create a Bug.
- If all Tasks in the Feature are completed, the Manager can close the Feature.

Tasks should be automatically assigned to the appropriate performer if known (e.g., when the Tester returns the Task to In Progress).
Regarding technologies, the project uses Spring Boot, Hibernate, Spring JPA, Spring Security, Flyway, and PostgreSQL.
The creation of tables and their population with test data should be done automatically (through migrations).
Next, an endpoint needs to be added to search for a Task by user (if specified), name (if specified), and status (if specified). The search should be optimal.
All services should be covered by unit tests.
Two approaches should be tried:

Use some mock library to avoid communicating with a real database.
Use testContainers. In other words, install Docker, connect the library, and every time tests are run, a separate Docker container with PostgreSQL and a new database will be deployed.
