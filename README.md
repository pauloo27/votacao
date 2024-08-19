# Loja

**Aplicação desktop para o gerenciamento de estoques** desenvolvida como exame
de suficiência para disciplina de Oficina de Integração 2.


## Executando o projeto

É requerido que o Java 22 e o Maven esteja instalado na máquina. Para executar 
testes, é necessário que o Docker esteja instalado e rodando.

1. Clone o repositório
1. Copie o arquivo `db.properties.example` para `db.properties` e preencha com
as informações do seu banco de dados. Pode ser utilizado o Postgres que está 
no `docker-compose.yml`, para isso inicialize o container com 
`docker-compose up -d`. O projeto cria as tabelas automaticamente.
1. Rode o projeto com `mvn clean compile exec:java -Dexec.mainClass="me.pauloo27.java.App"`.

## Rodar os testes

Para rodar os testes, execute o comando `mvn test`. É necessário que o Docker
esteja rodando para que os testes de integração possam ser executados. O
container do banco de dados é criado e destruído a cada execução dos testes.

## Requisitos funcionais

1. Cadastro de usuários
1. Login de usuários
1. Cadastro de produtos
1. Listagem de produtos
1. Atualização de produtos
1. Busca de produtos
1. Exclusão de produtos

## Arquitetura

## Tabelas do banco:
![Tables](./imgs/db.png)

## Arquitetura de alto nível
![Diagram](./imgs/diagram.png)

## Estratégia de testes

Definir a lógica de negócio em "Services" e testá-los por meio de testes
unitários e de integração com o banco de dados utilizado.

## Tecnologias usadas

1. Java v22, Swing para GUI e JDBC para conexão com banco
1. Postgresql v15 como SGBD 
1. JUnit para testes automatizados
1. [Testcontainers](https://testcontainers.com/) para testes de integração
