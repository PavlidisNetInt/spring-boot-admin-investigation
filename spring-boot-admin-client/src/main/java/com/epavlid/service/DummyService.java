package com.epavlid.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DummyService {

    public void dummyFun(){
        log.info("Dummy Run");
    }

}
