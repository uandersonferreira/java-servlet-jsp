<%@ page import = "java.util.Map" %><%@ page import = "java.util.HashMap" %>
<%@ page contentType = "text/html; charset=UTF-8" pageEncoding = "UTF-8" %>

<%
  // Recupera os parâmetros da requisição
  request.setCharacterEncoding("UTF-8");
  String mensagem = request.getParameter("mensagem");
  String nomeUsuarioDeslogado = request.getParameter("name");
  
  // Define um mapa de mensagens com seus textos e tipos (classe CSS)
  Map<String, String[]> mensagens = new HashMap<>();
  
  // Mensagens de sucesso
  mensagens.put("success-create", new String[]{"Operação concluída com sucesso!", "success-message"});
  mensagens.put("success-update-user", new String[]{"Usuário atualizado com sucesso!", "success-message"});
  mensagens.put("success-delete-user", new String[]{"Usuário deletado com sucesso!", "success-message"});
  mensagens.put("success-delete-telefone", new String[]{"Telefone deletado com sucesso!", "success-message"});
  mensagens.put("success-logout", new String[]{"Usuário deslogado com sucesso. Até logo, " + nomeUsuarioDeslogado, "success-message"});
  
  // Mensagens de erro
  mensagens.put("erro-login-session", new String[]{"Erro ao realizar Login. Já existe alguém logado na sessão!", "error-message"});
  mensagens.put("error-login-authentication", new String[]{"Erro ao realizar Login. Login ou senha incorretos!", "error-message"});
  mensagens.put("erro-create", new String[]{"Erro ao realizar a operação. Tente novamente!", "error-message"});
  mensagens.put("erro-update-user", new String[]{"Erro ao atualizar o usuário. Usuário não encontrado!", "error-message"});
  mensagens.put("erro-delete-user", new String[]{"Erro ao excluir o Usuário. Usuário não encontrado!", "error-message"});
  mensagens.put("erro-delete-telefone", new String[]{"Erro ao excluir o Telefone. Telefone não encontrado!", "error-message"});
  mensagens.put("erro-session-invalidate", new String[]{"[Session Inválida] Erro ao acessar funcionalidade, faça login novamente!", "error-message"});
  
  // Mensagens de alerta
  mensagens.put("alerta-campos", new String[]{"Por favor, preencha todos os campos obrigatórios!", "alert-message"});
  mensagens.put("alerta-nao-autenticado", new String[]{"Por favor, realize login: usuário não autenticado!", "alert-message"});
  mensagens.put("alerta-permissao", new String[]{"Você não possui permissão para executar esta ação!", "alert-message"});
  
  // Mensagens de informações gerais
  mensagens.put("info-carregando", new String[]{"Carregando... Por favor, aguarde.", "info-message"});
  mensagens.put("info-vazio", new String[]{"Nenhum item encontrado.", "info-message"});
  
  
  // Define o conteúdo da mensagem a ser exibida
  String mensagemTexto = null;
  String mensagemClasse = null;
  
  if (mensagem != null && mensagens.containsKey(mensagem)) {
    String[] mensagemInfo = mensagens.get(mensagem);
    mensagemTexto = mensagemInfo[0];  // Primeiro elemento: texto da mensagem
    mensagemClasse = mensagemInfo[1]; // Segundo elemento: classe CSS
  }
%>

<%-- Renderiza a mensagem se ela existir --%>
<% if (mensagemTexto != null && mensagemClasse != null) { %>
    <div class = "alert-msg <%= mensagemClasse %>">
        <%= mensagemTexto %>
    </div>
<% } %>




