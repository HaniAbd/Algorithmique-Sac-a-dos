package methodes;

import java.util.List;

import sacados.Objet;

public class TriRapide {
	
	private static void echanger(List<Objet> tab, int a, int b) {
		Objet tmp = tab.get(a);
		tab.set(a, tab.get(b));
		tab.set(b, tmp);
	}

	public static void triRapide(List<Objet> tab, int premier, int dernier) {
		
		for (Objet objet : tab) {
			objet.calculeRapportValeurPoids();
		}

		int j = premier + 1;
		int pivot = (premier + dernier) / 2;

		if (premier < dernier) {

			echanger(tab, premier, pivot);
			pivot = premier;

			for (int i = premier + 1; i <= dernier - 1; i++) {
				if (tab.get(i).getRapport() > tab.get(pivot).getRapport()) {
					echanger(tab, i, j);
					j += 1;
				}
			}

			echanger(tab, pivot, j - 1);
			pivot = j - 1;

			triRapide(tab, premier, pivot - 1);
			triRapide(tab, pivot + 1, dernier);
		}
		
	}
}
