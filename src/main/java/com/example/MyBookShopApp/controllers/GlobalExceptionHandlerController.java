package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.errs.EmptySearchException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.logging.Logger;

// контроллер для отлавливания всех ошибок централизованно
// вместо определения ExceptionHandler в каждом контроллере отдельно можем определить все необходимые ExceptionHandler методы в одном месте
// и если ошибка будет возникать в каком то из контроллеров то данный класс будет заниматься их обработкой
@ControllerAdvice
public class GlobalExceptionHandlerController {

    @ExceptionHandler(EmptySearchException.class)
    public String handleEmptySearchException(EmptySearchException e, RedirectAttributes redirectAttributes){
        Logger.getLogger(this.getClass().getSimpleName()).warning(e.getLocalizedMessage());
        redirectAttributes.addFlashAttribute("searchError", e);
        return "redirect:/";
    }
}
