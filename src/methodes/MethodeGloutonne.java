package methodes;

import java.util.List;

import sacados.Methode;
import sacados.Objet;
import sacados.SacADos;

public class MethodeGloutonne implements Methode {

	@Override
	public long resoudre(SacADos sac) {

		List<Objet> objetsPossibles = sac.getObjetsPossibles();
		float poidsMaximal = sac.getPoidsMaximal();

		long tDebut = System.currentTimeMillis();

		TriRapide.triRapide(objetsPossibles, 0, objetsPossibles.size());

		for (Objet objet : objetsPossibles) {

			if (sac.getPoidsReel() + objet.getPoids() <= poidsMaximal)

				sac.mettreDansLeSac(objet);
		}

		long tFin = System.currentTimeMillis();
		return (tFin - tDebut);
	}

	public String toString() {
		return "Méthode Gloutonne";
	}

}
