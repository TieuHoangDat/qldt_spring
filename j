[33mcommit e743ffdd11fbe58bbd248d28e7f7d62c970d92da[m[33m ([m[1;36mHEAD -> [m[1;32mmain[m[33m)[m
Author: LeDinhDuong21042003 <DuongLD.B21CN281@stu.ptit.edu.vn>
Date:   Fri Apr 12 10:02:36 2024 +0700

    add telegram notification

[1mdiff --git a/pom.xml b/pom.xml[m
[1mindex 026cd09..9b9602e 100644[m
[1m--- a/pom.xml[m
[1m+++ b/pom.xml[m
[36m@@ -17,6 +17,24 @@[m
 		<java.version>17</java.version>[m
 	</properties>[m
 	<dependencies>[m
[32m+[m
[32m+[m		[32m<dependency>[m
[32m+[m			[32m<groupId>org.telegram</groupId>[m
[32m+[m			[32m<artifactId>telegrambots-springboot-longpolling-starter</artifactId>[m
[32m+[m			[32m<version>7.2.0</version>[m
[32m+[m		[32m</dependency>[m
[32m+[m
[32m+[m		[32m<dependency>[m
[32m+[m			[32m<groupId>org.telegram</groupId>[m
[32m+[m			[32m<artifactId>telegrambots-longpolling</artifactId>[m
[32m+[m			[32m<version>7.2.0</version>[m
[32m+[m		[32m</dependency>[m
[32m+[m		[32m<dependency>[m
[32m+[m			[32m<groupId>org.telegram</groupId>[m
[32m+[m			[32m<artifactId>telegrambots-client</artifactId>[m
[32m+[m			[32m<version>7.2.0</version>[m
[32m+[m		[32m</dependency>[m
[32m+[m
 		<dependency>[m
 			<groupId>org.springframework.boot</groupId>[m
 			<artifactId>spring-boot-starter-data-jpa</artifactId>[m
[1mdiff --git a/src/main/java/com/ptit/qldt/QldtApplication.java b/src/main/java/com/ptit/qldt/QldtApplication.java[m
[1mindex 40c952d..b007794 100644[m
[1m--- a/src/main/java/com/ptit/qldt/QldtApplication.java[m
[1m+++ b/src/main/java/com/ptit/qldt/QldtApplication.java[m
[36m@@ -1,13 +1,116 @@[m
 package com.ptit.qldt;[m
 [m
[32m+[m[32mimport com.ptit.qldt.dtos.AccountDto;[m
[32m+[m[32mimport com.ptit.qldt.dtos.GroupDto;[m
[32m+[m[32mimport com.ptit.qldt.dtos.GroupRegistrationDto;[m
[32m+[m[32mimport com.ptit.qldt.services.GroupRegistrationService;[m
[32m+[m[32mimport com.ptit.qldt.services.GroupService;[m
[32m+[m[32mimport com.ptit.qldt.services.UserService;[m
[32m+[m[32mimport net.bytebuddy.asm.Advice;[m
[32m+[m[32mimport org.springframework.beans.factory.annotation.Autowired;[m
 import org.springframework.boot.SpringApplication;[m
 import org.springframework.boot.autoconfigure.SpringBootApplication;[m
[32m+[m[32mimport org.springframework.scheduling.annotation.EnableScheduling;[m
[32m+[m[32mimport org.springframework.scheduling.annotation.Scheduled;[m
[32m+[m[32mimport org.springframework.stereotype.Component;[m
[32m+[m[32mimport org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;[m
[32m+[m
[32m+[m[32mimport java.time.LocalDateTime;[m
[32m+[m[32mimport java.util.List;[m
 [m
 @SpringBootApplication[m
[32m+[m[32m@EnableScheduling[m
 public class QldtApplication {[m
 [m
 	public static void main(String[] args) {[m
 		SpringApplication.run(QldtApplication.class, args);[m
[32m+[m
 	}[m
[32m+[m	[32m@Autowired[m
[32m+[m	[32mprivate GroupRegistrationService groupRegistrationService;[m
[32m+[m	[32m@Autowired[m
[32m+[m	[32mprivate GroupService groupService;[m
[32m+[m[32m//	private List<AccountDto> listAccoutStudent;[m
[32m+[m
[32m+[m	[32m@Scheduled(cron = "0 30 6,8,11,13,15 * * *")[m
[32m+[m	[32mpublic void timeTable(){[m
[32m+[m		[32mString dayOfWeek = LocalDateTime.now().getDayOfWeek().toString();[m
[32m+[m		[32mString timeNow = LocalDateTime.now().getHour()+":"+LocalDateTime.now().getMinute();[m
[32m+[m		[32mSystem.out.println(timeNow);[m
[32m+[m		[32mSystem.out.println(convertDayOfWeek(dayOfWeek)+" "+convertTime(timeNow));[m
[32m+[m
[32m+[m		[32mList<AccountDto> allStudentAccount = groupService.findAllStudentAccount();[m
[32m+[m[32m//		List<GroupRegistrationDto> allGroupInDayOfWeek = groupRegistrationService.findGroupByDayOfWeekAndTime("Thứ 5","Kíp 2");[m
[32m+[m		[32mList<GroupRegistrationDto> allGroupInDayOfWeek = groupRegistrationService.findGroupByDayOfWeekAndTime(convertDayOfWeek(dayOfWeek),convertTime(timeNow));[m
[32m+[m		[32mfor(GroupRegistrationDto x : allGroupInDayOfWeek){[m
[32m+[m			[32mString userIdTelegaram = x.getAccount().getUser_id_telegram();[m
[32m+[m			[32mString beginTime = (LocalDateTime.now().getHour()+1)+":00";[m
[32m+[m			[32mString endTime = (LocalDateTime.now().getHour()+3)+":00";[m
[32m+[m			[32mString message = String.format("Bạn có lịch học của học phần %s , lớp %s , tại phòng %s , từ lúc %s đến %s ",[m
[32m+[m					[32mx.getGroup().getCourse().getName(),x.getGroup().getGroupId(),x.getGroup().getRoom(),beginTime,endTime);[m
[32m+[m			[32mString botToken = "7094394606:AAFTvgeWc3IJ91sjLYndFYGHKYAd3Ho_wp0";[m
[32m+[m			[32mtry (TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication()) {[m
[32m+[m				[32mbotsApplication.registerBot(botToken, new bot(botToken));[m
[32m+[m				[32mSystem.out.println("MyAmazingBot successfully started!");[m
[32m+[m				[32mbot testBot = new bot(botToken);[m
[32m+[m				[32mtestBot.sendMesseage(message,userIdTelegaram);[m
[32m+[m			[32m} catch (Exception e) {[m
[32m+[m				[32me.printStackTrace();[m
[32m+[m			[32m}[m
[32m+[m			[32mSystem.out.println(userIdTelegaram);[m
[32m+[m			[32mSystem.out.println(x.getGroup().getGroupId());[m
[32m+[m		[32m}[m
[32m+[m
 [m
[32m+[m
[32m+[m[32m//		System.out.println("curent time: "+ LocalDateTime.now());[m
[32m+[m[32m//		String botToken = "7094394606:AAFTvgeWc3IJ91sjLYndFYGHKYAd3Ho_wp0";[m
[32m+[m[32m//		try (TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication()) {[m
[32m+[m[32m//			botsApplication.registerBot(botToken, new bot(botToken));[m
[32m+[m[32m//			System.out.println("MyAmazingBot successfully started!");[m
[32m+[m[32m//			bot testBot = new bot(botToken);[m
[32m+[m[32m//			testBot.sendMesseage("duong");[m
[32m+[m[32m//		} catch (Exception e) {[m
[32m+[m[32m//			e.printStackTrace();[m
[32m+[m[32m//		}[m
[32m+[m	[32m}[m
[32m+[m	[32mpublic String convertDayOfWeek(String dayOfWeek){[m
[32m+[m		[32mif(dayOfWeek.equals("MONDAY")){[m
[32m+[m			[32mreturn "Thứ 2";[m
[32m+[m		[32m}[m
[32m+[m		[32mif(dayOfWeek.equals("TUESDAY")){[m
[32m+[m			[32mreturn "Thứ 3";[m
[32m+[m		[32m}[m
[32m+[m		[32mif(dayOfWeek.equals("WEDNESDAY")){[m
[32m+[m			[32mreturn "Thứ 4";[m
[32m+[m		[32m}[m
[32m+[m		[32mif(dayOfWeek.equals("THURSDAY")){[m
[32m+[m			[32mreturn "Thứ 5";[m
[32m+[m		[32m}[m
[32m+[m		[32mif(dayOfWeek.equals("FRIDAY")){[m
[32m+[m			[32mreturn "Thứ 6";[m
[32m+[m		[32m}[m
[32m+[m		[32mif(dayOfWeek.equals("SATURDAY")){[m
[32m+[m			[32mreturn "Thứ 7";[m
[32m+[m		[32m}[m
[32m+[m		[32mreturn null;[m
[32m+[m	[32m}[m
[32m+[m	[32mpublic String convertTime(String time){[m
[32m+[m		[32mif(time.equals("6:30")){[m
[32m+[m			[32mreturn "Kíp 1";[m
[32m+[m		[32m}[m
[32m+[m		[32mif(time.equals("8:30")){[m
[32m+[m			[32mreturn "Kíp 2";[m
[32m+[m		[32m}[m
[32m+[m		[32mif(time.equals("11:30")){[m
[32m+[m			[32mreturn "Kíp 3";[m
[32m+[m		[32m}[m
[32m+[m		[32mif(time.equals("13:30")){[m
[32m+[m			[32mreturn "Kíp 4";[m
[32m+[m		[32m}[m
[32m+[m		[32mif(time.equals("15:30")){[m
[32m+[m			[32mreturn "Kíp 5";[m
[32m+[m		[32m}[m
[32m+[m		[32mreturn null;[m
[32m+[m	[32m}[m
 }[m
[1mdiff --git a/src/main/java/com/ptit/qldt/bot.java b/src/main/java/com/ptit/qldt/bot.java[m
[1mnew file mode 100644[m
[1mindex 0000000..43fee17[m
[1m--- /dev/null[m
[1m+++ b/src/main/java/com/ptit/qldt/bot.java[m
[36m@@ -0,0 +1,58 @@[m
[32m+[m[32mpackage com.ptit.qldt;[m
[32m+[m
[32m+[m[32mimport okhttp3.OkHttpClient;[m
[32m+[m[32mimport org.springframework.stereotype.Component;[m
[32m+[m[32mimport org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;[m
[32m+[m[32mimport org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;[m
[32m+[m[32mimport org.telegram.telegrambots.longpolling.util.TelegramOkHttpClientFactory;[m
[32m+[m[32mimport org.telegram.telegrambots.meta.api.methods.send.SendMessage;[m
[32m+[m[32mimport org.telegram.telegrambots.meta.api.objects.Update;[m
[32m+[m[32mimport org.telegram.telegrambots.meta.exceptions.TelegramApiException;[m
[32m+[m[32mimport org.telegram.telegrambots.meta.generics.TelegramClient;[m
[32m+[m
[32m+[m[32mpublic class bot implements LongPollingSingleThreadUpdateConsumer {[m
[32m+[m
[32m+[m[32m    private final TelegramClient telegramClient;[m
[32m+[m
[32m+[m[32m    public bot(String botToken) {[m
[32m+[m[32m        telegramClient = new OkHttpTelegramClient(botToken);[m
[32m+[m[32m    }[m
[32m+[m[32m    public void sendMesseage(String text,String userIdTelegram){[m
[32m+[m[32m        SendMessage message = SendMessage // Create a message object[m
[32m+[m[32m                .builder()[m
[32m+[m[32m                .chatId(userIdTelegram)[m
[32m+[m[32m                .text(text)[m
[32m+[m[32m                .build();[m
[32m+[m[32m        try {[m
[32m+[m[32m            telegramClient.execute(message); // Sending our message object to user[m
[32m+[m[32m        } catch (TelegramApiException e) {[m
[32m+[m[32m            e.printStackTrace();[m
[32m+[m[32m        }[m
[32m+[m[32m    }[m
[32m+[m[32m    @Override[m
[32m+[m[32m    public void consume(Update update) {[m
[32m+[m[32m        // We check if the update has a message and the message has text[m
[32m+[m[32m        if (update.hasMessage() && update.getMessage().hasText()) {[m
[32m+[m[32m            // Set variables[m
[32m+[m[32m            String message_text = update.getMessage().getText();[m
[32m+[m[32m            System.out.println(message_text);[m
[32m+[m[32m            long chat_id = update.getMessage().getChatId();[m
[32m+[m[32m            System.out.println(chat_id);[m
[32m+[m
[32m+[m[32m            if(message_text.equals("/start")){[m
[32m+[m[32m                message_text = "Nhập mã sinh viên";[m
[32m+[m[32m            }[m
[32m+[m
[32m+[m[32m            SendMessage message = SendMessage // Create a message object[m
[32m+[m[32m                    .builder()[m
[32m+[m[32m                    .chatId(chat_id)[m
[32m+[m[32m                    .text(message_text)[m
[32m+[m[32m                    .build();[m
[32m+[m[32m            try {[m
[32m+[m[32m                telegramClient.execute(message); // Sending our message object to user[m
[32m+[m[32m            } catch (TelegramApiException e) {[m
[32m+[m[32m                e.printStackTrace();[m
[32m+[m[32m            }[m
[32m+[m[32m        }[m
[32m+[m[32m    }[m
[32m+[m[32m}[m
[1mdiff --git a/src/main/java/com/ptit/qldt/controllers/GroupController.java b/src/main/java/com/ptit/qldt/controllers/GroupController.java[m
[1mindex 110cb4d..959e6cb 100644[m
[1m--- a/src/main/java/com/ptit/qldt/controllers/GroupController.java[m
[1m+++ b/src/main/java/com/ptit/qldt/controllers/GroupController.java[m
[36m@@ -1,12 +1,10 @@[m
 package com.ptit.qldt.controllers;[m
 [m
 import com.ptit.qldt.dtos.AccountDto;[m
[31m-import com.ptit.qldt.dtos.CourseDto;[m
 import com.ptit.qldt.dtos.CourseRegistrationDto;[m
 import com.ptit.qldt.dtos.GroupDto;[m
 import com.ptit.qldt.models.Account;[m
 import com.ptit.qldt.models.Course;[m
[31m-import com.ptit.qldt.models.CourseRegistration;[m
 import com.ptit.qldt.models.Group;[m
 import com.ptit.qldt.services.GroupService;[m
 import com.ptit.qldt.services.UserService;[m
[36m@@ -42,7 +40,7 @@[m [mpublic class GroupController {[m
     @GetMapping("/groupByCourse/{courseId}")[m
     public String showGroupByCourse(@PathVariable String courseId , Model model){[m
         List<GroupDto> groups = groupService.getGroupsForCourse(courseId);[m
[31m-        List<AccountDto> accounts = groupService.findAllAccount();[m
[32m+[m[32m        List<AccountDto> accounts = groupService.findAllTeacherAccount();[m
         for(AccountDto accountDto : accounts){[m
             System.out.println(accountDto.getFullName());[m
         }[m
[36m@@ -67,7 +65,7 @@[m [mpublic class GroupController {[m
     @GetMapping("/groupss")[m
     public String showGroup(Model model){[m
         List<GroupDto> groups = groupService.findAllGroup();[m
[31m-        List<AccountDto> accounts = groupService.findAllAccount();[m
[32m+[m[32m        List<AccountDto> accounts = groupService.findAllTeacherAccount();[m
         Group group = new Group();[m
         model.addAttribute("accounts",accounts);[m
         model.addAttribute("groups", groups);[m
[36m@@ -77,7 +75,7 @@[m [mpublic class GroupController {[m
 [m
     @GetMapping("/groupByCourse/{courseId}/new")[m
     public String createGroup(@PathVariable(value = "courseId") String courseId ,Model model){[m
[31m-        List<AccountDto> accounts = groupService.findAllAccount();[m
[32m+[m[32m        List<AccountDto> accounts = groupService.findAllTeacherAccount();[m
         Group group = new Group();[m
         model.addAttribute("accounts",accounts);[m
         model.addAttribute("courseId",courseId);[m
[36m@@ -101,9 +99,11 @@[m [mpublic class GroupController {[m
         Course course = new Course();[m
         course.setId(course_id);[m
         String thoigian = "Thứ "+ day +",kíp "+time;[m
[31m-        String groupId = course_id+"_"+String.format("N%02d", Integer.parseInt(name_group.substring(5)));[m
[32m+[m[32m        String groupId = course_id + "_" + String.format("N%02d",Integer.parseInt(name_group));[m
[32m+[m[32m//        String groupId = course_id+"_"+String.format("N%02d", Integer.parseInt(name_group.substring(5)));[m
[32m+[m[32m        String group_name = "Nhóm " + name_group;[m
 [m
[31m-        group.setGroupName(name_group);[m
[32m+[m[32m        group.setGroupName(group_name);[m
         group.setGroupId(groupId);[m
         group.setCourse(course);[m
         group.setTime(thoigian);[m
[36m@@ -123,7 +123,7 @@[m [mpublic class GroupController {[m
                             Model model,[m
                             @PathVariable("courseId") String courseId){[m
         GroupDto groupDto = groupService.findGroupById(groupId);[m
[31m-        List<AccountDto> accounts = groupService.findAllAccount();[m
[32m+[m[32m        List<AccountDto> accounts = groupService.findAllTeacherAccount();[m
 //        groupDto.setRegisted(true);[m
 //        groupDto.setMaxStudents(20);[m
 //        groupDto.setAvailableSlots(20);[m
[36m@@ -192,7 +192,6 @@[m [mpublic class GroupController {[m
         model.addAttribute("file",filePath);[m
         List<CourseRegistrationDto> list =  new ArrayList<>();[m
         try {[m
[31m-//            String idCourse = "BAS1105";[m
             // Đường dẫn đến tệp Excelx[m
             System.out.println(filePath);[m
             String excelFilePath = filePath;[m
[36m@@ -269,7 +268,7 @@[m [mpublic class GroupController {[m
 [m
     // testtttttttttttttt[m
 //@PostMapping("/groupByCourse/{courseId}/{groupId}/addGrade")[m
[31m-//public String test(Model model, @RequestParam(value = "file") String filePath,@PathVariable(value = "courseId") String courseId){[m
[32m+[m[32m//public String com.ptit.qldt.test(Model model, @RequestParam(value = "file") String filePath,@PathVariable(value = "courseId") String courseId){[m
 //    model.addAttribute("file",filePath);[m
 //    String list = "";[m
 ////                groupService.readExcel("C:\\Users\\ASUS\\Desktop\\"+filePath,courseId);[m
[1mdiff --git a/src/main/java/com/ptit/qldt/dtos/AccountDto.java b/src/main/java/com/ptit/qldt/dtos/AccountDto.java[m
[1mindex 8af530f..526d628 100644[m
[1m--- a/src/main/java/com/ptit/qldt/dtos/AccountDto.java[m
[1m+++ b/src/main/java/com/ptit/qldt/dtos/AccountDto.java[m
[36m@@ -10,5 +10,6 @@[m [mpublic class AccountDto {[m
     private String fullName, firstName, lastName;[m
     private String username;[m
     private String email;[m
[32m+[m[32m    private String user_id_telegram;[m
     private Integer role;[m
 }[m
[1mdiff --git a/src/main/java/com/ptit/qldt/mappers/AccountMapper.java b/src/main/java/com/ptit/qldt/mappers/AccountMapper.java[m
[1mindex 422a9b4..ed79a6e 100644[m
[1m--- a/src/main/java/com/ptit/qldt/mappers/AccountMapper.java[m
[1m+++ b/src/main/java/com/ptit/qldt/mappers/AccountMapper.java[m
[36m@@ -21,6 +21,8 @@[m [mpublic class AccountMapper {[m
                 .lastName(lastName.toString())[m
                 .username(account.getUsername())[m
                 .email(account.getEmail())[m
[32m+[m[32m                .user_id_telegram(account.getUser_id_telegram())[m
[32m+[m[32m                .role(account.getRole())[m
                 .build();[m
         return accountDto;[m
     }[m
[1mdiff --git a/src/main/java/com/ptit/qldt/models/Account.java b/src/main/java/com/ptit/qldt/models/Account.java[m
[1mindex ff11c56..03d3e0d 100644[m
[1m--- a/src/main/java/com/ptit/qldt/models/Account.java[m
[1m+++ b/src/main/java/com/ptit/qldt/models/Account.java[m
[36m@@ -23,5 +23,6 @@[m [mpublic class Account {[m
     private String password;[m
     private String email;[m
     private Integer role;[m
[32m+[m[32m    private String user_id_telegram;[m
     private String otp;[m
 }[m
[1mdiff --git a/src/main/java/com/ptit/qldt/repositories/AccountRepository.java b/src/main/java/com/ptit/qldt/repositories/AccountRepository.java[m
[1mindex 540c049..6064aa3 100644[m
[1m--- a/src/main/java/com/ptit/qldt/repositories/AccountRepository.java[m
[1m+++ b/src/main/java/com/ptit/qldt/repositories/AccountRepository.java[m
[36m@@ -10,4 +10,7 @@[m [mpublic interface AccountRepository extends JpaRepository<Account,Integer> {[m
 [m
     @Query("SELECT a FROM Account a WHERE a.role = 2")[m
     List<Account> findAllTeacher();[m
[32m+[m
[32m+[m[32m    @Query("SELECT a FROM Account a WHERE a.role = 3")[m
[32m+[m[32m    List<Account> findAllStudent();[m
 }[m
[1mdiff --git a/src/main/java/com/ptit/qldt/repositories/CourseRepository.java b/src/main/java/com/ptit/qldt/repositories/CourseRepository.java[m
[1mindex ef64af7..ab6aff9 100644[m
[1m--- a/src/main/java/com/ptit/qldt/repositories/CourseRepository.java[m
[1m+++ b/src/main/java/com/ptit/qldt/repositories/CourseRepository.java[m
[36m@@ -11,8 +11,8 @@[m [mpublic interface CourseRepository extends JpaRepository<Course, String> {[m
 [m
     @Query("SELECT cr.course FROM CourseRegistration cr WHERE cr.account.account_id = :accountId AND cr.term = 'Kỳ 1 năm học 2023-2024'")[m
     List<Course> findCourseRegister(@Param("accountId") int accountId);[m
[31m-    @Query("SELECT c FROM Course c WHERE c.id LIKE CONCAT( '%',:name,'%') ")[m
[31m-    List<Course> findByName(@Param("name") String name);[m
[32m+[m[32m    @Query("SELECT c FROM Course c WHERE c.id LIKE CONCAT( '%',:name,'%') or c.name like CONCAT( '%',:name,'%') ")[m
[32m+[m[32m    List<Course> findByName(@Param("name") String name );[m
 [m
     @Query("SELECT cr FROM CourseRegistration cr WHERE cr.account.account_id = :accountId and cr.course.id = :courseId ")[m
     CourseRegistration findCourseRegisterByCourseIdAndAccountId(@Param("courseId") String courseId,@Param("accountId") int accountId);[m
[1mdiff --git a/src/main/java/com/ptit/qldt/repositories/GroupRegistrationRepository.java b/src/main/java/com/ptit/qldt/repositories/GroupRegistrationRepository.java[m
[1mindex 09b8ff0..583211e 100644[m
[1m--- a/src/main/java/com/ptit/qldt/repositories/GroupRegistrationRepository.java[m
[1m+++ b/src/main/java/com/ptit/qldt/repositories/GroupRegistrationRepository.java[m
[36m@@ -1,6 +1,7 @@[m
 package com.ptit.qldt.repositories;[m
 [m
 import com.ptit.qldt.models.Course;[m
[32m+[m[32mimport com.ptit.qldt.models.Group;[m
 import com.ptit.qldt.models.GroupRegistration;[m
 import org.springframework.data.jpa.repository.JpaRepository;[m
 import org.springframework.data.jpa.repository.Modifying;[m
[36m@@ -15,8 +16,18 @@[m [mpublic interface GroupRegistrationRepository  extends JpaRep