# Arkkitehtuurikuvaus

![Luokkakaavio](https://raw.githubusercontent.com/ViliLipo/otm-harjoitustyo/master/dokumentaatio/class_diagram.png)

![Sekvenssi kaavio uuden ruuan lisäämisestä](https://raw.githubusercontent.com/ViliLipo/otm-harjoitustyo/master/dokumentaatio/sequence_newMeal.png)

Ohjelma noudattelee kolmeosaista pakkausrakennetta, ja paketit ilmenevät
luokkakaaviosta.

Pakkaus wellnessapp.ui, sisältää JavaFX:llä toteutetun käyttöliittymän
muun sovelluksen tietorakenteen manipuloimiseen.

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
listaan ruokaaineiden nimistä. WellnessService on luokka joka vastaa
suuremmista toiminnallisista kokonaisuuksista.

Ruoka-aineiden ja ranvinto-aineiden suhteet kuvataan ohjelmassa hajautustaululla
luokassa FoodItem, jossa jokaista ravinto-arvoa vastaa double lukuarvo.
Samantapainen rakenne toistuu Meal-luokassa, jossa sitä käytetään
Aterian ja ruokaineiden suhteen kuvaamiseen.

## Pysyväistalletus

Pakkauksen wellnessapp.dao luokat huolehtivat tietojen tallettamisesta
sqlite-tietokantaan. Luokat noudattavat data-access-object suunnittelu mallia
ja jokaisen tietotyypin tallettamista varten on määrietty oma rajapinta.

[Tietokantaskeema.](https://github.com/ViliLipo/otm-harjoitustyo/blob/master/wellnessapp/src/main/resources/sqlite/dataBaseSchema.sqlite3)

Tietokanta koostuu kuudesta taulusta, joista FoodNameFi, ComponentValue ja
Component noudattelee Finelin avoimen datan csv:skeemaa.
Meal, InAMeal ja User käytetään sovelluksessa avoimen käyttäjän yhdistämiseen
ravintoaineiden dataan.


## Päätoiminnallisuudet

### Käyttäjän kirjautuminen
Uusi käyttäjä kirjaa kirjautumisruutuun haluamansa nimen ja salasanan ja painaa
nappia uusi käyttäjä. Tämä luo uuden käyttäjän tietokantaan.
Vanha käyttäjä kirjaa tietonsa ja painaa tämän jälkeen nappia kirjaudu.
Molemmat toiminnot päättyvät siihen, että käytttäjän tiedot haetaan tietokannasta
ja siirytään päänäkymään.

### Uuden aterian luominen
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

### Testaus
Dao-testien toteutus on hieman kankea koska tietokanta rakennetaan ja puretaan
useita kertoja niiden aikana. Tämä johtaa siihen, että testaamisessa
kuluu erittäin pitkä aika n.30s.
