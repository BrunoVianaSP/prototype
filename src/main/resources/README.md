# Instruções geraris para o funcionamento do Projeto na máquina local

# Configuração do Projeto:
Spring Boot Version: 2.0.0
Java version: 8

# Como rodar o projeto:
Opção 1: Executar o projeto pelo "Play" da IDE.
Opção 2: Executar comando "mvn clean package -Dspring.profiles.active=prod -DskipTests" após estar dentro da pasta do projeto.

# Regras de Gitflow
1 - As branches master, release e develop nunca sofrerão alterações a não ser em merges.
2 - Tudo o que for desenvolvido deverá ter sua própria branch para que não polua o código das branches de administração (master, release e develop)
