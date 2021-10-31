# Groupe 7

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

## Le jeu
Notre jeu sera un jeu de tir en vue du dessus avec un système de saison, le joueur pourra se déplacer dans toutes les directions. Notre héros sera accompagné d'un chien contrôlé par un automate pour l'aider dans le jeu.
 
 > **Implémentation  :** les déplacements du joueurs seront libres en x,y (pas de système de cases)

## La carte
La carte sera générée aléatoirement, il y aura une mémorisation.
On générera une carte plus grande que la vue du joueur pour lui permettre de se déplacer sans problèmes.
Il y aura également de nouveaux ennemis lorsque la carte est régénérée (quand par exemple le joueur avance et retourne sur ses pas suffisamment longtemps pour enclencher la génération de la carte).
 > **Implémentation :** le nombre d'ennemis et de mines seront définis par un pourcentage de la surface de la carte (zone visible et invisible du joueur) par exemple, s'il y a 5% d'ennemis, alors il y aura 5% de l'écran qui sera recouvert d'ennemis, ce pourcentage sera amené à augmenter au fur et à mesure que le joueur joue pour corser la difficulté.
 > **Représentation en interne :**  Utilisation du LinkedList d'entité qui contient la totalité des entités sur la partie visible et invisible du joueur, on peint l'intégralité du fond d'herbe (avec un canvas) puis on peint chaque entité de la liste se situant dans la zone visible de l'utilisateur.

## Les univers
Il y aura 2 univers différents :
### 1er univers :
**Paysage** : correspond à un décor de printemps, un décor basique avec de l'herbe ainsi que des flaques de boues à quelques endroits qui ralentissent le joueur. Des mines seront également disposées sur le terrain et tueront le joueur s'il marche dessus.

**Ennemis** : ennemis pas très fort qui tirerons sur le joueur.
>  **Implémentation :** pour gérer les collisions, on dessinera un carré transparent autour de chaque ennemi et sur le joueur, si une balle  touche ce carré, alors l'ennemi/le joueur qui se prend la balle prendra des dégâts. De plus, dans le modèle, quand une balle rencontre une entité, elle explose et toutes les entités autour perdent des pv.

----------------------------------------
### 2ème univers :
**Paysage** : correspond à un décor d'hiver, le paysage sera similaire à la génération précédente à la seule différence que le monde sera enneigé.

**Ennemis** : ennemis plus nombreux et plus forts, les mines seront recouvertes par la neige, empêchant le joueur de les voir au premier coup d’œil.

**Propriétés** : sprites du joueur et des ennemis différents, le joueur sera ralenti et les ennemis le seront aussi légèrement. Système de saison, donc quand on sera en plein milieu de l'hiver, les entités seront beaucoup plus ralentit qu'au début ou à la fin de l'hiver.

La transition pour passer de l'univers 1 à l'univers 2 se fera après que le joueur ait survécu pendant un certain laps de temps pour représenter le passage à une nouvelle saison.

> **Implémentation :** à chaque début de saison, on va armer un timer qui va s'activer après avoir joué un certain temps (environ 5 min) pour passer  à la prochaine saison.

## Gameplay

