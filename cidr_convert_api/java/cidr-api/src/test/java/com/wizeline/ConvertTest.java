package com.wizeline;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class ConvertTest extends TestCase
{
    /**
     * Create the test case
     *
     * @param testConvertClass name of the test case
     */
    public ConvertTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ConvertTest.class );
    }

    public void testValidCidrToMask()
    {
      assertEquals("128.0.0.0", Convert.cidrToMask("1"));
      assertEquals("255.255.0.0", Convert.cidrToMask("16"));
      assertEquals("255.255.248.0", Convert.cidrToMask("21"));
      assertEquals("255.255.255.255", Convert.cidrToMask("32"));
    }

    public void testValidMaskToCidr()
    {
      assertEquals("1", Convert.maskToCidr("128.0.0.0"));
      assertEquals("16", Convert.maskToCidr("255.255.0.0"));
      assertEquals("21", Convert.maskToCidr("255.255.248.0"));
      assertEquals("32", Convert.maskToCidr("255.255.255.255"));
    }

    public void testValidIpv4()
    {
      assertTrue(Convert.ipv4Validation("127.0.0.1"));
      assertTrue(Convert.ipv4Validation("0.0.0.0"));
      assertTrue(Convert.ipv4Validation("192.168.0.1"));
      assertTrue(Convert.ipv4Validation("255.255.255.255"));
    }

    public void testInvalidMaskToCidr()
    {
      assertEquals("Invalid", Convert.maskToCidr("0.0.0.0"));
      assertEquals("Invalid", Convert.maskToCidr("-1"));
      assertEquals("Invalid", Convert.maskToCidr("33"));
    }

    public void testInvalidCidrToMask()
    {
      assertEquals("Invalid", Convert.cidrToMask("0"));
      assertEquals("Invalid", Convert.cidrToMask("0.0.0.0.0"));
      assertEquals("Invalid", Convert.cidrToMask("255.255.255"));
      assertEquals("Invalid", Convert.cidrToMask("11.0.0.0"));
    }

    public void testInvalidIpv4()
    {
      assertFalse(Convert.ipv4Validation("192.168.1.2.3"));
      assertFalse(Convert.ipv4Validation("a.b.c.d"));
      assertFalse(Convert.ipv4Validation("255.256.250.0"));
      assertFalse(Convert.ipv4Validation("...."));
    }

    public void testCidrToMaskLimits()
    {
        assertEquals("Invalid", Convert.cidrToMask(""));
        assertEquals("Invalid", Convert.cidrToMask(null));
        assertEquals("Invalid", Convert.cidrToMask("0.0"));
        assertEquals("Invalid", Convert.cidrToMask("1.123"));
    }

    public void testMaskToCidrLimits()
    {
        assertEquals("Invalid", Convert.maskToCidr(""));
        assertEquals("Invalid", Convert.maskToCidr(null));
        assertEquals("Invalid", Convert.maskToCidr("12323!@#@!#!@#!@#!@#"));
        assertEquals("Invalid", Convert.maskToCidr("@#@!#@!.@!#@!#!@.!@#!@#@!"));
    }

    public void testIpv4Limit()
    {
        assertFalse(Convert.ipv4Validation(""));
        assertFalse(Convert.ipv4Validation(null));
        assertFalse(Convert.ipv4Validation("255.256.250.0.21321.2123.123213"));
        assertFalse(Convert.ipv4Validation("..........."));
        assertFalse(Convert.ipv4Validation("21321321wewr#$%$#@$@#4"));
        assertFalse(Convert.ipv4Validation("QQWQE>WEQWEQW.qweqwe.qweqweqw.123!@#!@#.123!@#!@"));
    }

    public void testMaskRange() {
        assertEquals("Invalid", Convert.maskToCidr("256.0.0.0"));
    }

}
