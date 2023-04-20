# Sous Chef API
Esse repositório possui a API utilizada no projeto Sous Chef.

Para **instalar**, siga os passos descritos [aqui](#instalação).

Caso opte por entender **como funciona**, veja aqui a [documentação](#documentação) e [como utilizar](#utilizando-a-api).

## **Sobre o Sous Chef/API**
O Sous Chef é uma aplicação que ajuda pessoas a descobrirem receitas que podem ser preparadas com base nos ingredientes que já possui.

### Especificações da API
- **Linguagem:** Java 17
- **Banco de dados:** PostgreSQL (versão mais recente)
- **Framework:** Spring Boot 2.7.x

### Documentação
As rotas da aplicação estão descritas e bem definidas na workspace do [Postman][postman].

---

## **Instalação**
Para maior praticidade, o projeto possui implementação com o [Docker compose](https://docs.docker.com/compose/). Caso prefira uma instalação manual, os requisitos sobre a versão da JDK e do PostgreSQL estão nas [especificações](#especificações-da-api).

### Usando o Docker compose
Primeiro, certifique-se de que **as portas 8080 e 5432 estão disponíveis**.
Caso queira mudar as portas ou o nome dos containers, altere o arquivo `docker-compose.yml`.

Para montar o ambiente, basta utilizar o seguinte comando:

> docker-compose up -d

Feito isso, os containers **souschef-api** e **souschef-db** serão gerados contendo, respectivamente, a aplicação em si e o banco de dados.

---

## **Autenticação**
Quase todo o acesso na aplicação requer que o usuário esteja autenticado. Sendo assim, **obtenha um token válido** ou **utilize o usuário padrão de teste**.

As credenciais de teste são:
- **Email:** teste@teste.com
- **Senha:** teste123

### Obtendo um token válido
São duas requisições necessárias:
- **Primeira requisição:** O usuário informa seu email e senha. Caso correto, o sistema gera um código OTP para a próxima etapa.
- **Segunda requisição:** O usuário informa novamente seu email acompanhado do código gerado.

**Atenção**: Se você estiver utilizando o usuário de teste, basta utilizar o código OTP 000000.
Caso não esteja utilizando, o código será mostrado no terminal.

Ao receber um código **HTTP OK** na segunda etapa, será adicionado um token válido no cabeçalho **Authorization** da resposta.

Você pode encontrar a primeira requisição [aqui][first-step-auth] e a segunda [aqui][second-step-auth].

### Utilizando o token
Para utilizar o token, basta informá-lo como um **Bearer token** no cabeçalho **Authorization** da requisição.

Você pode encontrar um exemplo na [listagem de receitas][list-recipes].

---

## **Utilizando a API**
Após obter um token seguindo os passos na [autenticação](#autenticação), você estará pronto para utilizar as principais funcionalidades. O fluxo será:
- **Passo 1:** O usuário informa **quais itens possui**.
- **Passo 2:** O sistema informa **quais receitas podem ser feitas**.

### Passo 1: Informando itens
Para informar um item, primeiro é necessário que ele exista. Você pode [criar um novo][create-item] ou utilizar o usuário de teste que já possui os produtos abaixo:

- 4 unidades de **pães**
- 2 unidades de **queijo**
- 1 unidade de **presunto**

Você pode mudar as quantidades usando [essa rota][update-item], mas essas quantidades já são suficientes para o segundo passo.

### Passo 2: Buscando receitas
O sistema já possui uma receita cadastrada que utiliza os produtos do passo anterior. Caso deseje, você também pode [criar uma nova][create-recipe].

Com as quantidades padrões dos produtos, é possível fazer **1 misto quente**. Ao [buscar as receitas disponíveis][search-recipes], devem ser retornadas as receitas em um formato JSON como o abaixo:

>
    {
        "id": "828b87a4-c96b-4eb3-9a1c-76d8502ba62a",
        "name": "Misto quente",
        "ingredients": [
            {
                "id": "37cce9ce-fc84-447a-889d-2d32a782c616",
                "name": "Pão",
                "quantity": 2
            },
            {
                "id": "59e435b0-93da-4d7f-8e3f-aef6f7961aa4",
                "name": "Queijo",
                "quantity": 1
            },
            {
                "id": "ce44ee8d-f83e-47d5-a028-8ef710dbe573",
                "name": "Presunto",
                "quantity": 1
            }
        ]
    }

Feito isso, você já concluiu o fluxo principal da aplicação. Todas as outras rotas e detalhes sobre podem ser acessados na [documentação](#documentação).

[swagger]: http://localhost:8080/swagger
[postman]: https://www.postman.com/charapadev/workspace/sous-chef-api
[register-user]: https://www.postman.com/charapadev/workspace/sous-chef-api/request/9424241-c471aa69-7f4a-4ae0-b5e1-451c4dfce653
[first-step-auth]: https://www.postman.com/charapadev/workspace/sous-chef-api/request/9424241-1b589cea-4c82-4eca-a8c1-85c5dcbf0d78
[second-step-auth]: https://www.postman.com/charapadev/workspace/sous-chef-api/request/9424241-818ed6a4-ff44-4d07-991c-30fa55555dbc
[list-recipes]: https://www.postman.com/charapadev/workspace/sous-chef-api/request/9424241-82aa55d0-2d42-43d9-a206-93cf12e9398c
[create-recipe]: https://www.postman.com/charapadev/workspace/sous-chef-api/request/9424241-0dde94e9-c435-426c-bdf6-885ac86c381f
[create-item]: https://www.postman.com/charapadev/workspace/sous-chef-api/request/9424241-a7b71f24-9a01-4ddb-a185-43f592759159
[update-item]: https://www.postman.com/charapadev/workspace/sous-chef-api/request/9424241-58db107d-9f8f-461b-af0e-a294b94f4d07
[search-recipes]: https://www.postman.com/charapadev/workspace/sous-chef-api/request/9424241-3b458cf2-6f40-4e37-9bfa-a55150020cd9