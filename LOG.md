## Jeudi 24 juin 2021
Répartition des tâches

**Valentin :** 
- Fix de quelques bug liés à la minimap
- Fix des problèmes liées à la sélection des automates dans le menu
- Implémentation du design pattern "poids mouche" pour les automates => permet d'avoir un seul automate "Ennemy" chargé en mémoire pour toutes nos entités "Ennemy"

## Mercredi 23 juin 2021
Répartition des tâches

**Valentin :** 
- Travail sur la partie model du menu permettant de choisir les automates
- Fix de plusieurs bugs concernant la minimap et la regénération des chunks

**Mirette et Hachem :** 
- Travail sur la partie graphique du chien
- Travail sur la barre représentant le niveau de vie du joueur et sur la barre représentant le nombre de balles restantes pour le joueur

## Mardi 22 juin 2021
Répartition des tâches

**Valentin :** 
- Modification des hitbox pour qu'elles soient circulaires

**Mirette :** 
- Travail sur la vue centrée sur le joueur

**Hachem :** 
- Création de la menu de configuration du jeu 

**Yannick :**
- Réecriture des automates Arme, Balle, Mine et Ennemi en GAL

## Lundi 21 juin 2021
Répartition des tâches

**Valentin :** 
- Finalisation de la regénération des chunks
- Travail sur la boue en ralentissant le joueur quand il marche sur un chunk

**Mirette et Hachem :** 
- Travail sur le comportement des ennemis qui sont censés se diriger vers le joueur lorsqu'ils le détectent

## Dimanche 20 juin 2021
Répartition des tâches

