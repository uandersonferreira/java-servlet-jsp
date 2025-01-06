A estratégia geral para construir filtros XML em Java segue este padrão:

1. **Estrutura Base**
```java
Element root = xmlDocument.createElement("livraria");  // Novo documento para resultado
NodeList elementos = xmlDocument.getElementsByTagName("elemento_alvo");  // Busca elementos
// Itera, filtra e adiciona ao root
return convertXmlToString(root);  // Converte resultado para string
```

2. **Padrões de Filtro**:

- **Filtro por Atributo** (categoria, idioma, cover):
```java
Element elemento = (Element) elementos.item(i);
if (elemento.getAttribute("atributo").equals(valorBuscado)) {
    root.appendChild(elemento.cloneNode(true));
}
```

- **Filtro por Conteúdo** (título, autor):
```java
if (elemento.getTextContent().contains(valorBuscado)) {
    root.appendChild(elemento.getParentNode().cloneNode(true));
}
```

- **Filtro por Valor Numérico** (preço, ano):
```java
double valor = Double.parseDouble(elemento.getTextContent());
if (valor >= minimo && valor <= maximo) {
    root.appendChild(elemento.getParentNode().cloneNode(true));
}
```

3. **Pontos Importantes**:
- Use `getParentNode()` quando precisar pegar o livro inteiro
- Use `cloneNode(true)` para copiar elementos com subárvore
- Converta strings para números quando necessário
- Crie um novo elemento root para armazenar resultados

Exemplo prático de como pensar ao criar um novo filtro:
1. Identifique o elemento alvo (livro, título, autor, etc.)
2. Determine se precisa filtrar por atributo ou conteúdo
3. Escolha o padrão de filtro apropriado
4. Adapte a lógica de comparação conforme necessário