# 🌍 **TravelProject**

[![Java](https://img.shields.io/badge/Java-21-blue?logo=java)](https://www.oracle.com/java/)
[![Build Tool](https://img.shields.io/badge/Maven-Automated-red?logo=apachemaven)](https://maven.apache.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

## 🛍 Descrizione

TravelProject è un'applicazione Java che consente la gestione di viaggi, partecipanti e organizzatori. Permette di creare viaggi, gestire utenti, visualizzare i dettagli e salvare i dati in formato JSON.

## 📦 Funzionalità principali

• ➕ Creazione e modifica di viaggi

• 🔐 Login con autenticazione di organizzatori e partecipanti

• 👥 Aggiunta e rimozione di partecipanti/organizzatori dai viaggi

• 📄 Visualizzazione viaggi con dettagli

• 💾 Salvataggio e caricamento dei dati in formato JSON

## 🧑‍💻 Tecnologie usate

• Java 21

• Jackson (per serializzazione JSON)

• IntelliJ IDEA

• Maven

• AppConfig.java per gestione configurazioni

## 🚀 Come eseguire il progetto

1. Clona il repository:

   ```bash
   git clone https://github.com/imgg98/TravelProject.git

2. Apri il progetto in IntelliJ IDEA (o altro IDE compatibile)

3. Esegui la classe Main in com.travel.app per avviare l'applicazione

## 📁 Struttura del progetto
```
TravelProject/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/travel/
│       │       ├── app/         # Classe Main
│       │       ├── config/      # AppConfig.java
│       │       ├── model/       # Entità come Trip, Person, ecc.
│       │       └── service/     # Logica applicativa (TripService, UserService)
│       └── resources/
│           └── data/
│               ├── users.json   # Dati utente di esempio
│               └── trips.json   # Dati viaggio di esempio
├── pom.xml
├── .gitignore
├── LICENSE
└── README.md
```
## 📄 Documentazione inclusa

• ✅ Analisi dei requisiti (testuale)

• ✅ File di esempio JSON (users.json, trips.json)

## Requisiti Non Funzionali

• Prestazioni: Il progetto è progettato per gestire un numero limitato di viaggi e partecipanti. Per usi in ambienti con alta concorrenza, è consigliato l'ottimizzazione delle operazioni di salvataggio e caricamento dei dati.

• Sicurezza: Al momento, l'autenticazione è gestita tramite login di base. Per un'applicazione di produzione, si consiglia di implementare una gestione più sicura delle credenziali, come l'uso di token di autenticazione e la protezione dei dati sensibili.

• Scalabilità: Il sistema supporta facilmente l'aggiunta di nuove funzionalità, ma il salvataggio e il caricamento di dati JSON possono diventare lenti con un numero maggiore di utenti o viaggi. Per scenari di maggiore carico, l'uso di un database relazionale o NoSQL sarebbe vantaggioso.

## Casi d'uso
1. Login di un partecipante

   Un partecipante può accedere al sistema inserendo le proprie credenziali (email e password). Dopo il login, potrà visualizzare i viaggi a cui è stato aggiunto.

   // Esempio di login
   ```
   Person user = userService.login("partecipante@esempio.com", "password123", users);
   ```
2. Creazione di un viaggio da parte di un organizzatore

   Un organizzatore autenticato può creare un nuovo viaggio specificando nome, destinazione, data di inizio e fine.

   // Esempio di creazione viaggio
   ```
   Trip newTrip = new Trip("Roma", "2025-06-15", "2025-06-20");
   tripService.createTrip(newTrip);
   ```
3. Modifica dei dettagli di un viaggio

   L'organizzatore può aggiornare la destinazione o le date di un viaggio esistente.

   // Esempio di modifica viaggio
   ```
   tripService.modifyTrip(trip, scanner);
   ```
4. Aggiunta di partecipanti od organizzatori

   È possibile aggiungere nuovi utenti a un viaggio, specificando se devono essere partecipanti od organizzatori.

   // Esempio di aggiunta utente
   ```
   tripService.addParticipantsOrOrganizers(trip, scanner, users, trips, USERS_FILE, TRIPS_FILE);
   ```
5. Rimozione di un utente da un viaggio

   Si possono rimuovere partecipanti od organizzatori da un viaggio esistente.

   // Esempio di rimozione utente
   ```
   tripService.removeUserFromTrip(trip, scanner);
   ```
## ✍️ Autore

• ⁫⁪Giulio Giangrande

## 📜 Licenza

Questo progetto è distribuito sotto licenza MIT. Consulta il file LICENSE per maggiori dettagli.