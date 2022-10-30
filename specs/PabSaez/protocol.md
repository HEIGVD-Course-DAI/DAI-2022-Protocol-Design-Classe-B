# DAI - Laboratoire 03 - Protocol Design
    
## Protocol objectives: what does the protocol do?
L'objectif est de permettre au client de se connecter à un serveur afin de faire des calculs simple (calculatrice)

## Overall behavior:
### What transport protocol do we use?
On va utiliser une communication TCP pour transmettre les messages.
### How does the client find the server (addresses and ports)?
Le client doit connaître l'adresse IP et le port (343434) du serveur.
### Who speaks first?
Le serveur parlera d'abord en mettant un message de bienvenue et un menu d'aide
### Who closes the connection and when?
Le client lorsqu'il n'a plus besoin d'utiliser le service proposé
    
## Messages:
### What is the syntax of the messages?
On commence par entrer l'opération puis les valeurs. 

Exemple : ADD 1 2
### What is the sequence of messages exchanged by the client and the server? (flow)
- Messages de bienvenues
- Demande de commande
- Lecture de l'entrée
- Sortie du résultat
- Message d'au revoir
### What happens when a message is received from the other party? (semantics)
Message d'erreur
## Specific elements (if useful)
### Supported operations
Addition et Multiplication
### Error handling
Message d'erreur demandant une entrée valide
### Extensibility
Ajout d'autre opérateurs: la soustraction ou la division
## Examples: examples of some typical dialogs.
**Client** <--------------------Bienvenue-------------------- **Serveur**

**Client** <-----Appuyer sur CTRL+Q pour quitter---- **Serveur**

**Client** <-----------Entrez une commande------------- **Serveur**

**Client** ----------------------ADD 1 2---------------------> **Serveur**

**Client** <----------------Le résultat est : 3--------------- **Serveur**

**Client** -----------------------CTRL+Q-------------------> **Serveur**
