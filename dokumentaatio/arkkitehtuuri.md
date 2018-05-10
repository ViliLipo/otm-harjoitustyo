# Arkkitehtuurikuvaus

![Luokkakaavio](https://raw.githubusercontent.com/ViliLipo/otm-harjoitustyo/master/dokumentaatio/class_diagram.png)


Ohjelma noudattelee kolmeosaista pakkausrakennetta, ja paketit ilmenevät
luokkakaaviosta.

Pakkaus wellnessapp.ui, sisältää JavaFX:llä toteutetun käyttöliittymän.

## Käyttöliittymä
Käyttöliittymä sisältää neljä erillista ohjainta ja näkymää
- kirjautuminen/uuden käyttäjän luominen
- päänäkymä, joka näyttää käyttäjän tiedot ja ateriat
- uuden aterian luominen.
- käyttäjän tietojen hallinta

Jokainen näistä on toteutettu JavaFXML-tiedoston ja JavaFXControllerin
yhdistelmällä. FXML tiedostot ladataan yksi kerrallaan sovelluksen Stageen.
Sovelluslogiikka on erotettu UI:sta luokkaan WellnessService.

Käyttöliittymän päänäkymän keskiössä on JavaFX.canvas johon piirretään
pylväsdiagrammi käyttäjän noin kuukauden päivittäisistä kaloritavoitteen
täyttymisistä. Kuvan piirtämisestä vastaa luokka wellnessapp.ui.DrawUtil.


## Sovelluslogiikka
Sovelluksen datamallin muodostaa, käyttäjä, ateria, ruoka-aine ja ravinto-aine.
Toiminnallisia kokonaisuuksia on, NutritionalComponentStructure,
FoodItemStructure, ja WellnessService. NutritionalComponentStructure
on singleton, joka vastaa ravintoarvotiedoista. Se perustuu
hajautustauluun. Se hakee tiedot rakentajassaan käyttäen DAO:oa.
FoodItemStructure on singleton joka vastaa ruokaineiden tietojen keräämisestä.
Se hakee tiedot rakentajassaan DAO:sta. Luokka perustuu hajautustauluihin ja
listaan ruoka-aineiden nimistä. WellnessService on luokka joka vastaa
suuremmista toiminnallisista kokonaisuuksista.

Ruoka-aineiden ja ranvinto-aineiden suhteet kuvataan ohjelmassa hajautustaululla
luokassa FoodItem, jossa jokaista ravinto-arvoa vastaa double lukuarvo.
Samantapainen rakenne toistuu Meal-luokassa, jossa sitä käytetään
Aterian ja ruokaineiden suhteen kuvaamiseen.

Käyttäjällä (User) on taas lista omista Aterioistaan(Meal). Tätä listaa
manipuloimalla luokan User-metodeissa tuotetaan käyttöliittymälle relevanttia
dataa. Esimerkiksi lista annettua päivää edeltäneiden seitsemän päivän kaloreista.

## Pysyväistalletus

Pakkauksen wellnessapp.dao luokat huolehtivat tietojen tallettamisesta
sqlite-tietokantaan. Luokat noudattavat data-access-object suunnittelu mallia
ja jokaisen tietotyypin tallettamista varten on määritelty oma rajapinta.

[Tietokantaskeema.](https://github.com/ViliLipo/otm-harjoitustyo/blob/master/wellnessapp/src/main/resources/sqlite/dataBaseSchema.sql)

Tietokanta koostuu kuudesta taulusta, joista FoodNameFi, ComponentValue ja
Component noudattelevat Finelin avoimen datan csv:skeemaa.
Meal, InAMeal ja User käytetään sovelluksessa avoimen käyttäjän yhdistämiseen
ravintoaineiden dataan.

Tietokantayhteys muodostetaan ulkoisena kirjastona asennetun SQLite driverin
avulla. Tietokannan eheydestä huolehtii tietokantamoottori itse, unique
ja foreign-key määrittelyjen avulla. Tietokanta poistaa itse liitostaulut, jos
liitoksen, kummasta vain päästä poistetaan rivi.


## Päätoiminnallisuudet

### Käyttäjän kirjautuminen
Uusi käyttäjä kirjaa kirjautumisruutuun haluamansa nimen ja salasanan ja painaa
nappia uusi käyttäjä. Tämä luo uuden käyttäjän tietokantaan.
Vanha käyttäjä kirjaa tietonsa ja painaa tämän jälkeen nappia kirjaudu.
Molemmat toiminnot päättyvät siihen, että käytttäjän tiedot haetaan tietokannasta
ja siirytään päänäkymään.

### Uuden aterian luominen

![Sekvenssi kaavio uuden ruuan lisäämisestä](https://raw.githubusercontent.com/ViliLipo/otm-harjoitustyo/master/dokumentaatio/sequence_newMeal.png)


Aterian luominen aloitetaan päänäkymästä painamalla nappia "uusi ateria".
Tämä lataa stagelle scenen NewMealMenu. Aterian luomista jatketaan valitsemalla
sille aika. Jos käyttäjän syöte on epäkelpo, ei ohjelma päästä jatkamaan.
Kun aika on syötetty ajan valitsin osio piilotetaan ja aterian ruuanlisäämis-
valikko tuodaan näkyviin. Käyttäjä voi lisätä ateriaan useita ruoka-aineita.
Ravinto-arvot näkyvät valikointi-laatikon alapuolella olevassa tekstikentässä.
Painamalla ok, ateria lisätään käyttäjälle ja kirjataan tietokantaan. Stagelle
ladataan mainMenu. Tämän dokumentin alussa oleva sekvenssikaavio kuvaa
uuden aterian luomista.

### Käyttäjän tietojen päivittäminen
Käyttäjän tietojen päivittäminen alkaa painamalla käyttjän nimeä päävalikossa.
Tämän jälkeen stagelle ladataan UserManagement.fxml. Tässä näkymässä
voidaan muuttaa käyttäjän salasana tai päivittäinen kaloritavoite.

## Ohjelman heikkoudet
### Tietorakenne
Ohjelman tietorakenne pyrkii tehokkuuteen ja nopeaan toimintaan mutta, sen
oikeellinen toiminta on erittäin riippuvainen siihen csv-tiedostoilla syötetyn
ravintoarvo datan oikeellisuudesta. Esim yksittäisten ruoka-aine ravinto-arvo
suhteiden puuttuminen voi pahimmassa tapauksessa estää ohjelman mielekkään
käyttämisen. Ongelma on onneksi rajattu vain siihen ruoka-aineeseen.

Testausta myös monimutkaistaa useat Singleton-patternia noudattavat luokat,
jotka pitää väkisin alustaa testien jälkeen, jotta niiden tilan saa nollattua
testien välissä.

### DAO-Luokat
Useat Dao-luokkien julkisest metodit eivät ilmoita epäonnistumisestaan mitenkään.
Dao-luokan void-metodit voisi refractoroida boolean-metodeiksi, joiden
arvo kuvaa onnistumista. Tai Dao-rajapinnoille voisi määritellä myös oman universaalin
ei SQLite spesifin exceptionin, jota kaikki sen toteuttavat luokat käyttäisivät
virhetilanteiden ilmoittamiseen.

### Testaus
Dao-testien toteutus on hieman kankea koska tietokanta rakennetaan ja puretaan
useita kertoja niiden aikana. Tämä johtaa siihen, että testaamisessa
kuluu erittäin pitkä aika n.30s.
