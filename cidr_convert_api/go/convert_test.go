package main

import (
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestValidCidrToMask(t *testing.T) {
	assert := assert.New(t)

	assert.Equal("128.0.0.0", cidrToMask("1"))
	assert.Equal("255.255.0.0", cidrToMask("16"))
	assert.Equal("255.255.248.0", cidrToMask("21"))
	assert.Equal("255.255.255.255", cidrToMask("32"))
}

func TestValidMaskToCidr(t *testing.T) {
	assert := assert.New(t)

	assert.Equal("1", maskToCidr("128.0.0.0"))
	assert.Equal("16", maskToCidr("255.255.0.0"))
	assert.Equal("21", maskToCidr("255.255.248.0"))
	assert.Equal("32", maskToCidr("255.255.255.255"))
}

func TestValidIpv4(t *testing.T) {
	assert := assert.New(t)

	assert.Equal(true, ipv4Validation("127.0.0.1"))
	assert.Equal(true, ipv4Validation("0.0.0.0"))
	assert.Equal(true, ipv4Validation("192.168.0.1"))
	assert.Equal(true, ipv4Validation("255.255.255.255"))
}


func TestInvalidMaskToCidr(t *testing.T) {
	assert := assert.New(t)

	assert.Equal("Invalid", maskToCidr("0.0.0.0"))
	assert.Equal("Invalid", maskToCidr("-1"))
	assert.Equal("Invalid", maskToCidr("33"))
}

func TestInvalidCidrToMask(t *testing.T) {
	assert := assert.New(t)

	assert.Equal("Invalid", cidrToMask("0"))
	assert.Equal("Invalid", cidrToMask("0.0.0.0.0"))
	assert.Equal("Invalid", cidrToMask("255.255.255"))
	assert.Equal("Invalid", cidrToMask("11.0.0.0"))
}


func TestInvalidIpv4(t *testing.T) {
	assert := assert.New(t)

	assert.Equal(false, ipv4Validation("192.168.1.2.3"))
	assert.Equal(false, ipv4Validation("a.b.c.d"))
	assert.Equal(false, ipv4Validation("255.256.250.0"))
	assert.Equal(false, ipv4Validation("...."))

}
