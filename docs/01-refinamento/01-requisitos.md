# Requisitos

## 1. Objetivo

Desenvolver uma API REST em Java com Spring Boot para gerenciar pautas e sessões de votação, permitindo o cadastro de pautas, abertura de sessões, registro de votos e apuração dos resultados.

A comunicação com o aplicativo mobile será realizada por meio de mensagens JSON, seguindo os formatos definidos no Anexo 1 do desafio.

---

## 2. Requisitos Funcionais

### Cadastrar pauta

* O sistema deve permitir o cadastro de uma nova pauta.
* A pauta deve possuir, no mínimo, um título.

### Consultar pautas

* O sistema deve permitir consultar as pautas cadastradas.
* As pautas devem poder ser apresentadas e selecionadas pelo aplicativo mobile.

### Abrir sessão de votação

* O sistema deve permitir abrir uma sessão de votação para uma pauta existente.
* A duração da sessão deve poder ser informada na abertura.
* Quando não informada, a duração padrão deve ser de 1 minuto.

### Registrar voto

* O sistema deve permitir que um associado vote em uma pauta enquanto a sessão estiver aberta.
* Os votos permitidos são `SIM` e `NAO`.
* O associado deve ser identificado por um ID único.
* Cada associado pode votar apenas uma vez por pauta.

### Apurar resultado

* O sistema deve contabilizar os votos de uma pauta.
* O resultado deve informar as quantidades de votos `SIM`, `NAO` e o total de votos.

### Comunicação com o aplicativo mobile

* As respostas destinadas à interação do usuário devem seguir o formato definido no Anexo 1.
* O sistema deve utilizar os formatos `FORMULARIO` e `SELECAO` conforme o fluxo definido para cada operação.

### Validar elegibilidade do associado (Bônus)

* O sistema deve consultar o serviço externo utilizando o CPF do associado.
* Deve tratar os retornos `404`, `ABLE_TO_VOTE` e `UNABLE_TO_VOTE`.

---

## 3. Requisitos Não Funcionais

### Persistência

* Pautas, sessões e votos devem ser persistidos em banco de dados.
* Os dados não podem ser perdidos após o restart da aplicação.

### Consistência

* O sistema deve garantir que um associado não consiga votar mais de uma vez na mesma pauta, inclusive em requisições concorrentes.

### Performance

* A aplicação deve suportar cenários com centenas de milhares de votos.
* A solução deve possuir testes de performance.

### Configuração

* Configurações externas, como URLs de integrações, devem ser configuráveis sem alteração do código.

### Resiliência

* Falhas em serviços externos não devem derrubar a aplicação.

### Observabilidade

* A aplicação deve possuir logs suficientes para acompanhar seu funcionamento e identificar problemas.

### Testabilidade

* As principais regras de negócio devem possuir testes automatizados.

### Documentação

* A API, a arquitetura e as principais decisões técnicas devem ser documentadas.

### Versionamento (Bônus)

* A API deve possuir uma estratégia definida de versionamento.

---

## 4. Fora do Escopo

### Gerenciamento de associados

Não serão implementadas operações de cadastro, alteração, consulta ou exclusão de associados.

Os associados será consideradao um contexto de negócio já existente. Em um cenário produtivo, sua gestão poderia ser uma funcionalidade existente ou uma responsabilidade de outro componente do sistema.

Para testes, alguns associados serão previamente cadastrados via Liquibase, contendo ID e CPF.

### Gerenciamento de assembleias

O gerenciamento de assembleias não faz parte do escopo desta implementação.

### Segurança

Autenticação e autorização não fazem parte do escopo, conforme permitido pelo enunciado.

### Aplicativo mobile

A implementação do aplicativo mobile não faz parte do projeto. O backend será responsável pelos componentes de servidor e pelos JSONs utilizados pelo aplicativo.

---

## 5. Premissas

* Cada associado possui um ID único.
* Uma pauta pode existir sem uma sessão de votação aberta.
* A sessão possui início e fim.
* A duração começa a ser contabilizada no momento da abertura da sessão.
* Votos somente podem ser registrados enquanto a sessão estiver aberta.
* Um associado pode votar apenas uma vez por pauta.
* Os associados utilizados nos testes serão disponibilizados via Liquibase.
* O aplicativo mobile será responsável por interpretar os JSONs retornados pela API.
