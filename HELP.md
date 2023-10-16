# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.1.4/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.1.4/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.1.4/reference/htmlsingle/index.html#web)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.1.4/reference/htmlsingle/index.html#data.sql.jpa-and-spring-data)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

### Richardson nível 2
O nível 2 da maturidade de Richardson é conhecido como "Recursos HTTP" e envolve a implementação de URLs significativas e explora a utilização dos métodos HTTP adequados. Aqui estão as características principais do nível 2:

Usar Recursos: Os recursos da aplicação são identificados por URLs significativas (URIs). Cada recurso deve ser único e representar uma entidade de negócios ou uma coleção de entidades.

Utilização de Métodos HTTP: Os métodos HTTP, como GET, POST, PUT e DELETE, são utilizados de acordo com suas semânticas. Por exemplo, o método GET é usado para recuperar informações, o método POST para criar recursos, o método PUT para atualizá-los e o DELETE para removê-los.

URLs Amigáveis: As URLs devem ser projetadas de forma amigável para humanos e refletir a estrutura lógica da aplicação. Isso torna a API mais fácil de entender e usar.

Manipulação de Coleções: As operações CRUD (Create, Read, Update, Delete) são aplicadas a coleções de recursos usando os métodos HTTP correspondentes. Por exemplo, o método GET pode ser usado para recuperar uma coleção de recursos.

Mensagens de Erro HTTP: A API responde a erros com códigos de status HTTP apropriados, como 404 para "Recurso Não Encontrado" ou 400 para "Solicitação Inválida". As respostas de erro devem fornecer informações úteis sobre o problema.

O nível 2 representa um avanço significativo em relação ao nível 1 (Recursos) da maturidade de Richardson, onde todas as operações eram tratadas como POSTs. No nível 2, a aplicação começa a adotar práticas RESTful mais sólidas, tornando-a mais previsível e alinhada com os princípios da arquitetura REST.

Lembrando que a maturidade de Richardson vai até o nível 4, sendo que o nível 3 envolve a hipermedia como o mecanismo de estado da aplicação (HATEOAS), e o nível 4 é considerado "REST puro". Cada nível representa um grau crescente de aderência aos princípios da arquitetura REST.