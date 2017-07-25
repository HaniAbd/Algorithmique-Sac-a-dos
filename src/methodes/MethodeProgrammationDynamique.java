package methodes;

import java.util.List;

import sacados.Methode;
import sacados.Objet;
import sacados.SacADos;

public class MethodeProgrammationDynamique implements Methode {

	@Override
	public long resoudre(SacADos sac) {
		List<Objet> objetsPossibles = sac.getObjetsPossibles();
		float poidsMaximal = sac.getPoidsMaximal();
		// On établi d'abord la précision en nombre de chiffre après la virgule
		// pour pouvoir les stocker dans l'index du tableau
		int poidsMaxiConverti = conversionFloatInteger(poidsMaximal, objetsPossibles);
		int coefMultiplicateur = coefMultiplicateur(objetsPossibles);

		long tDebut = System.currentTimeMillis();

		float[][] T = new float[objetsPossibles.size()][poidsMaxiConverti + 1];

		for (int p = 0; p <= poidsMaxiConverti; p++) {
			if (objetsPossibles.get(0).getPoids() * coefMultiplicateur > p)
				T[0][p] = 0;
			else
				T[0][p] = objetsPossibles.get(0).getValeur();
		}
		for (int i = 1; i < objetsPossibles.size(); i++) {
			for (int p = 0; p <= poidsMaxiConverti; p++) {
				if (p >= objetsPossibles.get(i).getPoids() * coefMultiplicateur)
					T[i][p] = Math.max(T[i - 1][p],
							T[i - 1][(int) (p - objetsPossibles.get(i).getPoids() * coefMultiplicateur)]
									+ objetsPossibles.get(i).getValeur());
				else
					T[i][p] = T[i - 1][p];
			}
		}

		int n = objetsPossibles.size() - 1;
		int p = poidsMaxiConverti;

		// On décale l'indice vers la gauche, tant que la valeur de l'ensemble
		// est au plus haut
		while (p >= 0 && T[n][p] == T[n][p - 1]) {
			p--;
		}

		while (p > 0) {
			while (n > 0 && T[n][p] == T[n - 1][p])
				n--;
			p = (int) (p - objetsPossibles.get(n).getPoids() * coefMultiplicateur);
			if (p >= 0) {
				sac.mettreDansLeSac(objetsPossibles.get(n));
			}
			n--;
		}

		long tFin = System.currentTimeMillis();
		return (tFin - tDebut);
	}

	/**
	 * Fonction qui converti le nombre flottant passé en paramètre, en String
	 * pour déterminer le nombre de chiffre après la virgule qui le compose
	 * 
	 * @param n
	 * @return
	 */
	private static int nombreDeChiffreAprèsLaVirgule(float n) {
		String str = String.valueOf(n);
		int positionVirgule = str.indexOf(".") + 1;
		String partieDecimale = str.substring(positionVirgule, str.length());

		// On supprime les zéros inutiles = qui se trouvent à la fin du nombre
		while (partieDecimale.endsWith("0"))
			partieDecimale = str.substring(positionVirgule++, str.length());
		return partieDecimale.length();
	}

	private int coefMultiplicateur(List<Objet> objetsPossibles) {
		int maximumActuel = 0; // Nombre de chiffre après la virgule par défaut
		for (Objet o : objetsPossibles) {
			maximumActuel = Math.max(maximumActuel, nombreDeChiffreAprèsLaVirgule(o.getPoids()));
		}
		if (maximumActuel == 0)
			return 1;
		return (int) Math.pow(10, maximumActuel);
	}

	private int conversionFloatInteger(float poidsMaximal, List<Objet> objetsPossibles) {
		return (int) poidsMaximal * coefMultiplicateur(objetsPossibles);
	}

	public String toString() {
		return "Méthode par Programmation dynamique";
	}

}
