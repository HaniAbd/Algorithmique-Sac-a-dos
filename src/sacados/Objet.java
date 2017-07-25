package sacados;

public class Objet implements Comparable<Objet> {
	private String nom;
	private float poids;
	private float borneSuperieure;
	private float rapport;

	public Objet(String nom, float poids, float valeur) {
		this.nom = nom;
		this.poids = poids;
		this.borneSuperieure = valeur;
	}

	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(nom + " de " + poids + "kg " + "et de valeur " + borneSuperieure + "\n");
		return str.toString();
	}

	public void calculeRapportValeurPoids() {
		this.rapport = this.borneSuperieure / this.poids;
	}

	public float getPoids() {
		return this.poids;
	}

	public float getRapport() {
		return this.rapport;
	}

	public float getValeur() {
		return this.borneSuperieure;
	}

	@Override
	public int compareTo(Objet o) {
		this.calculeRapportValeurPoids();
		o.calculeRapportValeurPoids();
		if(this.rapport>o.getRapport()) return 1;
		if(this.rapport<o.getRapport()) return -1;
		return 0;
	}
}
