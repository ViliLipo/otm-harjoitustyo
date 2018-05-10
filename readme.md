## WellnessApp
### Dokumentaatio
- [Vaatimusmäärittely](https://github.com/ViliLipo/otm-harjoitustyo/blob/master/dokumentaatio/requirements.md)
- [Työaikakirjanpito](https://github.com/ViliLipo/otm-harjoitustyo/blob/master/tyoaikakirjanpito.md)
- [Arkkitehtuurikuvaus](https://github.com/ViliLipo/otm-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)
- [Käyttöohje](https://github.com/ViliLipo/otm-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md)
- [Testaus](https://github.com/ViliLipo/otm-harjoitustyo/blob/master/dokumentaatio/testidokumentti.md)

### Esittely
WellnessApp on kursille Ohjelmistotekniikan menetelmät laadittu harjoitustyo.
Sovelluksen tarkoituksena on auttaa käyttäjäänsä ruokavalion seuraamisessa.
Sovelluksen ravintoaineiden tiedot ovat osa Finellin avointa dataa.

### Kurssin Loppustatus
Sovellus täyttää määrittelydokumentin vaatimukset. Sovelluksella on korkea
testikattavuus, sekä sen luokkien julkiset rajapinnat on dokumentoitu
JavaDoc kommenteilla.
Testeihin kuluu liian pitkä aika ja DAO-testejä tulisi optimoida. Tällä hetkellä
testipatterin suorittamiseen voi kulua jopa 30s.

Ohjelmaan on extrana toteutettu asennusta varten Debian paketti, joka
toimii Debian pohjaisilla Linux-järjestelmillä kuten Ubuntulla.

Sovellusta pitkään vaivasi, että testit eivät jostain Sqliteen tai tiedostoihin
liittyvästä syystä menneet läpi Windows alustalla. Nyt ainakin
NetBeans saa ohjelman testit läpi myös Windowsilla.


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
- JavaDoc
```
$ mvn javadoc:javadoc
```
### Julkaisut
[wellnessapp0.1.0](https://github.com/ViliLipo/otm-harjoitustyo/releases/tag/0.1.0)

[WellnessApp.0.1.1](https://github.com/ViliLipo/otm-harjoitustyo/releases/tag/0.1.1)

[Wellnessapp.0.1.2](https://github.com/ViliLipo/otm-harjoitustyo/releases/tag/0.1.2)
