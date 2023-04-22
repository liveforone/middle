package middle.recommendservice.repository;

public interface RecommendCustomRepository {

    Long findOneIdByUsernameForValidation(String username);
}
