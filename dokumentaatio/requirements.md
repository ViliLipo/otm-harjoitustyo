# The WellnessApp
# Vaatimusmäärittely
The WellnessApp on sovellus, jonka avulla voi seurata ruuasta saatavia
ravintoarvoja. Sovelluksen ensimmäinen versio kattaa ainoastaan energian seurannan,
mutta sen tietorakenne mahdollistaa muiden seurannan lisäämisen helposti.
## Featurelist


 - Käyttäjä
    - Voi luoda tunnuksen
    - Voi lisätä itselleen päivittäisen kaloritavoitteen
    - Voi lisätä itselleen ruokailutapahtuman
    - Ruokailutapahtumaan voi lisätä ruoka-aineita
    - Ruoka-aineiden tietoja voi tarkastella
  - Suunnitellut toiminnallisuudet
      - Laskee päivittäisen kalorien sisään oton ja muut ravintoarvot
        ja vertaa niitä viittellisiin arvoihin ja näyttää statistiikkaa.
      - Piirtää pylväsdiagrammin viikon tavoitteiden toteutumisesta
      - Lataa ruoka-aineiden tiedot finellin avoimesta datasta.
  - Rajoitteet
    - Ohjelmisto toimii Linux-ympäristössä ja OSX-käyttöjärjestelmillä.
    - Käyttäjien tiedot tallennetaan paikalliseen SQLITE tietokantaan(toistaiseksi)

# Jatkokehitysideoita
- Muiden ravintoaineiden esim. proteiinin saannin seuranta.
- Liikuntasuorituksien ja niiden kulutuksen seuranta.
- Tietokanta palvelimelle.
