package MakeupStore.model.entities.makeup;

import MakeupStore.model.exceptions.DaoException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MakeupExtractor {

    public List<Makeup> extractData(ResultSet resultSet) throws SQLException, DaoException {
        List<Makeup> phones = new ArrayList<>();
        while (resultSet.next()) {
            Makeup phone = new Makeup();
            phone.setId(resultSet.getLong("ID"));
            phone.setBrand(resultSet.getString("BRAND"));
            phone.setPrice(resultSet.getBigDecimal("PRICE"));
            phone.setWeightGr(resultSet.getInt("WEIGHTGR"));
            phone.setName(resultSet.getString("NAME"));
            phone.setCathegory(resultSet.getString("CATHEGORY"));
            phone.setImageUrl(resultSet.getString("IMAGEURL"));
            phone.setDescription(resultSet.getString("DESCRIPTION"));
            phones.add(phone);
        }
System.out.println(phones.toString());
        return phones;
    }
}
