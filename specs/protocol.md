# Specifications

L'objectif de ce protocole est de permettre la communication entre un client et un serveur « calculette ».

Le client doit pouvoir envoyer un calcul simple et le serveur doit en retourner le résultat.

C'est le client qui initie la connexion et qui la termine à l'envoie d'un message particulier.



# Syntaxe

Pour terminer la connexion on envoie le message : `QUIT` et le serveur répond `BYE` et ferme la connexion.

On se limite aux opérations à deux opérandes (de type double precision), la syntaxe serait la suivante :

```
OPERATION　OPERAND1　OPERAND2\n
```

Les opérations suivantes : `ADD MULTIPLY`
* Les opérandes doivent être parsable par `Integer.ParseInt`.

Le serveur analyse le message de la façon suivante
1. Vérifie que la première ligne est soit une opération soit `QUIT`
    1. Si c'est `QUIT` le serveur envoie BYE et ferme la connexion.
    2. Si c'est une opération :
        1. on vérifie qu'elle existe
        2. On parse le premier opérande si ok -> on continue autrement on répond par une erreur
        3. On parse le second opérande si ok -> on continue autrement on répond par une erreur
        4. On effectue l'opération `OPERAND1 OPERATION OPERAND2`, si elle s'est déroulée correctement -> on l'envoie au client autrement on envoie une erreur.

Toutes les commandes se terminent par `\n`.

## Gestion des erreurs
Quand une erreur se produit le serveur envoie `ERROR <Error Code>`
Les codes d'erreurs sont les suivants :
* 10 : Opération inconnue
* 20 : Operande 1 malformée
* 21 : Opérande 2 malformée

## Extensibilité :
* Le protocole doit au minimum implémenter la multiplication et l'addition.
* Le protocole ne gère qu'un calcul à la fois

# Exemples

Scenario normal
```
Server: HELLO
Bienvenue les opérations disponibles sont les suivantes: ADD, MULTIPLY
Format: OPERATION　OPERAND1　OPERAND2
Pour quitter: QUIT
Client: ADD　1　2
Server: RESULT 2
Client: QUIT
Server: BYE
```

Erreur
```
Server: HELLO
Bienvenue les opérations disponibles sont les suivantes: ADD, MULTIPLY
Format: OPERATION　OPERAND1　OPERAND2
Pour quitter: QUIT
Client: ADDX　1　2
Server: ERROR 10
Client: QUIT
Server: BYE
```