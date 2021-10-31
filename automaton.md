# Automates
Ce fichier contient les automates des différentes entités du projet.

# Sommaire

1. [Arme](#arme)
2. [Balle](#balle)
3. [Ennemi](#ennemi)

## Arme
    Arme(chargée){
    	* (chargée)			
    	| armeVide() ? armeNonChargée 	: Pop()
    	| Key(BUTTON1) ? True	  	: Egg(Balle) 
    	* (Pop)
    	| True ?			: chargée()
    }


## Balle

    Balle(init){
    	* (init)
    	| rencontreEnnemi() ? True	: explode()
    	| True ?			: move(F)	
    }
## Ennemi

    Ennemi(init) {
    	* (init)
    	| ballePercute() ? True		: Pop()
    	| mort() ? True			: meurt()  
    	| Closest(Joueur, T) ? True 	: tirer()
    	| True ? 			: suivreJoueur()
    	* (meurt)
    	| True ? 10%Egg(Arme) 		:explode()
    	* (suivreJoueur)
    	|
    	* (Pop)
    	| True ? 			: animationDegat()	
    }
