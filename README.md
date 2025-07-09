
#  MCP Spring Boot + React + Ollama

Application IA Web utilisant **Spring Boot (client/serveur)**, **MCP**, **Ollama (Qwen3)**, et une **interface React** simple pour interroger un agent intelligent avec des outils backend exposés.

---

##  Table des matières

1. [Présentation](#présentation)
2. [Technologies](#technologies)
3. [Architecture du projet](#architecture-du-projet)
4. [Configuration & lancement](#configuration--lancement)
5. [Fonctionnalités](#fonctionnalités)
6. [Extraits de code & explication](#extraits-de-code--explication)
7. [Captures d’écran](#captures-décran)

---

##  Présentation

Ce projet démontre l’intégration d’un agent IA local (via Ollama) dans une application client/serveur Spring Boot. L’agent peut interroger dynamiquement des outils métier (définis côté serveur) grâce au protocole **MCP (Model Context Protocol)**. L’interface web React permet à un utilisateur de poser des questions et recevoir des réponses enrichies.

---

##  Technologies

### Backend (Client + Serveur)

* Spring Boot
* Spring AI (MCP, Ollama)
* Ollama (modèle **Qwen3** local)
* Java 17+
* Maven

### Frontend

* React.js
* Axios
* JavaScript

---

##  Architecture du projet

```
 mcp-spring-python
├──  mcp-client
│   ├── AIAgent.java
│   ├── AIRestController.java
│   ├── application.properties
├──  mcp-server
│   ├── StockTools.java
│   ├── McpServerApplication.java
│   ├── application.properties
├──  python-mcp-serverr
│   ├── server.py
├──  frontend-chat
│   ├── App.js (React)
│   ├── package.json
└── mcp-servers.json
```

---

##  Configuration & Lancement

### 1. Démarrer Ollama avec un modèle compatible tools

```bash
ollama run qwen3
```


### 2. Lancer le serveur Spring Boot (tools)

```bash
cd mcp-server
./mvnw spring-boot:run
```

### 3. Lancer le client Spring Boot

```bash
cd mcp-client
./mvnw spring-boot:run
```

### 4. Démarrer le frontend React

```bash
cd frontend
npm install
npm start
```

---

##  Fonctionnalités

*  Exposition de **tools dynamiques** (via annotations `@Tool`)
*  Agent IA intelligent capable d'appeler les tools automatiquement
*  Historique de conversation mémoire (20 messages)
*  Interface web simple avec React
*  Intégration du protocole **MCP** avec `spring-ai`

---

##  Extraits de code & explication

###  Tool côté serveur

```java
@Tool(description = "Returns a company by its name")
public Company getCompanyByName(String name) {
    return companies.stream()
        .filter(company -> company.name().equalsIgnoreCase(name))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Not found"));
}
```

> Permet à l’IA d’interroger dynamiquement des données métier (ex: entreprises, stock…).

---

###  Agent IA côté client

```java
public AIAgent(ChatClient.Builder chatClient, ToolCallbackProvider toolCallbackProvider) {
    this.chatClient = chatClient
        .defaultToolCallbacks(toolCallbackProvider)
        .defaultSystem("Utilise les outils. Si tu ne sais pas, dis 'Je ne sais pas'")
        .build();
}
```

> L’agent est configuré avec un système prompt clair + accès aux tools.

---

###  Frontend React

```jsx
const res = await axios.get('http://localhost:8066/chat', { params: { query } });
setResponse(res.data);
```

> Une simple requête GET permet de communiquer avec l’IA côté serveur.

---

##  Captures d’écran


* Interface React avec champ de question
  - Saisie de requête utilisateur
  L'utilisateur saisit une question dans l'interface (ex : "liste des entreprises") et l'envoie au backend.

  ![image](https://github.com/user-attachments/assets/cd20eeec-6453-41a1-9de9-c1ef5a9a7d10)
  
  ![image](https://github.com/user-attachments/assets/7f3ff7c5-ccfa-4eed-9eb6-dc1b6275ddc7)

* Test via Postman (API REST)
  - Requête GET : /chat?query=...
  L’API /chat est testée avec Postman. On envoie une requête GET avec un paramètre query.
  ![image](https://github.com/user-attachments/assets/8743ae80-e4cc-419b-812f-2eed2df4af2a)
  ![image](https://github.com/user-attachments/assets/878549c2-e8b4-41e0-b691-56fdfefac643)

  - Réponse de l'agent (appel des tools)
  La réponse provient d’un appel interne de l’agent au tool getCompanyByName côté serveur, via MCP.
  ![image](https://github.com/user-attachments/assets/7ecea436-22fd-4a94-b4bd-6128b27ee487)

* Réponse IA utilisant les tools (ex: `getAllCompany`)
  ![image](https://github.com/user-attachments/assets/7fd87151-5e4f-406e-92e7-9c3f3c88ab1d)

* Console Spring Boot avec `Tool registered`
  - Démarrage des tools MCP
  Lors du lancement du serveur (mcp-server), les tools sont automatiquement détectés et enregistrés (@Tool), grâce à MethodToolCallbackProvider.

  ![image](https://github.com/user-attachments/assets/570f91c6-3bc7-4b34-8298-8bac0c0e114b)
  ![image](https://github.com/user-attachments/assets/1128c85f-eba1-4eef-948f-aa0f43d910a3)

* Swagger 
  ![image](https://github.com/user-attachments/assets/8230594a-b205-4c87-b9c6-81a52e1453b4)

---

##  Exemple de requêtes

*  `Quelle est l'activité de Maroc Telecom ?`
*  `Donne-moi la liste des entreprises`
*  `Quelle est la valeur boursière de Tesla ?`

---

