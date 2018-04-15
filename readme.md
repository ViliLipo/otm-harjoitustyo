## WellnessApp
### Dokumentaatio
- [Vaatimusmäärittely](https://github.com/ViliLipo/otm-harjoitustyo/blob/master/requirements.md)
- [Työaikakirjanpito](https://github.com/ViliLipo/otm-harjoitustyo/blob/master/tyoaikakirjanpito.md)

WellnessApp on kursille Ohjelmistotekniikan menetelmät laadittu harjoitustyo.
Sovelluksen tarkoituksena on auttaa käyttäjäänsä ruokavalion seuraamisessa.
Sovelluksen ravintoaineiden tiedot ovat osa Finellin avointa dataa.
Viikko3 kohdalla tietokantarajapinta sekä kirjautuminen toimiigit .
Tietokantarajapinnan yksikkötestit on aloitettu.
Projektin tietokantarajapinta on hieman paisunut ja sen takia toiminnallisuudet
laahaavat jäljessä. Tällä hetkellä voi kirjautua, luoda tunnuksen ja tarkastella
ruoka-aineiden ravintoarvoja.

### Komentoja
- Testikattavuus $ mvn test jacoco:report
- Paketointi $ mvn package
- Rivimäärä  $ find . -name "*.java" | xargs wc -l
