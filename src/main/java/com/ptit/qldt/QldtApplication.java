package com.ptit.qldt;

import com.ptit.qldt.dtos.AccountDto;
import com.ptit.qldt.dtos.GroupDto;
import com.ptit.qldt.dtos.GroupRegistrationDto;
import com.ptit.qldt.services.GroupRegistrationService;
import com.ptit.qldt.services.GroupService;
import com.ptit.qldt.services.UserService;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@EnableScheduling
public class QldtApplication {
	private static bot testbot;

	public static void main(String[] args) {
		SpringApplication.run(QldtApplication.class, args);
		String botToken = "7094394606:AAFTvgeWc3IJ91sjLYndFYGHKYAd3Ho_wp0";
			try (TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication()) {
				botsApplication.registerBot(botToken, new bot(botToken));
				System.out.println("MyAmazingBot successfully started!");
				testbot = new bot(botToken);
				try {
					Thread.currentThread().join();
				}catch (Exception ex){
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	@Autowired
	private GroupRegistrationService groupRegistrationService;
	@Autowired
	private GroupService groupService;
//	private List<AccountDto> listAccoutStudent;

	@Scheduled(cron = "0 30 6,8,11,13,15 * * *")
	public void timeTable(){
		String dayOfWeek = LocalDateTime.now().getDayOfWeek().toString();
		String timeNow = LocalDateTime.now().getHour()+":"+LocalDateTime.now().getMinute();
		System.out.println(timeNow);
		System.out.println(convertDayOfWeek(dayOfWeek)+" "+convertTime(timeNow));

		List<AccountDto> allStudentAccount = groupService.findAllStudentAccount();
//		List<GroupRegistrationDto> allGroupInDayOfWeek = groupRegistrationService.findGroupByDayOfWeekAndTime("Thứ 5","Kíp 2");
		List<GroupRegistrationDto> allGroupInDayOfWeek = groupRegistrationService.findGroupByDayOfWeekAndTime(convertDayOfWeek(dayOfWeek),convertTime(timeNow));
		for(GroupRegistrationDto x : allGroupInDayOfWeek){
			String userIdTelegaram = x.getAccount().getUser_id_telegram();
			String beginTime = (LocalDateTime.now().getHour()+1)+":00";
			String endTime = (LocalDateTime.now().getHour()+3)+":00";
			String message = String.format("Bạn có lịch học của học phần %s , lớp %s , tại phòng %s , từ lúc %s đến %s ",
					x.getGroup().getCourse().getName(),x.getGroup().getGroupId(),x.getGroup().getRoom(),beginTime,endTime);
			String botToken = "7094394606:AAFTvgeWc3IJ91sjLYndFYGHKYAd3Ho_wp0";
			if(userIdTelegaram!=null && convertTime(timeNow)!=null && convertDayOfWeek(dayOfWeek)!=null){
				try (TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication()) {
//					botsApplication.registerBot(botToken, new bot(botToken));
					System.out.println("MyAmazingBot successfully started!");
//					bot testBot = new bot(botToken);
					testbot.sendMesseage(message,userIdTelegaram);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			System.out.println(userIdTelegaram);
			System.out.println(x.getGroup().getGroupId());
		}



//		System.out.println("curent time: "+ LocalDateTime.now());
//		String botToken = "7094394606:AAFTvgeWc3IJ91sjLYndFYGHKYAd3Ho_wp0";
//		try (TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication()) {
//			botsApplication.registerBot(botToken, new bot(botToken));
//			System.out.println("MyAmazingBot successfully started!");
//			bot testBot = new bot(botToken);
//			testBot.sendMesseage("duong");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	public String convertDayOfWeek(String dayOfWeek){
		if(dayOfWeek.equals("MONDAY")){
			return "Thứ 2";
		}
		if(dayOfWeek.equals("TUESDAY")){
			return "Thứ 3";
		}
		if(dayOfWeek.equals("WEDNESDAY")){
			return "Thứ 4";
		}
		if(dayOfWeek.equals("THURSDAY")){
			return "Thứ 5";
		}
		if(dayOfWeek.equals("FRIDAY")){
			return "Thứ 6";
		}
		if(dayOfWeek.equals("SATURDAY")){
			return "Thứ 7";
		}
		return null;
	}
	public String convertTime(String time){
		if(time.equals("6:30")){
			return "Kíp 1";
		}
		if(time.equals("8:30")){
			return "Kíp 2";
		}
		if(time.equals("11:30")){
			return "Kíp 3";
		}
		if(time.equals("15:30")){
			return "Kíp 4";
		}
//		if(time.equals("15:30")){
//			return "Kíp 5";
//		}
		return null;
	}
}
