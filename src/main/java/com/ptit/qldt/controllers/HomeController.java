package com.ptit.qldt.controllers;

import com.ptit.qldt.dtos.NotificationDto;
import com.ptit.qldt.models.Notification;
import com.ptit.qldt.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ptit.qldt.mappers.NotificationMapper.mapToNotificationDto;

@Controller
public class HomeController {
    private NotificationService notificationService;
    @Autowired
    public HomeController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    @GetMapping("/home")
    public String listNotification(Model model) {
        List<NotificationDto> notifications = notificationService.findAllNotification();
        model.addAttribute("notifications", notifications);
        Notification notification = new Notification();
        model.addAttribute("notification", mapToNotificationDto(notification));
        model.addAttribute("blockEdit", false);
        model.addAttribute("homeactive","active");
        return "home";
    }

    @PostMapping("/home/create-notification")
    public String createNotification(@RequestParam("title") String title, @RequestParam("mes") String mes,Model model) {
        notificationService.save(title, mes);
        model.addAttribute("homeactive","active");
        return "redirect:/home";
    }

    @GetMapping("/home/{notificationId}/delete")
    public String deleteNotification(@PathVariable int notificationId,Model model) {
        notificationService.deleteNotificationById(notificationId);
        model.addAttribute("homeactive","active");
        return "redirect:/home";
    }

    @GetMapping("/home/{notificationId}/edit")
    public String editNotification(Model model, @PathVariable int notificationId) {
        List<NotificationDto> notifications = notificationService.findAllNotification();
        model.addAttribute("notifications", notifications);
        model.addAttribute("blockEdit", true);
        NotificationDto notificationDto = notificationService.findById(notificationId);
        model.addAttribute("notification", notificationDto);
        model.addAttribute("homeactive","active");
        return "home";
    }

    @GetMapping("/home/{notificationId}/detail")
    public String showNotification(Model model, @PathVariable int notificationId) {
        NotificationDto notificationDto = notificationService.findById(notificationId);
        model.addAttribute("notification", notificationDto);
        model.addAttribute("homeactive","active");
        return "notification";
    }

    @PostMapping("/home/{notificationId}/edit")
    public String updateNotification(Model model,@PathVariable int notificationId, @ModelAttribute("notification") NotificationDto notificationDto) {
        notificationDto.setId(notificationId);
        notificationService.updateNotification(notificationDto);
        model.addAttribute("homeactive","active");
        return "redirect:/home";
    }
}
