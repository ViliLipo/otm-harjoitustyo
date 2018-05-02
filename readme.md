## WellnessApp
### Dokumentaatio
- [Vaatimusmäärittely](https://github.com/ViliLipo/otm-harjoitustyo/blob/master/requirements.md)
- [Työaikakirjanpito](https://github.com/ViliLipo/otm-harjoitustyo/blob/master/tyoaikakirjanpito.md)
- [Arkkitehtuurikuvaus](https://github.com/ViliLipo/otm-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)
- [Käyttöohje](https://github.com/ViliLipo/otm-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md)
### Esittely
WellnessApp on kursille Ohjelmistotekniikan menetelmät laadittu harjoitustyo.
Sovelluksen tarkoituksena on auttaa käyttäjäänsä ruokavalion seuraamisessa.
Sovelluksen ravintoaineiden tiedot ovat osa Finellin avointa dataa.
### Status viikko 5

Tällä hetkellä voi kirjautua, luoda tunnuksen ja tarkastella
ruoka-aineiden ravintoarvoja. Aterioita voi lisätä käyttäjälle. Sovellus
piirtää pylväsdiagrammin käyttäjän edellisen kuukauden päivittäisistä kaloreista.


### Komentoja
- Testikattavuus
```
 $ mvn test jacoco:report
 ```
- Paketointi
```
 $ mvn package
 ```
- Rivimäärä
```
$ find . -name  '.java' | xargs wc -l
```
- Checkstyle
```
$ mvn jxr:jxr checkstyle:checkstyle
 ```
### Julkaisut
[wellnessapp0.1.0](https://github.com/ViliLipo/otm-harjoitustyo/releases/tag/0.1.0)
