# Objectif du protocole
L'objectif de ce protocole sera de permettre à un client d'envoyer des calculs à un serveur. Ce dernier devra renvoyer la réponse.

# Comportement du protocole
Nous allons utiliser le TCP comme protocole de transport.

Le client devra se connecter en local sur le port 9999.

Le client va envoyer la première requête et le serveur lui répondera.

Le client ferme la connexion quand il le veut, le serveur se remettera en attente.

# Message
Syntaxe du message : nombre operator nombre

Échange de messages :
1. client : calcul
2. serveur : réponse


# Éléments spécifiques
Opérations supportées : +, -, *, /, pow, sqrt

Erreurs possibles : syntaxe incorrecte / problème de range sur les nombres

# Exemples
1. client : 2+3
2. serveur : 5

1. client : 51 - 42
2. serveur : 9