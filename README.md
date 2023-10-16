# Banking

Olá, seja bem-vindo ao desafio para desenvolvedor backend Java, o foco desta etapa é testar seus conhecimentos técnicos em desenvolvimento de API’s de acordo com o escopo abaixo:

## Documentação da API

A API desta aplicação é documentada com o OpenAPI (anteriormente conhecido como Swagger). A seguir, você encontrará informações sobre os endpoints disponíveis e como utilizá-los.

### Acesso à Documentação

A documentação da API pode ser acessada através do Swagger UI, disponível no servidor local:

[Swagger UI](http://localhost:8080/swagger-ui.html)

### Endpoints da API

#### Client Controller

**Pegar Cliente por id**

- **Descrição**: Este endpoint retorna um cliente pelo id.
- **Método**: GET
- **URL**: /clients/{id}

**Editar Cliente**

- **Descrição**: Este endpoint edita um cliente filtrado pelo AccountId.
- **Método**: PUT
- **URL**: /clients/{id}
- **RequestBody**: [ClientRequest](#clientrequest)

**Deletar Cliente**

- **Descrição**: Este endpoint remove um cliente filtrado pelo AccountId.
- **Método**: DELETE
- **URL**: /clients/{id}

**Pegar todos os Clientes**

- **Descrição**: Este endpoint retorna todos os clientes.
- **Método**: GET
- **URL**: /clients

**Criar Cliente**

- **Descrição**: Este endpoint cria um cliente.
- **Método**: POST
- **URL**: /clients
- **RequestBody**: [ClientRequest](#clientrequest)

#### Balance Controller

**Sacar Saldo**

- **Descrição**: Este endpoint executa uma operação de saque do cliente contando uma taxa de administração.
- **Método**: PUT
- **URL**: /clients/{id}/withdraw
- **RequestBody**: [BalanceRequest](#balancerequest)

**Depositar Valor**

- **Descrição**: Este endpoint executa uma operação de depósito para o cliente.
- **Método**: PUT
- **URL**: /clients/{id}/deposit
- **RequestBody**: [BalanceRequest](#balancerequest)

#### Transaction Controller

**Transações de um cliente em uma data**

- **Descrição**: Este endpoint retorna todas as transações de um cliente em uma determinada data.
- **Método**: GET
- **URL**: /clients/{id}/transactions

**Consultar todas as transações**

- **Descrição**: Este endpoint retorna todas as transações do sistema.
- **Método**: GET
- **URL**: /clients/transactions

### Agradecimentos

Agradecemos por utilizar a nossa API. Se você tiver alguma dúvida ou precisar de assistência, entre em contato conosco.

---