**Valentin :** 
- Manipulation des chunks pour en générer des nouveaux (par exemple, si le joueur avance à droite d'un chunk, on va supprimer la colonne de chunk la plus à gauche pour en créer une nouvelle à droite), pour le moment pas entièrement fonctionnelle.

**Mirette et Hachem :** 
- Travail sur le comportement du joueur qui doit changer de position selon l'orientation de la souris

## Samedi 19 juin 2021
Répartition des tâches

**Valentin :** 
- Implémentation du design pattern "poids mouche" pour les entités, de cette façon on ne charge qu'une seule fois le sprite des entités pour toute les entités du jeu 

**Mirette et Hachem :** 
- Travail sur les animations des comportements du joueur

## Vendredi 18 juin 2021
Répartition des tâches
**Valentin :** 
- Travail sur la minimap affichant les positions des différentes entités

**Mirette et Hachem :** 
- Changement de la méthode utilisée pour faire les transitions entre les saisons suite aux remarques de notre tuteur

## Jeudi 17 juin 2021
**Travail réalisé :** affichage du personnage du model dans la vue, début d'affichage des ennemis

- Hachem et Mirette: structuration du code afin de relier les entités au niveau modéle-vue (affichage des ennemis et des autres entités).

- Malik: Merge le code qui relie l'architecture MVC plus l'automate sur le master en fixant les bugs qui surviennent. Prototypage des conditions et des actions sur les ennemies ainsi que le joeur (car maitenant le protytpage se fait sur un code 100% implémenté par le groupe ) .

- Victor: essais sur les déplacements en diagonal avec le clavier, et le déplacement avec la souris.

- Valentin et Malik : travail en commun pour les merge

- Valentin : implémentation graphique des ennemis et du chien grâce au model.

## Mercredi 16 juin 2021
**Travail réalisé :** mise en commun du travail, début de problèmes de merge donc nécessité de discuter avec l'équipe pour savoir quelle version garder par la suite. 

- Mirette et Hachem: modification de la méthode utilisée pour faire les transitions entre les saisons afin d'améliorer les performances + correction d'un bug au niveau de l'affichage graphique + assemblage modéle-vue-controller-automate. 

- Valentin et Malik : travail en commun pour les merge

## Mardi 15 juin 2021
**Les blocages :**

**Les problèmes rencontrés :** Le merge de la partie automate fait hier avec la partie model qui a generé des bugs mais qui sont fixées.

**Les réponses apportées :**
- Les dégâts subit par le chien vont être gérés au niveau de l'automate et non au niveau du code. Comme ça, on pourra le rendre invincible à la demande.


**Réflexions en cours :**

**Répartition des tâches :**
- Hachem et Mirette : finalisation de la partie graphique concernant les transitions entre les saisons (passage du printemps vers l'hiver).
- Valentin : Améliore le model.
- Malik : Améliore les automates.
- Victor et Yannick : travail sur la representation de la map .

**Malik :** 

- rajout de la condition CELL(D,C) et de l'action move qui fonctionne desormis sur plusieurs direction .

- lier l'automate à la partie model de valention (il était lié à la represenation du prof).
- merge la partie model et la partie automaton et fixer les bugs generés .

**Valentin :** 
- implémentation dans le modèle des ennemis
- utilisations de sprites différents pour plusieurs entités
- ajout de la documentation pour une très grande partie du code


## Lundi 14 juin 2021
**Les blocages :**
**Les problèmes rencontrés :** importation du prototypage fait dans le week-end et refactoring complet pour avoir un meilleur système de fichier
**Les réponses apportées :**
**Réflexions en cours :**

**Répartition des tâches :**
- Hachem et Mirette : prototypage des armes (tire quand on clique, avoir les balles font des dégâts). 
- Valentin : travaille sur le modèle en suivant la hiérarchie de classe établie.
- Malik : travaille sur les automates.
- Victor et Yannick : mise en place de la carte infini (pouvoir se déplacer librement sans être bloqué par les bords de la fenêtre).

**Valentin :** Mise en œuvre de la meilleur architecture du projet en renommant la majorité des dossier et en déplaçant d'autres fichiers / dossiers, travail sur le model : 
- Implémentation des chunks (diviser la carte en plusieurs petits carrés, utilisé pour la génération)
- Implémentation des hitbox pour le joueur (carré autour du joueur qui permettra de détecter les collisions)

## Dimanche 13 juin 2021
Réunion pour relire le proposal afin que tout le monde ait la même connaissance du projet et savoir si le contrat proposé convient à tout le monde, discussion sur la répartition des tâches.

## Samedi 12 juin 2021
Tout le monde à travailler de son côté, réunion dans la soirée pour mettre en commun tout ce qui a été fait et faire des merges.

**Ce qui a été fait pour le moment :**
- Déplacement simple d'un personnage.
- Gestion des ticks pour changer les saisons.

##  Vendredi 11 juin 2021
**Discussions de :**  
- L'implémentation du système de génération. 
- Les transitions de saisons.
-  Comment le joueur va pouvoir tirer dans toutes les directions.

En parlant avec notre tuteur, on a pu être éclaircis sur l'implémentation des automates.

**Début d'un prototypage simple :**
- Malik : travaille sur les automates.
- Valentin : travaille sur le modèle.
- Mirette et Hachem : tests sur la librairie fournie pour changer le fond et faire quelques tests graphique.
- Victor et Yannick : prennent en main la librairie graphique et font des tests.

## Jeudi 10 juin 2021
**Discussions de :** 
- représentation en interne de la carte 
- collisions avec les ennemis
- génération aléatoire de la carte
- gestion de la vitesse (pour accélérer la cadence ou ralentir le joueur)
- comment faire la transition des saisons

**Refonte des automates** : ceux que l'on avait faits la veille n'utilisaient pas la syntaxe imposée, on a divisé les automates à faire pour que chacun fasse une petite partie de son côté

## Mercredi 9 juin 2021
On a dégagé les différentes entités de nôtres jeu et leurs comportements, on a également commencé à écrire des automates pour quelques entités au comportement simple.

## Lundi 7 et mardi 8 juin 2021
Discussions globales sur le projet, ce que l'on va faire.
Discussions avec notre tuteur pour modeler nos idées et modifier notre jeu.

