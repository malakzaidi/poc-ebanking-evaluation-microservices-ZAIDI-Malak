# POC eBanking Microservices - Architecture Distribuée et IA Générative

## 1. Description du Projet

Ce projet est une démonstration technique (Proof of Concept) d'une plateforme bancaire moderne. L'objectif est de mettre en œuvre une architecture microservices complète, allant de la gestion transactionnelle classique à l'intégration d'un assistant intelligent basé sur le pattern RAG (Retrieval Augmented Generation).

L'application permet aux clients de gérer leurs bénéficiaires, d'effectuer des virements web/mobiles et d'interagir avec un Chatbot capable d'analyser les documents PDF internes de la banque pour répondre aux questions.

<img width="766" height="737" alt="Screenshot 2025-12-29 033806" src="https://github.com/user-attachments/assets/9adbdffd-4fec-4b82-a558-59041cca20a7" />


---

## 2. Architecture Technique détaillée

### A. Microservices d'Infrastructure (Spring Cloud)

Ces services assurent la robustesse et la scalabilité de l'écosystème :

* **Discovery Service (Eureka) :** Agit comme un annuaire. Chaque instance de microservice s'y enregistre dynamiquement pour permettre le Load Balancing et le routage.
* **Config Service :** Centralise les fichiers de configuration de tous les microservices sur un dépôt Git ou en local, facilitant la gestion des profils (dev, prod).
* **Gateway Service (Reactive Gateway) :** Unique porte d'entrée du système. Il redirige les requêtes vers les services appropriés et expose la documentation agrégée.

### B. Microservices Fonctionnels

* **Bénéficiaire-Service :** * Gestion du catalogue des destinataires.
* Données : ID, Nom, Prénom, RIB, Type (Physique/Morale).
* Exposition : API REST documentée sous Swagger.


* **Virement-Service :** * Cœur métier des transactions.
* Données : ID, ID Bénéficiaire, RIB Source, Montant, Description, Date, Type (Normal/Instantané).
* Interaction : Consomme les données du service bénéficiaire pour validation.


* **Chat-Bot-Service (IA) :** * Moteur d'IA utilisant Spring AI ou LangChain.
* Fonctionnement RAG : Extraction de texte depuis des PDF, vectorisation et stockage dans une base vectorielle, puis interrogation d'un LLM (GPT-4o / Llama 3) avec le contexte extrait.



### C. Interfaces Utilisateurs

* **Web :** Développé avec React ou Angular pour une expérience fluide.
* **Mobile :** Développé avec Flutter ou Android pour une utilisation nomade.

---

## 3. Guide de Développement et Tests

### Spécifications des APIs (OpenAPI/Swagger)

Chaque microservice métier intègre `springdoc-openapi`. Une fois le service lancé, la documentation est disponible sur :
`http://localhost:[port]/swagger-ui.html`

### Modèles de Données

#### Bénéficiaire

| Champ | Type | Description |
| --- | --- | --- |
| id | Long | Identifiant unique |
| nom | String | Nom du bénéficiaire |
| prenom | String | Prénom du bénéficiaire |
| rib | String | Relevé d'Identité Bancaire |
| type | Enum | PHYSIQUE, MORALE |

#### Virement

| Champ | Type | Description |
| --- | --- | --- |
| id | Long | Identifiant du virement |
| beneficiaireId | Long | Lien vers le bénéficiaire |
| ribSource | String | RIB du compte émetteur |
| montant | Double | Somme transférée |
| description | String | Libellé du virement |
| date | LocalDate | Date de l'opération |
| type | Enum | NORMAL, INSTANTANE |

---

## 4. Instructions d'Installation

### Étape 1 : Préparation de l'environnement

1. Installer le JDK 17 ou version supérieure.
2. Installer Maven 3.8+.
3. Configurer une base de données (H2 par défaut pour le test, ou PostgreSQL).

### Étape 2 : Lancement de l'infrastructure

Respectez l'ordre suivant pour assurer la connectivité :

1. **Config Service** : Port 8888.
2. **Discovery Service** : Port 8761.
3. **Gateway Service** : Port 8222.

### Étape 3 : Lancement des services métiers

Exécutez la commande suivante dans chaque dossier de service :

```bash
mvn spring-boot:run

```

### Étape 4 : Configuration du Chatbot

Pour le `chat-bot-service`, assurez-vous de configurer votre clé API (OpenAI ou locale via Ollama) dans le fichier `application.properties` et de placer les documents PDF dans le dossier `src/main/resources/docs`.

---

## 5. Technologies Clés

* **Backend** : Java, Spring Boot 3, Spring Data JPA, Hibernate.
* **Microservices** : Spring Cloud Gateway, Netflix Eureka, Spring Cloud Config.
* **IA** : Spring AI, Vector Database (ChromaDB ou PGVector), LLM GPT-4o/Llama 3.
* **Frontend** : React / Angular.
* **Documentation** : OpenAPI 3 / Swagger UI.

---

**Auteur : ZAIDI Malak**
---

**Souhaitez-vous que je vous aide à rédiger la classe Java du contrôleur pour le `chat-bot-service` ou la configuration du `gateway-service` ?**
