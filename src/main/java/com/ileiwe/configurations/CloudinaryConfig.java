package com.ileiwe.configurations;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration()
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", "damilare",
                    "api_key", "737177149221658",
                    "api_secret", "396dCUv6airvrACqo1IhQ7Ez-Ro"));
    }
}

