package at.ac.tuwien.inso.ticketline.server.service.implementation;

import at.ac.tuwien.inso.ticketline.dao.EmployeeDao;
import at.ac.tuwien.inso.ticketline.dao.NewsDao;
import at.ac.tuwien.inso.ticketline.dto.UserStatusDto;
import at.ac.tuwien.inso.ticketline.model.Employee;
import at.ac.tuwien.inso.ticketline.model.News;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.security.AuthUtil;
import at.ac.tuwien.inso.ticketline.server.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Implementation of the {@link at.ac.tuwien.inso.ticketline.server.service.NewsService} interface
 */
@Service
public class SimpleNewsService implements NewsService {

    @Autowired
    private NewsDao newsDao;

    @Autowired
    private EmployeeDao employeeDao;

	/**
	 * {@inheritDoc}
	 */
    @Override
    public News getNews(Integer id) throws ServiceException {
        try {
            return newsDao.findOne(id);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

	/**
	 * {@inheritDoc}
	 */
    @Override
    public News save(News news) throws ServiceException {
        try {
            news.setSubmittedOn(new Date());
            return newsDao.save(news);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

	/**
	 * {@inheritDoc}
	 */
    @Override
    public List<News> getAllNews() throws ServiceException {
        try {
            return newsDao.findAllOrderBySubmittedOnAsc();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public List<News> getSpecificNews() throws ServiceException {
        UserStatusDto userStatusDto = AuthUtil.getUserStatusDto(SecurityContextHolder.getContext().getAuthentication());

        try {
            Employee employee = employeeDao.findByUsername(userStatusDto.getUsername()).get(0);
            List<News> newsList = newsDao.findByDate(employee.getLastLogin());
            employee.setLastLogin(new Date());
            employeeDao.save(employee);
            return newsList;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    // -------------------- For Testing purposes --------------------

    /**
     * Sets the news dao.
     *
     * @param dao the new news dao
     */
    public void setNewsDao(NewsDao dao) {
        this.newsDao = dao;
    }

    // -------------------- For Testing purposes --------------------
}
