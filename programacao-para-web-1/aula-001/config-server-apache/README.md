# Aula 001

### 1. **Arquitetura da Web**

A Web é baseada em três pilares principais:

- **HTML (HyperText Markup Language)**: É a linguagem usada para criar páginas web.
- **URL (Uniform Resource Locator)**: É o endereço usado para acessar páginas web.
- **HTTP (HyperText Transfer Protocol)**: É o protocolo de comunicação que permite a troca de informações entre o
  cliente (navegador) e o servidor.

### 2. **URL**

A URL é basicamente o "endereço" de um recurso na web. Ela tem este formato:

- `protocolo://servidor:porta/caminho/recurso?parâmetros#ancora`
    - **Protocolo**: Exemplo, `http` ou `https`.
    - **Servidor**: O endereço do site (exemplo: `www.site.com.br`).
    - **Porta**: O número da porta no servidor que está ouvindo as requisições (ex: 8080).
    - **Caminho**: A localização do recurso no servidor.
    - **Parâmetros**: Informações extras que podem ser passadas para o servidor (ex: `?codigo=2`).
    - **Âncora**: Parte específica da página (ex: `#conteudo`).

### 3. **Comunicação Cliente-Servidor**

A web segue o modelo **cliente-servidor**:

- **Cliente**: O navegador, que faz as requisições (ex: você acessando um site).
- **Servidor**: O computador que recebe essas requisições e responde (ex: servidor onde o site está hospedado).

### 4. **Protocolo TCP/IP**

Este é o conjunto de protocolos que permite a comunicação pela internet:

- **Camada de Aplicação (HTTP)**: Onde navegadores e servidores web trocam dados.
- **Camada de Transporte (TCP)**: Garante que os dados sejam enviados de forma segura.
- **Camada de Rede (IP)**: Responsável por endereçar e entregar os dados.
- **Camada de Enlace e Física**: Trata da conexão física entre os computadores.

### 5. **Protocolo HTTP**

O HTTP é o protocolo de comunicação da web. Ele permite que o navegador solicite recursos (como páginas HTML, imagens,
etc.) ao servidor.

- **Exemplo de funcionamento**: O navegador faz uma requisição HTTP (`GET / HTTP/1.1`), e o servidor responde com a
  página solicitada (`HTTP/1.1 200 OK`).
- Até a versão 1.1, as mensagens eram em **texto puro**, mas na versão 2.0, elas são em **formato binário** (não
  legíveis por humanos).

### 6. **Métodos HTTP**

Estes são alguns dos principais métodos de requisição:

- **GET**: Solicita dados.
- **POST**: Envia dados ao servidor.
- **PUT**: Atualiza dados no servidor.
- **DELETE**: Remove dados.
- **PATCH**: Faz alterações parciais em dados.

### 7. **Socket**

Um **socket** é a "porta" de comunicação entre o cliente e o servidor em um nível mais baixo, geralmente usando o
protocolo TCP/IP. Ele permite a criação de **servidores e clientes** que podem se comunicar diretamente.

### 8. **Exemplo de Servidor HTTP com Socket**

Neste exemplo, um código simples em Java cria um servidor que espera por conexões de clientes. Quando um cliente se
conecta, o servidor responde com uma página HTML contendo um simples "olá". É uma forma básica de ver como funciona a
comunicação entre cliente e servidor.

### 9. **CGI (Common Gateway Interface)**

É um método que permite criar páginas web **dinâmicas**, onde você pode utilizar várias linguagens de programação (C,
Python, PHP, etc.) para gerar conteúdo. O servidor web executa o programa e retorna o resultado para o navegador.

### 10. **GlassFish**

**GlassFish** é um servidor de aplicativos Java que suporta tecnologias como **Servlets** e **JSP (JavaServer Pages)**,
usadas para desenvolver aplicações web em Java.

- Para usar o GlassFish, você cria um projeto seguindo uma estrutura de diretórios específica (HTML, CSS, JS, etc.).
- É possível **fazer deploy** da aplicação, ou seja, enviar a aplicação para o servidor rodar.

___

Para entender como as camadas dos modelos **OSI** e **TCP/IP** funcionam na prática para um desenvolvedor, é importante
saber o que acontece em cada camada e quais serviços ou tecnologias você pode encontrar no seu dia a dia de programação.


### Camada de Aplicação (7ª no OSI / 4ª no TCP/IP)

- **O que faz?**: Essa é a camada mais alta, onde as aplicações rodam e interagem com a rede. É onde ficam os protocolos
  que você mais utiliza diretamente, como o **HTTP**, **FTP**, **SMTP**, **DNS** e outros serviços. Para
  desenvolvedores, isso significa APIs, servidores web, clientes de e-mail e servidores de arquivos.

- **O que roda aqui?**:
    - **HTTP/HTTPS**: Navegadores acessando páginas da web.
    - **SMTP/IMAP/POP3**: Envio e recebimento de e-mails.
    - **FTP**: Transferência de arquivos entre servidores.
    - **DNS**: Resolução de nomes de domínio (como transformar "google.com" no endereço IP real).

