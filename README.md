# Quero Ler

API desenvolvida para gerenciamento de livros, metas de leitura, avaliações e interações entre leitores.

O projeto foi inspirado em plataformas como o Skoob, permitindo que usuários organizem leituras, participem de clubes do livro e compartilhem experiências literárias.

------------

# Objetivo

Criar uma aplicação onde os usuários possam:

* Adicionar livros que desejam ler;
* Criar metas de leitura;
* Avaliar livros;
* Escrever comentários sobre suas leituras;
* Definir privacidade das informações:
  * Público
  * Restrito para amigos
  * Privado
* Participar de clubes de leitura;
* Organizar encontros presenciais ou online.

Além disso, a aplicação possui perfis administrativos responsáveis pelo gerenciamento da plataforma:

## Administrador

Responsável por:

* Incluir documentos;
* Excluir usuários;
* Gerenciar informações da aplicação.

## Moderador

Responsável por:

* Excluir comentários que estejam fora das diretrizes da plataforma.

------------

# Regras gerais
* Os livros cadastrados ficam disponíveis para todos os usuários;
* O usuário pode definir o nível de privacidade das informações;
* Os perfis administrador e moderador já vêm pré-cadastrados no sistema;
* O documento da aplicação está organizado por épicos e histórias de usuário (HU).

------------

# Tecnologias utilizadas
* Java (JDK 21)
* Maven
* PostgreSQL
* REST Assured
* JUnit 5
* Swagger
------------
# Funcionalidades
## Usuários
* Cadastro de usuários
* Login
* Atualização de perfil
* Upload de foto
* Controle de privacidade
## Livros
* Cadastro de livros
* Busca de livros
* Organização de leituras
* Lista de desejos
## Metas de leitura
* Definição de metas
* Acompanhamento de progresso
* Controle de livros lidos
## Avaliações
* Avaliação de livros
* Comentários sobre leituras
* Controle de visibilidade
## Administração
* Exclusão de usuários
* Moderação de comentários
* Gerenciamento de aplicação
------------
# Como executar o projeto
## Pré-requisitos
* Java 21
* Maven
* PostgreSQL

## Clonar repositório
### Backend
git clone `https://github.com/ProjetoQueroLer/queroler-backend.git`
### Teste API
git clone `https://github.com/ProjetoQueroLer/queroler-api-testes.git`

------------
# Importante

O repositório de testes da API depende do ambiente configurado no repositório backend.

Inicialmente, foi identificado que o projeto de testes não conseguia conectar diretamente no banco de dados sozinho. Por isso, é necessário executar primeiro o repositório backend com Docker para disponibilizar o banco PostgreSQL e demais configurações da aplicação.

Após o backend estar em execução, o repositório de testes API conseguirá acessar corretamente o banco de dados e executar os testes.

------------

# Executar Backend
### Entrar na pasta do backend
`cd queroler-backend`
### Subir containers Docker
`Docker compose up -d --build`

# Executar Testes API
### Entrar na pasta do testes
`cd queroler-api-testes`
### Executar testes
`.\mvnw.cmd test` ou `mvn test`

------------

# Endpoints
### Usuário
GET /usuarios  
PUT /usuarios  
POST /usuarios  
DELETE /usuarios  
PUT /usuarios/dados-adicionais  
PUT /usuarios/alterar-senha  
PUT /usuarios/administrador **(manutenção)**    
POST /usuarios/livro  
GET /usuarios/{id}/comentarios **(manutenção)**  
GET /usuarios/fotos **(manutenção)**
### Login
POST /logins  
### Livro
PUT /livros/{id}/usuario  
GET /livros/{id}/capa  
PUT /livros/{id}/capa  
GET /livros  
POST /livros  
GET /livros/{id}/comentarios **(manutenção)**  
GET /livros/tela_de_leitura **(manutenção)**  
GET /livros/populares **(manutenção)**  
GET /livros/detalhados **(manutenção)**  
GET /livros/buscar/{isbn}
## EM BREVE
### Notificação
### Diario de leitura
### Documento
### Acompanhamento de leitura

------------

# Testes

Os testes automatizados foram desenvolvidos utilizando:

* REST Assured
* JUnit 5

------------

## Executar testes:

`mvn test`

------------

# Documentação API

Swagger disponível em:

http://localhost:8080/swagger-ui/index.html

------------

# Autor
| [<img src="https://avatars.githubusercontent.com/u/122066021?v=4" width=115><br><sub>Jeferson Lopes Eugenio</sub>](https://github.com/JefersonEuenio) |
| :---: |