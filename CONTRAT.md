# CONTRAT du groupe 7

### Résumé

- 1 joueur + 1 allié
- carte vue du dessus
- génération aléatoire avec mémorisation
- deux mondes = deux saisons
- coordonnées flottantes
- vitesse variable

### Légende
- :white_check_mark: ou :ballot_box_with_check: = contrat
- :eight_pointed_black_star: = optionnel
- :x: = pédagogiquement peu intéressant
- :warning: contrainte à respecter
- en __gras__ = ce qu'il ne faut pas oublier de montrer dans la démo

**Membres** :
* Yannick Sica  
* Victor Allègre
* Malik Salim Sedira
* Mirette Guirguis
* Hachem Mohsen
* Valentin De Oliveira (chef de projet)

# Sommaire

1. [Le jeu](#le-jeu)
2. [La carte](#la-carte)
3. [Les univers](#les-univers)
4. [Gameplay](#gameplay)
5. [Utilisations des automates](#utilisation-des-automates)
6. [Détails technique](#détails-technique)
7. [Propositions d'améliorations](#autres-propositions-daméliorations)

## :white_check_mark: Le jeu

Notre jeu sera un jeu de tir en vue du dessus avec un système de saison, le joueur pourra se déplacer dans toutes les directions. Notre héros sera accompagné d'un chien contrôlé par un automate pour l'aider dans le jeu.

 > **Implémentation  :** les déplacements du joueurs seront libres en x,y (pas de système de cases)

## La carte
:white_check_mark:
La carte sera générée aléatoirement, __il y aura une mémorisation.__

> On générera une carte plus grande que la vue du joueur pour lui permettre de se déplacer sans problème.
Le chien ne peut pas se déplacer au delà des limites de la carte et le chien ne déclenche pas la génération de la carte.


:white_check_mark: Il y aura également de __nouveaux ennemis lorsque la carte est régénérée__ (quand par exemple le joueur avance et retourne sur ses pas suffisamment longtemps pour enclencher la génération de la carte).

> - **Implémentation :** le nombre d'ennemis et de mines seront définis par un pourcentage de la surface de la carte (zone visible et invisible du joueur) par exemple, s'il y a 5% d'ennemis, alors il y aura 5% de l'écran qui sera recouvert d'ennemis, ce pourcentage sera amené à augmenter au fur et à mesure que le joueur joue pour corser la difficulté.
- **Représentation en interne :**  Utilisation du LinkedList d'entité qui contient la totalité des entités sur la partie visible et invisible du joueur (:question:), on peint l'intégralité du fond d'herbe (avec un canvas) puis on peint chaque entité de la liste se situant dans la zone visible de l'utilisateur.

## Les univers
Il y aura 2 univers différents :

### :white_check_mark: 1er univers
**Paysage** : __correspond à un décor de printemps__, un décor basique avec de l'herbe ainsi que
- des __flaques de boues__ à quelques endroits __qui ralentissent le joueur.__
- des mines seront également disposées sur le terrain et tueront le joueur s'il marche dessus.

**Ennemis** : ennemis pas très forts qui tireront sur le joueur.
>  **Implémentation :** pour gérer les collisions, on dessinera un carré transparent autour de chaque ennemi et sur le joueur, si une balle  touche ce carré, alors l'ennemi/le joueur qui se prend la balle prendra des dégâts. De plus, dans le modèle, quand une balle rencontre une entité, elle explose et toutes les entités autour perdent des pv.

----------------------------------------
### :white_check_mark: 2ème univers
**Paysage** : __correspond à un décor d'hiver__, le paysage sera similaire à la génération précédente à la seule différence que le monde sera enneigé.

**Ennemis** : ennemis plus nombreux et plus forts, __les mines seront recouvertes par la neige,__ empêchant le joueur de les voir au premier coup d’œil.

**Propriétés** : sprites du joueur et des ennemis différents, __le joueur sera ralenti et les ennemis le seront aussi légèrement.__ Système de saison, donc quand on sera en plein milieu de l'hiver, les entités seront beaucoup plus ralentit qu'au début ou à la fin de l'hiver.

La transition pour passer de l'univers 1 à l'univers 2 se fera après que le joueur ait survécu pendant un certain laps de temps pour représenter le passage à une nouvelle saison.

> **Implémentation :** à chaque début de saison, on va armer un timer qui va s'activer après avoir joué un certain temps (environ 5 min) pour passer  à la prochaine saison.

## Gameplay

Le principe est de survivre le plus de temps possibles (représentés par le nombre d'années survécus).
- :eight_pointed_black_star: Un boss apparaît pendant la saison hivernale, il serait préférable pour le joueur d'engager le combat contre celui-ci au début ou à la fin de l'hiver plutôt qu'en plein milieu, car sinon il aura plus de difficulté à se déplacer et ça sera plus dur pour lui de vaincre le boss.

> **Implémentation :** pour augmenter ou diminuer la vitesse, on va, pour chaque entité,  ajouter 2 champs : moveElapse qui représentera la distance parcourue avec le même sprite et imageElapsed qui représentera la durée passé avec le même sprite, il suffira de réduire ces limites si on veut accélérer le jeu, cela sera géré par la fonction tick.

À chaque année survécu par le joueur, il bénéfieciera d'atouts permanents (par exemple, en augmentant légèrement la cadence de son arme ou bien sa santé maximale)

- :eight_pointed_black_star: Il y aura des power-up que les ennemis auront lâché et dans les flaques de boue qui pourront donner des bonus temporaires au joueur (cadence accélérée, tirer plusieurs balles en même temps, amélioration pour le chien, ajout d'alliés ...).

##### :white_check_mark: Deuxième viewport

La deuxième viewport servira à afficher une __minimap de la taille du monde généré. Elle indiquera au joueur les marres de boues, la présence des power-up, les mines ainsi que ses alliés.__

**Attention :** durant l'hiver, les mines seront recouvertes par de la neige donc elle n'apparaîtrons plus sur la carte.

##### :white_check_mark: Le chien
Le joueur sera accompagné d'un chien qui pourra partir en éclaireur et __indiquer sur la minimap la présence d'un / des ennemi(s) que le chien a pu trouver.__

**Attention** : le chien sera invincible.

> :option: MP> modifiable via l'automate du chien.

> **Implémentation :** les ennemis auront un listener sur le chien pour connaître sa position, on part du principe que ce sont les ennemis qui vont détecter la présence du chien. Pour la détection, les ennemis auront un booléen indiquant s'ils sont détectés ou pas, si le chien est proche d'une certaine distance des ennemis, alors cette valeur passera à vrai. De plus, chaque ennemi aura d'autres champs tel que la distance par rapport au joueur (x, y) et distance par rapport au chien (x, y).
Ces champs seront mis à jour toutes les 2 secondes pour avoir les distances à jour.
Utilisation d'une file à priorité  selon la distance entre le joueur et les ennemis.

##### Finir le jeu

Le jeu ne dispose pas de fin à proprement dite, le joueur doit survivre le plus longtemps possible.

>:question: MP> c'est dommage... Pourquoi ne pas cacher une chose à trouver (un gibier qui se déplace ?) en creusant
= action wizz du chien et du joueur.
Ce qui donne plus d'intérêt aux mines et à la neige.


## Utilisation des automates
  Consulter le fichier automaton.md pour voir les implémentations concrètes au format .gal

### :white_check_mark: Pour les alliés
- **Chien :** pop : permet de savoir si __le joueur a rappelé le chien__, à faire de temps en temps pour voir si la condition est valide.
- **Joueur :** pop : __faire une animation pour signaler à l'utilisateur qu'il a pris des dégâts__

----------------------------------------
### :white_check_mark: Pour les ennemis
- **Ennemis  :** tirent de loin ou attaque au corps-à-corps (avec un couteau ou à mains nues)
pop : __faire une animation pour signaler à l'utilisateur que l'ennemi a pris des dégâts__
- **Mine :** pop : __fait clignoter la mine pour permettre au joueur de savoir où sont les mines pendant l'hiver__.
> **Implémentation :** on va détecter si le joueur se trouve sur une case dans toutes les directions (Nord, Est, Sud, Ouest) car il n'y a pas de syntaxe existante pour détecter si le joueur se trouve sur la même case que la mine.

> :exclamation: MP> CELL(H,M) avec H=Here, M=Missile

----------------------------------------

### :white_check_mark: Pour les armes

- **Arme à feu (pistolet) :** pop : recharger l'arme

- **Les balle** : **Power-up :**  donnent de nouvelles armes (par exemple, une arme qui tire dans 3 directions simultanément) , améliore la cadence (temporaire), redonne de la vie.

## Détails techniques (peuvent être amenés à être modifiés au cours du projet)

- :white_check_mark: **Utilisation des armes par les joueurs et les ennemis :** il y aura un attribut arme dans la classe du joueur et de l'ennemi qui indiquera l'arme qu'il a pour appeler l'automate de l'arme (et donc de l'appeler pour faire un step ou quand on fera un hit). Par contre, le sprite du joueur et des ennemis contiendra déjà le graphique de l'arme, on changera seulement son comportement sans changer son apparence.

- :white_check_mark: **Système de tir :** pour tirer à une position voulue, __on récupérera la position (x, y) de la souris et on enverra la balle dans cette direction__

- :white_check_mark: **Gérer les animations (par exemple pour recharger) :** ça se fera avec les ticks (par exemple en disant que l'animation dure 200 ticks), c'est le rechargement qui compte les ticks, on doit avertir l'automate qu'il est occupé (avec un booléen).
> :warning: MP>  utilisez une classe qui définit des constantes et surtout ne pas mettre de chiffre en dur dans le code.


## Autres propositions d'améliorations

### :eight_pointed_black_star: Avoir un boss avec un comportement différents des ennemis normaux
L'implémentation courante du boss est celle d'un ennemi avec un sprite différent qui sera plus fort et qui aura plus de vie que les ennemis normaux, on pourrait imaginer qu'il aurait une mécanique différente, il aura plusieurs actions possibles, il aura plusieurs têtes et pourra tirer des boules de feu, il faudrait battre chacune de ses têtes pour vaincre le boss.

### :eight_pointed_black_star: Améliorer le chien
Lorsque les ennemis seront trop proches du joueur et que le chien suivra le joueur, il pourra défendre le joueur en attaquant les ennemis

### :x: Le rajout d'un système pour permettre au joueur de créer ses armes
Le joueur pourrait, en accédant à un menu, créer des armes personnalisées en saisissant des informations basiques (sélectionner une photo pour l'arme, entrer la cadence, comment l'arme tire, etc.)

### :eight_pointed_black_star: Avoir une barre de givre dans le 2ème univers
Vu que le joueur sera dans un monde enneigé, même si le personnage aura un sprite adapté à son environnement, on pourrait intégrer une barre qui donne le niveau de givre du personnage, si cette barre est remplie alors, le joueur mourra d'hypothermie. Il y aurait également des ennemis qui pourrait nous tirer des boules de neige dessus, elles nous feraient pas beaucoup de dégâts mais augmenteraient notre barre de givre.