- **Exemplo prático**: Quando você faz uma requisição HTTP com JavaScript (fetch API), é na camada de aplicação que esse
  pedido começa. O protocolo HTTP lida com o pedido ao servidor e entrega os dados que você vai usar na sua aplicação.

### Camada de Transporte (4ª no OSI / 3ª no TCP/IP)

- **O que faz?**: Essa camada cuida da entrega dos dados de forma confiável ou não, dependendo do protocolo. No caso do
  **TCP**, garante que os pacotes cheguem na ordem correta, sem erros, e controla o fluxo de dados entre o cliente e o
  servidor. Já com o **UDP**, o foco é na velocidade, sem verificar se os pacotes chegaram.

- **O que roda aqui?**:
    - **TCP**: Protocolos como HTTP, SMTP, e FTP usam TCP porque precisam de uma comunicação confiável.
    - **UDP**: Protocolos como DNS, VoIP (chamadas de voz) e streaming de vídeo utilizam UDP, que é mais rápido, mas
      menos confiável.

- **Exemplo prático**: Ao criar um servidor web, o **TCP** é usado para garantir que os dados da sua página web cheguem
  corretamente ao navegador do usuário. Ao usar um serviço de vídeo ao vivo ou jogos online, o **UDP** é a escolha, pois
  a prioridade é a velocidade.

### Camada de Rede (3ª no OSI / 2ª no TCP/IP)

- **O que faz?**: A camada de rede é responsável por identificar e rotear pacotes entre redes diferentes. O protocolo
  principal aqui é o **IP** (Internet Protocol), que permite que os dados sejam enviados para o destino certo através da
  rede.

- **O que roda aqui?**:
    - **IP**: O protocolo que cuida do endereçamento e roteamento dos pacotes. Endereços **IPv4** e **IPv6** são
      utilizados para identificar dispositivos na rede.
    - **ICMP**: Utilizado por comandos como **ping**, que verifica se um dispositivo está acessível na rede.

- **Exemplo prático**: Quando você faz uma requisição HTTP, o endereço IP do servidor é necessário para que os pacotes
  sejam enviados corretamente. Se houver problemas de conexão, comandos como `ping` ou `tracert` ajudam a descobrir se o
  destino está acessível ou onde o problema ocorre.

### Camada de Enlace (2ª no OSI / 1ª no TCP/IP)

- **O que faz?**: A camada de enlace é responsável por garantir que os dados sejam entregues de forma correta no
  segmento da rede local (por exemplo, dentro de uma rede doméstica ou de um escritório). Aqui estão os protocolos que
  lidam com os **endereços MAC** e a entrega de pacotes no nível da rede local.

- **O que roda aqui?**:
    - **Ethernet**: O protocolo mais comum em redes cabeadas (LAN).
    - **Wi-Fi**: Para redes sem fio, o protocolo é baseado no padrão IEEE 802.11.
    - **ARP**: Protocolo que faz a tradução entre endereços IP e endereços MAC, para que o tráfego local possa ser
      entregue corretamente.

- **Exemplo prático**: Quando você está conectado ao Wi-Fi ou via cabo Ethernet, essa camada cuida de transferir os
  pacotes na sua rede local. Por exemplo, seu roteador mapeia o endereço IP para um endereço MAC (nível físico) para
  enviar os dados.

### Camada Física (1ª no OSI, parte da Camada de Enlace no TCP/IP)

- **O que faz?**: A camada física define como os dados são transmitidos pelo meio físico, como cabos, ondas de rádio, ou
  luz (fibra óptica).

- **O que roda aqui?**:
    - **Cabos Ethernet**, **ondas Wi-Fi**, **fibra óptica**: São os meios pelos quais os bits são efetivamente
      transmitidos.

- **Exemplo prático**: Quando você conecta um cabo Ethernet ao seu computador ou se conecta ao Wi-Fi, a camada física
  cuida da transmissão dos dados pelo ar ou pelo cabo até o roteador ou switch.

---

### Resumindo para um desenvolvedor:

- **Camada de Aplicação**: É onde você mais interage diretamente. Quando faz requisições HTTP, usa FTP, DNS ou conecta a
  um banco de dados via protocolo.
- **Camada de Transporte**: TCP e UDP garantem que seus dados cheguem corretamente ou rapidamente. TCP para comunicação
  confiável (ex.: servidores web) e UDP para comunicação rápida (ex.: jogos e streaming).
- **Camada de Rede**: IP lida com o roteamento dos pacotes através da internet. Você verá essa camada quando usar ping,
  traceroute ou lidar com problemas de rede.
- **Camada de Enlace**: Essa é mais invisível no seu dia a dia, mas está por trás do Wi-Fi, Ethernet, e do mapeamento de
  endereços MAC/IP.
- **Camada Física**: Responsável pelo meio de transmissão, seja cabo, Wi-Fi ou fibra.

