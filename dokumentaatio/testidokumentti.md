# Testidokumentti
## Yksikkötestaus
Wellnessapp-sovelluksen yksikkötestaus on toteutettu käyttäen JUnit, yksikkötestejä.
Näiden rivikattavuus on noin 85%.

![coverage](https://raw.githubusercontent.com/ViliLipo/otm-harjoitustyo/master/dokumentaatio/coverage.png)

Käyttöliittymää ei ole huomioitu yksikkötestauksessa tai kattavuudessa.
### Sovelluslogiikka
 NutritionalComponent-luokan toiminnallisuudet testataan luokassa, NutritionalComponentTest,
 FoodItem-luokan toiminnallisuudet testataan luokassa FoodItemTest ja Meal-luokan toiminnallisuudet
 luokassa MealTest.

### DAO-luokat
Luokille MealDaoSqlite3, FoodItemDaoSqlite3 ja NutritionalComponentDaoSqlite3
on omat testiluokat, joissa käytetetään tiedostona tilapäisiä tietokantoja,
jotka eivät ole yhteydessä sovelluksen oikeaan tietokantaan. UserDaoSqlite3
testataan vain integraatiotason testeissä.

## Integraatiotestaus
Sovelluksen integraatiotestaus tapahtuu, pääosin luokissa UserTest sekä,
WellnessServiceTest. Luokassa WellnessServiceTest lähes koko ohjelman
perustoiminnallisuudet kokeillaan. Luokassa UserTest testataan
Loogisen rakenteen kannalta monimutkaisimmat osat.
Myöskin paketin tools luokat testattaan vain integraatiotasolla, sillä
ne ovat suuren refraktoroinnin tarpeessa.

Luokat NutritionalComponentStructure ja FoodItemStructure testataan
vain integraatiotasolla, koska ne ovat käytännössä vain kokoelmia Javan
tarjoamista tietorakenteista, eikä niiden toiminnalisuudet ole monimutkaisia.
### Huomio JUnit-testeistä
Koska integraatiotason testeissä sovelluksen tietorakenne kasataan alusta
uudelleen jokaiselle testiluokalle, testit kestävät turhan kauan.
Tulevaisuudessa näille luokille voisi rakentaa jonkinlaisen MOCK-tietorakenteen.
## Järjestelmätestaus

Järjestelmätestaus suoritettiin käsin seuraavalla testipatteristolla. 1 onnistui
0 epäonnistui.

|Testitapaus| Tulos| Huomioita|
|:---------| :-:| :--------:|
|Uuden käyttäjän voi luoda| 1| |
|Kirjautuminen onnistuu|1 | |
|Salasanan vaihtaminen ei onnistu jos vahvistus ei täsmää|1 | |
|Salasanan vaihtaminen onnistuu normaalisti |1 | |
|Salasanan vaihdon jälkeen kirjautuminen onnistuu uudella salasanalla|1 | |
|Salasanan vaihdon jälkeen kirjautuminen ei onnistu vanhalla salasanalla |1 |  |
|Piirtoalusta ei hajoa vetimen "apinoimisella"| 1 |  |
| Kuvan skaala muuttuu kun käyttäjän tavoite muuttuu| 1| |
| Kaikki määritellyt näkymät ovat avattavissa| 1 | Käyttäjänhallinta, uusi ateria|
| Aikaa ei voi antaa uudelle aterialle väärässä formaatissa| 1 | |
| "nolla" filtteri ei aiheuta virhettä|1 | Viimeisen löytyneen aterian tiedot jäävät esiin|
| Tyhjää ateriaa ei voi lisätä|1 | Tyhjää ateriaa ei lisätä mutta käyttäjä ei saa siitä ilmoitusta.|
|Aterian voi poistaa| 1| |
| Poistettu ateria häviää myös tietokannasta|1 | Tarkistettiin uudelleen kirjautumisella|
|Poistetun aterian yhteydet poistuvat tietokannasta| 1 | Tarkastettiin SQLite sovelluksella|

Nämä kaikki testit suoritettiin onnistuneesti.

# Asennus
Sovellusta testattiin tilasta, jossa tietokanta ei ollut suorituksen alussa
olemassa. Sovelluksen alustustoimenpiteet toimivat moitteitta.
Debian paketti toimi moitteetta kahdella laitteella.

# Sovellukseen jääneet laatuongelmat
Sovellus ei toimi kovinkaan järkevästi, jos jokin muu toimija sabotoi tietokantatiedostoa
mm. muuttamalla sen oikeuksia, poistamalla tai lukitsemalla sen. Sovellus ei kaadu
mutta suoltaa virheilmoituksia. Virheilmoitukset on kuitenkin ohjattu käyttäjä
ystävälliseen ikkunaan. Siihen käytetään luokkaa fi.otm.wellnessapp.ui.FxLogHandler.

Sovellus tuottaa konsoliin kaksi epämieluisaa riviä, jos se käynnistetään
Linux-järjestelmällä, jossa ei ole asennettuna "adwaita" teemaa. Varoitukset ovat
lähtöisin tämän projektin ulkopuolisesta koodista. Ne voisi raa'asti poistaa
ohjaamalla kaiken outputin null pipeen, mutta se lienee turhaa.
