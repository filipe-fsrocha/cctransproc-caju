![Build Status](https://github.com/filipe-fsrocha/cctransproc-caju/actions/workflows/ci.yml/badge.svg?event=push)

## CCTtransPRoc - Credit Card Transaction Processor

O **CCTtransPRoc** é um microserviço especializado no processamento de transações de cartões de crédito. Projetado para gerenciar e validar transações financeiras, este serviço lida com a autorização e execução de pagamentos com cartões de crédito

## Regras
- **Regras de como o valor deve ser debitado:**
  - Se o `mcc` for `5411` ou `5412`, deve-se utilizar o saldo de `FOOD`.
  - Se o `mcc` for `5811` ou `5812`, deve-se utilizar o saldo de `MEAL`.
  - Para quaisaquer outros valores do `mcc`, deve-se utilizar o saldo de `CASH`.


- **Autorizador simples:**
  - Recebe a transação
  - Usa apenas o **MCC** para mapear a transação para uma categoria de benefícios.
  - Aprova ou rejeita a transação.
  - Caso a transação seja aprovada, o saldo da categoria mapeada deverá ser debitado.


- **Autorizador com fallback:**
  - Se o **MCC** não puder ser mapeado para uma categoria de benefícios ou se o saldo da categoria fornecida não for suficiente para pagar a transação inteira, verifica o saldo de `CASH` e, se for suficiente, debita esse saldo.
  - Se o **MCC** for incorreto, O nome do comerciante tem maior procedência sobre os **MCCs**

## Pontos levantados durante o desenvolvimento.
- É crucial que os dados sensíveis sejam criptografados, mas por se tratar de um testes, regras de criptografia não foram aplicadas.
- O histórico de transações foi mantido apenas para as transações bem-sucedidas; no entanto, é interessante registrar as tentativas rejeitadas sem debitar o saldo das contas.
- Para validar a transação, foi utilizado o padrão **Chain of Responsibility**, onde as validações são separadas em uma cadeia de processo, cada uma realizando apenas o que lhe é designado.
- Para resolver o problema de concorrência, podemos adotar duas abordagens:
  - Podemos realizar uma abordagem de lock diretamente no banco de dados.
  - Podemos utilizar o `Redis` para fazer o `lock` de forma centralizada, sem sobrecarregar o banco de dados.

> **Nota:** Embora o teste seja relativamente simples, projetei a estrutura de forma a garantir que ela possa ser escalada conforme o crescimento, mantendo a separação das responsabilidades por domínio. 

## Dependências
- Java: `openjdk-21`
- Spring Boot: `3.3.1`
- flyway
- flyway-database-postgresql
- lombok
- postgres
- h2
- modelmapper

## Cobertura de testes
Foi gerado um relatório de testes com o JaCoCo para avaliar a cobertura de código. A cobertura de testes na rotina principal, que é a transação, é de 100%.

## Execução do serviço
### Variáveis de ambiente
| Variável de ambiente | Definição                                       | Obrigatório | Default |
|----------------------|-------------------------------------------------|------------|---------|
| DB_URL               | Url de conexão com banco de dados               | Não        | jdbc:postgresql://localhost:5432/postgres |
| DB_USER | Usuário do banco de dados                       | Não        | postgres |
| DB_PASSWORD | Senha do usuário de conexão do  banco de dados | Não        | postgres |


- **Linux**
  - Ter o `docker` instalado.
  - Executar o seguinte comando, na pasta root do serviço
    ```bash
    sudo chmod +x build.sh
    sudo ./build.sh
    ```
    **Nota:** Aguarde os serviços inciarem para executar os testes.
- **Windows**
  - Build
    ```bash
    mvn clean install -DskipTests=true
    ```
  - Iniciar o banco de dados
    ```bash
    docker compose up db -d
    ```
  - Executar o aplicativo .jar
    ```bash
    mvn spring-boot:run
    ```

## API (Documentação)
A API principal é a `/transaction`, mas desenvolvi algumas APIs adicionais para complementar a transação. Durante a migração do banco de dados, alguns dados são pré-carregados na base.

**Host:** http://localhost:8080
- **[POST]/transaction**
  - request:
    - payload:
        ```json
        {
           "account": {
              "cardNumber": "5513121264313829",
              "password": "1234"
           },
           "totalAmount": 10.0,
           "mcc": 5411,
           "type": "POS",
           "merchant": "UBER EATS                  SAO PAULO BR"
        }
        ```
  - response:
    - 200 OK:
      - `{"code": "00"}`: Se a transação é **aprovada**.
      - `{"code": "51"}`: Se a transação é **rejeitada**, porque não tem saldo suficiente.
      - `{"code": "07"}`: Se acontecer qualquer outro problema que impeça a transação de ser processada.


- **[GET]/card/{cardNumber}**
  - path: 
    - cardNumber: `5513121264313829`
  - response:
       - 200 OK:
         ```json
         {
           "cardNumber": "5513 1212 6431 3829",
           "expirationDate": "2024-08",
           "cvc": "610",
           "accounts": [
               {
                   "id": "bb24c923-bc0e-4caa-86a4-c33f464371f9",
                   "accountType": "FOOD",
                   "totalAmount": 100.00
               },
               {
                   "id": "bb24c923-bc0e-4caa-86a4-c33f464371f8",
                   "accountType": "MEAL",
                   "totalAmount": 100.00
               },
               {
                   "id": "bb24c923-bc0e-4caa-86a4-c33f464371f7",
                   "accountType": "CASH",
                   "totalAmount": 200.00
               }
           ]
         }
         ```
       - 404 NOT_FOUND:
         ```json
         {
           "reason": "NOT_FOUND",
           "message": "Card 5513 1212 6431 382 not found."
         }
         ```
        
 
- **[GET]/merchant/{id}**
  - path:
    - id: `bb24c923-bc0e-4caa-86a4-c33f464371f8`
  - response:
    - 200 OK:
      ```json
      {
        "id": "bb24c923-bc0e-4caa-86a4-c33f464371f8",
        "name": "UBER EATS                  SAO PAULO BR",
        "createdAt": "2024-08-13T00:05:51.081328",
        "mcc": {
            "id": "c19151fa-560d-4c72-a1f2-288156a160e1",
            "mcc": 5812,
            "description": "RESTAURANTES",
            "active": true
        }
      }
      ```
    - 404 NOT_FOUND:
      ```json
      {
        "reason": "NOT_FOUND",
        "message": "Merchant bb24c923-bc0e-4caa-86a4-0c33f464371f not found."
      }
      ```
      
- **[GET]/mcc/{mcc}**
  - path:
    - mcc: `5411`
  - response:
    - 200 OK:
       ```json
      {
        "id": "f5ddc13b-3fc6-40ca-909e-354d33089a29",
        "mcc": 5411,
        "description": "MERCEARIAS/SUPERMERCADOS (GROCERY STORES/SUPERM.)",
        "active": true
      }
      ```
    - 404 NOT_FOUND:
       ```json
      {
          "reason": "NOT_FOUND",
          "message": "MCC 5410 not found."
      }
      ```
