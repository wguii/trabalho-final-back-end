# **Backend - Country Guesser**

Este é o backend para o protótipo do "Country Guesser", um jogo onde o a dinâmica consiste em adivinhar países com base em dicas fornecidas pela aplicação. Este protótipo foi desenvolvido para uma avaliação da disclipina de Backend da UNISATC.


## **Tecnologias Utilizadas**

- **Java 17**
- **Spring Boot 3.4.5**

## **Pré-requisitos**
- JDK 17.

- Maven para dependências.

**Passos para executar**:

Clone este repositório:

https://github.com/wguii/country-guesser-backend.git

Rode na sua IDE de preferência utilizando Maven.

O serviço estará rodando em http://localhost:8000.

## **Endpoints**

### **1. `/jogo/gerar`** (GET)

**Descrição**: Este endpoint gera um país aleatório e retorna as informações sobre ele, incluindo nome, região, capital, moeda e idiomas falados.

**Resposta (Exemplo)**:
{
"nome": "Botswana",
"regiao": "Africa",
"capital": "Gaborone",
"moeda": "Botswana pula",
"idiomas": ["English", "Tswana"]
}

### **2. `/jogo/verificar`** (POST)

**Descrição**:  
Este endpoint recebe a tentativa do usuário de adivinhar o país e retorna uma dica caso o usuário erre. Se o usuário acertar, será retornada uma mensagem de sucesso. Caso contrário, o sistema fornece uma dica, que pode ser sobre a **capital**, **moeda** ou **região** do país.

**Parâmetros**:

- **resposta** (query parameter): Nome do país que o usuário tenta adivinhar.
- **usuario** (queryn parameter): Usuário que está verificando

**Formato da requisição**:

- **Método**: `POST`
- **URL**: `/jogo/verificar?resposta={nome_do_pais}&usuarioId={id_do_usuario}`

Exemplo de requisição para tentar adivinhar "Botswana":

POST http://localhost:8000/jogo/verificar?resposta=Botswana&usuarioId=1

### **3. `/usuario`** (POST)

**Descrição**:
Cria um usuário no sistema

**Formato da requisição**:

- **Método**: `POST`
- **URL**: `/usuario`

### **4. `/usuario`** (GET)

**Descrição**:
Lista os usuários no sistema

**Formato da requisição**:

- **Método**: `GET`
- **URL**: `/usuario`


### **5. `/usuario/{id}`** (GET)

**Descrição**:
Lista o usuário no sistema pelo ID

**Formato da requisição**:

- **Método**: `GET`
- **URL**: `/usuario/{id}`

### **6. `/tentativas`**

Tentativas possui todos os métodos CRUD.