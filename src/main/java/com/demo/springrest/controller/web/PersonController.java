package com.demo.springrest.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.springrest.model.Person;

@Controller
@RequestMapping("persion")
public class PersonController {

    @ResponseBody
    @RequestMapping(value = "/p1", method = RequestMethod.GET)
    public Person getPerson1() {
        Person person = new Person("Bill", "Gates");
        return person;
    }
    
   
  
}