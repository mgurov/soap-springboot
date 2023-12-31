package com.example.demosoapclient

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.oxm.jaxb.Jaxb2Marshaller

@Configuration
class CountryConfiguration {

    @Bean
    fun marshaller(): Jaxb2Marshaller {
        val marshaller = Jaxb2Marshaller()
        // this package must match the package in the <generatePackage> specified in
        // pom.xml
        marshaller.contextPath = "com.example.consumingwebservice.wsdl"
        return marshaller
    }

    @Bean
    fun countryClient(marshaller: Jaxb2Marshaller): CountryClient {
        val client = CountryClient()
        client.defaultUri = "http://localhost:8080/ws"
        client.marshaller = marshaller
        client.unmarshaller = marshaller
        return client
    }
}