Star Wars Planets API
=======================

## Resumo

Este API foi desenvolvida para atender aos requisitos da [prova técnica](https://monosnap.com/file/BaJ2iVTF1tepCXiIqv4RQ25GCTlIqp). [Parte 2](https://monosnap.com/file/yutmF3SNVo0MIIeBGb6m5ABry9dB4s).

## Principais ferramentas utilizadas no desenvolvimento

* Spring Boot
* Spring Data JPA (para persistência no banco de dados)
* Apache Http Client (para criar requisições HTTP para a Swapi API)
* MySQL

## Instruções para executar a API

Esta API foi disponibilizada para testes no endereço: http://ec2-18-222-158-1.us-east-2.compute.amazonaws.com:8080/swagger-ui.html

Esse endereço corresponde ao Swagger da API, onde se encontra um documento técnico ilustrando quais são os endpoints disponíveis e seus formatos de request e response.

Foi disponibilizado também o [arquivo JAR](https://github.com/luislucana/starwars-api/blob/master/target/starwars-api.jar) executável desta API, caso deseje executa-lo localmente.

Nota: É necessário que o JRE 1.8 esteja instalado.

* Em caso de roda-lo manualmente, executar o comando:

   > java -jar starwars-api.jar

Nota (2): É necessário que o server do MySQL esteja sendo executado para que a aplicação possa inicializar.

#### Ilustração do swagger: https://monosnap.com/file/lm2piASkQU1NKh1d4IYmeuAvUkwxrq