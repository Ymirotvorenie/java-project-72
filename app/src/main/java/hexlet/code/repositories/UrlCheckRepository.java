package hexlet.code.repositories;

import hexlet.code.model.UrlCheck;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


public class UrlCheckRepository extends BaseRepository {

    public static List<UrlCheck> getUrlChecks(Long urlId) throws SQLException {
        var sql = "SELECT * FROM url_checks WHERE url_id = ?";
        try (var connection = dataSource.getConnection();
            var statement = connection.prepareStatement(sql)) {
            statement.setLong(1, urlId);
            var resultSet = statement.executeQuery();
            var result = new ArrayList<UrlCheck>();
            while (resultSet.next()) {
                result.add(getEntity(resultSet));
            }
            result.sort(Comparator.comparing(UrlCheck::getId));
            return result;
        }
    }

    public static Optional<UrlCheck> getUrlLastCheck(Long id) {
        var sql = "SELECT * FROM url_checks WHERE url_id = ? ORDER BY created_at DESC LIMIT 1";
        try (var connection = dataSource.getConnection();
            var statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getEntity(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    public static void save(UrlCheck check) throws SQLException {
        var sql = "INSERT INTO url_checks(url_id, status_code, h1, title, description, created_at) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (var connection = dataSource.getConnection();
            var preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, check.getUrlId());
            preparedStatement.setInt(2, check.getStatusCode());
            preparedStatement.setString(3, check.getH1());
            preparedStatement.setString(4, check.getTitle());
            preparedStatement.setString(5, check.getDescription());
            preparedStatement.setTimestamp(6, check.getCreatedAt());
            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                check.setId(generatedKeys.getLong("id"));
            } else {
                throw new SQLException("DB have not returned an id after saving an entity");
            }
        }
    }

    private static UrlCheck getEntity(ResultSet resultSet) throws SQLException {
        var id = resultSet.getLong("id");
        var statusCode = resultSet.getInt("status_code");
        var title = resultSet.getString("title");
        var h1 = resultSet.getString("h1");
        var description = resultSet.getString("description");
        var urlId = resultSet.getLong("url_id");
        var createdAt = resultSet.getTimestamp("created_at");
        return new UrlCheck(id, statusCode, title, h1, description, urlId, createdAt);
    }
}
