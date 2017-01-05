package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * DAO for {@link at.ac.tuwien.inso.ticketline.model.News}
 */
@Repository
public interface NewsDao extends JpaRepository<News, Integer> {

    /**
     * Finds all news ordered by the submission date
     * @return the list of news
     */
    @Query(value = "SELECT n FROM News n ORDER BY n.submittedOn DESC")
    List<News> findAllOrderBySubmittedOnAsc();

    /**
     * finds all news created after a specific date
     * @param date the news must have been crated after this date
     * @return a list of news that were created after that date
     */
    @Query(value = "SELECT n FROM News n WHERE n.submittedOn >= :date ORDER BY n.submittedOn DESC")
    List<News> findByDate(@Param("date") Date date);

}
