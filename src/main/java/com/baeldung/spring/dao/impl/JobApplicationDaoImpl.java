/**
 * 
 */
package com.baeldung.spring.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.spring.dao.JobApplicationDao;
import com.baeldung.spring.entity.JobApplication;
import com.baeldung.spring.entity.JobPosting;
import com.baeldung.spring.entity.JobSeeker;
import java.util.List;
import javax.persistence.Query;

/**
 * @author amayd
 *
 */
@Service
@Transactional
public class JobApplicationDaoImpl implements JobApplicationDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public JobApplication apply(int jobseekerId, int jobId, boolean resumeFlag, String resumePath) {
		JobApplication ja = new JobApplication();
		try {
			JobSeeker js = entityManager.find(JobSeeker.class, jobseekerId);
			JobPosting jp = entityManager.find(JobPosting.class, jobId);
			ja.setJobPosting(jp);
			ja.setJobSeeker(js);
			ja.setResume(resumeFlag);
			if (!resumePath.equals(null)) {
				ja.setResumePath(resumePath);
			}
			ja.setState(0);
                        ja.setShortListed(false);
			entityManager.persist(ja);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ja;
	}
        
        @Override
        public void save(JobApplication jobApplication){
            entityManager.persist(jobApplication);
        }

	@Override
	public boolean cancel(int jobAppId) {
		JobApplication ja = getJobApplication(jobAppId);
		if (ja != null) {
			entityManager.remove(ja);
		}
		return false;
	}

	public JobApplication getJobApplication(int jobAppId) {
		JobApplication ja = null;
		try {
			ja = entityManager.find(JobApplication.class, jobAppId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ja;
	}
        
        @Override
        public JobApplication getApplicationBySeekerAndId(JobSeeker seeker, JobPosting jobPosting){
        
        Query query = entityManager.createQuery("SELECT ja FROM JobApplication ja WHERE ja.jobposting = :jobposting and ja.jobSeeker = :jobSeeker");
                query.setParameter("jobposting", jobPosting);
		query.setParameter("jobSeeker", seeker);
		List<?> querylist = query.getResultList();
		return (JobApplication) querylist.get(0);
        
        }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.baeldung.spring.dao.JobApplicationDao#modifyJobApplicationStatus(int)
	 */
	@Override
	public JobApplication modifyJobApplicationStatus(int jobAppId, int state) {
		JobApplication ja = null;
		ja = getJobApplication(jobAppId);
		try {
			if (ja != null) {
				ja.setState(state);
				entityManager.merge(ja);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ja;
	}

	@Override
	public JobApplication updateApplication(JobApplication ja) {
		entityManager.merge(ja);
		return null;
	}
}
