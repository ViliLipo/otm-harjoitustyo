# Käyttöohje
Lataa WellnessApp, joko -jar tiedostona tai .deb pakettina
releasesta [release](https://github.com/ViliLipo/otm-harjoitustyo/releases). Huomioi että .deb
vaatii root-oikeudet asennukseen. Debian-paketit toimivat Debian-pohjaisissa
Linux-järjestelmissä.
.deb paketti asennetaan komennolla
```
sudo apt install ./WellnessApp.deb
```
Huomioi, että ohjelman pysyväistalletus tapahtuu kansioon
```
~/.WellnessApp/
```
Joten jos et halua että tämä jää tietokoneellesi kokeilun jälkeen poista se
komennolla
```
rm -r ~/.WellnessApp/
```
## Ohjelman käynnistäminen
Jos latasit -jar tiedoston mene latauskohteeseen ja käynnistä ohjelma
komennolla
 ```
 java -jar wellnessapp.jar
 ```
Jos taas asensit .deb paketin ohjelman tulisi käynnistyä komennolla
`wellnessapp`

## Kirjautuminen
![kirjautuminen](https://raw.githubusercontent.com/ViliLipo/otm-harjoitustyo/master/dokumentaatio/Login_screen.png)

 Ohjelma käynnistyy kirjautumis ruutuun. Uuden käyttäjän voi luoda
 täyttämällä kentät halutulla nimellä ja salasanalla ja painamalla
 "uusi käyttäjä". Kirjautuminen tapahtuu napilla kirjaudu.

## Päänäkymä
![päänäkymä](https://raw.githubusercontent.com/ViliLipo/otm-harjoitustyo/master/dokumentaatio/main_menu.png)

Kirjautumisen jälkeen olet päänäkymässä. Keskellä on pylväsdiagrammi
päivittäisestä energiasaannista kilokaloreissa. Siinä näkyvät pylväät
ovat vihreitä, jos päivän arvo on tarpeeksi lähellä tavoitetta, oranssi, jos
tavoite on hieman liian kaukana ja punainen jos tavoite on alitettu tai
ylitetty reilusti. Päänäkymän listaruudusta voi klikata rivejä, jolloin
näytetään tarkempia tietoja kyseisestä ateriasta.

## Aterian lisääminen
![aterian_lisääminen](https://raw.githubusercontent.com/ViliLipo/otm-harjoitustyo/master/dokumentaatio/adding_food.png)

Aterian lisäämisen voi aloittaa painamalla "uusi ateria".
Tämän jälkeen ilmestyvässä näkymässä valitaan kellonaika ja päivämäärä
aterialle. Tämän jälkeen päästään valikkoon, jossa ateriaan lisätään ruoka-aineita.
Ruoka-aine valitaan pudotusvalikosta, sitten määrä kenttään syötetään
määrä grammoina ja lopuksi ruoka lisätään ateriaan painamalla lisää.
Virheellisen ruuan voi poistaa klikkaamalla sitä laatikosta ja painamalla
"poista". Kun olet lisännyt haluamasi ruoka-aineet paina ok ja palaat
päävalikkoon.

## Käyttäjän hallinta
![käyttäjän hallinta](https://raw.githubusercontent.com/ViliLipo/otm-harjoitustyo/master/dokumentaatio/user_menu.png)

Voit siirtyä käyttäjän hallintaan painamalla käyttäjänimestäsi päävalikossa.
Tässä näkymässä voit vaihtaa salasanasi ja päivittäisen kaloritarpeesi.
