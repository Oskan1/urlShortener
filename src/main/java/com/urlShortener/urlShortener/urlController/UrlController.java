package com.urlShortener.urlShortener.urlController;


import com.urlShortener.urlShortener.entity.UrlShort;
import com.urlShortener.urlShortener.exceptionHandling.ResourceNotFoundException;
import com.urlShortener.urlShortener.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/")
public class UrlController {

    @Autowired
    private UrlRepository urlRepository;

    @PostMapping("url")
    public UrlShort createUrl(@RequestBody UrlShort entity){
        entity.setShortUrl();
        if(entity.getTimestamp()!=null){
        long delay = entity.getTimestamp().getTime() - System.currentTimeMillis();
        Runnable task  = () -> urlRepository.deleteAllByTimestampLessThanEqual(entity.getTimestamp());
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
        executor.schedule(task, delay, TimeUnit.MILLISECONDS);}
        urlRepository.save(entity);
        return entity;
    }
    @GetMapping("{shortenedUrl}")
    public ResponseEntity<UrlShort> getUrlByShortenedUrl(@PathVariable(value = "shortenedUrl") String shortUrl)
            // throws ResourceNotFoundException
    {
        UrlShort entity = urlRepository.findByShortUrl(shortUrl);

        if(entity == null){
            Logger logger = LoggerFactory.getLogger(UrlController.class);
            logger.error("Url does not exist in DB: " + shortUrl);
        }
            // throw new ResourceNotFoundException("Url does not exist in DB: " + shortUrl);

        assert entity != null;
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(entity.getMainUrl())).build();

    }

    @Transactional
    @GetMapping("urls")
    public List<UrlShort> getAlUrls(){
        System.out.println(Date.from(Instant.now()));
        urlRepository.deleteAllByTimestampLessThanEqual(Date.from(Instant.now()));
        return this.urlRepository.findAllByOrderByTimestampDesc();
    }
}

















