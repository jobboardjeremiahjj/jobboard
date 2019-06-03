/**
 *
 */
package com.baeldung.spring.controller;

import com.baeldung.spring.dao.CompanyDao;
import com.baeldung.spring.dao.InterestedDao;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.spring.dao.InterviewDao;
import com.baeldung.spring.dao.JobApplicationDao;
import com.baeldung.spring.dao.JobPostingDao;
import com.baeldung.spring.dao.QuestionDao;
import com.baeldung.spring.entity.Company;
import com.baeldung.spring.entity.Interview;
import com.baeldung.spring.entity.JobApplication;
import com.baeldung.spring.entity.JobPosting;
import com.baeldung.spring.entity.JobSeeker;
import com.baeldung.spring.entity.Question;
import com.baeldung.spring.entity.QuestionDto;
import com.baeldung.spring.mail.EmailServiceImpl;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * @author Avdeep
 *
 */
@Controller
@RequestMapping("/")
public class InterviewController {

    @Autowired
    InterviewDao interviewdao;

    @Autowired
    JobApplicationDao jobAppDao;

    @Autowired
    EmailServiceImpl emailService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    JobPostingDao jobDao;

    @Autowired
    CompanyDao companyDao;

    @Autowired
    QuestionDao questionDao;

    @RequestMapping(value = "/createinterview", method = RequestMethod.POST)
    public String createInterview(@RequestParam("jobId") String jobId, @RequestParam("datetime") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date date) {

        JobPosting p1 = jobDao.getJobPosting(Integer.parseInt(jobId));
        List<?> applications = companyDao.getJobApplications(p1);
        for (JobApplication jobApplication : (List<JobApplication>) applications) {

            if (jobApplication.getJobPosting().getJobId() == Integer.valueOf(jobId)) {
                if (jobApplication.getShortListed()) {
                    JobSeeker jobSeeker = jobApplication.getJobSeeker();
                    System.out.println("started");
                    jobApplication.setInterviewFlag(true);
                    jobApplication.setInterviewTime(date);
                    jobApplication.setInterviewAccepted(false);
                    jobAppDao.updateApplication(jobApplication);
                    String verificationUrl = "http://localhost:8084/jobboard/acceptinterview?appId=" + jobApplication.getAppId();
                    System.out.println("interview created");
                    emailService.sendSimpleMessage(jobSeeker.getEmailId(), "Interview call",
                            "Hi " + jobSeeker.getFirstName() + " " + jobSeeker.getLastName()
                            + ", \nYou have been selected for an interview for the position " + jobApplication.getJobPosting().getTitle() + " at time " + date
                            + ".\n If you are intereseted in it please click on the following link : \n" + verificationUrl);
                }
            }
        }
        String msg = "<div class=\"alert alert-success\" role=\"alert\">Interview emails have been sent to shortlisted applicants</div>";
        return "redirect:/company/showapplicants?jobId=" + jobId + "&msg=" + msg;
    }

    @RequestMapping(value = "/acceptinterview", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String acceptinterview(@RequestParam("appId") String appId, Model model) {
        System.out.println("started");
        JobApplication ja = jobAppDao.getJobApplication(Integer.parseInt(appId));
        JobSeeker jobSeeker = ja.getJobSeeker();
        ja.setInterviewAccepted(true);
        jobAppDao.updateApplication(ja);
        Company c = ja.getJobPosting().getCompany();
        String verificationUrl = "http://localhost:8084/jobboard/writexam?appId=" + ja.getAppId();
        emailService.sendSimpleMessage(jobSeeker.getEmailId(), "Interview",
                "Hi " + jobSeeker.getFirstName() + " " + jobSeeker.getLastName()
                + ", \nThank you for accepting our interview. Login at " + ja.getInterviewTime() + " "
                + ".\n using the following link : \n" + verificationUrl);
        String msg = "<div class=\"alert alert-success\" role=\"alert\">Thank you for accepting. We have sent you a link to your written interview.</div>";
        model.addAttribute("message", msg);
        return "index";
    }

    @RequestMapping(value = "/createxam", method = RequestMethod.GET)
    public String createExam(@RequestParam("jobId") String jobId, @RequestParam(name = "no") String qsnNo, Model model) {
        try {
            JobPosting p1 = jobDao.getJobPosting(Integer.parseInt(jobId));
            List<Question> questions = new ArrayList<>();
            questions = questionDao.createXam(Integer.valueOf(qsnNo.trim()));
            p1.setQuestions(questions);
            jobDao.updateJobPosting(p1);

            model.addAttribute("job", p1);
            model.addAttribute("questions", questions);
        } catch (Exception e) {
        }

        return "redirect:/showexam?jobId=" + jobId;
    }
    
    @RequestMapping(value = "/showexam", method = RequestMethod.GET)
	public String createExam(@RequestParam("jobId") String jobId, Model model) {
            try{
                JobPosting p1 = jobDao.getJobPosting(Integer.parseInt(jobId));
                List<Question> questions = new ArrayList<>();
               
               questions = p1.getQuestions();
                
                System.out.println("qqqqqqqqqqqqqqqqqqq " + questions.size());
                model.addAttribute("job", p1);
                model.addAttribute("questions", questions);
            } catch (Exception e){
            }
		
		return "exam";
	}

    @RequestMapping(value = "/writexam", method = RequestMethod.GET)
    public String writeExam(@RequestParam("appId") String appId, Model model) {
        try {
           
            long ONE_MINUTE_IN_MILLIS=60000;//millisecs
            JobApplication ja = jobAppDao.getJobApplication(Integer.parseInt(appId));

            Date interviewTime = ja.getInterviewTime();
            long t= interviewTime.getTime();
            Date afterAddingTenMins = new Date(t + (2 * ONE_MINUTE_IN_MILLIS));
            
            long diffInMillies = afterAddingTenMins.getTime() - new Date().getTime();
            System.out.println("sssssssssssssss " + diffInMillies);
            long timeleft = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
            
            model.addAttribute("timeleft", timeleft);
            
            
            if(timeleft > 0 && timeleft < 10){
                JobPosting jobPosting = ja.getJobPosting();
                model.addAttribute("questions", jobPosting.getQuestions());
                JobSeeker jobSeeker = ja.getJobSeeker();
                model.addAttribute("seeker", jobSeeker);
            } else if (timeleft > 10){
                model.addAttribute("message", "Interview not yet started");
            } else {
                model.addAttribute("message", "Interview finished at " + ja.getInterviewTime());
            }

              
        
            model.addAttribute("application", ja);
            model.addAttribute("job", ja.getJobPosting());
            
        } catch (Exception e) {
        }

        return "xampaper";
    }
    
    @RequestMapping(value = "/submitexam", method = RequestMethod.POST)
    public String submitExam(@ModelAttribute("dto") QuestionDto questionDto, Model model) {
        try {
            JobApplication ja = jobAppDao.getJobApplication(questionDto.getAppId());
            for(Question question : questionDto.getQuestions()){
                if(question.getAnswer().equals(question.getSelected())){
                    int result = ja.getInterviewResult();
                 
                    ja.setInterviewResult(result + 1);
                }
            }
            ja.setFinishTime(new Date());
            
            jobAppDao.updateApplication(ja);
            
            model.addAttribute("application", ja);
            model.addAttribute("message", "Thank you for completing our interview. You will be contacted via email.");
            
        } catch (Exception e) {
        }

        return "index";
    }
}
