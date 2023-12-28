package com.example.demosoapclient

import com.example.consumingwebservice.wsdl.GetCountryRequest
import com.example.consumingwebservice.wsdl.GetCountryResponse
import org.slf4j.LoggerFactory
import org.springframework.oxm.Marshaller
import org.springframework.ws.client.core.WebServiceMessageExtractor
import org.springframework.ws.client.core.support.WebServiceGatewaySupport
import org.springframework.ws.soap.client.core.SoapActionCallback
import org.springframework.ws.support.MarshallingUtils
import java.io.ByteArrayOutputStream

class CountryClient : WebServiceGatewaySupport() {
    fun getCountry(country: String): GetCountryResponse {
        val request = GetCountryRequest()
        request.name = country
        log.info("Requesting location for $country")
        return webServiceTemplate
            .marshalSendAndReceive(
                "http://localhost:8080/ws/countries", request,
                SoapActionCallback(
                    "http://spring.io/guides/gs-producing-web-service/GetCountryRequest"
                )
            ) as GetCountryResponse
    }

    fun getCountryWithRawPayload(country: String): Pair<GetCountryResponse, String> {
        val request = GetCountryRequest()
        request.name = country
        log.info("Requesting location for $country")
        val response = webServiceTemplate
            .sendAndReceive(
                "http://localhost:8080/ws/countries", { requestMessage ->
                    val marshaller: Marshaller = this.marshaller
                    MarshallingUtils.marshal(marshaller, request, requestMessage)
                    SoapActionCallback(
                        "http://spring.io/guides/gs-producing-web-service/GetCountryRequest"
                    ).doWithMessage(requestMessage)
                },
                WebServiceMessageExtractor { responseMessage ->
                    val buffer = ByteArrayOutputStream()
                    responseMessage.writeTo(buffer)
                    MarshallingUtils.unmarshal(unmarshaller, responseMessage) as GetCountryResponse to buffer.toString()
                }

            )

        return response as Pair<GetCountryResponse, String>
    }

    companion object {
        private val log = LoggerFactory.getLogger(CountryClient::class.java)
    }
}