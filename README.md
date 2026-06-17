#  Sistema de Gerenciamento de Biblioteca

Sistema de gerenciamento de biblioteca desenvolvido em **Java** e **MySQL** como projeto acadêmico da disciplina de Banco de Dados.

O objetivo do projeto é permitir o gerenciamento de livros, usuários, empréstimos e multas através de uma interface gráfica intuitiva, aplicando conceitos de modelagem de banco de dados, programação orientada a objetos e arquitetura em camadas.

---

##  Funcionalidades

###  Gerenciamento de Usuários
- Cadastro de usuários
- Autenticação/Login
- Controle de perfis (Cliente e Funcionário)
- Atualização de informações cadastrais

###  Gerenciamento de Livros
- Cadastro de livros
- Consulta de livros disponíveis
- Atualização de informações
- Controle de disponibilidade

###  Gerenciamento de Empréstimos
- Registro de empréstimos
- Controle de devoluções
- Histórico de empréstimos

###  Controle de Multas
- Registro de multas
- Consulta de pendências
- Controle financeiro relacionado aos empréstimos

---

##  Arquitetura do Projeto

O sistema foi desenvolvido seguindo uma arquitetura em camadas para promover organização, manutenção e separação de responsabilidades.

```text
src/
├── controller/
├── dao/
├── model/
├── service/
└── view/
```

### Camadas

| Camada | Responsabilidade |
|----------|----------------|
| View | Interface gráfica do sistema |
| Controller | Controle das ações do usuário |
| Service | Regras de negócio |
| DAO | Comunicação com o banco de dados |
| Model | Entidades e objetos do sistema |

---

##  Tecnologias Utilizadas

- Java
- Java Swing
- MySQL
- JDBC
- Git
- GitHub

---

##  Banco de Dados

O banco de dados foi modelado para controlar:

- Usuários
- Funcionários
- Livros
- Empréstimos
- Multas

O script de criação do banco encontra-se no arquivo:

```text
SQLBibliotecaEnviar.sql
```

---

##  Como Executar

### 1. Clonar o repositório

```bash
git clone https://github.com/SEU-USUARIO/NOME-DO-REPOSITORIO.git
```

### 2. Criar o banco de dados

Execute o script:

```text
SQLBibliotecaEnviar.sql
```

utilizando o MySQL Workbench ou outra ferramenta de sua preferência.

### 3. Configurar a conexão

No arquivo:

```java
ConnectionFactory.java
```

altere as credenciais de acesso ao banco:

```java
private static final String URL = "SUA URL";
private static final String USER = "SEU USUARIO";
private static final String PASSWORD = "SUA SENHA";
```

### 4. Executar o projeto

Abra o projeto em sua IDE Java preferida e execute a aplicação.

---

##  Conceitos Aplicados

Durante o desenvolvimento foram aplicados conhecimentos de:

- Programação Orientada a Objetos (POO)
- Modelagem de Banco de Dados
- SQL
- JDBC
- CRUD
- Arquitetura em Camadas
- Interface Gráfica com Java Swing
- Controle de Empréstimos e Multas
- Boas práticas de organização de código

---

##  Contexto Acadêmico

Projeto desenvolvido para a disciplina de Banco de Dados do curso de Ciência da Computação da Universidade de Fortaleza (UNIFOR).

O projeto teve como objetivo integrar conceitos de modelagem de dados e desenvolvimento de software em uma aplicação completa de gerenciamento de biblioteca.

---

##  Autor

**Emanuel Alves Melo**

- GitHub: https://github.com/reidaordem
- LinkedIn: www.linkedin.com/in/emanuel-alves-melo-62762239b
