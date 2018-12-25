package com.bedtime.stories.repository;


import com.bedtime.stories.helper.DateHelper;
import com.bedtime.stories.model.Tale;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class TaleRepositoryImpl {

    private PreparedStatement createPreparedStatement(Connection conn, String SQLquery) throws SQLException {
        PreparedStatement pstmt = null;
        pstmt = conn.prepareStatement(SQLquery);
        return pstmt;
    }


    private int saveRatingToDatabase(LocalDate date, int nrRating, float newRating) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String SQL = "update TaleRating set rating=?,nr_rating=? where date_added=?";
            PreparedStatement pstmt = createPreparedStatement(conn, SQL);
            pstmt.setFloat(1, newRating);
            pstmt.setInt(2, nrRating);
            pstmt.setObject(3, date);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated == 1) {
                return 0;
            }
        } catch (URISyntaxException | SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }








    public List<Tale> getTopTales(int limit) {
        List<Tale> resultList = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection()) {
            String SQL = "Select date_added from TaleRating order by rating DESC LIMIT ?";
            PreparedStatement pstmt = createPreparedStatement(conn, SQL);
            pstmt.setInt(1, limit);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    LocalDate dateAdded = rs.getDate("date_added").toLocalDate();
                    Tale tale = getTaleByDate(dateAdded);
                    if (tale != null) {
                        tale.setDateAdded(DateHelper.parseLocalDateIntoCorrectFormat(dateAdded));
                        resultList.add(tale);
                    }
                }
                return resultList;
            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Tale getTaleByDate(LocalDate date) {

        Tale tale = null;
        String correctDateString = DateHelper.parseLocalDateIntoCorrectFormat(date);
        try {
            ClassLoader cl = this.getClass().getClassLoader();
            InputStream inputStream = cl.getResourceAsStream("static/tales/tales.json");
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Tale> jsonMap = objectMapper.readValue(inputStream,
                    new TypeReference<Map<String, Tale>>() {
                    });
            tale = jsonMap.get(correctDateString);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tale;
    }


}
