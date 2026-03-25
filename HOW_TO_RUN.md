# Crisp & Chill — How to Run

## 1. Start MySQL
Open MySQL and run once (first time only):
```sql
CREATE DATABASE IF NOT EXISTS Crisp_Chill;
```

## 2. Start the Backend
```
cd backend
mvn clean install
mvn spring-boot:run
```
Runs on http://localhost:8080
Products are auto-seeded into MySQL on first startup.

## 3. Start the Frontend
Open a new terminal in the project root:
```
http-server . -p 3000
```
Open http://localhost:3000 in your browser.

---

## Credentials
- MySQL user: `root` / password: `root`
- Database: `Crisp_Chill`
- Backend port: `8080`
- Frontend port: `3000`
