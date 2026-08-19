# Fluxo Mobile

O fluxo abaixo representa a interação entre o aplicativo mobile e a API, utilizando os formatos de tela definidos no Anexo 1.

Os endpoints `/app` são responsáveis por fornecer as telas para o aplicativo mobile.

Os endpoints `/api/v1` são responsáveis pela execução das operações de negócio.

---

## 1. Cadastro de pauta

### Endpoint da tela

```http
GET /app/form/agendas
```

Retorna um `FORMULARIO` para preenchimento dos dados da pauta.

### Exemplo de resposta

```json
{
  "tipo": "FORMULARIO",
  "titulo": "Cadastrar pauta",
  "itens": [
    {
      "tipo": "INPUT_TEXTO",
      "id": "title",
      "titulo": "Título da pauta",
      "valor": ""
    }
  ],
  "botaoAcao": {
    "texto": "Cadastrar",
    "url": "/api/v1/agendas",
    "body": {}
  }
}
```

Ao preencher o formulário e selecionar `Cadastrar`, o aplicativo enviará os dados para:

```http
POST /api/v1/agendas
```

---

## 2. Listagem de pautas para gerenciamento

### Endpoint da tela

```http
GET /app/select/agendas/management
```

Retorna uma `SELECAO` contendo as pautas cadastradas.

### Exemplo de resposta

```json
{
  "tipo": "SELECAO",
  "titulo": "Pautas",
  "itens": [
    {
      "texto": "Reforma do Estatuto",
      "url": "/app/form/agendas/{agendaId}/voting-session",
      "body": {}
    },
    {
      "texto": "Aquisição de imóvel",
      "url": "/app/form/agendas/{agendaId}/voting-session",
      "body": {}
    }
  ]
}
```

Ao selecionar uma pauta, o aplicativo realizará:

```http
POST /app/form/agendas/{agendaId}/voting-session
```

---

## 3. Abertura da sessão de votação

### Endpoint da tela

```http
POST /app/form/agendas/{agendaId}/voting-session
```

Retorna um `FORMULARIO` para informar a duração da sessão.

### Exemplo de resposta

```json
{
  "tipo": "FORMULARIO",
  "titulo": "Abrir sessão",
  "itens": [
    {
      "tipo": "INPUT_NUMERO",
      "id": "durationInMinutes",
      "titulo": "Duração da sessão em minutos",
      "valor": 1
    }
  ],
  "botaoAcao": {
    "texto": "Abrir sessão",
    "url": "/api/v1/agendas/{agendaId}/voting-session",
    "body": {}
  }
}
```

Ao selecionar `Abrir sessão`, o aplicativo enviará os dados para:

```http
POST /api/v1/agendas/{agendaId}/voting-session
```

---

## 4. Listagem de pautas para votação

### Endpoint da tela

```http
GET /app/select/agendas/voting
```

Retorna uma `SELECAO` contendo as pautas disponíveis para votação.

### Exemplo de resposta

```json
{
  "tipo": "SELECAO",
  "titulo": "Escolha uma pauta para votar",
  "itens": [
    {
      "texto": "Reforma do Estatuto",
      "url": "/app/select/agendas/{agendaId}/voting",
      "body": {}
    },
    {
      "texto": "Aquisição de imóvel",
      "url": "/app/select/agendas/{agendaId}/voting",
      "body": {}
    }
  ]
}
```

Ao selecionar uma pauta, o aplicativo realizará:

```http
POST /app/select/agendas/{agendaId}/voting
```

---

## 5. Seleção do voto

### Endpoint da tela

```http
POST /app/select/agendas/{agendaId}/voting
```

Quando a pauta possuir uma sessão aberta, a API retorna uma `SELECAO` com as opções de voto.

### Exemplo de resposta

```json
{
  "tipo": "SELECAO",
  "titulo": "Reforma do Estatuto",
  "itens": [
    {
      "texto": "SIM",
      "url": "/api/v1/agendas/{agendaId}/votes",
      "body": {
        "memberId": "{memberId}",
        "option": "YES"
      }
    },
    {
      "texto": "NÃO",
      "url": "/api/v1/agendas/{agendaId}/votes",
      "body": {
        "memberId": "{memberId}",
        "option": "NO"
      }
    }
  ]
}
```

Ao selecionar `SIM` ou `NÃO`, o aplicativo realizará:

```http
POST /api/v1/agendas/{agendaId}/votes
```
