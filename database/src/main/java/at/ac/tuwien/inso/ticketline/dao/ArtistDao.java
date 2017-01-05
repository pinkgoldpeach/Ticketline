package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Carla on 08/05/2016.
 */

/**
 * DAO for {@link at.ac.tuwien.inso.ticketline.model.Artist}
 */
@Repository
public interface ArtistDao extends JpaRepository<Artist, Integer> {

    Artist findOne(Integer id);

    /**
     * Finds all artist ordered by lastname, pagination is possible
     * @param pageable Abstract interface for pagination information.
     * @return the list of all found artists
     */
    @Query(value = "SELECT a FROM Artist a ORDER BY a.lastname ASC")
    Page<Artist> findAllPageable(Pageable pageable);


    /**
     * finds all artist by first and lastname
     * @param firstname the first name of the artist
     * @param lastname the last name of the artist
     * @return list of all found artists
     */
    @Query(value = "SELECT a FROM Artist a WHERE lower(a.firstname) LIKE lower(CONCAT('%',:firstname,'%')) AND lower(a.lastname) LIKE lower(CONCAT('%',:lastname,'%'))")
    List<Artist> findByNameLike(@Param("firstname") String firstname, @Param("lastname") String lastname);

    /**
     * finds all artist by firstname
     * @param firstname the first name of the artist
     * @return list of all found artists
     */
    @Query(value = "SELECT a FROM Artist a WHERE lower(a.firstname) LIKE lower(CONCAT('%',:firstname,'%'))")
    List<Artist> findByFirstnameLike(@Param("firstname") String firstname);

    /**
     * finds all artist by lastname
     * @param lastname the last name of the artist
     * @return list of all found artists
     */
    @Query(value = "SELECT a FROM Artist a WHERE lower(a.lastname) LIKE lower(CONCAT('%',:lastname,'%'))")
    List<Artist> findByLastnameLike(@Param("lastname") String lastname);
}
