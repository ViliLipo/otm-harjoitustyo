# Arkkitehtuurikuvaus

![Luokkakaavio](https://github.com/ViliLipo/otm-harjoitustyo/blob/master/dokumentaatio/class_diagram.png)



Ohjelma noudattelee kolmeosaista pakkausrakennetta, ja paketit ilmenevät
luokkakaaviosta.

Pakkaus wellnessapp.ui, sisältää JavaFX:llä toteutetun käyttöliittymän
muun sovelluksen tietorakenteen manipuloimiseen.

## Käyttöliittymä
Käyttöliittymä sisältää kolme erillista ohjainta ja näkymää
- kirjautuminen/uuden käyttäjän luominen
- päänäkymä, joka näyttää käyttäjän tiedot ja ateriat
- uuden aterian luominen.

Jokainen näistä on toteutettu JavaFXML-tiedoston ja JavaFXControllerin
yhdistelmällä. FXML tiedostot ladataan yksi kerrallaan sovelluksen Stageen.
Sovelluslogiikka on erotettu UI:sta luokkaan WellnessService.

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

## Pysyväistalletus

Pakkauksen wellnessapp.dao luokat huolehtivat tietojen tallettamisesta
sqlite-tietokantaan. Luokat noudattavat data-access-object suunnittelu mallia
ja jokaisen tietotyypin tallettamista varten on määrietty oma rajapinta.
Tietokannan skeema ja muut määrittelyt täydennetään tänne myöhemmin.
