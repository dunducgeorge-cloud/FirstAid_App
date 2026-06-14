# Proiect-DSDM
First Aid Helper este o aplicație Android pentru gestionarea situațiilor de urgență, ce combină educația cu utilitatea practică. Proiectul este structurat pe patru piloni principali:

 1. Ghid de Prim Ajutor 
O bibliotecă digitală intuitivă ce oferă instrucțiuni pas cu pas, structurate algoritmic, pentru intervenții rapide în caz de urgențe medicale majore (Arsuri, Leșin, Fracturi, Înecare, Infarct, Insolație). Informațiile sunt stocate local, garantând accesul instantaneu în orice condiții, chiar și în zone fără acoperire GSM sau internet.

2. Triaj Rapid și Evaluare Primară
Un sistem de asistență interactiv bazat pe decision tree care ghidează utilizatorul printr-un set de întrebări binare (DA/NU) pentru evaluarea semnelor vitale ale victimei (conștiență, respirație, sângerări masive). Modulul generează instant recomandarea potrivită: monitorizarea victimei sau declanșarea protocolului de alertă maximă.

3. Profil Medical de Urgență (ICE - In Case of Emergency)
O secțiune securizată ce permite gestionarea de profile medicale multiple (nume, vârstă, alergii sau boli cronice) asociate contului de utilizator. Persistența datelor este asigurată local prin serializare JSON (Gson) și stocare securizată în SharedPreferences, respectând confidențialitatea datelor biologice.

4. Modul de Urgență și Integrare Servicii
Include un buton flotant (FAB) de apel rapid ce interfațează direct cu dialer-ul sistemului de operare pentru apelarea numărului unic de urgență 112. De asemenea, modulul oferă o funcție de geolocație prin Implicit Intents, ce deschide hărțile native pentru identificarea imediată a punctelor farmaceutice din proximitate.
