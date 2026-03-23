# URL Shortener Service

## Tech Stack
- Java
- Spring Boot
- Spring Data JPA
- H2 Database
- Redis
- Docker (optional)

## Features
- Short URL generation using Base62 encoding
- REST API design (v1 versioning)
- Redirect handling
- Redis caching with TTL
- Click count tracking
- Clean layered architecture (Controller, Service, Repository)

## Architecture
Client → Controller → Service → DB + Redis Cache

## Future Improvements
- Rate limiting
- Analytics dashboard
- Docker deployment
