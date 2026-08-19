# Endpoints da API

Os endpoints abaixo representam as operações de negócio da aplicação.

Os endpoints responsáveis pela montagem das telas do aplicativo mobile estão documentados separadamente.

A API será versionada utilizando `/api/v1`.

---

## 1. Criar Pauta

### Endpoint

```http
POST /api/v1/agendas
```

### Request

```json
{
  "title": "Reforma do Estatuto"
}
```

### Response

```http
HTTP 201 Created
```

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "title": "Reforma do Estatuto",
  "createdAt": "2026-08-19T18:00:00Z"
}
```

---

## 2. Listar Pautas

### Endpoint

```http
GET /api/v1/agendas
```

### Response

```http
HTTP 200 OK
```

```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "title": "Reforma do Estatuto",
    "createdAt": "2026-08-19T18:00:00Z"
  },
  {
    "id": "6ba7b810-9dad-11d1-80b4-00c04fd430c8",
    "title": "Aquisição de imóvel",
    "createdAt": "2026-08-19T18:05:00Z"
  }
]
```

---

## 3. Abrir Sessão de Votação

### Endpoint

```http
POST /api/v1/agendas/{agendaId}/voting-session
```

### Request

```json
{
  "durationInMinutes": 1
}
```

O campo `durationInMinutes` é opcional.

Quando não informado, será utilizada a duração padrão de 1 minuto.

### Response

```http
HTTP 201 Created
```

```json
{
  "id": "7c9e6679-7425-40de-944b-e07fc1f90ae7",
  "agendaId": "550e8400-e29b-41d4-a716-446655440000",
  "startedAt": "2026-08-19T18:10:00Z",
  "endedAt": "2026-08-19T18:11:00Z"
}
```

---

## 4. Registrar Voto

### Endpoint

```http
POST /api/v1/agendas/{agendaId}/votes
```

### Request

```json
{
  "memberId": "c56a4180-65aa-42ec-a945-5fd21dec0538",
  "option": "YES"
}
```

Valores permitidos:

```text
YES
NO
```

### Response

```http
HTTP 201 Created
```

```json
{
  "id": "16fd2706-8baf-433b-82eb-8c7fada847da",
  "agendaId": "550e8400-e29b-41d4-a716-446655440000",
  "memberId": "c56a4180-65aa-42ec-a945-5fd21dec0538",
  "option": "YES",
  "createdAt": "2026-08-19T18:10:30Z"
}
```

---

## 5. Consultar Resultado da Votação

### Endpoint

```http
GET /api/v1/agendas/{agendaId}/result
```

### Response

```http
HTTP 200 OK
```

```json
{
  "agendaId": "550e8400-e29b-41d4-a716-446655440000",
  "yes": 10,
  "no": 7,
  "total": 17
}
```

A regra para determinar o resultado final da agenda, como `APPROVED` ou `REJECTED`, será definida durante o refinamento das regras de negócio.

---

## 6. Resumo dos Endpoints

| Método | Endpoint                                    | Descrição           |
| ------ | ------------------------------------------- | ------------------- |
| `POST` | `/api/v1/agendas`                           | Criar agenda        |
| `GET`  | `/api/v1/agendas`                           | Listar agendas      |
| `POST` | `/api/v1/agendas/{agendaId}/voting-session` | Abrir sessão        |
| `POST` | `/api/v1/agendas/{agendaId}/votes`          | Registrar voto      |
| `GET`  | `/api/v1/agendas/{agendaId}/result`         | Consultar resultado |
