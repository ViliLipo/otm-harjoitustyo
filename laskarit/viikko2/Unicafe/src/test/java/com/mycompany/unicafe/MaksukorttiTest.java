package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void saldoCorrectOnSetup() {
        assertEquals(10, kortti.saldo());
    }
    
    @Test
    public void addingMoneyWorksCorrectly() {
        kortti.lataaRahaa(10);
        assertEquals(20, kortti.saldo());
    }
    
    @Test
    public void moneyIsTakenIfBalanceIsOkay() {
        kortti.otaRahaa(9);
        assertEquals(1, kortti.saldo());
    }
    
    @Test
    public void moneyIsNotTakenIfBalanceIsNotOkay() {
        kortti.otaRahaa(11);
        assertEquals(10, kortti.saldo());
    }
    
    @Test
    public void otaRahaaReturnValuesAreCorrect() {
        assertEquals(false, kortti.otaRahaa(11));
        assertTrue(kortti.otaRahaa(9));
    }
    @Test
    public void toStringIsCorrect() {
        assertEquals("saldo: 0.10", kortti.toString());
        kortti.lataaRahaa(100);
        assertEquals("saldo: 1.10", kortti.toString());
    }
}
