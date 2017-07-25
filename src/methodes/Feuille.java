package methodes;

import java.util.List;
import sacados.Objet;

public class Feuille {
	private float poids;
	private float borneInferieure;
	private float borneSuperieure;
	private int profondeur;

	public String toString() {
		return "sup="+borneSuperieure+"|inf="+borneInferieure+"|prof="+profondeur+"|poids="+poids+"|objets=";
	}
	
	public Feuille() {
	}

	public Feuille(Feuille mere) {
		this.poids = mere.poids;
		this.borneInferieure = mere.borneInferieure;
		this.borneSuperieure = mere.borneSuperieure;
		this.profondeur = mere.profondeur + 1;
	}

	public void calculeBorne(List<Objet> objets, float poidsMaximal) {
		float poidsReel = poids;
		this.borneInferieure = this.borneSuperieure;
		for (int n = this.profondeur; n <= objets.size() - 1; ++n) {
			Objet objet = objets.get(n);
			objet.calculeRapportValeurPoids();
			if (poidsReel + objet.getPoids() > poidsMaximal) {
				this.borneInferieure += (poidsMaximal - poidsReel) * (objet.getRapport());
				break;
			}
			poidsReel += objet.getPoids();
			this.borneInferieure += objet.getValeur();
		}
	}

	public int getProfondeur() {
		return this.profondeur;
	}

	public float getBorneI() {
		return this.borneInferieure;
	}

	public float getBorneS() {
		return this.borneSuperieure;
	}

	public float getPoids() {
		return this.poids;
	}

	public void ajoutePoids(float p) {
		this.poids += p;
	}

	public void ajouteBorneS(float valeur) {
		this.borneSuperieure += valeur;
	}

}
