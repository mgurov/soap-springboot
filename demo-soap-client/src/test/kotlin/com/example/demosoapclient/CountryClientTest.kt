package com.example.demosoapclient

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CountryClientTest(
    @Autowired private val countryClient: CountryClient
) {

    @Test
    fun `should return country`() {
        val country = countryClient.getCountry("Spain")
        println(country)

        assertThat(country.country.name).isEqualTo("Spain")
        assertThat(country.country.capital).isEqualTo("Madrid")
    }

    @Test
    fun `should return country and payload`() {
        val country = countryClient.getCountryWithRawPayload("Spain")
        println(country)

        assertThat(country.first.country.name).isEqualTo("Spain")
        assertThat(country.first.country.capital).isEqualTo("Madrid")
        assertThat(country.second).contains("<SOAP-ENV:Envelope")
    }

}