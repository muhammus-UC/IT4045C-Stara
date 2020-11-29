package com.stara.enterprise;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MaxMindGeoIP2Test {
    private final String dbLocation = "assets/GeoLite2-Country.mmdb";
    private String ip;
    private CountryResponse response;
    private DatabaseReader ipDbReader;

    private void givenIPDatabaseAvailable() throws IOException {
        // Reinitialize variable to ensure data across tests doesn't cause false passes
        ip = null;
        response = null;
        ipDbReader = null;

        File ipDbFile = new File(dbLocation);
        ipDbReader = new DatabaseReader.Builder(ipDbFile).build();
    }

    @Test
    void confirmUSIP_returnsUSCountryName() throws IOException, GeoIp2Exception {
        givenIPDatabaseAvailable();
        whenIPFromUS();
        thenCountryNameIsUS();
    }

    private void whenIPFromUS() throws IOException, GeoIp2Exception {
        // IP Address of uc.edu
        ip = "129.137.4.225";

        InetAddress inetAddress = InetAddress.getByName(ip);
        response = ipDbReader.country(inetAddress);
    }

    private void thenCountryNameIsUS() {
        assertEquals("United States", response.getCountry().getName());
        assertEquals("US", response.getCountry().getIsoCode());
    }

    @Test
    void confirmUKIP_returnsUKCountryName() throws IOException, GeoIp2Exception {
        givenIPDatabaseAvailable();
        whenIPFromUK();
        thenCountryNameIsUK();
    }

    private void whenIPFromUK() throws IOException, GeoIp2Exception {
        // IP Address of UK Dedicated Servers Ltd
        ip = "5.101.137.196";

        InetAddress inetAddress = InetAddress.getByName(ip);
        response = ipDbReader.country(inetAddress);
    }

    private void thenCountryNameIsUK() {
        assertEquals("United Kingdom", response.getCountry().getName());
        assertEquals("GB", response.getCountry().getIsoCode());
    }
}
