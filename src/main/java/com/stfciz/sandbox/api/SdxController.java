package com.stfciz.sandbox.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stfciz.sandbox.service.SdxService;

/**
 * 
 * @author stfciz
 *
 * 25 juin 2015
 */
@RestController
public class SdxController {

  @Autowired
  private SdxService<String> sbxService;
  
  @RequestMapping("/sleep")
  public SleepResponse sleep(@RequestParam(defaultValue="500", value="d") int delay) {
    return new SleepResponse(this.sbxService.sleep(delay));
  }
}
