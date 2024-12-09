package com.carbon.footprint.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.carbon.footprint.dto.CarbonFootprintDTO;
import com.carbon.footprint.model.CarbonFootprint;

import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;

@Repository
public interface CarbonFootprintRepository extends JpaRepository<CarbonFootprint, Long> {
	
	CarbonFootprint findByCarbonFootprintId(Long carbonFootprintId);
	void deleteByUserId(String userId);
	
	@Query("SELECT new com.carbon.footprint.dto.CarbonFootprintDTO(" +
		       "cf.footprintMonth, cf.footprintYear, cf.transportation, " +
		       "cf.electricity, cf.lpg, cf.shipping, cf.airConditioner) " +
		       "FROM CarbonFootprint cf")
	List<CarbonFootprintDTO> findAllAsDTO();
	
	@Query("SELECT new com.carbon.footprint.dto.CarbonFootprintDTO(" +
		       "cf.footprintMonth, cf.footprintYear, cf.transportation, " +
		       "cf.electricity, cf.lpg, cf.shipping, cf.airConditioner) " +
		       "FROM CarbonFootprint cf " +
		       "WHERE cf.userId = :userId")
	List<CarbonFootprintDTO> findByUserId(@Param("userId") String userId);
	
	// Check if a record exists for the given userId, footprintMonth, and footprintYear
	@Query("SELECT cf FROM CarbonFootprint cf "
			+ "WHERE cf.userId = :userId AND "
			+ "cf.footprintMonth = :month AND "
			+ "cf.footprintYear = :year")
    Optional<CarbonFootprint> findByUserIdAndMonthAndYear(
    		@Param("userId") String userId, 
    		@Param("month") String month, 
    		@Param("year") int year);

	// Return CarbonFootprintDTOs for sums by month and year
	@Query("SELECT new com.carbon.footprint.dto.CarbonFootprintDTO(" +
	       "cf.transportation, cf.electricity, cf.lpg, " +
	       "cf.shipping, cf.airConditioner) " +
	       "FROM CarbonFootprint cf " +
	       "WHERE cf.footprintMonth = :month AND cf.footprintYear = :year")
	Optional<List<CarbonFootprintDTO>> findSumsByMonthAndYear(
	        @Param("month") String month,
	        @Param("year") int year);

	// Return all CarbonFootprintDTOs for a specific month and year
	@Query("SELECT new com.carbon.footprint.dto.CarbonFootprintDTO(" +
	       "cf.footprintMonth, cf.footprintYear, cf.transportation, " +
	       "cf.electricity, cf.lpg, cf.shipping, cf.airConditioner) " +
	       "FROM CarbonFootprint cf " +
	       "WHERE cf.footprintMonth = :month AND cf.footprintYear = :year")
	Optional<List<CarbonFootprintDTO>> findAllSumsByMonthAndYear(
	        @Param("month") String month,
	        @Param("year") int year);
	
	@Query(value = """
	        SELECT COUNT(*) 
	        FROM carbon_footprint 
	        WHERE user_id = :userId 
	          AND (footprint_year * 12 + CASE 
	                                        WHEN footprint_month = 'January' THEN 1
	                                        WHEN footprint_month = 'February' THEN 2
	                                        WHEN footprint_month = 'March' THEN 3
	                                        WHEN footprint_month = 'April' THEN 4
	                                        WHEN footprint_month = 'May' THEN 5
	                                        WHEN footprint_month = 'June' THEN 6
	                                        WHEN footprint_month = 'July' THEN 7
	                                        WHEN footprint_month = 'August' THEN 8
	                                        WHEN footprint_month = 'September' THEN 9
	                                        WHEN footprint_month = 'October' THEN 10
	                                        WHEN footprint_month = 'November' THEN 11
	                                        WHEN footprint_month = 'December' THEN 12
	                                    END) 
	              BETWEEN (:year * 12 + :month - 5) AND (:year * 12 + :month)
	        """, nativeQuery = true)
	    int countFootprintsForLast6Months(@Param("userId") String userId, 
	                                      @Param("year") int year, 
	                                      @Param("month") int month);
	
	@Query("SELECT c FROM CarbonFootprint c WHERE c.userId = :userId " +
		       "ORDER BY c.footprintYear DESC, " +
		       "FIELD(c.footprintMonth, 'January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December') DESC")
	Optional<CarbonFootprint> findLatestFootprintByUser(@Param("userId") String userId);
	
	@Query("SELECT c.creationDate AS creationDate, c.updatedDate AS updatedDate, c.footprintMonth AS footprintMonth, c.footprintYear AS footprintYear " +
		       "FROM CarbonFootprint c " +
		       "WHERE c.userId = :userId " +
		       "ORDER BY c.updatedDate DESC, c.creationDate DESC")
	List<Tuple> findLatestActivityByUserId(@Param("userId") String userId);
	
	@Query("SELECT CONCAT(c.footprintMonth, '-', c.footprintYear) AS monthYear, " +
		       "AVG(c.transportation + c.electricity + c.lpg + c.shipping + c.airConditioner) AS totalFootprint " +
		       "FROM CarbonFootprint c " +
		       "WHERE c.userId IN (" +
		       "   SELECT cf.userId " +
		       "   FROM CarbonFootprint cf " +
		       "   WHERE (cf.footprintMonth = :currentMonth AND cf.footprintYear = :currentYear) " +
		       "      OR (cf.footprintMonth = :previousMonth AND cf.footprintYear = :previousYear) " +
		       "   GROUP BY cf.userId " +
		       "   HAVING COUNT(DISTINCT CONCAT(cf.footprintMonth, '-', cf.footprintYear)) = 2" +
		       ") " +
		       "AND ((c.footprintMonth = :currentMonth AND c.footprintYear = :currentYear) " +
		       "     OR (c.footprintMonth = :previousMonth AND c.footprintYear = :previousYear)) " +
		       "GROUP BY c.footprintMonth, c.footprintYear")
		List<Object[]> getTotalFootprintForValidUsers(
		        @Param("currentMonth") String currentMonth,
		        @Param("currentYear") int currentYear,
		        @Param("previousMonth") String previousMonth,
		        @Param("previousYear") int previousYear);

	 
	 
	 @Modifying
     @Transactional
     @Query("DELETE FROM CarbonFootprint cf WHERE cf.footprintYear < :year OR (cf.footprintYear = :year AND cf.footprintMonth < :month)")
     void deleteByFootprintYearBeforeOrFootprintYearAndFootprintMonthBefore(@Param("year") int year, @Param("month") String month);
}