Le principe est de survivre le plus de temps possibles (représentés par le nombre d'années survécus). Un boss apparaît pendant la saison hivernale, il serait préférable pour le joueur d'engager le combat contre celui-ci au début ou à la fin de l'hiver plutôt qu'en plein milieu, car sinon il aura plus de difficulté à se déplacer et ça sera plus dur pour lui de vaincre le boss.

> **Implémentation :** pour augmenter ou diminuer la vitesse, on va, pour chaque entité,  ajouter 2 champs : moveElapse qui représentera la distance parcourue avec le même sprite et imageElapsed qui représentera la durée passé avec le même sprite, il suffira de réduire ces limites si on veut accélérer le jeu, cela sera géré par la fonction tick.
	
À chaque année survécu par le joueur, il bénéfieciera d'atouts permanents (par exemple, en augmentant légèrement la cadence de son arme ou bien sa santé maximale) 


Il y aura des power-up que les ennemis auront lâché et dans les flaques de boue qui pourront donner des bonus temporaires au joueur (cadence accélérée, tirer plusieurs balles en même temps, amélioration pour le chien, ajout d'alliés ...).

**Deuxième viewport :** la deuxième viewport sera permise via l'utilisation d'une minimap qui indiquera au joueur les marres de boues, la présence des power-up, les mines ainsi que ses alliés.

**Attention :** durant l'hiver, les mines seront recouvertes par de la neige donc elle n'apparaîtrons plus sur la carte.

**Le chien :** le joueur sera accompagné d'un chien qui pourra partir en éclaireur et indiquer sur la minimap la présence d'un / des ennemi(s) que le chien a pu trouver.
**Attention** : le chien sera invincible.

> **Implémentation :** les ennemis auront un listener sur le chien pour connaître sa position, on part du principe que ce sont les ennemis qui vont détecter la présence du chien. Pour la détection, les ennemis auront un booléen indiquant s'ils sont détectés ou pas, si le chien est proche d'une certaine distance des ennemis, alors cette valeur passera à vrai. De plus, chaque ennemi aura d'autres champs tel que la distance par rapport au joueur (x, y) et distance par rapport au chien (x, y).
Ces champs seront mis à jour toutes les 2 secondes pour avoir les distances à jour.
Utilisation d'une file à priorité  selon la distance entre le joueur et les ennemis.

**Finir le jeu :** le jeu ne dispose pas de fin à proprement dite, le joueur doit survivre le plus longtemps possible.

## Utilisation des automates
  Consulter le fichier automaton.md pour voir les implémentations concrètes au format .gal

### Pour les alliés
**Chien :** pop : permet de savoir si le joueur a rappelé le chien, à faire de temps en temps pour voir si la condition est valide.
**Joueur :** pop : faire une animation pour signaler à l'utilisateur qu'il a pris des dégâts
  
----------------------------------------
### Pour les ennemis
**Ennemis  :** tirent de loin ou attaque au corps-à-corps (avec un couteau ou à mains nues) 
pop : faire une animation pour signaler à l'utilisateur que l'ennemi a pris des dégâts 
**Mine :** pop : fait clignoter la mine pour permettre au joueur de savoir où sont les mines pendant l'hiver.
> **Implémentation : ** on va détecter si le joueur se trouve sur une case dans toutes les directions (Nord, Est, Sud, Ouest) car il n'y a pas de syntaxe existante pour détecter si le joueur se trouve sur la même case que la mine.
 	 
----------------------------------------

### Pour les armes
**Arme à feu (pistolet) :** pop : recharger l'arme

**Les balle** 

**Power-up :**  donnent de nouvelles armes (par exemple, une arme qui tire dans 3 directions simultanément) , améliore la cadence (temporaire), redonne de la vie.

## Détails technique (peut-être amené à être modifié au cours du projet)
**Utilisation des armes par les joueurs et les ennemis :** il y aura un attribut arme dans la classe du joueur et de l'ennemi qui indiquera l'arme qu'il a pour appeler l'automate de l'arme (et donc de l'appeler pour faire un step ou quand on fera un hit). Par contre, le sprite du joueur et des ennemis contiendra déjà le graphique de l'arme, on changera seulement son comportement sans changer son apparence.

**Système de tir :** pour tirer à une position voulue, on récupérera la position (x, y) de la souris et on enverra la balle dans cette direction
 
**Gérer les animations (par exemple pour recharger) :** ça se fera avec les ticks (par exemple en disant que l'animation dure 200 ticks), c'est le rechargement qui compte les ticks, on doit avertir l'automate qu'il est occupé (avec un booléen)


## Autres propositions d'améliorations

### Avoir un boss avec un comportement différents des ennemis normaux
L'implémentation courante du boss est celle d'un ennemi avec un sprite différent qui sera plus fort et qui aura plus de vie que les ennemis normaux, on pourrait imaginer qu'il aurait une mécanique différente, il aura plusieurs actions possibles, il aura plusieurs têtes et pourra tirer des boules de feu, il faudrait battre chacune de ses têtes pour vaincre le boss.

### Améliorer le chien
Lorsque les ennemis seront trop proches du joueur et que le chien suivra le joueur, il pourra défendre le joueur en attaquant les ennemis

### Le rajout d'un système pour permettre au joueur de créer ses armes
Le joueur pourrait, en accédant à un menu, créer des armes personnalisées en saisissant des informations basiques (sélectionner une photo pour l'arme, entrer la cadence, comment l'arme tire, etc.)

### Avoir une barre de givre dans le 2ème univers
Vu que le joueur sera dans un monde enneigé, même si le personnage aura un sprite adapté à son environnement, on pourrait intégrer une barre qui donne le niveau de givre du personnage, si cette barre est remplie alors, le joueur mourra d'hypothermie. Il y aurait également des ennemis qui pourrait nous tirer des boules de neige dessus, elles nous feraient pas beaucoup de dégâts mais augmenteraient notre barre de givre.
