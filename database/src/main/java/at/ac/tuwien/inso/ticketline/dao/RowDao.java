package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Row;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Carla on 15/05/2016.
 */
/**
 * DAO for {@link at.ac.tuwien.inso.ticketline.model.Row}
 */
@Repository
public interface RowDao extends JpaRepository<Row, Integer>, RowDaoCustom {

    /**
     * Finds all rows ordered by name, pagination is possible
     * @param pageable Abstract interface for pagination information.
     * @return the list of all found rows
     */
    @Query(value = "SELECT r FROM Row r ORDER BY r.name ASC")
    Page<Row> findAllPageable(Pageable pageable);



}
