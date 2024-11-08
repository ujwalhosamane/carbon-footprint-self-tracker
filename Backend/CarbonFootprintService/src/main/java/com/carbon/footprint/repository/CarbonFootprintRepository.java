package com.carbon.footprint.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.carbon.footprint.model.CarbonFootprint;

@Repository
public interface CarbonFootprintRepository extends JpaRepository<CarbonFootprint, Long> {
	
	CarbonFootprint findByCarbonFootprintId(Long carbonFootprintId);
	List<CarbonFootprint> findByUserId(String userId);
	
	void deleteByUserId(String userId);
	
	// Check if a record exists for the given userId, footprintMonth, and footprintYear
	@Query("SELECT cf FROM CarbonFootprint cf "
			+ "WHERE cf.userId = :userId AND "
			+ "cf.footprintMonth = :month AND "
			+ "cf.footprintYear = :year")
    Optional<CarbonFootprint> findByUserIdAndMonthAndYear(
    		@Param("userId") String userId, 
    		@Param("month") String month, 
    		@Param("year") int year);
	
	
	
	@Query("SELECT new com.carbon.footprint.model.CarbonFootprint(" +
	       "cf.transportation, cf.electricity, cf.lpg, " +
	       "cf.shipping, cf.airConditioner) " +
	       "FROM CarbonFootprint cf " +
	       "WHERE cf.footprintMonth = :month AND cf.footprintYear = :year")
	Optional<List<CarbonFootprint>> findSumsByMonthAndYear(
	        @Param("month") String month,
	        @Param("year") int year);

	
	
	@Query("SELECT cf FROM CarbonFootprint cf " +
		       "WHERE cf.footprintMonth = :month AND cf.footprintYear = :year")
	Optional<List<CarbonFootprint>> findAllSumsByMonthAndYear(
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
	
//	@Query("SELECT new com.carbon.footprint.model.CarbonFootprint(" +
//	       "cf.transportation, cf.electricity, cf.lpg, " +
//	       "cf.shipping, cf.airConditioner) " +
//	       "FROM CarbonFootprint cf " +
//	       "WHERE cf.footprintMonth = :month AND cf.footprintYear = :year AND cf.userId = :userId")
//	Optional<CarbonFootprint> findSumsByUserIdAndMonthAndYear(
//	        @Param("userId") String userId,
//	        @Param("month") String month,
//	        @Param("year") int year);

	
//	
//	@Query("SELECT new com.carbon.footprint.model.CarbonFootprint(" +
//	       "SUM(cf.transportation), SUM(cf.electricity), SUM(cf.lpg), " +
//	       "SUM(cf.shipping), SUM(cf.airConditioner)) " +
//	       "FROM CarbonFootprint cf " +
//	       "WHERE cf.userId = :userId")
//	Optional<CarbonFootprint> findSumsByUserId(@Param("userId") String userId);

}
