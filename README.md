# iTrack API

API REST para gerenciamento de **chamados, usuários, setores e equipamentos de TI**, desenvolvida como projeto de estudo com Java, Spring Boot e PostgreSQL.

O projeto tem como objetivo praticar a construção de uma aplicação back-end completa, passando por modelagem de banco de dados, relacionamentos com JPA, criação de CRUD, regras de negócio, organização em camadas e integração com PostgreSQL.

## Status do projeto

Primeira versão funcional do back-end.

Atualmente a aplicação permite:

- cadastrar, consultar, atualizar e excluir setores;
- cadastrar, consultar, atualizar e excluir usuários;
- cadastrar, consultar, atualizar e excluir equipamentos;
- cadastrar, consultar, atualizar e excluir chamados;
- associar usuários e equipamentos aos seus respectivos setores;
- associar chamados a usuários e, opcionalmente, a equipamentos;
- impedir o uso de patrimônio já cadastrado em outro equipamento;
- iniciar novos chamados com o status `ABERTO`;
- definir automaticamente a data de abertura do chamado;
- persistir os dados em PostgreSQL.

## Tecnologias

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- Postman
- Git / GitHub


## Próximas melhorias

As próximas etapas planejadas para o projeto incluem:

- padronização do tratamento de erros HTTP;
- validação de campos obrigatórios;
- respostas adequadas com `400`, `404` e `409`;
- `@RestControllerAdvice`;
- DTOs para separar entidades JPA dos dados recebidos pela API;
- regras de transição de status dos chamados;
- testes automatizados;
- documentação com Swagger / OpenAPI;
- autenticação e autorização;
- interface front-end;
- versionamento do banco com migrations;
- deploy da aplicação.

## Objetivo

Este projeto foi desenvolvido com finalidade de estudo e portfólio, acompanhando a evolução no desenvolvimento back-end com Java e Spring Boot.

A ideia é evoluir o iTrack gradualmente, adicionando regras de negócio, segurança, testes e interface sem perder a organização da arquitetura construída na primeira versão.


#### Mais breve atualizarei melhor o READ ME mais sobre o projeto, até mais! :)