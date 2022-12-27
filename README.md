# Sicred - Desafio Técnico 
 * Autor: Gabriel Marques (https://www.linkedin.com/in/gabriel-marques1989/)
 * Última atualização do readme: 19/12/2022
 
## Tecnologias
- Spring Boot (v2.7.4)
- Spring Data JPA
- Spring Validation
- Spring Security + JWT Token
- PostgreSQL
- Mapstruct
- Lombok
- Swagger
- Gatling
- TestNg
- JMS

## Projeto base (boilerplate)
  - https://github.com/Genc/spring-boot-boilerplate

## Instruções

- Antes de mais nada, é necessário subir uma instância de banco de dados com o database "sicred"
- Configurar o banco de dados
   - No arquivo de configuração (src/main/resources/application.yml), definir as configurações de banco de dados (postgresql)
   - Por padrão, o projeto já está configurado para executar automaticamente os scripts de criação de tabelas/sequences/etc
- Configurar servidor JMS (https://activemq.apache.org/) e startá-lo
- No **application.yml** seguem as configurações padrão para o ActiveMQ
  - Caso não seja configurado corretamente, o projeto irá executar sem problemas, porém, não irá enviar as mensagens para a fila
  - Será logado o erro "Could not refresh JMS Connection for destination [...]"
- Subir a aplicação
  - Alt + Shift + F10 (Atalho do run no intellij) > SicredApplication
  - Aplicação está configurada para rodar na porta 8080 (src/main/resources/application.yml)

- Testes manuais:
  - Na pasta src/support/postman estão as collections (e variáveis) para teste dos endpoints
- Testes unitários:
   - Alt + Shift + F10 (Atalho do run no intellij) > tests
- Para os testes de performance:
   - Necessário que a aplicação esteja rodando na porta 8080
   - Alt + Shift + F10 (Atalho do run no intellij) > Performance Test
   - O arquivo para definir as configurações é PerformanceSimulation.java (configurar definições - não está com muita abstração em razão de tempo - foi feito do zero)
- Swagger:
  - Acessível via: http://localhost:8080/swagger-ui/index.html

## Importante
- Foi implementado um sistema de login, é necessário estar logado (jwt via header de authorization) para criar pautas, sessões ou votar
- Para a mensageria, foi implementado um consumer simples que apenas registra o log da mensagem recebida
- Foi criado um método agendado (FinishSession.finishEndedSessions - executado a cada 60 segundos). 
  - Esse método chama o serviço, que busca as sessões já encerradas e altera sua flag para "ended = true"
  - Além disso, esse método faz o envio da votação finalizada para o tópico JMS
  - Poderia ter sido criada uma validação na votação (se a sessão já foi encerrada), mas preferi evitar refactors no fim do projeto.
- Venho de uma escola em que se evita comentários nos códigos, por isso evitei documentar usando comentários ou javadocs, mas não tenho problema em fazê-lo.
- Tudo foi feito na MASTER por pura praticidade (visto que é um projeto solo), mas em projetos reais, utilizo o gitflow (inclusive com PR interno (e avaliação de qualidade de código interna) + PR oficial pra branchs de desenvolvimento coletivo).

## Postman

- Para facilitar os testes, foi criado uma collection no postman com os endpoints e payloads (src/postman)
- Para usar, basta fazer o import pro postman (tanto collections quanto variáveis)
- Existem scripts do postman setando as variáveis automáticamente para os próximos requests/reuso.
- Com a primeira carga de dados das variáveis globais, é possível já fazer os testes de cadastro de usuário, login, criação de pautas, sessões, votação e contagem de votação
* É possível executar o folder "serviços" no runner do postman para fazer o fluxo acima:

- ![alt text](https://i.imgur.com/rIV3jDZ.png)
* Também é possível repassar as jornadas apenas usando as variáveis globais, conforme abaixo

- ![alt text](https://i.imgur.com/zhTbQL1.png)

### Observações Técnicas

- Qualidade de código
  - Alguns pontos não puderam ser revistos em razão do prazo, mas muita coisa poderia ser revisada para melhoria de organização
  - Os testes unitários não estão exaustivos. Fiz alguns testes apenas pra ilustrar proeficiência
- Integração
  - A api de integração "https://user-info.herokuapp.com/users/" não está funcionando. Em razão disso, usei a API "https://api.cpfcnpj.com.br" apenas para ilustrar a integração.
  - Em razão do tempo, não fiz uma abstração para criar os clients. No arquivo "UserValidationService.java" ele é instanciado dentro do método que faz seu uso. Isso foi uma decisão tomada para permitir a injeção do mock no teste unitário sem a necessidade de criar uma estrutura de injeção de dependência para o feignClient.
- Testes de Performance
  - Não tinha proeficiência em testes de performance, mas estudei e implementei um mini-framework para testes de performance em gatling, integrado ao projeto.
- Versionamento
  - Fiz alguns versionamentos simples no serviço de cadastros, apenas modificando a classe retornada dos serviços

### Licença
Apache License 2.0

### Autor
- Gabriel Marques
  - Linkedin: https://www.linkedin.com/in/gabriel-marques1989/
  - Email: marques.gabriel.1989@gmail.com