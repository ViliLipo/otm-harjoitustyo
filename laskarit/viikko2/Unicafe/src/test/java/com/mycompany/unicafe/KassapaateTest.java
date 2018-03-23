/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.unicafe;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author vili
 */
public class KassapaateTest {
    Kassapaate kp;
    
    public KassapaateTest() {
    }
    
    @Before
    public void setUp() {
        kp = new Kassapaate();
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void constructorWorksCorrectly(){
        assertEquals(100000, kp.kassassaRahaa());
        assertEquals(0, kp.edullisiaLounaitaMyyty());
        assertEquals(0, kp.maukkaitaLounaitaMyyty());
    }

    /**
     * Test of syoEdullisesti method, of class Kassapaate.
     */
    @Test
    public void testSyoEdullisesti_int_kassa_kasvaa() {
        System.out.println("syoEdullisesti");
        assertEquals(1, kp.syoEdullisesti(241));
        assertEquals(100240, kp.kassassaRahaa());
    }
    
    @Test
    public void testSyoEdullisesti_int_lounaat_kasvaa() {
        assertEquals(1, kp.syoEdullisesti(241));
        assertEquals(1, kp.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void testSyoEdullisest_int_liian_vahan_rahaa() {
        assertEquals(230, kp.syoEdullisesti(230));
        assertEquals(0, kp.edullisiaLounaitaMyyty());
        assertEquals(100000, kp.kassassaRahaa());
    }
    

    /**
     * Test of syoMaukkaasti method, of class Kassapaate.
     */
    @Test
    public void testSyoMaukkaasti_int() {
        System.out.println("syoMaukkaasti");
        assertEquals(1, kp.syoMaukkaasti(401));
        assertEquals(100400, kp.kassassaRahaa());
        assertEquals(1, kp.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void testSyoMaukkaasti_int_liian_vahan_rahaa() {
        assertEquals(380, kp.syoMaukkaasti(380));
        assertEquals(100000, kp.kassassaRahaa());
        assertEquals(0, kp.maukkaitaLounaitaMyyty());
    }

    /**
     * Test of syoEdullisesti method, of class Kassapaate.
     */
    @Test
    public void testSyoEdullisesti_Maksukortti() {
        Maksukortti kortti = new Maksukortti(240);
        assertTrue(kp.syoEdullisesti(kortti));
        assertEquals(1, kp.edullisiaLounaitaMyyty());
        assertEquals(100000, kp.kassassaRahaa());
        assertEquals(0, kortti.saldo());
    }
    @Test
    public void testSyoEdullisesti_Maksukortti_liian_vahan_rahaa() {
        Maksukortti kortti = new Maksukortti(230);
        assertFalse(kp.syoEdullisesti(kortti));
        assertEquals(0, kp.edullisiaLounaitaMyyty());
        assertEquals(100000, kp.kassassaRahaa());
        assertEquals(230, kortti.saldo());
    }

    /**
     * Test of syoMaukkaasti method, of class Kassapaate.
     */
    @Test
    public void testSyoMaukkaasti_Maksukortti() {
        System.out.println("syoMaukkaasti");
        Maksukortti kortti = new Maksukortti(500);
        assertTrue(kp.syoMaukkaasti(kortti));
        assertEquals(1, kp.maukkaitaLounaitaMyyty());
        assertEquals(100000, kp.kassassaRahaa());
        assertEquals(100, kortti.saldo());
    }
    
    @Test
    public void testSyoMaukkaasti_Maksukortti_liian_vahan_rahaa() {
        Maksukortti kortti = new Maksukortti(300);
        assertFalse(kp.syoMaukkaasti(kortti));
        assertEquals(0, kp.maukkaitaLounaitaMyyty());
        assertEquals(100000, kp.kassassaRahaa());
        assertEquals(300, kortti.saldo());
    }

    /**
     * Test of lataaRahaaKortille method, of class Kassapaate.
     */
    @Test
    public void testLataaRahaaKortille() {
        System.out.println("lataaRahaaKortille");
        Maksukortti kortti = new Maksukortti(0);
        kp.lataaRahaaKortille(kortti, 1000);
        assertEquals(101000, kp.kassassaRahaa());
        assertEquals(1000, kortti.saldo());
    }
    @Test
    public void testLataaRahaaKortille_invalid_sum() {
        Maksukortti kortti  = new Maksukortti(0);
        kp.lataaRahaaKortille(kortti, -1);
        assertEquals(100000, kp.kassassaRahaa());
        assertEquals(0, kortti.saldo());
    }
    
}
