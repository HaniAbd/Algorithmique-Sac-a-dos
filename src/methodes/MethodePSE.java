package methodes;

import java.util.List;
import java.util.LinkedList;

import sacados.*;

public class MethodePSE implements Methode {

	@Override
	public long resoudre(SacADos sac) {

		List<Objet> objetsPossibles = sac.getObjetsPossibles();
		float poidsMaximal = sac.getPoidsMaximal();

		long tDebut = System.currentTimeMillis();

		TriRapide.triRapide(objetsPossibles, 0, objetsPossibles.size());
		System.out.println(objetsPossibles);
		Feuille racine = new Feuille();
		racine.calculeBorne(objetsPossibles, poidsMaximal);

		// On utilise LinkedList pour pouvoir récupérer facilement la première
		// feuille (comme dans une pile)
		LinkedList<Feuille> arbreFeuilles = new LinkedList<Feuille>();
		arbreFeuilles.push(racine);

		Feuille feuille = new Feuille();
		while (!arbreFeuilles.isEmpty()) {

			// On prends la racine de l'arbre contenant les feuilles
			Feuille feuilleTmp = arbreFeuilles.poll();

			/*
			 * La taille de l'arbre ne doit pas dépasser le nombre d'objets ET
			 * la borne inférieure (valeur la plus petite connue à cet instant)
			 * NE doit PAS dépasser la borne supérieure (valeur la plus grande
			 * connue cet instant) (tout cela augmente la rapidité d'éxec.)
			 */
			if (feuilleAcceptable(feuille, feuilleTmp, objetsPossibles)) {

				// On place en copie la feuille créée pour savoir si on va
				// pouvoir ajouter d'autres objets sans enfreindre les
				// contraintes
				Feuille feuilleSansObjetsAjoutes = new Feuille(feuilleTmp);
				feuilleSansObjetsAjoutes.calculeBorne(objetsPossibles, poidsMaximal);

				if (feuilleSansObjetsAjoutes.getBorneI() > feuille.getBorneS())
					arbreFeuilles.push(feuilleSansObjetsAjoutes);

				// On regarde maintenant ce qu'il se passe si on en ajoute
				Feuille feuilleAvecObjets = new Feuille(feuilleTmp);
				Objet objet = objetsPossibles.get(feuilleTmp.getProfondeur());
				feuilleAvecObjets.ajoutePoids(objet.getPoids());

				float poidsReel = feuilleAvecObjets.getPoids();
				/*
				 * Si le poids maximal n'est pas dépassé après avoir rajouté
				 * l'objet...
				 */
				if (poidsReel <= poidsMaximal
						&& objetNEstPasDejaDansLeSac(sac, feuilleTmp)) {

					// ...on le met dans le sac
					sac.mettreDansLeSac(objetsPossibles.get(feuilleTmp.getProfondeur()));
					feuilleAvecObjets.ajouteBorneS(objet.getValeur());
					feuilleAvecObjets.calculeBorne(objetsPossibles, poidsMaximal);

					/*
					 * Si la valeur de la feuille contenant les objets ajoutes
					 * est plus grande que la borne superieure, alors cela nous
					 * interesse, on peut continuer d'explorer l'arbre
					 */
					if (feuilleAvecObjets.getBorneI() > feuille.getBorneS()) {
						arbreFeuilles.push(feuilleAvecObjets);
					}

					/*
					 * Si la valeur de la feuille actuelle reste plus petite que
					 * la borne superieure (valeur la plus grande connue à cet
					 * instant) alors on l'ajoute au noeud/feuille
					 */
					if (feuille.getBorneS() < feuilleAvecObjets.getBorneS())
						feuille = feuilleAvecObjets;
				}
			}
		}

		long tFin = System.currentTimeMillis();
		return (tFin - tDebut);
	}

	private boolean objetNEstPasDejaDansLeSac(SacADos sac, Feuille feuilleTmp) {
		List<Objet> objetsPossibles = sac.getObjetsPossibles();
		return !sac.getObjetsDansLeSac().contains(objetsPossibles.get(feuilleTmp.getProfondeur()));
	}

	private boolean feuilleAcceptable(Feuille feuille, Feuille feuilleTmp, List<Objet> objetsPossibles) {
		return (feuilleTmp.getProfondeur() < objetsPossibles.size() - 1)
				&& (feuilleTmp.getBorneI() > feuille.getBorneS());
	}

	public String toString() {
		return "Méthode PSE";
	}
}
