Essa aplicação possui cinco pontos principais: uma página `index.html` para login e quatro endpoints servidos por
`Servlets` no servidor.

### 1. `index.html` (Página de Login)

- **URL:** `/index.html`
- **Descrição:** Esta página HTML serve como a interface inicial onde o usuário insere suas credenciais (login e senha).
- **Funcionamento:** O formulário de login envia os dados para o endpoint do `LoginServlet`, que irá autenticar o
  usuário.
- **Retorno:** Um formulário HTML com campos para login e senha.

### 2. `LoginServlet`

- **URL:** `/login`
- **Descrição:** Recebe as credenciais enviadas pelo formulário de `index.html`, valida o login e cria uma sessão se as
  credenciais estiverem corretas.
- **Funcionamento:** Verifica as credenciais do usuário (há três usuários codificados diretamente para fins de exemplo).
  Se o login for bem-sucedido, uma sessão é criada, permitindo o acesso à página de promoções.
- **Retorno:**
    - **Sucesso no login:** Redireciona o usuário para uma página de boas-vindas.
    - **Falha no login:** Exibe uma mensagem de erro, permitindo ao usuário tentar novamente.

### 3. `ComputadorServlet`

- **URL:** `/computador`
- **Descrição:** Exibe uma página com informações sobre um computador (nome, preço e descrição).
- **Funcionamento:** O `ComputadorServlet` verifica se o usuário está logado:
    - **Usuário não logado:** Define um `cookie` para registrar que o usuário visitou a página "Computador".
    - **Usuário logado:** Exibe diretamente as informações do produto.
- **Retorno:** HTML com informações sobre o computador. Se o usuário não estiver logado, o `cookie` de visita é
  configurado para indicar que a página foi visitada.

### 4. `PerfumeServlet`

- **URL:** `/perfume`
- **Descrição:** Exibe uma página com informações sobre um perfume (nome, preço e descrição).
- **Funcionamento:** Semelhante ao `ComputadorServlet`, este servlet também verifica se o usuário está logado:
    - **Usuário não logado:** Define um `cookie` para registrar que o usuário visitou a página "Perfume".
    - **Usuário logado:** Exibe diretamente as informações do produto.
- **Retorno:** HTML com informações sobre o perfume. Se o usuário não estiver logado, um `cookie` de visita é
  configurado.

### 5. `PromocaoServlet`

- **URL:** `/promocao`
- **Descrição:** Exibe a página de promoções, aplicando um desconto ao último produto visitado (desde que a visita tenha
  sido registrada em um `cookie`).
- **Funcionamento:** O servlet verifica se o usuário está logado (sessão ativa):
    - **Usuário logado:** Recupera o nome do último produto visitado a partir do `cookie`, aplica um desconto de 10% no
      preço original e exibe a página com o valor promocional.
    - **Usuário não logado:** Redireciona o usuário para a página de login.
- **Retorno:**
    - **Se logado:** Exibe o produto com o desconto aplicado.
    - **Se não logado:** Redireciona para `index.html`.

### Resumo dos Endpoints

| Endpoint            | URL           | Ação                                                                                                                                                 |
|---------------------|---------------|------------------------------------------------------------------------------------------------------------------------------------------------------|
| `index.html`        | `/index.html` | Formulário de login para o usuário.                                                                                                                  |
| `LoginServlet`      | `/login`      | Verifica login e senha, cria sessão para o usuário logado ou exibe erro se falhar.                                                                   |
| `ComputadorServlet` | `/computador` | Exibe detalhes de um computador e define um `cookie` de visita se o usuário não estiver logado.                                                      |
| `PerfumeServlet`    | `/perfume`    | Exibe detalhes de um perfume e define um `cookie` de visita se o usuário não estiver logado.                                                         |
| `PromocaoServlet`   | `/promocao`   | Exibe uma promoção de 10% no último produto visitado (com base no `cookie`), caso o usuário esteja logado. Caso contrário, redireciona para o login. 




# Escreva um sistema que tenha 4 servlets e 1 página html: Login, Computador, Perfume e Promoção e index.html
# A página "index.html" deve ter um formulário que solicite o login e a senha do usuário e envie os dados para o servlet Login
# No servlet Login crie a sessão do usuário se o login e senha estiverem corretos (cadastre três usuários diferentes de forma fixa no código);
# Na página "Computador" mostre um computador com seu respectivo preço e descrição; Adicione um cookie quando o usuário visitar esta página sem estar logado;
# Na página "Perfume" mostre um perfume com seu respectivo preço e descrição; Adicione um cookie quando o usuário visitar esta página sem estar logado;
# O usuário somente poderá ver a página "Promoção" se ele estiver logado; Mostre nesta página um desconto de 10% no produto que o usuário visitou antes de estar logado.