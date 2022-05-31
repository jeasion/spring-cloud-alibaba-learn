package com.jeasion.provider.service.impl;

import org.springframework.stereotype.Service;
import com.jeasion.provider.service.ProviderService;

/**
 * @author liushanping
 */
@Service
public class ProviderServiceImpl implements ProviderService {
    @Override
    public String hello() {
        return "Service hello";
    }
}
