# Consulta Crédito

Este projeto é composto por um backend feito em Java 8 e Spring Boot e um frontend feito em Angular. 
O banco de dados PostgreSQL. 
Faz a publicação em um tópico do Kafka
Todos os serviços são orquestrados via Docker Compose.

## Como executar o projeto

1. **Pré-requisitos:**
   - Docker
   - Docker Compose

2. **Suba todos os serviços:**

   ```bash
   docker-compose up --build
   ```

   O comando irá:
   - Construir as imagens do backend e frontend
   - Subir os containers do backend, frontend, banco de dados, Kafka e Zookeeper

3. **Acesse a aplicação:**
   - Frontend: [http://localhost](http://localhost)
   - Backend (API): [http://localhost:8080/api](http://localhost:8080/api)

   A aplicação já tem alguns dados de exemplo, basta buscar pelas NFS-es : 7891011 ou 1122334 
   Obs: Garanta que não tenham outras aplicações rodando na portas locais: 80 e 8080

4. **Parar os serviços:**

   ```bash
   docker-compose down -v --remove-orphans
   ```
---