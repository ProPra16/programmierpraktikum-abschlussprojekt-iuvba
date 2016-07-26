<img src="https://travis-ci.org/ProPra16/programmierpraktikum-abschlussprojekt-iuvba.svg" alt="build:started">

Zum Bauen der Gradle Task wurde "Build.Gradle" verwendet.
Ausführen des Programms:

Mit Terminal zu MenuGUI navigieren. Eingeben von:

javac MenuGUI.java
java MenuGUI




Eigentlich hatten wir versucht ein jar-File zu erstellen. 
Und dann: 

-> Navigation mit der Konsole zum jar-File (TDD Trainer.jar)
-> Dann in der Konsole den Befehl „java -jar TDD Trainer.jar“ eingeben


Wir bekommen hier den Fehler:

kein Hauptmanifestattribut

Bei dem Versuch den Fehler zu beheben stießen wir auf den Bug:

http://bugs.java.com/bugdatabase/view_bug.do?bug_id=8021205

welcher durch ein Update behoben werden sollte, aber leider nicht behoben wird.